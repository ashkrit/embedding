from flask import Flask
from sentence_transformers import SentenceTransformer
from flask import request
import logging
import embeddings.embeddings_service as es
from typing import Callable,Dict



app = Flask(__name__)

logging.basicConfig(level=logging.INFO)

# Map to store model name and function


model_repository:Dict[str,Callable[[str,str],list[float]]] = {}

model_repository["st/all-MiniLM-L6-v2"] = es.embedidngs_st
model_repository["ollama/all-minilm"] = es.embedidngs_ollama
model_repository["ollama/gemma:2b"] = es.embedidngs_ollama
model_repository["ollama/nomic-embed-text"] = es.embedidngs_ollama
model_repository["openai/text-embedding-3-small"] = es.embedidngs_openai
model_repository["google/embedding-001"] = es.embedidngs_googleai


@app.route('/')
def welcome():
    return "Welcome to the sentence embedding service"

@app.route('/list')
def list():

    model_info=[]
    for x in model_repository.keys():
        (p , n) = x.split("/")
        model_info.append({"provider":p,"name":n})

    return {"models": model_info}


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
    app.run(debug=True , port=9090)
