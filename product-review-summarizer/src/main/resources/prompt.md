# Prompts for various tasks

## Prompt for mock data generation
````
You are responsible for generating mock data for following products in following Products XML Tags: 

- The mock data is for reviews
- reviews-to-generate is the number of reviews to be generated.
- Each product has a desired rating. This should be overall rating of the product. Make sure that each individual review sum up to this number.
- Each product has age group range (= age-min and age-max). Using this range, reviews for a product should be generated with the manner and style for an age.
- A product review can contain information on Shipping, Customer Care, Payment, Quality. The amount of the review containing this information is inside <{shipping | customer-care | quality | payment}-count> tag
-
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

````
