from fastapi import FastAPI
from schema import QueryRequest, QueryResponse
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
    results = vector_store.search(query_embedding, top_k=1)

    best_match = results[0]

    return QueryResponse(
        incidentId=best_match["incidentId"],
        service=best_match["service"],
        severity=best_match["severity"],
        description=best_match["description"],
        rootCause=best_match["rootCause"],
        resolution=best_match["resolution"],
        prevention=best_match["prevention"]
    )
