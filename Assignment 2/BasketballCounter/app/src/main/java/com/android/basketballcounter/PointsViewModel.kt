package com.android.basketballcounter

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "MainActivity"

class PointsViewModel : ViewModel() {

    private val teamA = Team("TeamA")
    private val teamB = Team("TeamB")

    val getAPoints: Int
        get() = teamA.points

    val getBPoints: Int
        get() = teamB.points

    fun resetPoints() {
        teamA.points = 0
        teamB.points = 0
    }

    fun add3Points(team: String) {
        if(team == "TeamA") {
            teamA.points += 3
        } else {
            teamB.points += 3
        }
    }

    fun add2Points(team: String) {
        if(team == "TeamA") {
            teamA.points += 2

        } else {
            teamB.points += 2
        }
    }

    fun addPoint(team: String) {
        if(team == "TeamA") {
            teamA.points++
        } else {
            teamB.points++
        }
    }

}