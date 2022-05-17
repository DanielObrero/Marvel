package com.example.marvel.models

import com.example.marvel.models.ImgRespuesta
import com.google.gson.annotations.SerializedName

class DatosComics(
    @SerializedName("title")
    val title:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("thumbnail")
    val thumbnail: ImgRespuesta,
)


