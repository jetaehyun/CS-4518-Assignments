package com.android.basketballcounter

import androidx.lifecycle.ViewModel

class PointsViewModel : ViewModel() {

    private val teams = table_game()

    val getAPoints: Int
        get() = teams.teamAScore

    val getBPoints: Int
        get() = teams.teamBScore

    fun setPoints(points: Int, teamName: String?) {
        when(teamName) {
            "TeamA" -> teams.teamAScore = points
            "TeamB" -> teams.teamBScore = points
            else -> {
                teams.teamAScore = points
                teams.teamBScore = points
            }
        }
    }

    fun add3Points(team: String) {
        if(team == "TeamA") {
            teams.teamAScore += 3
        } else {
            teams.teamBScore += 3
        }
    }

    fun add2Points(team: String) {
        if(team == "TeamA") {
            teams.teamAScore += 2

        } else {
            teams.teamBScore += 2
        }
    }

    fun addPoint(team: String) {
        if(team == "TeamA") {
            teams.teamAScore++
        } else {
            teams.teamBScore++
        }
    }

}