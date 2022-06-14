package com.demo.discovery_incubator

import android.app.Activity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.discovery_incubator.databinding.ActivityMainBinding

class MainActivity : Activity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(viewBinding.root)

        this.layoutManager = LinearLayoutManager(this)
        viewBinding.recyclerView.layoutManager = this.layoutManager

        this.adapter = RecyclerAdapter()
        viewBinding.recyclerView.adapter = this.adapter

        viewBinding.searchBar.setOnQueryTextListener(this)
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