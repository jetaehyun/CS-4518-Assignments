package com.android.basketballcounter

import androidx.lifecycle.ViewModel

class TeamListViewModel: ViewModel() {

    private val teamRepository = TeamRepository.get()
    val teamListLiveData = teamRepository.getTeams()

}


