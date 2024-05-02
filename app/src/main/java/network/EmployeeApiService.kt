import model.EmployeesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface EmployeeApiService {
    @GET
    fun getEmployees(@Url url: String): Call<EmployeesResponse>
}

