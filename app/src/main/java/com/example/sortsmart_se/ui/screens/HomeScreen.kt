package com.example.sortsmart_se.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.sortsmart_se.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sortsmart_se.ui.components.SearchBar
import com.example.sortsmart_se.ui.theme.GreenPrimary

import com.example.sortsmart_se.viewmodel.WasteViewModel

@Composable
fun HomeScreen(
    viewModel: WasteViewModel,
    onNavigateToSearch: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.smart_waste_sorting),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.OfflinePin, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Works Offline", color = GreenPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Read-only search bar that navigates to search screen
        Box(modifier = Modifier.clickable(onClick = {
            viewModel.updateSearchQuery("")
            onNavigateToSearch()
        })) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                    viewModel.updateSearchQuery("")
                    onNavigateToSearch()
                },
                readOnly = true,
                enabled = false
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.quick_categories),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickCategoryItem(icon = Icons.Default.WaterDrop, labelResId = R.string.plastic, iconTint = Color(0xFF42A5F5), onClick = {
                viewModel.updateSearchQuery("plastic")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.Description, labelResId = R.string.paper, iconTint = Color(0xFF90CAF9), onClick = {
                viewModel.updateSearchQuery("paper")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.WineBar, labelResId = R.string.glass, iconTint = Color(0xFF66BB6A), onClick = {
                viewModel.updateSearchQuery("glass")
                onNavigateToSearch()
            })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickCategoryItem(icon = Icons.Default.Restaurant, labelResId = R.string.food, iconTint = Color(0xFFFFB74D), onClick = {
                viewModel.updateSearchQuery("food")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.Build, labelResId = R.string.metal, iconTint = Color(0xFF9E9E9E), onClick = {
                viewModel.updateSearchQuery("metal")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.Delete, labelResId = R.string.residual, iconTint = Color(0xFF757575), onClick = {
                viewModel.updateSearchQuery("trash")
                onNavigateToSearch()
            })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            QuickCategoryItem(icon = Icons.Default.Warning, labelResId = R.string.hazardous, iconTint = Color(0xFFEF5350), onClick = {
                viewModel.updateSearchQuery("battery")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.MedicalServices, labelResId = R.string.medicine, iconTint = Color(0xFFE57373), onClick = {
                viewModel.updateSearchQuery("medicine")
                onNavigateToSearch()
            })
            QuickCategoryItem(icon = Icons.Default.Checkroom, labelResId = R.string.clothes, iconTint = Color(0xFFBA68C8), onClick = {
                viewModel.updateSearchQuery("clothes")
                onNavigateToSearch()
            })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            QuickCategoryItem(icon = Icons.Default.Computer, labelResId = R.string.electronics, iconTint = Color(0xFF607D8B), onClick = {
                viewModel.updateSearchQuery("electronics")
                onNavigateToSearch()
            })
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Browse Categories Card
        Card(
            onClick = {
                viewModel.updateSearchQuery("")
                onNavigateToSearch()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(GreenPrimary, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(id = R.string.browse_categories), fontWeight = FontWeight.Bold, color = GreenPrimary)
                    Text(stringResource(id = R.string.browse_categories_desc), fontSize = 12.sp, color = Color.Gray)
                }
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = GreenPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recent Searches Card
        Card(
            onClick = {
                viewModel.updateSearchQuery("")
                onNavigateToSearch()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Outlined.History, contentDescription = null, tint = Color(0xFF1976D2))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(id = R.string.recent_searches), fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
                    Text(stringResource(id = R.string.recent_searches_desc), fontSize = 12.sp, color = Color.Gray)
                }
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF0D47A1))
            }
        }
    }
}

@Composable
fun QuickCategoryItem(icon: ImageVector, labelResId: Int, iconTint: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFFF3F4F6), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = stringResource(id = labelResId), tint = iconTint, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = labelResId), fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
