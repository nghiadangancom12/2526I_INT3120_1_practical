package com.example.superheroes

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superheroes.ui.theme.SuperHeroesTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    heroes: List<Hero>,
    modifier: Modifier=Modifier,
    contentPadding: PaddingValues= PaddingValues(0.dp)
){
    val visibleState=remember{
        MutableTransitionState(false).apply{ targetState=true }
    }

    AnimatedVisibility(
        visibleState=visibleState,
        enter= fadeIn(animationSpec=spring(dampingRatio= DampingRatioLowBouncy)),
        exit= fadeOut(),
        modifier = modifier
    ){
        LazyColumn(contentPadding=contentPadding){
            itemsIndexed(heroes){ index, hero ->
                HeroListItem(
                    hero=hero,
                    modifier=Modifier
                        .padding(horizontal=18.dp, vertical=12.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness= StiffnessVeryLow,
                                    dampingRatio= DampingRatioLowBouncy
                                ),
                                initialOffsetY={ it * (index+1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun HeroListItem(hero: Hero, modifier: Modifier=Modifier){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation=3.dp),
        modifier = modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .padding(20.dp)
                .sizeIn(minHeight=78.dp)
        ){
            Column(modifier=Modifier.weight(1f)) {
                Text(
                    text= stringResource(hero.nameStringRes),
                    style= MaterialTheme.typography.displaySmall,
                    fontSize=22.sp
                )
                Text(
                    text= stringResource(hero.descriptionStringRes),
                    style= MaterialTheme.typography.bodyLarge,
                    fontSize=17.sp,
                    modifier= Modifier.padding(top=6.dp)
                )
            }
            Spacer(Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(10.dp))
            ){
                Image(
                    painter= painterResource(hero.imageResource),
                    contentDescription=null,
                    alignment= Alignment.TopCenter,
                    contentScale= ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode=Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroPreview(){
    val hero=Hero(R.string.hero1,R.string.description1,R.drawable.android_superhero1)
    SuperHeroesTheme{
        HeroListItem(hero=hero)
    }
}

@Preview("Heroes List")
@Composable
fun HeroesPreview(){
    SuperHeroesTheme(darkTheme=false){
        Surface(color=MaterialTheme.colorScheme.background){
            HeroesList(heroes=HeroesRepository.heroes)
        }
    }
}
