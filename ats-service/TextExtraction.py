import fitz 
from docx import Document


def extract_text_from_pdf(pdf_file):
    text = ""
    doc = fitz.open(stream=pdf_file.read(), filetype="pdf")
    for page in doc:
        text += page.get_text()
    return text

def extract_text_from_docx(docx_file):
    doc = Document(docx_file)
    text = "\n".join([para.text for para in doc.paragraphs])
    return text


def extract_resume_text(file, file_type="pdf"):
    if file_type.lower() == "pdf":
        return extract_text_from_pdf(file)
    elif file_type.lower() == "docx":
        return extract_text_from_docx(file)
    else:
        raise ValueError("Unsupported file type. Use 'pdf' or 'docx'")


def extract_jd_text(jd_file=None, jd_string=None, file_type="txt"):
    
    if jd_string:
        return jd_string

    if not jd_file:
        raise ValueError("Provide either jd_file or jd_string")

    if file_type.lower() == "txt":
        with open(jd_file, "r", encoding="utf-8") as f:
            return f.read()
    elif file_type.lower() == "pdf":
        with open(jd_file, "rb") as f:
            return extract_text_from_pdf(f)
    elif file_type.lower() == "docx":
        with open(jd_file, "rb") as f:
            return extract_text_from_docx(f)
    else:
        raise ValueError("Unsupported file type. Use txt/pdf/docx")
if __name__ == "__main__":
    import sys
    file_path = sys.argv[1]        
    file_type = sys.argv[2]        

    # Extract text
    if file_type.lower() == "pdf":
        text = extract_text_from_pdf(open(file_path, "rb"))
    elif file_type.lower() == "docx":
        text = extract_text_from_docx(open(file_path, "rb"))
    else:
        raise ValueError("Unsupported file type")

 
    print(text, flush=True)

