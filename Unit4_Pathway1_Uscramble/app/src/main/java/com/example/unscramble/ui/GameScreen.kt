package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val currentGameState by gameViewModel.gameFlow.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier.statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)

        GameLayout(
            onUserGuessChanged = { gameViewModel.updatePlayerGuess(it) },
            wordCount = currentGameState.currentWordCount,
            userGuess = gameViewModel.playerGuess,
            onKeyboardDone = { gameViewModel.submitUserGuess() },
            currentScrambledWord = currentGameState.currentScrambledWord,
            isGuessWrong = currentGameState.isGuessedWordWrong,
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(mediumPadding)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(modifier = Modifier.fillMaxWidth(), onClick = { gameViewModel.submitUserGuess() }) {
                Text(text = stringResource(R.string.submit), fontSize = 16.sp)
            }

            OutlinedButton(onClick = { gameViewModel.goToNextWord() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.skip), fontSize = 16.sp)
            }
        }

        GameStatus(score = currentGameState.score, modifier = Modifier.padding(20.dp))

        if (currentGameState.isGameOver) {
            FinalScoreDialog(score = currentGameState.score, onPlayAgain = { gameViewModel.startNewGame() })
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = stringResource(R.string.score, score),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(
    currentScrambledWord: String,
    wordCount: Int,
    isGuessWrong: Boolean,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentPadding = dimensionResource(R.dimen.padding_medium)

    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(
                modifier = Modifier.clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(Alignment.End),
                text = stringResource(R.string.word_count, wordCount),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(text = currentScrambledWord, style = MaterialTheme.typography.displayMedium)
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface
                ),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) Text(stringResource(R.string.wrong_guess)) else Text(stringResource(R.string.enter_your_word))
                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onKeyboardDone() })
            )
        }
    }
}

@Composable
private fun FinalScoreDialog(score: Int, onPlayAgain: () -> Unit, modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = { activity.finish() }) { Text(text = stringResource(R.string.exit)) }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) { Text(text = stringResource(R.string.play_again)) }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}
