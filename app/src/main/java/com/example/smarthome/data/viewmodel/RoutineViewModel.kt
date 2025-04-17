package com.example.smarthome.data.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.RoutineRepository
import com.example.smarthome.data.database.RoutineDatabase
import com.example.smarthome.data.entities.Routine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineViewModel(application: Application) : AndroidViewModel(application) {

    // Use Flow and convert it to LiveData using asLiveData()
    val readAllData = RoutineDatabase.getDatabase(application).routineDao().getAllRoutines().asLiveData()

    private val repository: RoutineRepository

    init {
        val routineDao = RoutineDatabase.getDatabase(application).routineDao()
        repository = RoutineRepository(routineDao)
    }

    fun addRoutine(routine: Routine) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addRoutine(routine)
                Log.d("RoutineViewModel", "Routine added: $routine")
            } catch (e: Exception) {
                Log.e("RoutineViewModel", "Error adding routine", e)
            }
        }
    }

    fun updateRoutine(routine: Routine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRoutine(routine)
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRoutine(routine)
        }
    }
}
