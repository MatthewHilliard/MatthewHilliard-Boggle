package com.example.matthewhilliard_boggle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class BoardFragment : Fragment() {
    private val boardButtons: Array<Button> by lazy {
        arrayOf(
            buttonOne, buttonTwo, buttonThree, buttonFour,
            buttonFive, buttonSix, buttonSeven, buttonEight,
            buttonNine, buttonTen, buttonEleven, buttonTwelve,
            buttonThirteen, buttonFourteen, buttonFifteen, buttonSixteen
        )
    }

    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button
    private lateinit var buttonTen: Button
    private lateinit var buttonEleven: Button
    private lateinit var buttonTwelve: Button
    private lateinit var buttonThirteen: Button
    private lateinit var buttonFourteen: Button
    private lateinit var buttonFifteen: Button
    private lateinit var buttonSixteen: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.board_fragment, container, false)

        newGame(view)
        return view
    }

    private fun newGame(view: View){
        buttonOne = view.findViewById(R.id.buttonOne)
        buttonTwo = view.findViewById(R.id.buttonTwo)
        buttonThree = view.findViewById(R.id.buttonThree)
        buttonFour = view.findViewById(R.id.buttonFour)
        buttonFive = view.findViewById(R.id.buttonFive)
        buttonSix = view.findViewById(R.id.buttonSix)
        buttonSeven = view.findViewById(R.id.buttonSeven)
        buttonEight = view.findViewById(R.id.buttonEight)
        buttonNine = view.findViewById(R.id.buttonNine)
        buttonTen = view.findViewById(R.id.buttonTen)
        buttonEleven = view.findViewById(R.id.buttonEleven)
        buttonTwelve = view.findViewById(R.id.buttonTwelve)
        buttonThirteen = view.findViewById(R.id.buttonThirteen)
        buttonFourteen = view.findViewById(R.id.buttonFourteen)
        buttonFifteen = view.findViewById(R.id.buttonFifteen)
        buttonSixteen = view.findViewById(R.id.buttonSixteen)

        randomizeLetters()
        resetButtonColor()
    }

    private fun randomizeLetters(){
        boardButtons.forEach { button ->
            val randomLetter = ('A'..'Z').random()
            button.text = randomLetter.toString()
        }
    }

    private fun resetButtonColor(){

    }
}