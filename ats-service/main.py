from fastapi import FastAPI
from pydantic import BaseModel
from scoring import calculate_ats_score

app = FastAPI(
    title="ATS Scoring Microservice",
    description="Hybrid ATS scoring using Exact, Phrase and Semantic matching",
    version="1.0.0"
)


# ----------------------------
# Request Model
# ----------------------------
class ATSRequest(BaseModel):
    resume_text: str
    job_description: str


# ----------------------------
# Health Check (important)
# ----------------------------
@app.get("/")
def health_check():
    return {"status": "ATS Service Running"}


# ----------------------------
# ATS Score Endpoint
# ----------------------------
@app.post("/ats-score")
def get_ats_score(request: ATSRequest):
    result = calculate_ats_score(
        request.resume_text,
        request.job_description
    )
    return result
