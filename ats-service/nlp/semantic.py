from sentence_transformers import SentenceTransformer, util
import re

model = SentenceTransformer("all-MiniLM-L6-v2")



def split_sentences(text):
    return [s.strip() for s in re.split(r'[.!?]', text) if s.strip()]


def calculate_semantic_score(resume_text, jd_text, threshold=0.50):

    jd_list = split_sentences(jd_text)
    resume_list = split_sentences(resume_text)

    if not jd_list or not resume_list:
        return 0, []

    jd_embeddings = model.encode(jd_list)
    resume_embeddings = model.encode(resume_list)

    semantic_matches = 0
    matched_details = []

    used_resume_indices = set()  

    for i, jd_emb in enumerate(jd_embeddings):

        cosine_scores = util.cos_sim(jd_emb, resume_embeddings)[0]

        max_score = cosine_scores.max().item()
        max_index = cosine_scores.argmax().item()

        if max_score > threshold and max_index not in used_resume_indices:
            semantic_matches += 1
            used_resume_indices.add(max_index)

            match_info = {
                "jd_sentence": jd_list[i],
                "matched_resume_sentence": resume_list[max_index],
                "score": round(max_score, 3)
            }

            matched_details.append(match_info)
    semantic_score = semantic_matches / len(jd_list)
    print("FINAL SCORE:", semantic_score)

    print("\nMATCHED PAIRS:")
    for m in matched_details:
        print("\nJD:", m["jd_sentence"])
        print("Resume:", m["matched_resume_sentence"])
        print("Score:", m["score"])
    return semantic_score, matched_details



