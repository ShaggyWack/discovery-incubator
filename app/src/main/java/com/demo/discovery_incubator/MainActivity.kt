package com.demo.discovery_incubator

import android.app.Activity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)

        this.layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = this.layoutManager

        this.adapter = RecyclerAdapter()
        recycler_view.adapter = this.adapter

        search_bar.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }
}