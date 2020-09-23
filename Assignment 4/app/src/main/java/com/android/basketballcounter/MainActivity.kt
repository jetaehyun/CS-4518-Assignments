package com.android.basketballcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.*

private const val TAG = "MainActivity"
const val Debug = "Debug"

class MainActivity : AppCompatActivity(), TeamListFragment.Callbacks, TeamFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null) {
            Log.d(Debug, "MainActivity onCreate()")
            val team = table_game()
            val fragment = TeamFragment.newInstance(team.id)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }

    }

    override fun onDisplaySelected(string: String) {
        Log.d(TAG, "MainActivity.onDisplaySelected: $string")
        when (string) {
            "TeamA" -> {
                val fragment = TeamListFragment.newInstance(string)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            "TeamB" -> {
                val fragment = TeamListFragment.newInstance(string)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            "Tie" -> {
                val fragment = TeamListFragment.newInstance(string)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            else -> {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTeamSelected(team: UUID) {
        Log.d(TAG, "MainActivity.onTeamSelected: $team")
        val fragment = TeamFragment.newInstance(team)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }

}