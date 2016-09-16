package com.github.siosio.upsource.exception

class ReviewNotFoundException(projectId:String, reviewId:String) :
    RuntimeException("review not found. projectId:$projectId, reviewId:$reviewId")
