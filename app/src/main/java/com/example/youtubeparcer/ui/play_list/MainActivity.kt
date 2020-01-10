package com.example.youtubeparcer.ui.play_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeparcer.R
import com.example.youtubeparcer.adapter.PlaylistAdapter
import com.example.youtubeparcer.model.ItemsItem
import com.example.youtubeparcer.model.PlaylistModel
import com.example.youtubeparcer.ui.detail_playlist.DetailPlaylistActivity
import com.example.youtubeparcer.utils.NetworkUtils
import com.example.youtubeparcer.utils.isShow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null
    private var adapter: PlaylistAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initAdapter()
        fetchPlaylist()
        getDataFromDatabase()
    }
    private fun initAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = PlaylistAdapter() {item: ItemsItem -> clickItem(item)}
        recycler_view.adapter = adapter

    }
    private fun clickItem(item: ItemsItem) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra("id", item.id)
        intent.putExtra("title", item.snippet.title)
        intent.putExtra("channelTitle", item.snippet.channelId)
        intent.putExtra("etag", item.etag)
        startActivity(intent)
    }
        private fun fetchPlaylist(){
            if ( !NetworkUtils.isOnline(applicationContext)){
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                showNoConnection(true)
            }
        val data = viewModel?.getPlaylistData( )
        data?.observe(this, Observer<PlaylistModel> {
            val model: PlaylistModel? = data.value
            when {
                model != null -> {
                    updateAdapterData(model)
                    updateDatabasePlayList(model)
                    getDataFromDatabase()
                }
            }
        })
}
    private fun updateAdapterData(model: PlaylistModel?) {
        val data = model?.items
        adapter?.updateData(data)
    }


    fun restart(view: View) {
        if (! NetworkUtils.isOnline(applicationContext)){
            showNoConnection(true)
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }else{
            if(NetworkUtils.isOnline(applicationContext)){
                fetchPlaylist()
                showNoConnection(false)
            }
        }
    }
    private fun showNoConnection(isShown : Boolean){
        btnRestart.isShow(isShown)
        imageInet.isShow(isShown)
    }
    private fun updateDatabasePlayList(model: PlaylistModel) {
        model.let {  viewModel?.insertPlayListData(it)}
    }

    private fun getDataFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val model = viewModel?.getDataFromDB()
            if (model != null && !model.items.isNullOrEmpty()) {
                updateAdapterData(model)
            }
        }
    }
}



