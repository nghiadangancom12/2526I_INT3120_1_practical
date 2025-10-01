package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    UiCard()
                }
            }
        }
    }
}

@Composable
fun UiCard() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(2f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(R.string.avatar_desc),
                modifier = Modifier.size(140.dp).clip(CircleShape).background(Color.White).padding(8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(R.string.user_name), fontSize = 36.sp, color = Color.White)
            Text(text = stringResource(R.string.user_title), fontSize = 20.sp, color = Color(0xFFB0BEC5))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 48.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ContactRow(iconI = Icons.Default.Phone, txt = stringResource(R.string.user_phone))
            ContactRow(iconI = Icons.Default.Email, txt = stringResource(R.string.user_email))
        }
    }
}

@Composable
fun ContactRow(iconI: androidx.compose.ui.graphics.vector.ImageVector, txt: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = iconI, contentDescription = null, tint = Color(0xFF64FFDA), modifier = Modifier.size(26.dp))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = txt, color = Color(0xFFECEFF1), fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCard() {
    BusinessCardTheme { UiCard() }
}
