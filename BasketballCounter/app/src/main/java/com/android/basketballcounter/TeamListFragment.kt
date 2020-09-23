package com.android.basketballcounter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_TEAMFRAGMENT_FILTER = "teamlistfragment"

class TeamListFragment: Fragment() {

    interface Callbacks {
        fun onTeamSelected(team: UUID)
    }

    private val teamListViewModel: TeamListViewModel by lazy {
        ViewModelProviders.of(this).get(TeamListViewModel::class.java)
    }

    private var callbacks: Callbacks? = null
    private lateinit var teamRecyclerView: RecyclerView
    private var adapter: TeamAdapter? = TeamAdapter(emptyList())
    private lateinit var whoWon: String


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
        whoWon = arguments?.getSerializable(ARG_TEAMFRAGMENT_FILTER) as String

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_list, container, false)
        Log.d(Debug, "TeamListFragment onCreateView()")
        teamRecyclerView = view.findViewById(R.id.team_recycler_view) as RecyclerView
        teamRecyclerView.layoutManager = LinearLayoutManager(context)

        teamRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        teamListViewModel.teamListLiveData.observe(
            viewLifecycleOwner,
            Observer { teams ->
                teams?.let {
                    Log.i(Debug, "Got teams ${teams.size}")
                    updateUI(teams)
                }

            }
        )
    }

    private fun updateUI(teams: List<table_game>) {

        val list = mutableListOf<table_game>()

        for(element in teams) {
            if(whoWon == "Tie") break

            list += when {
                element.teamAScore > element.teamBScore && whoWon == "TeamA" -> {
                    mutableListOf(element)
                }
                element.teamAScore < element.teamBScore && whoWon == "TeamB" -> {
                    mutableListOf(element)
                }
                else -> {
                    continue
                }
            }
        }

        adapter = if(whoWon == "Tie") TeamAdapter(teams) else TeamAdapter(list)
        teamRecyclerView.adapter = adapter
    }

    private inner class TeamAdapter(var teams: List<table_game>)
        : RecyclerView.Adapter<TeamHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
            val view = layoutInflater.inflate(R.layout.list_item_team, parent, false)
            return TeamHolder(view)
        }

        override fun getItemCount() = teams.size

        override fun onBindViewHolder(holder: TeamHolder, position: Int) {
            val team = teams[position]
            holder.bind(team)
        }

    }

    private inner class TeamHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var team: table_game

        val winningMarkImageView: ImageView = itemView.findViewById((R.id.winningMark))
        val titleTextView: TextView = itemView.findViewById(R.id.game_title)
        val dateTextView: TextView = itemView.findViewById(R.id.game_date)
        val teamAName: TextView = itemView.findViewById(R.id.teamAName)
        val teamBName: TextView = itemView.findViewById(R.id.teamBName)
        val teamAFinal: TextView = itemView.findViewById(R.id.teamAFinal)
        val teamBFinal: TextView = itemView.findViewById(R.id.teamBFinal)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(team: table_game) {
            this.team = team

            titleTextView.text = "Juego de WPI"
            dateTextView.text = team.date.toString()
            teamAName.text = team.teamAName
            teamAFinal.text = team.teamAScore.toString()
            teamBName.text = team.teamBName
            teamBFinal.text = team.teamBScore.toString()
            when {
                team.teamAScore > team.teamBScore -> {
                    winningMarkImageView.setImageResource(R.drawable.lettera)
                }
                team.teamAScore < team.teamBScore -> {
                    winningMarkImageView.setImageResource(R.drawable.letterb)
                }
                else -> {
                    winningMarkImageView.setImageResource(R.drawable.lettert)
                }
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onTeamSelected(team.id)
        }

    }

    companion object {

        fun newInstance(string: String): TeamListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TEAMFRAGMENT_FILTER, string)
            }
            return TeamListFragment().apply {
                arguments = args
            }
        }
    }
}