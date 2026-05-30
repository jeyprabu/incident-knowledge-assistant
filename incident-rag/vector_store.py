import faiss
import numpy as np


class VectorStore:

    def __init__(self, dimension):

        self.index = faiss.IndexFlatL2(
            dimension
        )

        self.documents = []

    def add(
            self,
            embeddings,
            documents):

        embeddings = np.array(
            embeddings,
            dtype="float32"
        )

        self.index.add(
            embeddings
        )

        self.documents.extend(
            documents
        )

    def search(
            self,
            query_embedding,
            top_k=5):

        query_embedding = np.array(
            query_embedding,
            dtype="float32"
        )

        distances, indices = self.index.search(
            query_embedding,
            top_k
        )

        return [
            self.documents[i]
            for i in indices[0]
            if i < len(self.documents)
        ]