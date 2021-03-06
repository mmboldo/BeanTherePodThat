import functools
from flask import Flask, render_template, flash, request, redirect, url_for, Blueprint, current_app, session, jsonify
from werkzeug.utils import secure_filename
from flask_pymongo import PyMongo
from datetime import datetime, timezone
from extentsions import mongo
from bson import json_util
import json


app = Flask(__name__)
app.config["MONGO_URI"] = "mongodb+srv://admin:admin@cluster0.zhcnd.mongodb.net/BeanTherePodThat?retryWrites=true&w=majority" # replace the URI with your own connection

db = mongo.db
bp = Blueprint('machine', __name__, url_prefix='/machine')
collection = mongo


@bp.route('/')
def myMachine():
    if 'email' in session:
        email = session['email']
        machines = db.myMachines.find({'email': email})
        return render_template("machine/machine.html", machines = machines)
    return redirect(url_for('login'))
    


@bp.route('/add-machine', methods=['GET', 'POST'])
def addMachine():
    email = session['email']
    if 'email' in session:
        if request.method == 'POST':            
            machineName = request.form['machineName']
            targetMachine = db.coffeeMachines.find_one({'machineName':machineName})
            db.users.update_one({'email':email},{'$addToSet': { 
                'myMachines': {
                    'id':targetMachine['_id'],
                    'machineName':targetMachine['machineName'], 
                    'machineType':targetMachine['machineType'], 
                    'brand':targetMachine['brand'], 
                    'imageFilename':targetMachine['imageFilename'], 
                    'url':targetMachine['url'], 
                    'description':targetMachine['description']} }},  upsert=False)
            db.myMachines.insert_one({
                    'email':email,
                    'id':targetMachine['_id'],
                    'machineName':targetMachine['machineName'], 
                    'machineType':targetMachine['machineType'], 
                    'brand':targetMachine['brand'], 
                    'imageFilename':targetMachine['imageFilename'], 
                    'url':targetMachine['url'], 
                    'description':targetMachine['description']  })
            return redirect('/machine')

    
        machines = db.coffeeMachines.find({})
        myMachines = db.myMachines.find({'email':email})
        return render_template("machine/add_machine.html", machines=machines, myMachines=myMachines)
    return redirect(url_for('login'))


@bp.route('remove-machine',methods=['GET','POST'])
def removeMachine():
    if 'email' in session:
        email = session['email']
        if request.method == 'POST':            
            machineName = request.form['machineName']
            targetMachine = db.coffeeMachines.find_one({'machineName':machineName})
            db.users.update_one({'email':email},{'$pull': { 
                'myMachines': {
                    'machineName':targetMachine['machineName'], 
                     }}})
            db.myMachines.delete_one({
                    'email':email,
                    'id':targetMachine['_id'],
                    'machineName':targetMachine['machineName'], 
                    'machineType':targetMachine['machineType'], 
                    'brand':targetMachine['brand'], 
                    'imageFilename':targetMachine['imageFilename'], 
                    'url':targetMachine['url'], 
                    'description':targetMachine['description']  })
            return redirect('/machine')

    
        machines = db.myMachines.find({'email':email})
        return render_template("machine/remove_machine.html", machines=machines)
    return redirect(url_for('login'))


if __name__ == '__main__':
    app.run(host = '0.0.0.0' ,debug=True) 