package com.example.smarthome.data

import com.example.smarthome.data.dao.RoutineDao
import com.example.smarthome.data.entities.Routine
import kotlinx.coroutines.flow.Flow

class RoutineRepository(private val routineDao: RoutineDao) {

    val readAllData: Flow<List<Routine>> = routineDao.getAllRoutines()

    suspend fun addRoutine(routine: Routine) {
        routineDao.insertRoutine(routine)
    }

    suspend fun updateRoutine(routine: Routine) {
        routineDao.updateRoutine(routine)
    }

    suspend fun deleteRoutine(routine: Routine) {
        routineDao.deleteRoutine(routine)
    }
}
