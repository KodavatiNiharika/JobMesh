import spacy
import re
 
nlp = spacy.load("en_core_web_sm")


def clean_text(text: str) -> str: 

    if not text:
        return ""
 
    text = text.lower()
 
    text = re.sub(r"[^a-zA-Z0-9+#.\s]", " ", text)
 
    text = re.sub(r"\s+", " ", text).strip()

    doc = nlp(text)

    tokens = []

    for token in doc: 
        if token.is_stop or token.is_punct:
            continue

        lemma = token.lemma_.strip()

        if lemma:
            tokens.append(lemma)

    return " ".join(tokens)


"""
This function preprocesses resume text by cleaning special characters,
 removing stopwords,
   performing lemmatization(developing changes to develop, kinda generalized) using spaCy,
     and returning a normalized version of the text suitable for ATS keyword matching
       or NLP-based similarity scoring.
    
    Preserve technical terms like C++, C#, Node.js
"""
