package com.example.matthewhilliard_boggle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.math.abs

class BoardFragment : Fragment() {

    interface BoardFragmentListener {
        fun submitPressed(text: CharSequence)
    }
    private var listener: BoardFragment.BoardFragmentListener? = null

    private val boardButtons: Array<Button> by lazy {
        arrayOf(
            buttonOne, buttonTwo, buttonThree, buttonFour,
            buttonFive, buttonSix, buttonSeven, buttonEight,
            buttonNine, buttonTen, buttonEleven, buttonTwelve,
            buttonThirteen, buttonFourteen, buttonFifteen, buttonSixteen
        )
    }

    private val pressedButtons = mutableListOf<Button>()

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

    private var prevButton: Button? = null

    private lateinit var currentGuessText: TextView
    private lateinit var clearButton: Button
    private lateinit var submitButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.board_fragment, container, false)

        newGame(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BoardFragment.BoardFragmentListener) {
            listener = context
        }
    }

    fun newGame(view: View){
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

        clearButton = view.findViewById(R.id.clearButton)
        clearButton.setOnClickListener(){
            clearPressed()
        }

        submitButton = view.findViewById(R.id.submitButton)
        submitButton.setOnClickListener(){
            submitPressed(view)
        }

        currentGuessText = view.findViewById(R.id.currentGuessText)
        currentGuessText.text = ""

        pressedButtons.clear()
        randomizeLetters()
        resetButtons()
    }

    private fun randomizeLetters(){
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        var vowelCount = 0
        while(vowelCount < 2) {
            vowelCount = 0
            boardButtons.forEach { button ->
                val randomLetter = ('A'..'Z').random()

                if (vowels.contains(randomLetter)) {
                    vowelCount++
                }

                button.text = randomLetter.toString()
            }
        }
    }

    private fun resetButtons(){
        prevButton = null
        boardButtons.forEachIndexed { index, button ->
            button.tag = index
            button.setBackgroundColor(Color.WHITE)
            button.setOnClickListener {
                letterPressed(button)
            }
        }
    }

    private fun letterPressed(button: Button){
        if (pressedButtons.contains(button)) {
            Toast.makeText(requireContext(), "This letter has already been used", Toast.LENGTH_SHORT).show()
            return
        } else if(prevButton != null){
            val currRow = button.tag.toString().toInt() / 4
            val currCol = button.tag.toString().toInt() % 4
            val prevRow = prevButton!!.tag.toString().toInt() / 4
            val prevCol = prevButton!!.tag.toString().toInt() % 4

            if (!isValidConnection(currRow, currCol, prevRow, prevCol)) {
                Toast.makeText(requireContext(), "You may only select connected letters", Toast.LENGTH_SHORT).show()
                return
            }
        }
        button.setBackgroundColor(Color.GRAY)
        currentGuessText.append(button.text)
        pressedButtons.add(button)
        prevButton = button
    }

    private fun isValidConnection(currRow: Int, currCol: Int, prevRow: Int, prevCol: Int): Boolean {
        val rowDiff = abs(currRow - prevRow)
        val colDiff = abs(currCol - prevCol)

        return rowDiff <= 1 && colDiff <= 1
    }

    private fun clearPressed(){
        currentGuessText.text = ""
        pressedButtons.clear()
        resetButtons()
    }

    private fun submitPressed(view: View){
        listener?.submitPressed(currentGuessText.text)
        currentGuessText.text = ""
        pressedButtons.clear()
        resetButtons()
    }
}