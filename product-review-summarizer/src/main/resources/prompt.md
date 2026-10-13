# Prompts for various tasks

## Prompt for mock data generation
````
You are responsible for generating mock data for following products in following Products XML Tags: 

- The mock data is for reviews
- reviews-to-generate is the number of reviews to be generated.
- Each product has a desired rating. This should be overall rating of the product. Make sure that each individual review sum up to this number.
- Each product has age group range (= age-min and age-max). Using this range, reviews for a product should be generated with the manner and style for an age.
- A product review can contain information on Shipping, Customer Care, Payment, Quality. The amount of the review containing this information is inside <{shipping | customer-care | quality | payment}-count> tag
- Checkout provided JSON example to see how the reviews should be structured

<Products>
	<item>
		<name>A Fantasy Book</name>
		<age-group>
			<age-min>15</age-min>
			<age-max>60</age-max>
		</age-group>
    <desired-rating>4</desired-rating>
    <reviews-to-generate>14</reviews-to-generate>
    <shipping-reviews-count>2</shipping-reviews-count>
    <customer-care-reviews-count>1</customer-care-reviews-count>
    <quality-reviews-count>1</quality-reviews-count>
	</item>
	<item>
		<name>Super Duper Mixer</name>
		<age-group>
			<age-min>18</age-min>
			<age-max>50</age-max>
		</age-group>
    <desired-rating>2</desired-rating>
    <reviews-to-generate>22</reviews-to-generate>
    <shipping-reviews-count>2</shipping-reviews-count>
    <customer-care-reviews-count>2</customer-care-reviews-count>
    <quality-reviews-count>13</quality-reviews-count>
    <payment-reviews-count>5</payment-reviews-count>
	</item>
	<item>
		<name>70s Tape Recorder</name>
		<age-group>
			<age-min>45</age-min>
			<age-max>85</age-max>
		</age-group>
    <desired-rating>3</desired-rating>
    <reviews-to-generate>55</reviews-to-generate>
    <shipping-reviews-count>20</shipping-reviews-count>
    <customer-care-reviews-count>6</customer-care-reviews-count>
    <quality-reviews-count>23</quality-reviews-count>
    <payment-reviews-count>6</payment-reviews-count>
	</item>
	<item>
		<name>GS5</name>
		<age-group>
			<age-min>15</age-min>
			<age-max>30</age-max>
		</age-group>
    <desired-rating>5</desired-rating>
    <reviews-to-generate>79</reviews-to-generate>
    <customer-care-reviews-count>23</customer-care-reviews-count>
    <quality-reviews-count>42</quality-reviews-count>
    <payment-reviews-count>14</payment-reviews-count>
	</item>
	<item>
		<name>Vacuum Cleaner 4503</name>
		<age-group>
			<age-min>25</age-min>
			<age-max>55</age-max>
		</age-group>
    <desired-rating>1</desired-rating>
    <reviews-to-generate>56</reviews-to-generate>
    <shipping-reviews-count>24</shipping-reviews-count>
    <quality-reviews-count>32</quality-reviews-count>
	</item>
</Products>

{
  "product_id": 1,
  "product_name": "Narnia",
  "total_stars": 4,
  "reviews": [
    {
      "review_title": "A very great book",
      "review_text": "The book was very fun to read. It awakened feelings that are hard to describe. I had some issues with the payment, but it was quickly solved by a good customer care agent.",
      "review_stars": 5
    },
    {
      "review_title": "Such an amazing book",
      "review_text": "The author has done such a great job expressing herself",
      "review_stars": 5
    },
    {
      "review_title": "Just missing that one bit",
      "review_text": "Enjoyed it quite a well but something is missing. Also, the delivery was kinda late. Yet, I still enjoyed waiting for it",
      "review_stars": 4
    },
    {
      "review_title": "Wrong order",
      "review_text": "My kid ordered it without me noticing it. I thought I had to pay for it. But the customer support was nice and they gave me my money back and I got keep the book. The kiddo is happy",
      "review_stars": 4
    }
  ]
}

{
  "product_id": 2,
  "product_name": "Super Duper Mixer",
  "total_stars": 2,
  "reviews": [
    {
      "review_title": "Horrible mixer",
      "review_text": "Horrible mixer. The build quality was so bad that I had to return it asap. The return policy is even worse. They did their best to not give me a refund.",
      "review_stars": 1
    },
    {
      "review_title": "Never ever again",
      "review_text": "I think I made a mistake buying this mixer. While it does its job, the power isn't enough",
      "review_stars": 2
    },
    {
      "review_title": "It is what is",
      "review_text": "Does for what the price is. Plastic quality, poor power, but from price perspective you can't expect much.",
      "review_stars": 3
    },
    {
      "review_title": "Meh",
      "review_text": "Besides fast delivery, there isn't much to say here. The quality is bad, the customer support is bad, only shipping is good.",
      "review_stars": 2
    }
  ]
}

{
  "product_id": 3,
  "product_name": "70's Tape Recorder",
  "total_stars": 3,
  "reviews": [
    {
      "review_title": "Back to good old days",
      "review_text": "Had a blast listening on this tape recorder. Good old days",
      "review_stars": 3
    },
    {
      "review_title": "Rough start",
      "review_text": "Delivery got delayed due to unknown reason. But when it arrived, I had a blast. Finally able to listen to my old tapes. Memory lanes",
      "review_stars": 3
    },
    {
      "review_title": "A headphone jack is missing",
      "review_text": "While advertised as a 70's tape recorder, it doesn't have a headphone jack. You can connect to bluetooth but that just doesn't give that 70s vibe",
      "review_stars": 3
    },
    {
      "review_title": "Questionable quality",
      "review_text": "I feel like the quality could have been better. It feels like a cheap plastic. But it could be me. Besides that the delivery was great. Right on the time.",
      "review_stars": 3
    }
  ]
}
````
