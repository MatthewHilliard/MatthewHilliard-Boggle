package com.example.matthewhilliard_boggle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.Locale

class ScoreFragment : Fragment() {

    interface ScoreFragmentListener {
        fun newGamePressed()
    }

    private var listener: ScoreFragmentListener? = null
    private var wordList: List<String>? = null
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
            listener?.newGamePressed()
        }

        wordList = requireContext().assets.open("words.txt").bufferedReader().readLines().map { it.toUpperCase(
            Locale.ROOT) }

        return view
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
            if (wordList?.contains(currentGuessText.toString()) == true) {
                currScore += 1
                Toast.makeText(requireContext(), "That's correct, +1", Toast.LENGTH_SHORT).show()
            } else {
                currScore -= 10
                Toast.makeText(requireContext(), "That's incorrect, -10", Toast.LENGTH_SHORT).show()
            }

            score.text = "SCORE: $currScore"
        } catch (e: FileNotFoundException) {
            Toast.makeText(requireContext(), "File not found", Toast.LENGTH_SHORT).show()
        }

        score.text = "SCORE: $currScore"
    }
}