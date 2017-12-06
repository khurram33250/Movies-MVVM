package com.example.hasham.movies_mvvm.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ApiResponse(@SerializedName("page")
                       val page: Int,
                       @SerializedName("total_results")
                       val totalResults: Int? = null,
                       @SerializedName("total_pages")
                       val totalPages: Int? = null,
                       @SerializedName("results")
                       val results: List<Movie>)

@Entity
data class Movie(@PrimaryKey(autoGenerate = true)
                 val bid: Long,
                 @SerializedName("adult")
                 val adult: Boolean? = null,
                 @SerializedName("backdrop_path")
                 val backdropPath: String? = null,
                 @SerializedName("budget")
                 val budget: Int? = null,
                 @SerializedName("homepage")
                 val homepage: String? = null,
                 @SerializedName("id")
                 val id: Int? = null,
                 @SerializedName("imdb_id")
                 val imdbId: String? = null,
                 @SerializedName("original_language")
                 val originalLanguage: String? = null,
                 @SerializedName("original_title")
                 val originalTitle: String? = null,
                 @SerializedName("overview")
                 val overview: String = "",
                 @SerializedName("popularity")
                 val popularity: Float? = null,
                 @SerializedName("poster_path")
                 val posterPath: String? = null,
                 @SerializedName("release_date")
                 val releaseDate: String? = null,
                 @SerializedName("revenue")
                 val revenue: Int? = null,
                 @SerializedName("runtime")
                 val runtime: Int? = null,
                 @SerializedName("status")
                 val status: String? = null,
                 @SerializedName("tagline")
                 val tagline: String? = null,
                 @SerializedName("title")
                 val title: String? = null,
                 @SerializedName("video")
                 val video: Boolean? = null,
                 @SerializedName("vote_average")
                 val voteAverage: Float? = null,
                 @SerializedName("vote_count")
                 val voteCount: Int? = null,
                 val releaseDateFormated: String? = null

)  : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Float::class.java.classLoader) as? Float,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(bid)
        parcel.writeValue(adult)
        parcel.writeString(backdropPath)
        parcel.writeValue(budget)
        parcel.writeString(homepage)
        parcel.writeValue(id)
        parcel.writeString(imdbId)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeValue(revenue)
        parcel.writeValue(runtime)
        parcel.writeString(status)
        parcel.writeString(tagline)
        parcel.writeString(title)
        parcel.writeValue(video)
        parcel.writeValue(voteAverage)
        parcel.writeValue(voteCount)
        parcel.writeString(releaseDateFormated)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie = Movie(parcel)

        override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
    }

    val _overview = overview
    get(): String? {
        return "OVERVIEW:\n\n"+overview
    }
}

