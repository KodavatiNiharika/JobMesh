import spacy

nlp = spacy.load("en_core_web_sm")


def extract_phrases(text):
    doc = nlp(text)
    phrases = set()
 
    for chunk in doc.noun_chunks:
        cleaned_chunk = chunk.text.lower().strip()
        if len(cleaned_chunk) > 2:
            phrases.add(cleaned_chunk)
 
    tokens = [
        token.lemma_.lower()
        for token in doc
        if not token.is_stop and not token.is_punct
    ]

    for i in range(len(tokens) - 1):
        bigram = tokens[i] + " " + tokens[i + 1]
        phrases.add(bigram)

    return phrases

"""
This function extracts meaningful noun phrases using spaCy's noun chunk parser
 and also generates bigram combinations from cleaned tokens
   to capture multi-word technical skills for better resume-job matching.
"""
