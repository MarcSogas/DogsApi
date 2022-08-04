package com.example.retrofit_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_1.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:DogAdapter
    private val dogImage = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //adapter para el recyclerview
        binding.svDogs.setOnQueryTextListener(this)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImage)
        binding.rvDogs.layoutManager=LinearLayoutManager(this)
        binding.rvDogs.adapter=adapter
    }

    private fun getRetrofit():Retrofit{
        return  Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call =getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies = call.body()
            //volver al hilo principal
            runOnUiThread{
                if(call.isSuccessful){
                    //show recyclerview
                    val images = puppies?.images?: emptyList()
                    dogImage.clear()
                    dogImage.addAll(images)
                    adapter.notifyDataSetChanged()

                }else{

                    showError()
                }
                hideKeyboard()
            }

        }

    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)

    }

    private fun showError(){
        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //buscar
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

}