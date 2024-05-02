package viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.Employee
import repository.EmployeeRepository

class EmployeeViewModel : ViewModel() {
    private val repository = EmployeeRepository()

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchEmployees(endpoint: String = "employees.json") {
        Log.d("EmployeeViewModel", "fetchEmployees called")
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.fetchFromEndpoint(endpoint)
                _employees.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "An unknown error occurred"
                Log.e("EmployeeViewModel", "Error fetching data", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
