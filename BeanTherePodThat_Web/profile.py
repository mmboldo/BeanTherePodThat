import functools
from flask import Flask, render_template, flash, request, redirect, url_for, Blueprint, current_app, session
from werkzeug.utils import secure_filename
from flask_pymongo import PyMongo
from datetime import datetime, timezone
from extentsions import mongo
from bson import json_util


db = mongo.db
bp = Blueprint('profile', __name__, url_prefix='/profile')
collection = mongo


@bp.route("/", methods=['GET'])
def displayProfile():
    if 'email' in session:
        email = session.get('email')
        profile = db.profile.find_one({"email": email})
        if profile == None:
            init_profile(email)
        profile = db.profile.find_one({"email": email})
        return render_template("profile/profile.html", profile=profile)
    return redirect(url_for('login'))

    
@bp.route("/edit-profile", methods=['GET', 'POST'])
def edit_profile():
    email = session.get('email')
    profile = db.profile.find_one({"email": email})
    

    if request.method == "POST":
        firstname = request.form["firstname"]
        lastname = request.form["lastname"]
        email = request.form["email"]
        occupation = request.form["occupation"]
        password = request.form['password']
        db.users.update_one({
            'email':profile['email']
        },{
            '$set': {
                'firstName': firstname,
                'lastName': lastname,
                'password': password,
                'occupation':occupation,
             }
        }
        )
        db.profile.update_one({
            'email': profile['email']
        },{
            '$set': {
                'firstname':firstname,
                'lastname':lastname,
                'email':email,
                'occupation':occupation,
            }
        }, upsert=False)
        return redirect('/profile')
    
    return render_template("profile/edit_profile.html", profile=profile)

# profile image
@bp.route('/upload-profile', methods=['POST'])
def upload():
    email = session.get('email')
    profile = db.profile.find_one({"email": email})
    error = None
    if 'profile_image' in request.files:
        profile_image = request.files['profile_image']
        if profile_image.filename == '':
            error = 'No Filename'                 
        elif not allowed_image(profile_image.filename):
            error = 'That file extension is not allowed'                
            
        if error is None:
            filename = secure_filename(profile_image.filename)
            collection.save_file(profile_image.filename, profile_image)
            db.profile.update_one({
                'email': profile['email']
            },{
                '$set': {
                    'profile_image_name':profile_image.filename
                }
            }, upsert=False)
            db.users.update_one({
                'email': profile['email']
            },{
                '$set': {
                    'profileImageName':profile_image.filename
                }
            }, upsert=False)
            return redirect('/profile/edit-profile')

    flash(error)
                
    return render_template("profile/edit_profile.html", profile=profile)


# testing use only
@bp.route("/insert_profile", methods=['GET', 'POST'])
def insert_profile():
   
    if request.method == "POST":
        user_birthday = datetime.strptime(request.form['birthday'], "%Y-%m-%d")
        db.profile.insert_one({
            'username':request.form['username'],
            'firstname':request.form['firstname'],
            'lastname': request.form['lastname'],
            'machine':request.form['machine'],
            'email':request.form['email'],
            'occupation':request.form['occupation'],
            'birthday':user_birthday})
        return render_template("profile.html")
    return render_template("insert_profile.html")


def update_profile(request):
    current_profile = db.profile.find_one({"email": "testaccount1@gmail.com"})
    current_profile['username'] = request.form['username']
    current_profile['firstname'] = request.form['firstname']
    current_profile['lastname'] =  request.form['lastname']
    current_profile['machine'] = request.form['machine']
    current_profile['email'] = request.form['email']
    current_profile['occupation'] = request.form['occupation']
    current_profile['birthday'] = datetime.strptime(request.form['birthday'], "%Y-%m-%d")


# image limitation
def allowed_image(filename):

    if not "." in filename:
        return False

    ext = filename.rsplit(".", 1)[1]

    if ext.upper() in current_app.config["ALLOWED_IMAGE_EXTENSIONS"]:
        return True
    else:
        return False


def allowed_image_filesize(filesize):

    if int(filesize) <= current_app.config["MAX_IMAGE_FILESIZE"]:
        return True
    else:
        return False

# create empty profile

def init_profile(email):
    birthday = datetime.strptime("2021-1-1", "%Y-%m-%d")
    db.profile.insert_one({
            'username':'',
            'firstname':'',
            'lastname': '',
            'machine':'',
            'email':email,
            'occupation':'',
            'birthday':birthday,
            'profile_image_name':'default_profile.jpg'})
        

if __name__ == '__main__':
    app.run(host = '0.0.0.0' ,debug=True) 