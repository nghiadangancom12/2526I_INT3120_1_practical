package com.example.superheroes

import com.example.superheroes.ui.theme.SuperHeroesTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

class MainActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            SuperHeroesTheme{
                Surface(
                    modifier= Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                ){
                    SuperheroesApp()
                }
            }
        }
    }

    @Composable
    fun SuperheroesApp(){
        Scaffold(
            modifier=Modifier.fillMaxSize(),
            topBar={ TopAppBar() }
        ){ paddingValues->
            val heroes=HeroesRepository.heroes
            HeroesList(heroes=heroes, contentPadding=paddingValues)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(modifier: Modifier=Modifier){
        CenterAlignedTopAppBar(
            title={
                Text(
                    text= stringResource(R.string.app_name),
                    style=MaterialTheme.typography.headlineMedium,
                )
            },
            modifier=modifier
        )
    }

    @Preview(showBackground=true)
    @Composable
    fun SuperHeroesPreview(){
        SuperHeroesTheme{
            SuperheroesApp()
        }
    }
}
