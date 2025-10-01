package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woof.ui.theme.WoofTheme

class MainActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            WoofTheme{
                Surface(modifier=Modifier.fillMaxSize()){
                    WoofApp()
                }
            }
        }
    }
}

@Composable
fun WoofApp(){
    Scaffold(
        topBar={WoofTopAppBar()}
    ){ padding->
        LazyColumn(contentPadding=padding){
            items(dogsList){
                DogItem(dog=it,
                    modifier=Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }
        }
    }
}

@Composable
fun DogItem(dog: Dog, modifier: Modifier=Modifier){
    var expanded by remember{ mutableStateOf(false)}
    Card(modifier=modifier){
        Column(
            modifier=Modifier.animateContentSize(
                animationSpec=spring(
                    dampingRatio=Spring.DampingRatioNoBouncy,
                    stiffness=Spring.StiffnessMedium
                )
            )
        ){
            Row(
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ){
                DogIcon(dog.drawableRes)
                DogInformation(dog.label,dog.age)
                Spacer(Modifier.weight(1f))
                DogItemButton(expanded=expanded, onClick={expanded=!expanded})
            }
            if(expanded){
                DogHobby(
                    dog.description,
                    modifier=Modifier.padding(
                        start=dimensionResource(R.dimen.padding_medium),
                        top=dimensionResource(R.dimen.padding_small),
                        bottom=dimensionResource(R.dimen.padding_medium),
                        end=dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
private fun DogItemButton(expanded:Boolean,onClick:()->Unit,modifier:Modifier=Modifier){
    IconButton(onClick=onClick, modifier=modifier){
        Icon(
            imageVector=if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription=stringResource(R.string.expand_button_content_description),
            tint=MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier:Modifier=Modifier){
    CenterAlignedTopAppBar(
        title={
            Row(verticalAlignment=Alignment.CenterVertically){
                Image(
                    modifier=Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter=painterResource(R.drawable.ic_woof_logo),
                    contentDescription=null
                )
                Text(
                    text=stringResource(R.string.app_name),
                    style=MaterialTheme.typography.displayLarge,
                    fontSize=28.sp
                )
            }
        },
        modifier=modifier
    )
}

@Composable
fun DogIcon(@DrawableRes dogIcon:Int, modifier:Modifier=Modifier){
    Image(
        modifier=modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale=ContentScale.Crop,
        painter=painterResource(dogIcon),
        contentDescription=null
    )
}

@Composable
fun DogInformation(@StringRes dogName:Int, dogAge:Int, modifier:Modifier=Modifier){
    Column(modifier=modifier){
        Text(
            text=stringResource(dogName),
            style=MaterialTheme.typography.displayMedium,
            fontSize=22.sp,
            modifier=Modifier.padding(top=dimensionResource(R.dimen.padding_small))
        )
        Text(
            text=stringResource(R.string.years_old,dogAge),
            style=MaterialTheme.typography.bodyLarge,
            fontSize=18.sp
        )
    }
}

@Composable
fun DogHobby(@StringRes dogHobby:Int, modifier:Modifier=Modifier){
    Column(modifier=modifier){
        Text(
            text=stringResource(R.string.about),
            style=MaterialTheme.typography.labelSmall,
            fontSize=16.sp
        )
        Text(
            text=stringResource(dogHobby),
            style=MaterialTheme.typography.bodyLarge,
            fontSize=18.sp,
            modifier=Modifier.padding(top=6.dp, bottom=8.dp)
        )
    }
}

@Preview
@Composable
fun WoofPreview(){
    WoofTheme(darkTheme=false){ WoofApp() }
}

@Preview
@Composable
fun WoofDarkThemePreview(){
    WoofTheme(darkTheme=true){ WoofApp() }
}
