from fastapi import FastAPI
from schema import QueryRequest, QueryResponse, IncidentResult
from embedding import EmbeddingModel
from vector_store import VectorStore
import json

app = FastAPI(title="Incident RAG Service")

embedder = EmbeddingModel()
vector_store = VectorStore(dimension=384)


@app.on_event("startup")
def load_incidents():
    documents = []
    texts = []

    with open("data/incident_dataset.jsonl") as f:
        for line in f:
            incident = json.loads(line)

            text = f"""
            Incident ID: {incident['incident_id']}
            Service: {incident['service']}
            Severity: {incident['severity']}
            Description: {incident['description']}
            Root Cause: {incident['root_cause']}
            Resolution: {incident['resolution']}
            Prevention: {incident['prevention']}
            """

            documents.append({
                "incidentId": incident["incident_id"],
                "service": incident["service"],
                "severity": incident["severity"],
                "incidentType": incident["incident_type"],
                "description": incident["description"],
                "rootCause": incident["root_cause"],
                "resolution": incident["resolution"],
                "prevention": incident["prevention"],
                "content": text
            })

            texts.append(text)

    embeddings = embedder.embed(texts)
    vector_store.add(embeddings, documents)

    print(f"Indexed {len(documents)} incidents")


@app.post("/rag/query", response_model=QueryResponse)
def query_incidents(request: QueryRequest):
    query_embedding = embedder.embed([request.query])
    results = vector_store.search(
        query_embedding,
        top_k=10
    )
    best_match = results[0]
    
    filtered = []

    for incident in results:
        if request.service:
            if incident["service"] != request.service:
                continue    
        if request.severity:
            if incident["severity"] != request.severity:
                continue
        if request.incidentType:
            if incident["incidentType"] != request.incidentType:
                continue
        filtered.append(incident)
        
    matches = []

    for incident in filtered[:5]:

        matches.append(
            IncidentResult(
                incidentId=incident["incidentId"],
                service=incident["service"],
                severity=incident["severity"],
                description=incident["description"],
                rootCause=incident["rootCause"],
                resolution=incident["resolution"],
                prevention=incident["prevention"]
            )
        )

    return QueryResponse(
        results=matches
    )
    
