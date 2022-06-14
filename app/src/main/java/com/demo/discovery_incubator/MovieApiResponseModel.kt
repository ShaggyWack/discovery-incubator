package com.demo.discovery_incubator

data class MovieApiResponseModel(
    val title: String,
    val description: String,
    val images: ArrayList<MovieApiImageResponseModel>
) {

    inner class MovieApiImageResponseModel(
        val path: String,
        val extension: String,
        val pathIncludingExtension: String
    )
}
