import json
import os

from dotenv import load_dotenv
from openai import OpenAI
from openai._utils import flatten

from anichart_api_caller import AniChartApiCaller

env_path = os.path.join(os.path.dirname(__file__), "..", ".env")
load_dotenv(env_path)
client = OpenAI()

anime_list = AniChartApiCaller().get_animes()

system_prompt = f"""
You are an expert in recommending Anime to users.
Using genres and a description they provide, you go through the provided json list and recommend them 2 animes only.
The ranking should be based on the meanScore.
The json list:
{json.dumps(anime_list)}
"""

genres = {genre for anime in anime_list for genre in anime.get("genres", [])}
formatted_genres = "\n".join(f"* {genre}" for genre in genres)

user_genre_prompt = input(f"""
To get some AI powered recommendations, you can pick few genres: 
Available genres: 
{formatted_genres}
"""
)

user_description_prompt = input(f"""
What are you looking for? Write a description.
""")

user_prompt = "User genres: " + user_genre_prompt + " Description: " + user_description_prompt

messages = [
    {"role": "system", "content": system_prompt},
    {"role": "user", "content": user_prompt},
]

response = client.chat.completions.create(model="gpt-4.1-nano", messages=messages)

print(response.choices[0].message.content)
