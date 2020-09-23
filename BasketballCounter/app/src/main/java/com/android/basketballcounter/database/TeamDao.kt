package com.android.basketballcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.basketballcounter.table_game
import java.util.*

@Dao
interface TeamDao {

    @Query("SELECT * FROM table_game")
    fun getTeams(): LiveData<List<table_game>>

    @Query("SELECT * FROM table_game WHERE id=(:id)")
    fun getTeam(id: UUID): LiveData<table_game?>

    @Update
    fun updateTeam(team: table_game)

    @Insert
    fun addTeam(team: table_game)
}