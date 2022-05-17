package com.example.marvel.Escuchadores

import com.example.marvel.models.DatosComics
import com.example.marvel.models.SuperHeroes

interface OnClickListener {
    fun detail(superhero:SuperHeroes)
    fun detailcomics(info:DatosComics)
}