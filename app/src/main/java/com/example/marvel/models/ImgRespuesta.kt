package com.example.marvel.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImgRespuesta(
    @SerializedName("path")
    val path:String,
    @SerializedName("extension")
    val extension:String
):Serializable
