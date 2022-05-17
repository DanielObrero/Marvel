package com.example.marvel.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Comics (
    @SerializedName("available")
    val available: Long,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("returned")
    val returned: Long
):Serializable
