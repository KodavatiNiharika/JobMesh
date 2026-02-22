from sentence_transformers import SentenceTransformer, util
 
model = SentenceTransformer("all-MiniLM-L6-v2")


def calculate_semantic_score(jd_phrases, resume_phrases, threshold=0.75):

    if not jd_phrases or not resume_phrases:
        return 0, []

    jd_list = list(jd_phrases)
    resume_list = list(resume_phrases)

    jd_embeddings = model.encode(jd_list)
    resume_embeddings = model.encode(resume_list)

    semantic_matches = 0
    matched_phrases = []

    for i, jd_emb in enumerate(jd_embeddings):
        cosine_scores = util.cos_sim(jd_emb, resume_embeddings)
        max_score = cosine_scores.max().item()

        if max_score > threshold:
            semantic_matches += 1
            matched_phrases.append(jd_list[i])

    semantic_score = semantic_matches / len(jd_list)

    return semantic_score, matched_phrases

"""
This function computes semantic similarity between job description phrases and resume phrases using sentence embeddings.
 It converts phrases into dense vectors using a transformer model and 
 measures cosine similarity to calculate a normalized semantic match score.

 Improvement : Generate similarity matrix for JD once and use it every time for large-scale systems
"""