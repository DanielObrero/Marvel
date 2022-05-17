package com.example.marvel.MarvelAPI

import com.example.marvel.models.DataRespuesta
import com.example.marvel.models.DataRespuesta2
import com.example.marvel.models.MarvelRespuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPIServices {

    @GET("characters?limit=100&ts=1650539203&apikey=92732975b1e7c176c5585699bb9a48be&hash=ec0d8e08f7eb3e699e8460ad83aecdbc")
    fun obtenerlistapersonajes(@Query("offset") of:String): Call<MarvelRespuesta<DataRespuesta>>

    @GET("comics?limit=1&ts=1650539203&apikey=92732975b1e7c176c5585699bb9a48be&hash=ec0d8e08f7eb3e699e8460ad83aecdbc")
    fun obtenerlistapersonajes2(@Query("offset") of:String): Call<MarvelRespuesta<DataRespuesta2>>
    @GET("events?limit=1&ts=1650539203&apikey=92732975b1e7c176c5585699bb9a48be&hash=ec0d8e08f7eb3e699e8460ad83aecdbc")
    fun obtenerlistaeventos(@Query("offset") of:String): Call<MarvelRespuesta<DataRespuesta2>>



}