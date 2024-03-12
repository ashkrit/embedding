from flask import Flask
from sentence_transformers import SentenceTransformer
from flask import request
import logging
import requests
import json

app = Flask(__name__)

logging.basicConfig(level=logging.INFO)

model:SentenceTransformer = SentenceTransformer('all-MiniLM-L6-v2')

def embedidngs_st(text:str) -> list[float]:
    embedding = model.encode(sentences=text)
    return embedding.tolist()

## Hashmap to store model name and function


model_repository={}

model_repository["all-MiniLM-L6-v2"] = embedidngs_st




@app.route('/')
def welcome():
    return "Welcome to the sentence embedding service"

@app.route('/embedding', methods=['POST'])
def embedding():
    
    ## get request payload as JSON
    payload = request.json

    logging.info(f'Received payload: {payload}')

    sentence:str = payload.get("text")
    model_name:str = payload.get("model")


    model = model_repository[model_name]
    vector = model(sentence)
    return {'embedding': vector}


if __name__ == '__main__':
    logging.info('Starting embedding server')
    logging.info('Loading model')
    app.run(debug=True)