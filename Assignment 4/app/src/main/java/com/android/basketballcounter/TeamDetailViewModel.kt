package com.android.basketballcounter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
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
}