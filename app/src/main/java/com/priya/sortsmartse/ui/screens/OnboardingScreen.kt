package com.priya.sortsmartse.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priya.sortsmartse.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(0) }

    var expandedMunicipality by remember { mutableStateOf(false) }
    var selectedMunicipality by remember { mutableStateOf("Malmö (Sysav)") }
    val municipalities = listOf("Malmö (Sysav)", "Stockholm (SVOA)", "Göteborg (Renova)", "Lund", "Helsingborg")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentStep == 0) {
            // Slide 1: Welcome
            Text("Welcome to SortSmart SE 🌱", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GreenPrimary, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Your intelligent guide to recycling in Sweden. Scan items or search the database to find exactly which bin they belong in.",
                fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { currentStep = 1 },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Next", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            // Slide 2: Setup
            Text("Customize Your Setup", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GreenPrimary, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Different municipalities have slightly different recycling rules. Choose yours below:",
                fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Municipality Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedMunicipality,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Municipality") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expandedMunicipality = true }) {
                            Text("▼")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedMunicipality,
                    onDismissRequest = { expandedMunicipality = false },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    municipalities.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                selectedMunicipality = selectionOption
                                expandedMunicipality = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("* Rules currently optimized for Sysav/Malmö standards.", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = {
                    // Save to SharedPreferences
                    val prefs = context.getSharedPreferences("sortsmart_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putBoolean("onboarded", true).putString("municipality", selectedMunicipality).apply()
                    onFinish()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Get Started", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
