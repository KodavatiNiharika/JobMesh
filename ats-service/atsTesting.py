from keyword import extract_keywords

# Sample Job Description text
test_text = """
Looking for a Java developer with Spring Boot and REST API experience.
Must know MySQL and Microservices architecture.
"""

keywords = extract_keywords(test_text)

print("Extracted Keywords:\n")
for word in keywords:
    print(word)
