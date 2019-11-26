package com.architectcoders.collection.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokeResult(
    @SerializedName("base_attack")
    @Expose
    val baseAttack: Int,
    @SerializedName("base_defense")
    @Expose
    val baseDefense: Int,
    @SerializedName("base_stamina")
    @Expose
    val baseStamina: Int,
    @SerializedName("form")
    @Expose
    val form: String,
    @SerializedName("pokemon_id")
    @Expose
    val pokemonId: Int,
    @SerializedName("pokemon_name")
    @Expose
    val pokemonName: String
): Parcelable
