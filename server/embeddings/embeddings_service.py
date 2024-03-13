from config.app_config import application_settings
from sentence_transformers import SentenceTransformer
import logging
import requests
import os


OLLAMA_API_EMBEDDINGS:str = application_settings["ollama_api"]
OPENAI_API_EMBEDDINGS:str =application_settings["openai_api"]
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