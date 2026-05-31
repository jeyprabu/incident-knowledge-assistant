# Incident Knowledge Assistant

## Overview

Incident Knowledge Assistant is an enterprise-style incident intelligence platform that enables engineers to semantically search historical production incidents and quickly identify relevant root causes, resolutions, and preventive actions.

The system combines a Java-based API layer with a Python retrieval engine powered by vector embeddings and FAISS similarity search. Incident records are stored in PostgreSQL and indexed into an in-memory vector store during application startup.

The solution is designed to simulate real-world internal engineering tools used by Site Reliability Engineering (SRE), Production Support, and Platform Engineering teams.

---

## Architecture

### Components

### Spring Boot Backend

* Exposes REST APIs for incident queries
* Handles request validation and orchestration
* Communicates with the Python retrieval service using WebClient
* Returns structured incident responses

### Python Retrieval Service

* Loads incident records from PostgreSQL
* Generates semantic embeddings using Sentence Transformers
* Creates a FAISS vector index
* Performs Top-K semantic retrieval with metadata filtering

### PostgreSQL

* Source of truth for incident data
* Stores incident metadata and resolution history
* Contains 1,000 historical incident records

### FAISS Vector Store

* Stores vector embeddings in memory
* Enables low-latency similarity search
* Returns semantically relevant incidents

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/ae1e5542-15ff-4331-8745-27084532537f" />


## Technology Stack

### Backend

* Java 21
* Spring Boot
* Maven

### Retrieval Service

* Python
* FastAPI
* Sentence Transformers
* FAISS
* NumPy

### Database

* PostgreSQL



## API

### Query Incidents

**Endpoint**

```http
POST /api/incidents/query
```

### Request

```json
{
  "question": "database connection timeout",
  "severity": "SEV-1",
  "service": "order-service"
}
```


## Running the Application

### Prerequisites

* Java 21
* Maven
* Python 3.10+
* PostgreSQL 15+
* Git

---

### Start PostgreSQL

Create database:

```sql
CREATE DATABASE incidentdb;
```

Load incident data into PostgreSQL.

---

### Start Retrieval Service

```bash
cd incident-rag

pip install -r requirements.txt

uvicorn app:app --reload --port 8000
```

---

### Start Spring Boot Backend

```bash
cd incident-backend

mvn spring-boot:run
```

---

## Performance Characteristics

* 1,000 incident records indexed
* 10 incident attributes searchable
* Top-K semantic retrieval
* Sub-second response times
* Lightweight CPU-only execution

