package com.example.matthewhilliard_boggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), ScoreFragment.ScoreFragmentListener, BoardFragment.BoardFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun newGamePressed() {
        Log.d("MainActivity", "onNewGameClicked() called")
        val boardFragment = supportFragmentManager.findFragmentById(R.id.fragment_board) as BoardFragment?
        boardFragment?.newGame(boardFragment.requireView())
    }

    override fun submitPressed(currentGuessText: CharSequence) {
        val scoreFragment = supportFragmentManager.findFragmentById(R.id.fragment_score) as ScoreFragment?
        scoreFragment?.checkWord(scoreFragment.requireView(), currentGuessText)
    }
}