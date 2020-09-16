package com.android.basketballcounter

import java.util.*

data class Team(val id: UUID = UUID.randomUUID(), var date: Date = Date(), var gameTitle: String = "", var teamName: String = "") {
    var points: Int = 0
}