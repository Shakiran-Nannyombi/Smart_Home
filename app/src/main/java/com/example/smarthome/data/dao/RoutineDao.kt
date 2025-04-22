package com.example.smarthome.data.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smarthome.data.entities.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

    @Query("SELECT * FROM routines")
    fun getAllRoutines(): Flow<List<Routine>>

    @Query("SELECT COUNT(*) FROM routines WHERE taskName = :taskName AND time = :time AND recurrence = :recurrence")
    suspend fun routineExists(taskName: String, time: String, recurrence: String): Int

    @Update
    suspend fun updateRoutine(routine: Routine)

    @Delete
    suspend fun deleteRoutine(routine: Routine)
}
