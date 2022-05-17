package com.example.marvel.Principales

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.marvel.Adaptadores.CharacterAdapter
import com.example.marvel.Adaptadores.ComicsAdapter
import com.example.marvel.Escuchadores.OnClickListener
import com.example.marvel.MarvelAPI.MarvelAPIServices
import com.example.marvel.R
import com.example.marvel.databinding.ActivityDetallesBinding
import com.example.marvel.models.DataRespuesta2
import com.example.marvel.models.DatosComics
import com.example.marvel.models.MarvelRespuesta
import com.example.marvel.models.SuperHeroes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallesActivity : AppCompatActivity(),OnClickListener {
    private lateinit var mBinding:ActivityDetallesBinding
    var retrofit:Retrofit?=null
    var retrofitevents:Retrofit?=null
    var i=0
    var i2=0
    var count=0
    var count2=0
    private lateinit var mAdapter: ComicsAdapter
    private lateinit var mGridLayout: GridLayoutManager
    var todos:ArrayList<DatosComics>?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityDetallesBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
var tipo="comics"

        var e =0
        var superHeroes=intent.getSerializableExtra("superhero") as? SuperHeroes

        var url=superHeroes?.comics?.collectionURI.toString().split("comics")
        var url2=url[0].split(":")
        var urlcompleta="${url2[0]}s:${url2[1]}"

        retrofit= Retrofit.Builder()
            .baseUrl(urlcompleta)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var urlevents=superHeroes?.events?.collectionURI.toString().split("events")
        var url2events=urlevents[0].split(":")
        var urlcompletaevents="${url2events[0]}s:${url2events[1]}"

        retrofitevents= Retrofit.Builder()
            .baseUrl(urlcompletaevents)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        mBinding.tvNombre.text=superHeroes?.name
        if (superHeroes?.description!!.isEmpty()){
            mBinding.tvDescripcion.text="Description not found"
        }else {
            mBinding.tvDescripcion.text=superHeroes?.description
        }
        Log.d("detalles","$urlcompleta")
        var path=superHeroes!!.thumbnail.path.split(":")
        var img="${path[0]}s:${path[1]}.${superHeroes!!.thumbnail.extension}"
        Glide.with(this)
            .load(img)
            .centerCrop()
            .into(mBinding.imgSuperhero)
        mBinding.btnsiguiente.setOnClickListener {
        if (tipo=="comics"){
            if (i<(count-1)){
                i++
                setupRecyclerView(i.toString())
            }
        }
        if (tipo=="events"){
            if (i2<(count2-1)){
                i2++
                setupRecyclerViewevents(i2.toString())
            }
        }


        }
        mBinding.btncomic.setOnClickListener {

tipo="comics"
                setupRecyclerView(i.toString())

            mBinding.btncomic.isEnabled=false
            mBinding.btnevents.isEnabled=true
            mBinding.btncomic.setBackgroundColor(getColor(R.color.black))
            mBinding.btnevents.setBackgroundColor(getColor(R.color.ic_launcher_background))
        }
        mBinding.btnevents.setOnClickListener {

tipo="events"
                setupRecyclerViewevents(i2.toString())

            mBinding.btnevents.isEnabled=false
            mBinding.btncomic.isEnabled=true
            mBinding.btnevents.setBackgroundColor(getColor(R.color.black))
            mBinding.btncomic.setBackgroundColor(getColor(R.color.ic_launcher_background))

        }
        mBinding.btnanterior.setOnClickListener {
            if (tipo=="comics"){
                if (i!=0){
                    i--
                    setupRecyclerView(i.toString())
                }
            }
            if(tipo=="events"){
                if (i2!=0){
                    i2--
                    setupRecyclerViewevents(i2.toString())
                }
            }




        }
        if (e==0){
            e=1
            setupRecyclerView(i.toString())
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupRecyclerView(of:String) {
        mAdapter= ComicsAdapter(ArrayList(),this)
        mGridLayout= GridLayoutManager(this,1)

        obtenerDatos(of)


        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }

    }

    private fun obtenerDatos(of:String) {
        if (of.toInt()!=0){
            todos!!.clear()
        }

        var service: MarvelAPIServices =retrofit!!.create(MarvelAPIServices::class.java)
        var marvelrespuestacall: Call<MarvelRespuesta<DataRespuesta2>> = service.obtenerlistapersonajes2(of)
        marvelrespuestacall.enqueue(object: Callback<MarvelRespuesta<DataRespuesta2>> {
            override fun onResponse(
                call: Call<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>,
                response: retrofit2.Response<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>
            ) {
                if (response.isSuccessful){
                    var marvelrespuesta: MarvelRespuesta<com.example.marvel.models.DataRespuesta2>? = response.body()
                    var personajes:ArrayList<DatosComics> = marvelrespuesta!!.data!!.results
Log.d("offset","$of")
                    if (marvelrespuesta.data!!.total!=0){
                        mBinding.textView2.visibility=View.GONE
                        mBinding.recyclerView.visibility=View.VISIBLE
                        count=marvelrespuesta.data!!.total
                        todos=personajes
                        mAdapter.setStores(todos!!)
                    }else{
                        mBinding.textView2.text="No hay Comics"
                        mBinding.textView2.visibility=View.VISIBLE
                        mBinding.recyclerView.visibility=View.INVISIBLE
                    }






                }else{
                    Log.d("Errir","Not successful")
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>, t: Throwable) {
                Log.d("Errir",t.message.toString())
            }
        })
    }

    private fun setupRecyclerViewevents(of:String) {
        mAdapter= ComicsAdapter(ArrayList(),this)
        mGridLayout= GridLayoutManager(this,1)

        obtenerDatosevents(of)


        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }

    }

    private fun obtenerDatosevents(of:String) {
        if (of.toInt()!=0){
            todos!!.clear()
        }

        var service: MarvelAPIServices =retrofit!!.create(MarvelAPIServices::class.java)
        var marvelrespuestacall: Call<MarvelRespuesta<DataRespuesta2>> = service.obtenerlistaeventos(of)
        marvelrespuestacall.enqueue(object: Callback<MarvelRespuesta<DataRespuesta2>> {
            override fun onResponse(
                call: Call<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>,
                response: retrofit2.Response<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>
            ) {
                if (response.isSuccessful){
                    var marvelrespuesta: MarvelRespuesta<com.example.marvel.models.DataRespuesta2>? = response.body()
                    var personajes:ArrayList<DatosComics> = marvelrespuesta!!.data!!.results
                    Log.d("offset","$of")
                    if (marvelrespuesta.data!!.total!=0){
                        mBinding.textView2.visibility=View.GONE
                        mBinding.recyclerView.visibility=View.VISIBLE
                        count2=marvelrespuesta.data!!.total
                        todos=personajes
                        mAdapter.setStores(todos!!)
                    }else{
                        mBinding.textView2.text="No hay Eventos"
                        mBinding.textView2.visibility=View.VISIBLE
                        mBinding.recyclerView.visibility=View.INVISIBLE
                    }







                }else{
                    Log.d("Errir","Not successful")
                }
            }
            override fun onFailure(call: Call<MarvelRespuesta<com.example.marvel.models.DataRespuesta2>>, t: Throwable) {
                Log.d("Errir",t.message.toString())
            }
        })
    }

    override fun detail(superhero: SuperHeroes) {
        TODO("Not yet implemented")
    }

    override fun detailcomics(info: DatosComics) {
        val dialog=layoutInflater.inflate(R.layout.dialogo,null)
        var path=info.thumbnail.path.split(":")
        var img="${path[0]}s:${path[1]}.${info.thumbnail.extension}"

        dialog.findViewById<TextView>(R.id.tvname).text=info.title
        dialog.findViewById<TextView>(R.id.tvdescription).text=info.description
        var imagen:ImageView=dialog.findViewById<ImageView>(R.id.imgportada)
        Glide.with(this)
            .load(img)
            .centerCrop()
            .into(imagen)

        var dialogq = MaterialAlertDialogBuilder(this).apply {
            setTitle("Detalles")
            setView(dialog)
            setPositiveButton("Cerrar",null)
        }.show()
    }


}

