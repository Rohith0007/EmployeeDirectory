package repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Employee
import network.RetrofitInstance

class EmployeeRepository {

    suspend fun fetchFromEndpoint(endpoint: String): List<Employee> = withContext(Dispatchers.IO) {
        val response = RetrofitInstance.api.getEmployees(endpoint).execute()
        if (response.isSuccessful && response.body() != null) {
            response.body()!!.employees
        } else {
            throw Exception("Failed to fetch employees: ${response.errorBody()?.string()}")
        }
    }
}
