package com.android.basketballcounter

import androidx.lifecycle.ViewModel

class TeamListViewModel: ViewModel() {
    val teamList = mutableListOf<MutableList<Team>>()

    init {
        for (i in 0 until 100) {
            val teamA = Team()
            teamA.gameTitle = "Game #$i"
            teamA.teamName = "Team A"
            teamA.points = (0..100).random()

            val teamB = Team()
            teamB.gameTitle = "Game #$i"
            teamB.teamName = "Team B"
            teamB.points = (0..100).random()
            val formTeam = mutableListOf(teamA, teamB)

            teamList += formTeam
        }
    }
}