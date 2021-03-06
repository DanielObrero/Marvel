package com.example.marvel.models

import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class MarvelRespuesta<T>(
    @SerializedName("code")
    val code:Int,
    @SerializedName("status")
    val status:String,
    @SerializedName("copyright")
    val copyright:String,
    @SerializedName("attributionText")
    val attributionText:String,
    @SerializedName("attributionHTML")
    val attributionHTML:String,
    @SerializedName("etag")
    val etag:String,
    @SerializedName("data")
    val data: T?


)