package com.android.basketballcounter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.android.basketballcounter.database.TeamDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "game-database"

class TeamRepository private constructor(context: Context) {

    private val database : TeamDatabase = Room.databaseBuilder(
        context.applicationContext,
        TeamDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val teamDao = database.teamDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTeams(): LiveData<List<table_game>> = teamDao.getTeams()

    fun getTeam(id: UUID): LiveData<table_game?> = teamDao.getTeam(id)

    fun updateTeam(team: table_game) {
        executor.execute {
            teamDao.updateTeam(team)
        }
    }

    fun addTeam(team: table_game) {
        executor.execute {
            teamDao.addTeam(team)
        }
    }

    companion object {
        private var INSTANCE: TeamRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = TeamRepository(context)
            }
        }

        fun get(): TeamRepository {
            return INSTANCE ?:
                    throw IllegalStateException("TeamRepository must be initialized")
        }
    }

}