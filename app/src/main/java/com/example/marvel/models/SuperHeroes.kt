package com.example.marvel.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SuperHeroes(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("modified")
    val modified:String,
    @SerializedName("thumbnail")
    val thumbnail:ImgRespuesta,
    @SerializedName("comics")
    val comics:Comics,
    @SerializedName("events")
    val events:Comics



):Serializable