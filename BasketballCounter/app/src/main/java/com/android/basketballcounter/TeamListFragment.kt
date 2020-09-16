package com.android.basketballcounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TeamListFragment: Fragment() {

    private lateinit var teamRecyclerView: RecyclerView
    private var adapter: TeamAdapter? = null

    private val teamListViewModel: TeamListViewModel by lazy {
        ViewModelProviders.of(this).get(TeamListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_list, container, false)

        teamRecyclerView = view.findViewById(R.id.team_recycler_view) as RecyclerView
        teamRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }

    private fun updateUI() {
        val teams = teamListViewModel.teamList
        adapter = TeamAdapter(teams)
        teamRecyclerView.adapter = adapter
    }

    private inner class TeamAdapter(var teams: MutableList<MutableList<Team>>)
        : RecyclerView.Adapter<TeamHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
            val view = layoutInflater.inflate(R.layout.list_item_team, parent, false)
            return TeamHolder(view)
        }

        override fun getItemCount() = teams.size

        override fun onBindViewHolder(holder: TeamHolder, position: Int) {
            val team = teams[position]
            holder.apply {
                titleTextView.text = team[0].gameTitle
                dateTextView.text = team[0].date.toString()
                teamAName.text = team[0].teamName
                teamAFinal.text = team[0].points.toString()
                teamBName.text = team[1].teamName
                teamBFinal.text = team[1].points.toString()
            }
        }
    }
    private inner class TeamHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = itemView.findViewById(R.id.game_title)
        val dateTextView: TextView = itemView.findViewById(R.id.game_date)
        val teamAName: TextView = itemView.findViewById(R.id.teamAName)
        val teamBName: TextView = itemView.findViewById(R.id.teamBName)
        val teamAFinal: TextView = itemView.findViewById(R.id.teamAFinal)
        val teamBFinal: TextView = itemView.findViewById(R.id.teamBFinal)
    }

    companion object {
        fun newInstance(): TeamListFragment {
            return TeamListFragment()
        }
    }
}