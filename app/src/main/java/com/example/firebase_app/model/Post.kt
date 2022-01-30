package com.example.firebase_app.model

class Post {
    var userId: String
    var postTitle: String
    var postDescription: String

    constructor(userId: String, postTitle: String, postDescription: String) {
        this.userId = userId
        this.postTitle = postTitle
        this.postDescription = postDescription
    }
}