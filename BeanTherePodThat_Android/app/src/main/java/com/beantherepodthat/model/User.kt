package com.beantherepodthat.model
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("email") val email: String,
        @SerializedName("firstName") val firstName: String,
        @SerializedName("lasteName") val lastName: String,
        @SerializedName("password") val password: String, //kept it here in case we need it. Consider removing in case of security issues.
        @SerializedName("occupation") val occupation: String? = "not given.",
        @SerializedName("profileImageName") val profileImageName: String? = "default_profile.png",
        @SerializedName("myCoffees") val myCoffees: List<MyCoffees>
)
