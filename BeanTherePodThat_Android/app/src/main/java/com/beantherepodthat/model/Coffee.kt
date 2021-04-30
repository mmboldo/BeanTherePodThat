package com.beantherepodthat.model

import com.google.gson.annotations.SerializedName

data class Coffee(
        @SerializedName("id") val id: Int,
        @SerializedName("coffeeName") val coffeeName: String,
        @SerializedName("coffeeImg") val coffeeImg: String,
        @SerializedName("brand") val brand: String,
        @SerializedName("description") val description: String,
        @SerializedName("intensity") val intensity: Int,
        @SerializedName("cupSize") val cupSize: String,
        @SerializedName("roast") val roast: Int,
        @SerializedName("acidity") val acidity: Int,
        @SerializedName("bitterness") val bitterness: Int,
        @SerializedName("body") val body: Int,
        @SerializedName("ingredients") val ingredients: String,
        @SerializedName("machine") val machine: String
        )
