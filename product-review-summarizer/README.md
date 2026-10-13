# Product Review Summarizer

## Concept
Fetching the reviews from an e-commerce website and summarizing them for 
- Customers, who are interested in the product. Each review category is separately summerized. 
- Different product teams like shipping department, customer service so they can improve.

## Reviews
Reviews are synthetic created using LLMs. [Prompt](src/main/resources/prompt.md)  
The LLM takes care of review generation. Everything which deterministic in nature is done manually.

## External APIs
For summerizing OPENAI API will be used.

## Caching
For cost saving purposes, caching would be used.
