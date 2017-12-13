package com.example.hasham.movies_mvvm.data.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Khurram on 08-Dec-17.
 */

data class DramaResponse(@SerializedName("page")
                         val page: Int? = null,
                         @SerializedName("total_results")
                         val totalResults: Int? = null,
                         @SerializedName("total_pages")
                         val totalPages: Int? = null,
                         @SerializedName("results")
                         val results: List<Drama>? = null)

@Entity
data class Drama(@PrimaryKey(autoGenerate = true)
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
                 val overview: String? = null,
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
) : Parcelable {
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

    companion object CREATOR : Parcelable.Creator<Drama> {
        override fun createFromParcel(parcel: Parcel): Drama = Drama(parcel)

        override fun newArray(size: Int): Array<Drama?> = arrayOfNulls(size)
    }

    val _overview: String
        get(): String {
            return "OVERVIEW:\n\n" + overview
        }

    val _releaseDate: String
        get(): String {
            return "RELEASED ON:  " + releaseDate
        }

    val _voteAverage: String
        get(): String {
            return "RATING:  " + voteAverage.toString()
        }

//    var  _releaseDateFormated: String = ""
//        get(): String {z
//
//            val input = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
//            val output = SimpleDateFormat("dd MMM yyyy", Locale.UK)
//
//            try {
//                val movieDate = input.parse(releaseDate)  // parse input
//                _releaseDateFormated = output.format(movieDate)    // format output
//                return "RELEASED ON:  " + _releaseDateFormated
//            } catch (e: ParseException) {
//                e.printStackTrace()
//            }
//
//            return _releaseDateFormated
//        }

}