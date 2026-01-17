# Incident Knowledge Assistant

An enterprise-style backend application that enables engineers to query historical production incidents using semantic search.  
The system is designed to retrieve relevant incident details from structured incident reports using a lightweight Retrieval-Augmented approach without relying on large language models (LLMs).

---

## 🚀 Project Overview

In large-scale enterprise environments, incident knowledge is often scattered across post-mortems, tickets, and documents. This project centralizes that knowledge and enables semantic querying over past incidents to help engineers quickly identify root causes and resolutions.

The system is intentionally designed to be:
- Lightweight
- CPU-only
- Laptop-friendly
- Enterprise-realistic

No LLMs, GPUs, or heavy infrastructure are required.

---

## 🏗️ Architecture

The application consists of two independent services:

### 1️⃣ Spring Boot Backend (Java)
- Acts as the orchestration and API layer
- Exposes REST endpoints for querying incidents
- Delegates semantic retrieval to the Python service
- Uses clean DTO-based contracts and layered architecture

### 2️⃣ Python Retrieval Service
- Loads historical incident data from a `.jsonl` dataset
- Converts incident text into vector embeddings
- Stores embeddings in an in-memory FAISS index
- Performs semantic similarity search to retrieve the most relevant incident

The services communicate synchronously over HTTP.

---

## 📊 Incident Dataset

Incidents are stored in **JSON Lines (`.jsonl`)** format.

Each incident contains:
- Incident ID
- Service name
- Severity
- Description
- Root cause
- Resolution
- Prevention steps
- Timestamp

This dataset represents realistic enterprise production incidents such as:
- Database outages
- Deployment failures
- Performance degradation
- Batch job failures

---

## 🔍 How Retrieval Works (No LLM)

1. Incident reports are converted into embedding vectors at startup
2. Vectors are indexed using FAISS (CPU-only)
3. User queries are embedded at runtime
4. The most semantically similar incident is retrieved
5. Structured incident details are returned as the response

This is a **retrieval-only RAG-style system**.  
Text generation and summarization are intentionally omitted to keep the system lightweight.

---

## ⚙️ How to Run the Application

### 🔹 Prerequisites
- Java 21
- Maven
- Python 3.10+
- Git

---

### 🔹 Start the Python Retrieval Service

```
cd incident-rag
pip install -r requirements.txt
uvicorn app:app --reload --port 8000
```

### 🔹 Start the Java Spring-Boot
```
cd incident-backend
mvn spring-boot:run
```

## Testing the Application

### API Endpoint on PostMan
POST request
```
http://localhost:8080/api/incidents/query
```

### JSON Questions
1. {"question": "Scheduled reports failed overnight"}
2. {"question": "Users were unable to access the system due to authentication problems"}
3. {"question": "System slowed down during peak traffic hours"}
4. {"question": "Service outage immediately after deployment"}
5. {"question": "Order creation failed due to database connection issues"}

