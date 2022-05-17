package com.example.marvel.Adaptadores

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.Escuchadores.OnClickListener
import com.example.marvel.R
import com.example.marvel.databinding.ItemComicBinding
import com.example.marvel.databinding.ItemStoreBinding
import com.example.marvel.models.DatosComics
import com.example.marvel.models.SuperHeroes

class ComicsAdapter(private var personajes:ArrayList<DatosComics>,var listener: OnClickListener) :
RecyclerView.Adapter<ComicsAdapter.ViewHolder>(){

    private lateinit var mContext:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context

        val view=LayoutInflater.from(mContext).inflate(R.layout.item_comic,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character=personajes.get(position)
        with(holder){
            setlistener(character)
            var path=character.thumbnail.path.split(":")
            var img="${path[0]}s:${path[1]}.${character.thumbnail.extension}"
            binding.tvName.setText(character.title)

            Log.d("ImagenSuperHero","")

            Glide.with(mContext)
                .load(img)
                .centerCrop()
                .into(binding.imgPhoto)




        }
    }
    fun setStores(stores: ArrayList<DatosComics>) {
        this.personajes=stores
        notifyDataSetChanged()
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding= ItemComicBinding.bind(view)
        fun setlistener(superHeroes: DatosComics){
            with(binding.root){
                setOnClickListener{
                    listener.detailcomics(superHeroes)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int =personajes.size

}