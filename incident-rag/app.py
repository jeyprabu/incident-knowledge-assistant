from fastapi import FastAPI
from schema import (
    QueryRequest,
    QueryResponse,
    IncidentResult
)

from embedding import EmbeddingModel
from vector_store import VectorStore

import psycopg2

app = FastAPI(
    title="Incident RAG Service"
)

embedder = EmbeddingModel()
vector_store = VectorStore(
    dimension=384
)


@app.on_event("startup")
def load_incidents():

    conn = psycopg2.connect(
        host="localhost",
        database="incidentdb",
        user="postgres",
        password="root"
    )

    cursor = conn.cursor()

    cursor.execute("""
        SELECT
            incident_id,
            service,
            severity,
            incident_type,
            description,
            impact,
            root_cause,
            resolution,
            prevention
        FROM incidents
    """)

    rows = cursor.fetchall()

    documents = []
    texts = []

    for row in rows:

        text = f"""
        Incident ID: {row[0]}
        Service: {row[1]}
        Severity: {row[2]}
        Incident Type: {row[3]}
        Description: {row[4]}
        Impact: {row[5]}
        Root Cause: {row[6]}
        Resolution: {row[7]}
        Prevention: {row[8]}
        """

        documents.append({
            "incidentId": row[0],
            "service": row[1],
            "severity": row[2],
            "incidentType": row[3],
            "description": row[4],
            "impact": row[5],
            "rootCause": row[6],
            "resolution": row[7],
            "prevention": row[8],
            "content": text
        })

        texts.append(text)

    embeddings = embedder.embed(
        texts
    )

    vector_store.add(
        embeddings,
        documents
    )

    cursor.close()
    conn.close()

    print(
        f"Indexed {len(documents)} incidents"
    )


@app.post(
    "/rag/query",
    response_model=QueryResponse
)
def query_incidents(
        request: QueryRequest):

    query_embedding = embedder.embed(
        [request.query]
    )

    results = vector_store.search(
        query_embedding,
        top_k=10
    )

    filtered = []

    for incident in results:

        if (
                request.service
                and incident["service"]
                != request.service
        ):
            continue

        if (
                request.severity
                and incident["severity"]
                != request.severity
        ):
            continue

        if (
                request.incidentType
                and incident["incidentType"]
                != request.incidentType
        ):
            continue

        filtered.append(
            incident
        )

    matches = []

    for incident in filtered[:5]:

        matches.append(
            IncidentResult(
                incidentId=incident[
                    "incidentId"
                ],
                service=incident[
                    "service"
                ],
                severity=incident[
                    "severity"
                ],
                description=incident[
                    "description"
                ],
                rootCause=incident[
                    "rootCause"
                ],
                resolution=incident[
                    "resolution"
                ],
                prevention=incident[
                    "prevention"
                ]
            )
        )

    return QueryResponse(
        results=matches
    )