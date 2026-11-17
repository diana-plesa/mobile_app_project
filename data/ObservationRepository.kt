package com.example.skyjournal.data

import kotlinx.coroutines.flow.Flow

class ObservationRepository(private val dao: ObservationDao) {

    val all: Flow<List<Observation>> = dao.getAll()

    fun get(id: Long): Flow<Observation?> = dao.getById(id)

    suspend fun add(observation: Observation): Long = dao.insert(observation)

    suspend fun update(observation: Observation) = dao.update(observation)

    suspend fun delete(observation: Observation) = dao.delete(observation)
}