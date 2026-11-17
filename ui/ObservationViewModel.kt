package com.example.skyjournal.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyjournal.data.AppDatabase
import com.example.skyjournal.data.Observation
import com.example.skyjournal.data.ObservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ObservationViewModel(application: Application) : AndroidViewModel(application) {

    //setup
    private val repository = ObservationRepository(
        AppDatabase.getInstance(application).observationDao()
    )

    //starts pipe for all observations - stateflow holds last list
    val observations: StateFlow<List<Observation>> = repository.all //read all
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    //starts pipe for single observation
    fun observation(id: Long): Flow<Observation?> = repository.get(id) //read one

    // id == 0 -> create, otherwise -> update
    fun save(id: Long, title: String, notes: String) {
        viewModelScope.launch { //coroutine - task that occupies a thread while it runs
            if (id == 0L) {
                repository.add( //create
                    Observation(
                        title = title,
                        notes = notes,
                        timestamp = System.currentTimeMillis()
                    )
                )
            } else {
                val existing = repository.get(id).first() ?: return@launch
                repository.update(existing.copy(title = title, notes = notes)) //update
            }
        }
    }

    fun delete(observation: Observation) {
        viewModelScope.launch { repository.delete(observation) } //delete
    }
}
