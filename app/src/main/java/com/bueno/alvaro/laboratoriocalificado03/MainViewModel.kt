package com.bueno.alvaro.laboratoriocalificado03

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    val teacherList = MutableLiveData<List<TeacherResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val errorApi = MutableLiveData<String>()

    init {
        getAllTeachers()
    }

    private fun getAllTeachers() {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(TeacherApi::class.java).getTeachers()
                if (call.isSuccessful) {
                    call.body()?.let {
                        isLoading.postValue(false)
                        teacherList.postValue(it.teachers)
                    }
                } else {
                    isLoading.postValue(false)
                    errorApi.postValue("Error: ${call.code()}")
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                errorApi.postValue("Exception: ${e.message}")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
