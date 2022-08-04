package com.example.retrofit_1

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_1.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {
private val binding = ItemDogBinding.bind(view)
    fun bind(image:String){
//cargamos la imagen
//convertir url a imagen
        Picasso.get().load(image).into(binding.ivDog)
    }
}