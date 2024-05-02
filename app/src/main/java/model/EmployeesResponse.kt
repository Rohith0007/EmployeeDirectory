package model

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("employees")
    val employees: List<Employee>
)
