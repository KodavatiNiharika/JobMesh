from nlp.preprocessing import clean_text
from nlp.keyword import extract_keywords
from nlp.phrase import extract_phrases
from nlp.semantic import calculate_semantic_score


 
EXACT_WEIGHT = 0.5
PHRASE_WEIGHT = 0.2
SEMANTIC_WEIGHT = 0.3


def calculate_ats_score(resume_text, jd_text):

    # Cleaning 
    resume_clean = clean_text(resume_text)
    jd_clean = clean_text(jd_text)

    #  Keyword Extraction 
    resume_keywords = extract_keywords(resume_clean)
    jd_keywords = extract_keywords(jd_clean)

    exact_matches = jd_keywords.intersection(resume_keywords)
    missing_keywords = jd_keywords - resume_keywords

    exact_score = (
        len(exact_matches) / len(jd_keywords)
        if jd_keywords else 0
    )

    #  Phrase Extraction 
    resume_phrases = extract_phrases(resume_clean)
    jd_phrases = extract_phrases(jd_clean)

    phrase_matches = jd_phrases.intersection(resume_phrases)

    phrase_score = (
        len(phrase_matches) / len(jd_phrases)
        if jd_phrases else 0
    )

    # Semantic Similarity 
    semantic_score, semantic_matches = calculate_semantic_score(
        jd_phrases,
        resume_phrases
    )

    #  Final Weighted Score 
    final_score = (
        exact_score * EXACT_WEIGHT +
        phrase_score * PHRASE_WEIGHT +
        semantic_score * SEMANTIC_WEIGHT
    ) * 100

    return {
        "exact_score": round(exact_score * 100, 2),
        "phrase_score": round(phrase_score * 100, 2),
        "semantic_score": round(semantic_score * 100, 2),
        "final_score": round(final_score, 2),
        "matched_keywords": list(exact_matches),
        "missing_keywords": list(missing_keywords),
        "matched_phrases": list(phrase_matches),
        "semantic_matched_phrases": semantic_matches
    }
