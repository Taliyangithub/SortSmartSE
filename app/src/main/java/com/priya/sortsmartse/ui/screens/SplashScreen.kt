package com.priya.sortsmartse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priya.sortsmartse.R
import com.priya.sortsmartse.ui.theme.GreenPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(2000L) // 2 second delay
        onNavigateToHome()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.splash_smart_waste),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.splash_sorting),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(id = R.string.splash_assistant),
            color = Color(0xFFC5E1A5), // Light yellowish green
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.splash_sort_smarter),
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(id = R.string.splash_recycle_better),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
