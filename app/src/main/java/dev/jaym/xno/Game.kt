package dev.jaym.xno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import dev.jaym.xno.databinding.ActivityGameBinding

class Game : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityGameBinding? = null
    //initializing a array of board which has array of buttons
    lateinit var board: Array<Array<MaterialButton?>>
    lateinit var PLAYER: String
    private var winnerFound: Boolean = false

    private var movesPlayed = 0
    //making a currentBoard array to save current board state every time a move is made
    private var currentBoardState = Array(3) {IntArray(3)}
    private var playerXPoints = 0
    private var playerOPoints = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val chosenSymbol = intent.getStringExtra("SYMBOL")

        PLAYER = chosenSymbol!!

        winnerFound = false

        binding?.tvTurn?.text = "Turn: Player $PLAYER"

        //adding array of buttons inside array of board
        board = arrayOf(
            arrayOf(binding?.btn1, binding?.btn2, binding?.btn3),
            arrayOf(binding?.btn4, binding?.btn5, binding?.btn6),
            arrayOf(binding?.btn7, binding?.btn8, binding?.btn9)
        )

        //handling click of each button using a common onclick listener
        for (row in board) {
            for (btn in row) {
                btn?.setOnClickListener(this)
            }
        }

        //initializing the current state of board
        initBoard()

        //implementing when play again button is clicked
        binding?.btnPlayAgain?.setOnClickListener {
            //initializing the board again
            initBoard()
            //making resetting turn count
            movesPlayed = 0
            //assigning originally chosen symbol's turn on reset
            PLAYER = chosenSymbol
            //clearing the result text on reset
            binding?.tvResult?.text = ""
            winnerFound = false
        }

        //implementing when reset button is clicked
        binding?.btnReset?.setOnClickListener {
            //initializing the board again
            initBoard()
            //making resetting turn count
            movesPlayed = 0
            //resetting player points variables
            playerXPoints = 0
            playerOPoints = 0
            //resetting player points count
            updatePlayerPoints(0, "X")
            updatePlayerPoints(0, "O")
            //assigning originally chosen symbol's turn on reset
            PLAYER = chosenSymbol
            //clearing the result text on reset
            binding?.tvResult?.text = ""
            winnerFound = false
        }
    }

    private fun initBoard() {
        //going through every element inside the array(i.e basically each button)
        for (i in 0..2) {
            for (j in 0..2) {
                currentBoardState[i][j] = -1
            }
        }
        //enabling all the buttons and removing all the text inside buttons
        for (row in board) {
            for (btn in row) {
                btn?.isEnabled = true
                btn?.text = ""
            }
        }
    }

    //handling clicks of button
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btn1 -> {
                updateBox(0, 0, PLAYER)
            }
            R.id.btn2 -> {
                updateBox(0, 1, PLAYER)
            }
            R.id.btn3 -> {
                updateBox(0, 2, PLAYER)
            }
            R.id.btn4 -> {
                updateBox(1, 0, PLAYER)
            }
            R.id.btn5 -> {
                updateBox(1, 1, PLAYER)
            }
            R.id.btn6 -> {
                updateBox(1, 2, PLAYER)
            }
            R.id.btn7 -> {
                updateBox(2, 0, PLAYER)
            }
            R.id.btn8 -> {
                updateBox(2, 1, PLAYER)
            }
            R.id.btn9 -> {
                updateBox(2, 2, PLAYER)
            }
        }
        //changing player turn name and X and O symbol on every click
        PLAYER = if (PLAYER == "X") "O" else "X"
        //increasing movesPlayed on every click
        movesPlayed++
        //updating the display according to the player
        updateDisplay("Turn: Player $PLAYER")

        //checking if there is a winner on every click
        checkWinner()

        //if 9 moves are played then game is draw so updating result
        if (movesPlayed == 9 && !winnerFound) {
            updateResult("Game Draw")
        }

    }

    private fun checkWinner() {
        //checking for rows
        for (i in 0..2) {
            //checking if Eg. (0,0) == (0,1) && (0,0) == (0,2) i.e checking if whole row has same value inside currentBoardState
            if (currentBoardState[i][0] == currentBoardState[i][1] && currentBoardState[i][0] == currentBoardState[i][2]) {
                //now checking if that value is 1 or 0 to determine if PLAYER X or O has won
                if (currentBoardState[i][0] == 1) {
                    updateResult( "PLAYER X WON")
                    //incrementing playerX points count on win
                    playerXPoints++
                    //updating points display
                    updatePlayerPoints(playerXPoints, "X")
                    winnerFound = true
                    //breaking so it does not continue to check further
                    break
                }else if (currentBoardState[i][0] == 0) {
                    updateResult("PLAYER O WON")
                    //incrementing playerO points count on win
                    playerOPoints++
                    //updating points display
                    updatePlayerPoints(playerOPoints, "O")
                    winnerFound = true
                    break
                }
            }
        }

        //checking for columns
        for (i in 0..2) {
            //checking if Eg. (0,0) == (1,0) && (0,0) == (2,0) i.e checking if whole column has same value inside currentBoardState
            if (currentBoardState[0][i] == currentBoardState[1][i] && currentBoardState[0][i] == currentBoardState[2][i]) {
                //now checking if that value is 1 or 0 to determine if PLAYER X or O has won
                if (currentBoardState[0][i] == 1) {
                    updateResult( "PLAYER X WON")
                    //incrementing playerX points count on win
                    playerXPoints++
                    //updating points display
                    updatePlayerPoints(playerXPoints, "X")
                    winnerFound = true
                    //breaking so it does not continue to check further
                    break
                }else if (currentBoardState[0][i] == 0) {
                    updateResult("PLAYER O WON")
                    //incrementing playerO points count on win
                    playerOPoints++
                    //updating points display
                    updatePlayerPoints(playerOPoints, "O")
                    winnerFound = true
                    break
                }
            }
        }

        //checking diagonals
        if (currentBoardState[0][0] == currentBoardState[1][1] && currentBoardState[0][0] == currentBoardState[2][2]) {
            //now checking if that value is 1 or 0 to determine if PLAYER X or O has won
            if (currentBoardState[0][0] == 1) {
                updateResult("PLAYER X WON")
                //incrementing playerX points count on win
                playerXPoints++
                //updating points display
                updatePlayerPoints(playerXPoints, "X")
                winnerFound = true

            }else if (currentBoardState[0][0] == 0) {
                updateResult("PLAYER O WON")
                //incrementing playerO points count on win
                playerOPoints++
                //updating points display
                updatePlayerPoints(playerOPoints, "O")
                winnerFound = true
            }
        }

        if (currentBoardState[0][2] == currentBoardState[1][1] && currentBoardState[0][2] == currentBoardState[2][0]){
            //now checking if that value is 1 or 0 to determine if PLAYER X or O has won
            if (currentBoardState[0][2] == 1) {
                updateResult("PLAYER X WON")
                //incrementing playerX points count on win
                playerXPoints++
                //updating points display
                updatePlayerPoints(playerXPoints, "X")
                winnerFound = true

            }else if (currentBoardState[0][2] == 0) {
                updateResult("PLAYER O WON")
                //incrementing playerO points count on win
                playerOPoints++
                //updating points display
                updatePlayerPoints(playerOPoints, "O")
                winnerFound = true
            }
        }
    }

    private fun updateBox(row: Int, col: Int, player: String) {
        val value = if (player == "X") 1 else 0
        //changing the box(button) text according to the row and col and player(X or O)
        board[row][col]?.text = player
        //disabling that button so it cannot be clicked again
        board[row][col]?.isEnabled = false
        //changing current board state by changing the value of the that element in the array according to the player
        currentBoardState[row][col] = value
    }

    //updating the player turn display
    private fun updateDisplay(s: String) {
        binding?.tvTurn?.text = s
    }

    //updating player points count
    private fun updatePlayerPoints(points: Int, player: String) {
        if (player == "X") {
            binding?.tvPlayerXPoints?.text = "$points"
        }else if (player == "O") {
            binding?.tvPlayerOPoints?.text = "$points"
        }
    }

    //updating the result display
    private fun updateResult(s: String) {
        binding?.tvResult?.text = s
        disableAllButtons()
    }

    //to disable all buttons on end of game
    private fun disableAllButtons() {
        for (row in board) {
            for (btn in row) {
                //disabling button
                btn?.isEnabled = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}