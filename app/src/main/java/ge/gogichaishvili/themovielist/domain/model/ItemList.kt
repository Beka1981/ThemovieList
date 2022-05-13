package ge.gogichaishvili.themovielist.domain.model

import java.io.Serializable

data class ItemList(
    //ItemList  ცუდი სახელია again რამე movie ს და TvShow ს გამაერთიანებელი გიდა
    val id: Int,
    val originalTitle: String?,
    val posterPath: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val viewType: ItemTypesEnum
) : Serializable // აქ რატო გჭირდება Serializable ? თუ სხვაგან უნდა გადააწოდოო ეს კლასი ანდდროიდის ჭრილში Parcelable  გამოიყენე
