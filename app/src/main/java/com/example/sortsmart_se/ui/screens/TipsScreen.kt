package com.example.sortsmart_se.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sortsmart_se.R
import com.example.sortsmart_se.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
    ) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.eco_tips_title), fontWeight = FontWeight.Bold, color = GreenPrimary) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TipCard(
                    icon = Icons.Default.EnergySavingsLeaf,
                    titleResId = R.string.tip_1_title,
                    descResId = R.string.tip_1_desc,
                    iconTint = Color(0xFF4CAF50)
                )
            }
            item {
                TipCard(
                    icon = Icons.Default.Eco,
                    titleResId = R.string.tip_2_title,
                    descResId = R.string.tip_2_desc,
                    iconTint = Color(0xFF8BC34A)
                )
            }
            item {
                TipCard(
                    icon = Icons.Default.Recycling,
                    titleResId = R.string.tip_3_title,
                    descResId = R.string.tip_3_desc,
                    iconTint = Color(0xFF03A9F4)
                )
            }
            item {
                TipCard(
                    icon = Icons.Default.Newspaper,
                    titleResId = R.string.tip_4_title,
                    descResId = R.string.tip_4_desc,
                    iconTint = Color(0xFF9C27B0)
                )
            }
        }
    }
}

@Composable
fun TipCard(icon: ImageVector, titleResId: Int, descResId: Int, iconTint: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(iconTint.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(32.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = titleResId),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = descResId),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
