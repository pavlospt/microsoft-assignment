package com.github.pavlospt.microsoftassignment.api.models

import com.google.gson.annotations.SerializedName


data class BeerModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("first_brewed") val firstBrewed: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("brewers_tips") val brewerTips: String,
    @SerializedName("contributed_by") val contributedBy: String
)