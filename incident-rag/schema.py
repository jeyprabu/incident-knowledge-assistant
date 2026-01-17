from pydantic import BaseModel

class IndexRequest(BaseModel):
    document_id: str
    text: str

class QueryRequest(BaseModel):
    query: str

class QueryResponse(BaseModel):
    incidentId: str
    service: str
    severity: str
    description: str
    rootCause: str
    resolution: str
    prevention: str

