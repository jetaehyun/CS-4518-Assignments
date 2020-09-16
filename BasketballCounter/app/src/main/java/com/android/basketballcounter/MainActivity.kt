package com.android.basketballcounter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val KEY_TEAM_A = "indexA"
private const val KEY_TEAM_B = "indexB"
private const val TAG = "MainActivity"
const val TAGSAVE = "TAGSAVECHECK"
private const val REQUEST_SCORE_CODE = 0

class MainActivity : AppCompatActivity() {

//    private lateinit var score3AButton: Button
//    private lateinit var score2AButton: Button
//    private lateinit var score1AButton: Button
//    private lateinit var score3BButton: Button
//    private lateinit var score2BButton: Button
//    private lateinit var score1BButton: Button
//    private lateinit var resetButton: Button
//    private lateinit var saveScoreButton: Button
//    private lateinit var displayScoreButton: Button
//    private lateinit var teamAScoreText: TextView
//    private lateinit var teamBScoreText: TextView

//    private val pointsViewModel: PointsViewModel by lazy {
//        ViewModelProviders.of(this).get(PointsViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle? called")
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null) {
            val fragment = TeamListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }

//        val APoints = savedInstanceState?.getInt(KEY_TEAM_A, 0) ?: 0
//        val BPoints = savedInstanceState?.getInt(KEY_TEAM_B, 0) ?: 0
//        pointsViewModel.setPoints(APoints, "TeamA")
//        pointsViewModel.setPoints(BPoints, "TeamB")
//
//        score1AButton = findViewById(R.id.scoreA1)
//        score2AButton = findViewById(R.id.scoreA2)
//        score3AButton = findViewById(R.id.scoreA3)
//        score1BButton = findViewById(R.id.scoreB1)
//        score2BButton = findViewById(R.id.scoreB2)
//        score3BButton = findViewById(R.id.scoreB3)
//        resetButton = findViewById(R.id.reset)
//        saveScoreButton = findViewById(R.id.saveScore)
//        displayScoreButton = findViewById(R.id.displayScore)
//        teamAScoreText = findViewById(R.id.teamAScore)
//        teamBScoreText = findViewById(R.id.teamBScore)
//
//        teamAScoreText.setText(APoints.toString())
//        teamBScoreText.setText(BPoints.toString())
//
//        saveScoreButton.setOnClickListener {
//            Log.d(TAGSAVE, "onClick() for SAVE is called, startActivityForResult()")
//            val teamAscore = pointsViewModel.getAPoints
//            val teamBscore = pointsViewModel.getBPoints
//            val intent = SaveScoreActivity.newIntent(this@MainActivity, teamAscore, teamBscore)
//            startActivityForResult(intent, REQUEST_SCORE_CODE)
//
//        }

//        resetButton.setOnClickListener {
//            pointsViewModel.setPoints(0, null)
//            teamAScoreText.setText("0")
//            teamBScoreText.setText("0")
//        }
//
//        score1AButton.setOnClickListener {
//            pointsViewModel.addPoint("TeamA")
//            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
//        }
//
//        score2AButton.setOnClickListener {
//            pointsViewModel.add2Points("TeamA")
//            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
//        }
//
//        score3AButton.setOnClickListener {
//            pointsViewModel.add3Points("TeamA")
//            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
//        }
//
//        score1BButton.setOnClickListener {
//            pointsViewModel.addPoint("TeamB")
//            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
//        }
//
//        score2BButton.setOnClickListener {
//            pointsViewModel.add2Points("TeamB")
//            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
//        }
//
//        score3BButton.setOnClickListener {
//            pointsViewModel.add3Points("TeamB")
//            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
//        }

    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_SCORE_CODE) {
            Log.d(TAGSAVE, "OnActivityResult() has been called")
            val isConfirmed = data?.getBooleanExtra(USER_CONFIRMED, false)
            if(isConfirmed!!) {
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not Saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TEAM_A, pointsViewModel.getAPoints)
        outState.putInt(KEY_TEAM_B, pointsViewModel.getBPoints)
    }*/

}