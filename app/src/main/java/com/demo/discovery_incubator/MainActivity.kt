package com.demo.discovery_incubator

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

class MainActivity : Activity(), androidx.appcompat.widget.SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnCloseListener {

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: RecyclerAdapter

    private lateinit var dataCompositeDisposable: CompositeDisposable

    private val BASE_URL = "http://frontendshowcase.azurewebsites.net"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_main)

        dataCompositeDisposable = CompositeDisposable()

        this.layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = this.layoutManager

        loadDataList()

        search_bar.setOnQueryTextListener(this)
        search_bar.setOnCloseListener(this)
    }

    private fun loadDataList() {
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(DataCollectionApi::class.java)

        dataCompositeDisposable?.add(
            requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { responseMovieList ->
                    val cardItemList = ArrayList<CardItem>()
                    for (item in responseMovieList) {
                        cardItemList.add(CardItem.fromMovieApiResponse(item))
                    }
                    cardItemList
                }
                .subscribe(
                    { result: ArrayList<CardItem> -> handleListResponse(result) },
                    { error: Throwable -> handleError(error) })
        )
    }

    private fun loadSingleIssueData(issueId: Int) {
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(DataCollectionApi::class.java)

        dataCompositeDisposable?.add(
            requestInterface.getSingleIssueData(issueId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { responseMovie ->
                    val cardItemList = ArrayList<CardItem>()
                    cardItemList.add(CardItem.fromMovieApiResponse(responseMovie))
                    cardItemList
                }
                .subscribe(
                    { result -> handleListResponse(result) },
                    { error -> handleError(error) })
        )
    }

    private fun handleListResponse(movieList: ArrayList<CardItem>) {
        this.adapter = RecyclerAdapter(movieList, WeakReference(applicationContext))
        recycler_view.adapter = this.adapter
    }

    private fun handleError(error: Throwable) {
        Toast.makeText(
            applicationContext,
            error.localizedMessage,
            Toast.LENGTH_LONG
        ).show()

        Log.e("TAG", error.localizedMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataCompositeDisposable?.clear()

    }

    override fun onPause() {
        super.onPause()
        dataCompositeDisposable?.clear()
    }

    // SearchView Listeners
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            try {
                val parsedInt = query!!.toInt()
                loadSingleIssueData(parsedInt)
            } catch (nfe: NumberFormatException) {
                // not a valid int
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onClose(): Boolean {
        loadDataList()
        return false
    }
}