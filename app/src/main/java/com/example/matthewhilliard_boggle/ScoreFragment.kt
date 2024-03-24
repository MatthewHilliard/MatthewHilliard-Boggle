package com.example.matthewhilliard_boggle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ScoreFragment : Fragment() {

    interface ScoreFragmentListener {
        fun newGamePressed()
    }

    private var listener: ScoreFragmentListener? = null

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
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ScoreFragmentListener) {
            listener = context
        }
    }

    fun checkWord(view: View, currentGuessText: CharSequence){
        var score = view.findViewById<TextView>(R.id.scoreText)
        currScore += 1
        score.text = "SCORE: $currScore"
    }
}