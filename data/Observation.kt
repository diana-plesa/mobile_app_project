package com.example.skyjournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//one row

@Entity(tableName = "observations")
data class Observation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val notes: String,
    val timestamp: Long,
    // Filled in Milestone 2 (sensors and camera)
    val azimuth: Float? = null,
    val pitch: Float? = null,
    val photoUri: String? = null
)