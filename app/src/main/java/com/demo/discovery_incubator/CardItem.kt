package com.demo.discovery_incubator

class CardItem(
    val title: String,
    val subtitle: String,
    val image: String
) {
    companion object Conversion {
        fun fromMovieApiResponse(response: MovieApiResponseModel): CardItem {
            return if (response.images.size != 0) {
                CardItem(
                    response.title,
                    response.description,
                    response.images.get(0).pathIncludingExtension
                )
            } else {
                CardItem(
                    response.title,
                    response.description,
                    "https://picsum.photos/200/300"
                )
            }
        }
    }
}