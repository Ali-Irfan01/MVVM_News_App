package com.example.myapplication.databaseROOMTwo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.databaseROOMTwo.adapter.PersonAdapter
import com.example.myapplication.databaseROOMTwo.viewModel.PersonViewModel
import com.example.myapplication.databinding.ActivityRoomTwoMainBinding
import kotlinx.coroutines.launch

class RoomTwoMainActivity : AppCompatActivity() {

    val adapter by lazy { PersonAdapter() }
    private lateinit var myViewModel: PersonViewModel
    private lateinit var binding: ActivityRoomTwoMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomTwoMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel = ViewModelProvider(this)[PersonViewModel::class.java]

        binding.recyclerViewRoom.adapter = adapter
        binding.recyclerViewRoom.layoutManager = LinearLayoutManager(this)


        lifecycleScope.launch{
            val address = Address("Carnival Street", 175)
            val person = Person(0, "Ali", "Irfan", 22, getBitmap(), address)
            myViewModel.insertPerson(person)
        }
//        val address = Address("Carnival Street", 175)
//        val person = Person(0, "Ali", "Irfan", 22, address)
//        myViewModel.insertPerson(person)
//
        myViewModel.readPerson.observe(this) {
            adapter.setData(it)
        }
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data("https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q=")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}