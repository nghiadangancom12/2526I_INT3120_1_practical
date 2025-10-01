package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val gameFlow: StateFlow<GameUiState> = _uiState.asStateFlow()

    var playerGuess by mutableStateOf("")
        private set

    private var guessedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    init {
        startNewGame()
    }

    fun startNewGame() {
        guessedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = getUnusedRandomWordAndShuffle())
    }

    fun updatePlayerGuess(guessedWord: String) {
        playerGuess = guessedWord
    }

    fun submitUserGuess() {
        if (playerGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score + SCORE_INCREASE
            advanceToNextRound(updatedScore)
        } else {
            _uiState.update { it.copy(isGuessedWordWrong = true) }
        }
        updatePlayerGuess("")
    }

    fun goToNextWord() {
        advanceToNextRound(_uiState.value.score)
        updatePlayerGuess("")
    }

    private fun advanceToNextRound(updatedScore: Int) {
        if (guessedWords.size == MAX_NO_OF_WORDS){
            _uiState.update {
                it.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            _uiState.update {
                it.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = getUnusedRandomWordAndShuffle(),
                    currentWordCount = it.currentWordCount +1,
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffledWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) tempWord.shuffle()
        return String(tempWord)
    }

    private fun getUnusedRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        return if (guessedWords.contains(currentWord)) getUnusedRandomWordAndShuffle()
        else {
            guessedWords.add(currentWord)
            shuffledWord(currentWord)
        }
    }
}
