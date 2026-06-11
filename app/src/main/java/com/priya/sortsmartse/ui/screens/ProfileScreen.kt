package com.priya.sortsmartse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priya.sortsmartse.ui.theme.GreenPrimary
import com.priya.sortsmartse.viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: WasteViewModel, onNavigateToFavorites: () -> Unit) {
    var expandedMunicipality by remember { mutableStateOf(false) }
    var selectedMunicipality by remember { mutableStateOf("Malmö (Sysav)") }
    val municipalities = listOf("Malmö (Sysav)", "Stockholm (SVOA)", "Göteborg (Renova)", "Lund", "Helsingborg")

    var isSwedish by remember { mutableStateOf(true) }
    
    val favoriteItems by viewModel.favoriteItems.collectAsState(initial = emptyList())
    val itemsSorted = favoriteItems.size
    val impactKg = "%.1f".format(itemsSorted * 0.19f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = { Text("Profile", fontWeight = FontWeight.Bold, color = GreenPrimary) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(modifier = Modifier.padding(16.dp)) {
            // Gamification Stats Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB74D))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Your Impact", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = GreenPrimary)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("🎉 You have correctly sorted $itemsSorted items!", fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("That's approximately $impactKg kg saved from the landfill.", color = Color.Gray, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))

            // Favorites Navigation
            SettingRow(
                icon = Icons.Default.Favorite,
                title = "Saved Favorites & History",
                subtitle = "View your bookmarked items",
                onClick = onNavigateToFavorites
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF3F4F6))

            // Municipality Selector
            Box {
                SettingRow(
                    icon = Icons.Default.LocationCity,
                    title = "Municipality",
                    subtitle = selectedMunicipality,
                    onClick = { expandedMunicipality = true }
                )
                DropdownMenu(
                    expanded = expandedMunicipality,
                    onDismissRequest = { expandedMunicipality = false }
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

            Spacer(modifier = Modifier.height(4.dp))
            Text("* Rules currently optimized for Sysav/Malmö standards.", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 56.dp))

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF3F4F6))

            // Language Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = Color.Gray)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Language", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(text = if (isSwedish) "Svenska" else "English", color = Color.Gray, fontSize = 14.sp)
                }
                Switch(
                    checked = isSwedish,
                    onCheckedChange = { isSwedish = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = GreenPrimary, checkedTrackColor = Color(0xFFC8E6C9))
                )
            }
        }
    }
}

@Composable
fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
    }
}
