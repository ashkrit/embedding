from flask import Flask
from sentence_transformers import SentenceTransformer
from flask import request
import logging
import requests

app = Flask(__name__)

logging.basicConfig(level=logging.INFO)



model:SentenceTransformer = SentenceTransformer('all-MiniLM-L6-v2')


@app.route('/')
def welcome():
    return "Welcome to the sentence embedding service"

@app.route('/embedding', methods=['POST'])
def embedding():
    sentence = request.json['text']
    model_name = request.json['model']

    if model_name == 'all-MiniLM-L6-v2':
        embedding = model.encode(sentence)
        return {'embedding': embedding.tolist()}
    else:
        return {'error': 'Model not found'}


if __name__ == '__main__':
    logging.info('Starting embedding server')
    logging.info('Loading model')
    app.run(debug=True)