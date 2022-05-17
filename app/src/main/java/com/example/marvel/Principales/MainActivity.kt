package com.example.marvel.Principales

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvel.Adaptadores.CharacterAdapter
import com.example.marvel.MarvelAPI.MarvelAPIServices
import com.example.marvel.Escuchadores.OnClickListener
import com.example.marvel.R
import com.example.marvel.databinding.ActivityMainBinding
import com.example.marvel.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() , OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private var i:Int=0
    var todos:ArrayList<SuperHeroes>?=null
    private lateinit var mAdapter: CharacterAdapter
    private lateinit var mGridLayout: GridLayoutManager
    var retrofit:Retrofit?=null
    var datos:String?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        retrofit=Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        setupRecyclerView()
        SystemClock.sleep(1000);
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupRecyclerView() {
        mAdapter= CharacterAdapter(ArrayList(),this)
        mGridLayout= GridLayoutManager(this,resources.getInteger(R.integer.main_columns))

        obtenerDatos(i.toString())
        mBinding.recyclerView.setOnScrollChangeListener(
            View.OnScrollChangeListener { v, scrollX, scrollY, _, _ ->
                if (!v.canScrollVertically(1)){
                    mBinding.progressBar.visibility=View.VISIBLE
                    mBinding.textView.visibility=View.VISIBLE
                    i+=100
                    obtenerDatos(i.toString())
                }

            })

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }

    }

    private fun obtenerDatos(of:String) {


            var service:MarvelAPIServices=retrofit!!.create(MarvelAPIServices::class.java)
            var marvelrespuestacall: Call<MarvelRespuesta<DataRespuesta>> = service.obtenerlistapersonajes(of)
            marvelrespuestacall.enqueue(object:Callback<MarvelRespuesta<DataRespuesta>>{
                override fun onResponse(
                    call: Call<MarvelRespuesta<DataRespuesta>>,
                    response: retrofit2.Response<MarvelRespuesta<DataRespuesta>>
                ) {
                    if (response.isSuccessful){
                        var marvelrespuesta: MarvelRespuesta<DataRespuesta>? = response.body()
                        var personajes:ArrayList<SuperHeroes> = marvelrespuesta!!.data!!.results

                        if (of=="0"){
                            todos=personajes
                        }else{
                            todos!!.addAll(personajes)
                        }
                        mBinding.progressBar.visibility=View.GONE
                        mBinding.textView.visibility=View.GONE
                        mAdapter.setStores(todos!!)

                    }else{
                        Log.d("Errir","Not successful")
                    }
                }
                override fun onFailure(call: Call<MarvelRespuesta<DataRespuesta>>, t: Throwable) {
                    Log.d("Errir",t.message.toString())
                }
            })



        }

    override fun detail(superhero: SuperHeroes) {
        val intent = Intent(this, DetallesActivity::class.java).apply {
            putExtra("superhero",superhero)
        }
        startActivity(intent)

    }

    override fun detailcomics(info: DatosComics) {
        TODO("Not yet implemented")
    }


}





