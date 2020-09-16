package com.android.basketballcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class TeamFragment : Fragment() {

    private lateinit var teamA: Team
    private lateinit var teamB: Team

    private lateinit var score3AButton: Button
    private lateinit var score2AButton: Button
    private lateinit var score1AButton: Button
    private lateinit var score3BButton: Button
    private lateinit var score2BButton: Button
    private lateinit var score1BButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveScoreButton: Button
    private lateinit var displayScoreButton: Button
    private lateinit var teamAScoreText: TextView
    private lateinit var teamBScoreText: TextView

    private val pointsViewModel: PointsViewModel by lazy {
        ViewModelProviders.of(this).get(PointsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)
        score2AButton = view.findViewById(R.id.scoreA2)
        score3AButton = view.findViewById(R.id.scoreA3)
        score1BButton = view.findViewById(R.id.scoreB1)
        score1AButton = view.findViewById(R.id.scoreA1)
        score2BButton = view.findViewById(R.id.scoreB2)
        score3BButton = view.findViewById(R.id.scoreB3)
        resetButton = view.findViewById(R.id.reset)
        saveScoreButton = view.findViewById(R.id.saveScore)
        displayScoreButton = view.findViewById(R.id.displayScore)
        teamAScoreText = view.findViewById(R.id.teamAScore)
        teamBScoreText = view.findViewById(R.id.teamBScore)

        teamAScoreText.setText("0")
        teamBScoreText.setText("0")
        return view
    }

    override fun onStart() {
        super.onStart()
        resetButton.setOnClickListener {
            pointsViewModel.setPoints(0, null)
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