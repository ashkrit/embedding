from config.app_config import application_settings
from sentence_transformers import SentenceTransformer
import logging
import requests
import os


model: SentenceTransformer = SentenceTransformer('all-MiniLM-L6-v2')


def embedidngs_st(text: str, model_name: str) -> list[float]:
    embedding = model.encode(sentences=text)
    return embedding.tolist()


def embedidngs_ollama(text: str, model_name: str) -> list[float]:
    payload = {
        "model": model_name,
        "prompt": text
    }
    endpoint = application_settings["ollama_api"]

    reply = requests.post(endpoint, json=payload)
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
    endpoint = application_settings["openai_api"]
    reply = requests.post(endpoint, json=payload, headers=headers)
    reply_json = reply.json()
    first_item = reply_json['data'][0]
    vector = first_item['embedding']
    return vector


def embedidngs_googleai(text: str, model_name: str) -> list[float]:
    payload = {
        "model": f"models/{model_name}",
        "content": {
            "parts": [{"text": text}]
        }
    }
    api_key = os.getenv("gemma_key")
    headers = {
        'Content-Type': 'application/json',
    }
    endpoint = application_settings["googleai_api"]
    with_api_key = f"{endpoint}?key={api_key}"
    logging.info(with_api_key)
    logging.info(payload)
    reply = requests.post(with_api_key, json=payload, headers=headers)
    reply_json = reply.json()
    first_item = reply_json['embedding']
    vector = first_item['values']
    return vector
