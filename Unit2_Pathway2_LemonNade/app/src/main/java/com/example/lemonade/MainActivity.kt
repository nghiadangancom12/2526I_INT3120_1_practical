package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LemonadeTheme{
                Surface(
                    modifier=Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                ){
                    LemonadeApp()
                } } } } }

@Composable
fun LemonadeApp(){
    var step by remember{ mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0)}
    val imageRes=when(step){
        1->R.drawable.lemon_tree
        2->R.drawable.lemon_squeeze
        3 ->R.drawable.lemon_drink
        else->R.drawable.lemon_restart
    }
    val imageDescription=when(step){
        1->R.string.lemon_tree_content_description
        2-> R.string.lemon_content_description
        3->R.string.lemonade_content_description
        else-> R.string.empty_glass_content_description
    }
    val textRes=when(step){
        1 ->R.string.tap_lemon_tree
        2->R.string.tap_lemon
        3 -> R.string.tap_lemonade
        else ->R.string.tap_restart
    }
    Column(
        modifier=Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment=Alignment.CenterHorizontally,
        verticalArrangement=Arrangement.Center
    ){
        Text(
            text= stringResource(id=textRes),
            fontSize=22.sp,
            modifier=Modifier.padding(bottom=24.dp)
        )
        Image(
            painter=painterResource(id=imageRes),
            contentDescription= stringResource(id=imageDescription),
            modifier=Modifier
                .size(240.dp)
                .padding(12.dp).clickable{
                    when(step){
                        1->{ step=2
                            squeezeCount=(2..4).random()
                        }
                        2 ->{
                            squeezeCount--
                            if(squeezeCount==0){ step=3 }
                        }
                        3->{step=4}
                        else->{ step=1 }
                    }
                }
        )
    }
}
