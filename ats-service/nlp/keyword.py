import spacy

nlp = spacy.load("en_core_web_sm")

def extract_keywords(text):
    doc = nlp(text)
    keywords = set()

    for token in doc:
        if token.pos_ in ["NOUN", "PROPN"]:
            if not token.is_stop:
                keywords.add(token.lemma_.lower())

    return keywords

"""spacy has parts of speech tagging
This function extracts important nouns and proper nouns from text using spaCy's POS tagging,
 removes stopwords, applies lemmatization,
and returns a unique set of normalized keywords for use in resume matching or NLP tasks."""