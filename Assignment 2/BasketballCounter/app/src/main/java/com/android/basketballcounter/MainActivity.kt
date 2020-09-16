package com.android.basketballcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private lateinit var score3AButton: Button
    private lateinit var score2AButton: Button
    private lateinit var score1AButton: Button
    private lateinit var score3BButton: Button
    private lateinit var score2BButton: Button
    private lateinit var score1BButton: Button
    private lateinit var resetButton: Button
    private lateinit var teamAScoreText: TextView
    private lateinit var teamBScoreText: TextView

    private val pointsViewModel: PointsViewModel by lazy {
        ViewModelProviders.of(this).get(PointsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        score1AButton = findViewById(R.id.scoreA1)
        score2AButton = findViewById(R.id.scoreA2)
        score3AButton = findViewById(R.id.scoreA3)
        score1BButton = findViewById(R.id.scoreB1)
        score2BButton = findViewById(R.id.scoreB2)
        score3BButton = findViewById(R.id.scoreB3)
        resetButton = findViewById(R.id.reset)
        teamAScoreText = findViewById(R.id.teamAScore)
        teamBScoreText = findViewById(R.id.teamBScore)

        resetButton.setOnClickListener {
            pointsViewModel.resetPoints()
            teamAScoreText.setText("0")
            teamBScoreText.setText("0")
        }

        score1AButton.setOnClickListener {
            pointsViewModel.addPoint("TeamA")
            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
        }

        score2AButton.setOnClickListener {
            pointsViewModel.add2Points("TeamA")
            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
        }

        score3AButton.setOnClickListener {
            pointsViewModel.add3Points("TeamA")
            teamAScoreText.setText(pointsViewModel.getAPoints.toString())
        }

        score1BButton.setOnClickListener {
            pointsViewModel.addPoint("TeamB")
            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
        }

        score2BButton.setOnClickListener {
            pointsViewModel.add2Points("TeamB")
            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
        }

        score3BButton.setOnClickListener {
            pointsViewModel.add3Points("TeamB")
            teamBScoreText.setText(pointsViewModel.getBPoints.toString())
        }

    }
}