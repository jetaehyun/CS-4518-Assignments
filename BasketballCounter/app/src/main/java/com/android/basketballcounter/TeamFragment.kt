package com.android.basketballcounter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val ARG_TEAM_ID = "teamA_id"

class TeamFragment : Fragment() {

    interface Callbacks {
        fun onDisplaySelected(string: String)
    }

    private var callbacks: Callbacks? = null

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
    private lateinit var teamAText: TextView
    private lateinit var teamBScoreText: TextView
    private lateinit var teamBText: TextView
    private lateinit var teams: table_game

/*    private val pointsViewModel: PointsViewModel by lazy {
        ViewModelProviders.of(this).get(PointsViewModel::class.java)
    }*/

    private val teamDetailViewModel: TeamDetailViewModel by lazy {
        ViewModelProviders.of(this).get(TeamDetailViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teams = table_game()
        teams.teamAName = "Equipo A"
        teams.teamBName = "Equipo B"
        val teamID: UUID = arguments?.getSerializable(ARG_TEAM_ID) as UUID
        teamDetailViewModel.loadTeam(teamID)

        Log.d(Debug, "TeamFragment onCreate()")

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Debug, "TeamFragment onCreateView()")
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
        teamAText = view.findViewById(R.id.teamA)
        teamBText = view.findViewById(R.id.teamB)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamDetailViewModel.teamLiveData.observe(
            viewLifecycleOwner,
            Observer { teams ->
                teams?.let {
                    this.teams = teams
                    updateUI()

                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        resetButton.setOnClickListener {
            teams.teamAScore = 0
            teams.teamBScore = 0
            teamAScoreText.setText("0")
            teamBScoreText.setText("0")
        }

        saveScoreButton.setOnClickListener {
//            Log.d(Debug, "TeamFragment onStart() saveScoreButton Listener")
            // code to create random games
//            for(x in 0..150) {
//                val test = table_game()
//                test.teamAName = "Test ${x + 1} A"
//                test.teamBName = "Test ${x + 1} B"
//                test.teamAScore = (0..100).random()
//                test.teamBScore = (0..100).random()
//                teamDetailViewModel.createTeam(test)
//            }
            teamDetailViewModel.saveTeam(teams)

        }

        score1AButton.setOnClickListener {
//            pointsViewModel.addPoint("TeamA")
            teams.teamAScore++
            updateUI()
        }

        score2AButton.setOnClickListener {
//            pointsViewModel.add2Points("TeamA")
            teams.teamAScore += 2
            updateUI()
        }

        score3AButton.setOnClickListener {
//            pointsViewModel.add3Points("TeamA")
            teams.teamAScore += 3
            updateUI()
        }

        score1BButton.setOnClickListener {
//            pointsViewModel.addPoint("TeamB")
            teams.teamBScore++
            updateUI()
        }

        score2BButton.setOnClickListener {
//            pointsViewModel.add2Points("TeamB")
            teams.teamBScore += 2
            updateUI()
        }

        score3BButton.setOnClickListener {
//            pointsViewModel.add3Points("TeamB")
            teams.teamBScore += 3
            updateUI()
        }

        displayScoreButton.setOnClickListener {
            teamDetailViewModel.saveTeam(teams)

            when {
                teams.teamAScore > teams.teamBScore -> {
                    callbacks?.onDisplaySelected("TeamA")
                }
                teams.teamAScore < teams.teamBScore -> {
                    callbacks?.onDisplaySelected("TeamB")
                }
                else -> {
                    callbacks?.onDisplaySelected("Tie")
                }
            }
        }
    }

    private fun updateUI() {
        teamAScoreText.setText(teams.teamAScore.toString())
        teamBScoreText.setText(teams.teamBScore.toString())
        teamAText.setText(teams.teamAName)
        teamBText.setText(teams.teamBName)
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        fun newInstance(team: UUID): TeamFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TEAM_ID, team)
            }

            return TeamFragment().apply {
                arguments = args
            }

        }
    }
}