package model

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("full_name") val full_name: String,
    @SerializedName("phone_number") val phone_number: String?,
    @SerializedName("email_address") val email_address: String,
    @SerializedName("biography") val biography: String?,
    @SerializedName("photo_url_small") val photo_url_small: String?,
    @SerializedName("photo_url_large") val photo_url_large: String?,
    @SerializedName("team") val team: String,
    @SerializedName("employee_type") val employee_type: String
)
