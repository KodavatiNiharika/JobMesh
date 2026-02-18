from ats_scoring import extract_resume_text, extract_jd_text

# Resume
with open("resume.pdf", "rb") as f:
    resume_text = extract_resume_text(f, file_type="pdf")

print("Resume Text (first 500 chars):")
print(resume_text)

# Job Description
jd_text = """
Looking for a React frontend developer with experience in Spring Boot, AWS, 
and computer networks. Knowledge of Docker and Kubernetes is a plus.
"""

print("JD Text:")
print(jd_text)
