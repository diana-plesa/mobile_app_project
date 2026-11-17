package com.example.skyjournal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//defines dao functions, sets pipes
@Dao
interface ObservationDao {

    @Query("SELECT * FROM observations ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Observation>>

    @Query("SELECT * FROM observations WHERE id = :id")
    fun getById(id: Long): Flow<Observation?>

    @Insert
    suspend fun insert(observation: Observation): Long

    @Update
    suspend fun update(observation: Observation)

    @Delete
    suspend fun delete(observation: Observation)
}