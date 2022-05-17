package com.example.marvel.models

import com.google.gson.annotations.SerializedName

data class DataRespuesta2(
    @SerializedName("offset")
    val offset:Int,
    @SerializedName("limit")
    val limit:Int,
    @SerializedName("total")
    val total:Int,
    @SerializedName("count")
    val count:Int,
    @SerializedName("results")
    val results:ArrayList<DatosComics>

        )
