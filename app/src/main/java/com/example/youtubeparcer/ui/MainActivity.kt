package com.example.youtubeparcer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeparcer.R
import com.example.youtubeparcer.adapter.PlaylistAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val data = mutableListOf<String>()

    private var adapter: PlaylistAdapter? = null

    var name = "name"
    val surname = "name"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addDataToList()
        initAdapter()
        updateAdapterData(data)
    }

    private fun addDataToList() {
        data.add("Hello")
        data.add("Hi")
        data.add("Привет")
        data.add("Салам")
    }

    private fun initAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = PlaylistAdapter()
        recycler_view.adapter = adapter
    }

    private fun updateAdapterData(list: MutableList<String>) {
        adapter?.updateData(list)
    }
}
