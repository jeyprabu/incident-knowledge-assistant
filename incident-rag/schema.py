from pydantic import BaseModel
from typing import Optional, List
class IndexRequest(BaseModel):
    document_id: str
    text: str

class QueryRequest(BaseModel):
    query: str
    service: Optional[str] = None
    severity: Optional[str] = None
    incidentType: Optional[str] = None
    
    
class IncidentResult(BaseModel):
    incidentId: str
    service: str
    severity: str
    description: str
    rootCause: str
    resolution: str
    prevention: str


class QueryResponse(BaseModel):
    results: List[IncidentResult]

