import faiss
import numpy as np

class VectorStore:
    def __init__(self, dimension: int):
        self.index = faiss.IndexFlatL2(dimension)
        self.documents = []

    def add(self, embeddings, docs):
        self.index.add(embeddings)
        self.documents.extend(docs)

    def search(self, query_embedding, top_k=1):
        distances, indices = self.index.search(query_embedding, top_k)
        return [self.documents[i] for i in indices[0]]
