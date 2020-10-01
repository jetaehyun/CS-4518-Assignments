package com.android.basketballcounter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*

class TeamDetailViewModel : ViewModel() {

    private val teamRepository = TeamRepository.get()
    private val teamIdLiveDate = MutableLiveData<UUID>()

    var teamLiveData: LiveData<table_game?> =
        Transformations.switchMap(teamIdLiveDate) { teamId ->
            teamRepository.getTeam(teamId)
        }

    fun loadTeam(teamId: UUID) {
        teamIdLiveDate.value = teamId
    }

    fun saveTeam(team: table_game) {
        teamRepository.updateTeam(team)
    }

    fun createTeam(team: table_game) {
        teamRepository.addTeam(team)
    }

    fun getAPhotoFile(team: table_game): File {
        return teamRepository.getAPhotoFile(team)
    }

    fun getBPhotoFile(team: table_game): File {
        return teamRepository.getBPhotoFile(team)
    }
}