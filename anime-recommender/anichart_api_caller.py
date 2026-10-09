import json
import requests


class AniChartApiCaller:
    url = "https://graphql.anilist.co"
    query = """
    query ($season: MediaSeason, $seasonYear: Int, $page: Int, $perPage: Int) {
      Page(page: $page, perPage: $perPage) {
        media(season: $season, seasonYear: $seasonYear, type: ANIME, format: TV, sort: POPULARITY_DESC) {
          title {
            english
          }
          meanScore
          genres
          description
        }
      }
    }
    """
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
        "Content-Type": "application/json",
        "Accept": "application/json",
    }

    def __init__(self, season="WINTER", season_year=2026, page=1, per_page=10):
        self.variables = {
            "season": season,
            "seasonYear": season_year,
            "page": page,
            "perPage": per_page,
        }

    def get_animes(self):
        response = requests.post(
            self.url,
            json={"query": self.query, "variables": self.variables},
            headers=self.headers,
            timeout=10,
        )
        response.raise_for_status()

        payload = response.json()
        if payload.get("errors"):
            raise RuntimeError(payload["errors"])

        media = payload["data"]["Page"]["media"]
        anime_list = []
        for anime in media:
            title = anime["title"]
            anime_list.append(
                {
                    "title": title.get("english"),
                    "meanScore": anime.get("meanScore"),
                    "genres": anime.get("genres"),
                    "description": anime.get("description"),
                }
            )
        return anime_list


if __name__ == "__main__":
    try:
        animes = AniChartApiCaller().get_animes()
        print(json.dumps(animes, indent=2))
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err}")
        print(f"Server Response Content:\n{http_err.response.text[:500]}")
    except json.decoder.JSONDecodeError:
        print("Failed to decode JSON. The server sent back non-JSON content.")
    except Exception as err:
        print(f"An unexpected error occurred: {err}")
