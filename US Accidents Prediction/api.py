# from flask import Flask, jsonify
#
#
# app = Flask(__name__)
# @app.route("/")
# def hello():
#     return jsonify({"about": "Hello World!"})
#
# if __name__ == '__main__':
#     app.run(debug=True)

import numpy as np
from flask import Flask, request, jsonify, render_template
import pickle

app = Flask(__name__)
model = pickle.load(open('/Users/varunsluckysmac/Documents/Academics/CMPE\ 255/Project/model.pkl', 'rb'))


@app.route('/')
def home():
    return "Hello World"


@app.route('/predict', methods=['POST'])
def predict():
    '''
    For rendering results on HTML GUI
    '''
    int_features = [int(x) for x in request.form.values()]
    final_features = [np.array(int_features)]
    prediction = model.predict(final_features)

    output = round(prediction[0], 2)

    return output


@app.route('/predict_api', methods=['POST'])
def predict_api():
    '''
    For direct API calls trought request
    '''
    data = request.get_json(force=True)
    prediction = model.predict([np.array(list(data.values()))])

    output = prediction[0]
    return jsonify(output)


if __name__ == "__main__":
    app.run(debug=True)
