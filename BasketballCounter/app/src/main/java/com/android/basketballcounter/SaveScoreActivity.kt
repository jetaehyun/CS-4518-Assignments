package com.android.basketballcounter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

private const val TEAMASCORES = "com.android.basketballscore.teamAScores"
private const val TEAMBSCORES = "com.android.basketballscore.teamBScores"

const val USER_CONFIRMED = "com.android.basketballscore.user_confirmed"

class SaveScoreActivity : AppCompatActivity() {

    private var teamAScore = 0
    private var teamBScore = 0

    private lateinit var confirmScoreButton: Button
    private lateinit var saveScoreATextView: TextView
    private lateinit var saveScoreBTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_score)

        teamAScore = intent.getIntExtra(TEAMASCORES, -1)
        teamBScore = intent.getIntExtra(TEAMBSCORES, -1)

        confirmScoreButton = findViewById(R.id.confirmScore)
        saveScoreATextView = findViewById(R.id.saveScoreA)
        saveScoreBTextView = findViewById(R.id.saveScoreB)

        saveScoreBTextView.setText(teamBScore.toString())
        saveScoreATextView.setText(teamAScore.toString())

        confirmScoreButton.setOnClickListener {
            Log.d(TAGSAVE, "setResult() is called")
            setConfirmation(true)
        }

    }

    private fun setConfirmation(confirm: Boolean) {
        val data = Intent().apply {
            putExtra(USER_CONFIRMED, confirm)
        }

        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, teamAScore: Int, teamBScore: Int): Intent {
            return Intent(packageContext, SaveScoreActivity::class.java).apply {
                Log.d(TAGSAVE, "newIntent(), putExtra() has been called")
                putExtra(TEAMASCORES, teamAScore)
                putExtra(TEAMBSCORES, teamBScore)
            }
        }
    }
}