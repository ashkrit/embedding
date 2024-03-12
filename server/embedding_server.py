from flask import Flask
from sentence_transformers import SentenceTransformer
from flask import request
import logging
import requests
import json
import os

app = Flask(__name__)

logging.basicConfig(level=logging.INFO)

OLLAMA_API_EMBEDDINGS = "http://localhost:11434/api/embeddings"
OPENAI_API_EMBEDDINGS = "https://api.openai.com/v1/embeddings"
model: SentenceTransformer = SentenceTransformer('all-MiniLM-L6-v2')



def embedidngs_st(text: str, model_name: str) -> list[float]:
    embedding = model.encode(sentences=text)
    return embedding.tolist()


def embedidngs_ollama(text: str, model_name: str) -> list[float]:
    payload = {
        "model": model_name,
        "prompt": text
    }
    reply = requests.post(OLLAMA_API_EMBEDDINGS, json=payload)
    reply_json = reply.json()
    vectors = reply_json['embedding']
    return vectors


def embedidngs_openai(text: str, model_name: str) -> list[float]:
    payload = {
        "model": model_name,
        "input": text
    }
    api_key = os.getenv("OPENAI_TOKEN")
    headers = {
        'Authorization': f'Bearer {api_key}',
        'Content-Type': 'application/json',
    }
    reply = requests.post(OPENAI_API_EMBEDDINGS, json=payload, headers=headers)
    reply_json = reply.json()
    first_item = reply_json['data'][0]
    vector = first_item['embedding']
    return vector

# Hashmap to store model name and function


model_repository = {}
model_repository["st/all-MiniLM-L6-v2"] = embedidngs_st
model_repository["ollama/all-minilm"] = embedidngs_ollama
model_repository["ollama/gemma:2b"] = embedidngs_ollama
model_repository["ollama/nomic-embed-text"] = embedidngs_ollama
model_repository["openai/text-embedding-3-small"] = embedidngs_openai


@app.route('/')
def welcome():
    return "Welcome to the sentence embedding service"


@app.route('/embedding', methods=['POST'])
def embedding():

    # get request payload as JSON
    payload = request.json

    logging.info(f'Received payload: {payload}')

    sentence: str = payload.get("text")
    model_name: str = payload.get("model")
    provider: str = payload.get("provider")

    model = model_repository[f"{provider}/{model_name}"]
    vector = model(sentence, model_name)
    return {"len": len(vector), 'embedding': vector}


if __name__ == '__main__':
    logging.info('Starting embedding server')
    logging.info('Loading model')
    app.run(debug=True)
