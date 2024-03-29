package com.example.matthewhilliard_boggle

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import java.io.FileNotFoundException
import java.util.Locale
import kotlin.math.abs

class ScoreFragment : Fragment() , SensorEventListener {

    interface ScoreFragmentListener {
        fun newGamePressed()
    }

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var newGameOnCooldown = false
    private val cooldownDuration: Long = 3000

    private var listener: ScoreFragmentListener? = null
    private var wordList: List<String>? = null
    private val usedWords = mutableSetOf<String>()
    private var currScore = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.score_fragment, container, false)

        view.findViewById<Button>(R.id.newGameButton).setOnClickListener {
            currScore = 0
            view.findViewById<TextView>(R.id.scoreText).text = "SCORE: 0"
            usedWords.clear()
            listener?.newGamePressed()
        }

        wordList = requireContext().assets.open("words.txt").bufferedReader().readLines().map { it.toUpperCase(
            Locale.ROOT) }

        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        return view
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            val sum = abs(x) + abs(y) + abs(z)

            if (sum > 16   && !newGameOnCooldown) {
                newGameOnCooldown = true
                Handler(Looper.getMainLooper()).postDelayed({
                    newGameOnCooldown = false
                }, cooldownDuration)
                currScore = 0
                view?.findViewById<TextView>(R.id.scoreText)?.text  = "SCORE: 0"
                usedWords.clear()
                listener?.newGamePressed()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Nothing needed here
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ScoreFragmentListener) {
            listener = context
        }
    }

    fun checkWord(view: View, currentGuessText: CharSequence){
        val score = view.findViewById<TextView>(R.id.scoreText)

        try {
            if(usedWords.contains(currentGuessText.toString())){
                currScore -= 10
                Toast.makeText(requireContext(), "Word already used, -10", Toast.LENGTH_SHORT).show()
            } else if (wordList?.contains(currentGuessText.toString()) == true && currentGuessText.isNotEmpty() && isValidWord(currentGuessText)) {
                val addScore = calculateScore(currentGuessText)
                currScore += addScore
                usedWords.add(currentGuessText.toString())
                Toast.makeText(requireContext(), "That's correct, +$addScore", Toast.LENGTH_SHORT).show()
            }
            else {
                if(currScore - 10 < 0){
                    currScore = 0
                } else{
                    currScore -= 10
                }
                Toast.makeText(requireContext(), "That's incorrect, -10", Toast.LENGTH_SHORT).show()
            }

            score.text = "SCORE: $currScore"
        } catch (e: FileNotFoundException) {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show()
        }

        score.text = "SCORE: $currScore"
    }

    private fun calculateScore(currentGuessText: CharSequence): Int{
        var score = 0
        val special = listOf('S', 'Z', 'P', 'X', 'Q')
        var usedSpecial = false
        for (char in currentGuessText){
            if (char in listOf('A', 'E', 'I', 'O', 'U')) {
                score += 5
            } else {
                score += 1
                if (char in special) {
                    usedSpecial = true
                }
            }
        }
        if(usedSpecial){
            return score * 2
        }
        return score
    }

    private fun isValidWord(word: CharSequence): Boolean {
        if (word.length < 4){
            return false
        }

        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        var vowelCount = 0
        for (char in word) {
            if (char in vowels) {
                vowelCount++
            }
        }
        return vowelCount >= 2
    }
}