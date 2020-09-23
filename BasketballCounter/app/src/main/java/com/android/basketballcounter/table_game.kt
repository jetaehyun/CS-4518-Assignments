package com.android.basketballcounter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class table_game(@PrimaryKey val id: UUID = UUID.randomUUID(),
                      @ColumnInfo(name = "teamAName")var teamAName: String = "",
                      @ColumnInfo(name = "teamBName")var teamBName: String = "",
                      @ColumnInfo(name = "teamAScore")var teamAScore: Int = 0,
                      @ColumnInfo(name = "teamBScore")var teamBScore: Int = 0,
                      @ColumnInfo(name = "date")var date: Date = Date())