package com.android.basketballcounter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.io.File
import java.util.*

private const val ARG_TEAM_ID = "teamA_id"
private const val REQUEST_A_PHOTO = 2
private const val REQUEST_B_PHOTO = 3

class TeamFragment : Fragment() {

    interface Callbacks {
        fun onDisplaySelected(string: String)
    }

    private var callbacks: Callbacks? = null
    private lateinit var beatBox: BeatBox

    private lateinit var teams: table_game
    private lateinit var teamAFile: File
    private lateinit var teamBFile: File
    private lateinit var photoUri: Uri
    private lateinit var photoUri2: Uri

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
    private lateinit var weatherTextView: TextView
    private lateinit var photoAButton: ImageButton
    private lateinit var photoBButton: ImageButton
    private lateinit var soundAButton: ImageButton
    private lateinit var soundBButton: ImageButton
    private lateinit var photoAView: ImageView
    private lateinit var photoBView: ImageView

//    private lateinit var mediaPlayer: MediaPlayer
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
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beatBox = context?.assets?.let { BeatBox(it) }!!
        teams = table_game()
        teams.teamAName = "Equipo A"
        teams.teamBName = "Equipo B"
        val teamID: UUID = arguments?.getSerializable(ARG_TEAM_ID) as UUID
        teamDetailViewModel.loadTeam(teamID)
        teamAFile = teamDetailViewModel.getAPhotoFile(teams)
        teamBFile = teamDetailViewModel.getBPhotoFile(teams)

        photoUri = FileProvider.getUriForFile(requireActivity(),
            "com.android.basketballcounter.fileprovider",
            teamAFile)
        photoUri2 = FileProvider.getUriForFile(requireActivity(),
            "com.android.basketballcounter.fileprovider",
            teamBFile)

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
        photoAButton = view.findViewById(R.id.teamA_camera) as ImageButton
        photoAView = view.findViewById(R.id.teamA_photo) as ImageView
        photoBButton = view.findViewById(R.id.teamB_camera) as ImageButton
        photoBView = view.findViewById(R.id.teamB_photo) as ImageView
        soundAButton = view.findViewById(R.id.teamA_sound) as ImageButton
        soundBButton = view.findViewById(R.id.teamB_sound) as ImageButton
        weatherTextView = view.findViewById(R.id.weatherText)

        val weatherLiveData: LiveData<WeatherItem> = OpenWeatherFetcher().fetchWeather()
        val tempLiveDate: LiveData<TemperatureItem> = OpenWeatherFetcher().fetchTemp()
        var text = ""
        weatherLiveData.observe(
            this,
            Observer { weatherItems ->
                Log.d(Debug, "$weatherItems")
                text = "${weatherItems.main}\n${weatherItems.description}\n"
                weatherTextView.setText(text)

            })

        tempLiveDate.observe(
            this,
            Observer { weatherItems ->
                Log.d(Debug, "$weatherItems")
                val temp = String.format("%.3f",weatherItems.temp - 273.15)
                text += "Temp: $temp" + " Celsius"
                weatherTextView.setText(text)

            })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamDetailViewModel.teamLiveData.observe(
            viewLifecycleOwner,
            Observer { teams ->
                teams?.let {
                    this.teams = teams
                    teamAFile = teamDetailViewModel.getAPhotoFile(teams)
                    teamBFile = teamDetailViewModel.getBPhotoFile(teams)
                    photoUri = FileProvider.getUriForFile(requireActivity(),
                    "com.android.basketballcounter.fileprovider",
                    teamAFile)
                    photoUri = FileProvider.getUriForFile(requireActivity(),
                        "com.android.basketballcounter.fileprovider",
                        teamBFile)
                    updateUI()

                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()

        soundAButton.apply {
            setOnClickListener {
                val uri: Uri = Uri.parse("android.resource://com.android.basketballcounter/" + R.raw.applause4)

                val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )

                    setDataSource(requireContext(), uri)
                    prepare()
                    start()
                }
//                beatBox.play(beatBox.sounds[0])
            }
        }

        soundBButton.apply {
            setOnClickListener {
                beatBox.play(beatBox.sounds[1])
            }
        }

        photoBButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
//            if (resolvedActivity == null) {
//                isEnabled = false
//            }

            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri2)

                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)

                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri2,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }

                startActivityForResult(captureImage, REQUEST_B_PHOTO)
            }
        }

        photoAButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
//            if (resolvedActivity == null) {
//                isEnabled = false
//            }

            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)

                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }

                startActivityForResult(captureImage, REQUEST_A_PHOTO)
            }
        }

        resetButton.setOnClickListener {
            teams.teamAScore = 0
            teams.teamBScore = 0
            teamAScoreText.setText("0")
            teamBScoreText.setText("0")
        }

        saveScoreButton.setOnClickListener {
            teamDetailViewModel.saveTeam(teams)
        }

        score1AButton.setOnClickListener {
            teams.teamAScore++
            updateUI()
        }

        score2AButton.setOnClickListener {
            teams.teamAScore += 2
            updateUI()
        }

        score3AButton.setOnClickListener {
            teams.teamAScore += 3
            updateUI()
        }

        score1BButton.setOnClickListener {
            teams.teamBScore++
            updateUI()
        }

        score2BButton.setOnClickListener {
            teams.teamBScore += 2
            updateUI()
        }

        score3BButton.setOnClickListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return

            requestCode == REQUEST_A_PHOTO -> {
                requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoView()
            }
            requestCode == REQUEST_B_PHOTO -> {
                requireActivity().revokeUriPermission(photoUri2, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                updatePhotoView()
            }
        }
    }

    private fun updateUI() {
        teamAScoreText.setText(teams.teamAScore.toString())
        teamBScoreText.setText(teams.teamBScore.toString())
        teamAText.setText(teams.teamAName)
        teamBText.setText(teams.teamBName)
        updatePhotoView()
    }

    private fun updatePhotoView() {
        if(teamAFile.exists()) {
            val bitmap = getScaledBitmap(teamAFile.path, requireActivity())
            photoAView.setImageBitmap(bitmap)
        } else {
            photoAView.setImageDrawable(null)
        }

        if(teamBFile.exists()) {
            val bitmap = getScaledBitmap(teamBFile.path, requireActivity())
            photoBView.setImageBitmap(bitmap)
        } else {
            photoBView.setImageDrawable(null)
        }

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