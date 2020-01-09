package com.example.youtubeparcer.ui.detail_playlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubeparcer.R
import com.example.youtubeparcer.adapter.DetailPlaylistAdapter
import com.example.youtubeparcer.model.DetailPlaylistModel
import com.example.youtubeparcer.model.ItemsItem
import com.example.youtubeparcer.ui.detail_video.DetailVideoActivity
import kotlinx.android.synthetic.main.activity_detail_playlist.recycler_view
class DetailPlaylistActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailPlaylistViewModel
    private lateinit var adapter: DetailPlaylistAdapter
    private var id: String? = null
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_playlist)
        viewModel = ViewModelProviders.of(this).get(DetailPlaylistViewModel::class.java)
        initAdapter()
        getIntentData()
        subscribeToViewModel()
    }
    private fun getIntentData() {
        id = intent?.getStringExtra("id")
        title = intent?.getStringExtra("title")
        description = intent?.getStringExtra("etag")
    }

    private fun initAdapter() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = DetailPlaylistAdapter {item: ItemsItem -> click(item)}
        recycler_view.adapter = adapter
    }
    private fun click(item: ItemsItem) {
        val intent = Intent(this, DetailVideoActivity::class.java)
        intent.putExtra("playlistId", id)
        intent.putExtra("description", item.contentDetails.itemCount)
        intent.putExtra("videoId", item.snippet.resourceId.videoId)
        startActivity(intent)
    }
    private fun subscribeToViewModel() {
        val data = id?.let { viewModel.fetchDetailPlaylistData(it) }
        data?.observe(this, Observer<DetailPlaylistModel> {
            if (data.value != null) {
                updateViews(data.value!!)
            }
        })
    }
    private fun updateViews(it: DetailPlaylistModel) {
        adapter.updateData(it.items)
    }
}
