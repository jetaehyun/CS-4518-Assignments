package com.android.basketballcounter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.basketballcounter.table_game

@Database(entities = [table_game::class], version=1, exportSchema = false)
@TypeConverters(TeamTypeConverters::class)
abstract class TeamDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

}