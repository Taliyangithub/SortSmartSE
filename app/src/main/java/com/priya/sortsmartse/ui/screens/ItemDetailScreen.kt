package com.priya.sortsmartse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Share
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Recycling
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.priya.sortsmartse.R
import com.priya.sortsmartse.model.RecycleAction
import com.priya.sortsmartse.model.WasteCategory
import com.priya.sortsmartse.ui.theme.GreenPrimary
import com.priya.sortsmartse.viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: WasteViewModel,
    onNavigateBack: () -> Unit
) {
    val item by viewModel.getItemById(itemId).collectAsState(initial = null)

    val context = LocalContext.current

    item?.let { wasteItem ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back))
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Sorting ${wasteItem.name}? It goes in ${wasteItem.category.name}. Preparation: ${wasteItem.preparation} #SortSmartSE")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = GreenPrimary
                            )
                        }
                        IconButton(onClick = { viewModel.toggleFavorite(wasteItem) }) {
                            Icon(
                                imageVector = if (wasteItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.favorite),
                                tint = if (wasteItem.isFavorite) GreenPrimary else Color.Gray
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            containerColor = Color.White
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                if (wasteItem.category == WasteCategory.HAZARDOUS ||
                    wasteItem.action == RecycleAction.HAZARDOUS) {
                    Surface(
                        color = Color(0xFFFFF3CD),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning", tint = Color(0xFF856404))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "HAZARDOUS WASTE: Do not put in regular bins. Take to nearest recycling center.",
                                color = Color(0xFF856404),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFFF3F4F6), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(wasteItem.name.take(1), fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Column {
                        Text(text = wasteItem.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = wasteItem.action.bgColor,
                            shape = RoundedCornerShape(4.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Recycling,
                                    contentDescription = null,
                                    tint = wasteItem.action.textColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = wasteItem.action.label,
                                    color = wasteItem.action.textColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = wasteItem.instruction, fontSize = 14.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sections
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailSection(icon = Icons.Outlined.Recycling, title = stringResource(id = R.string.how_to_dispose), content = wasteItem.howToDispose)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFE5E7EB))
                        DetailSection(icon = Icons.AutoMirrored.Outlined.EventNote, title = stringResource(id = R.string.preparation), content = wasteItem.preparation)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFE5E7EB))
                        DetailSection(icon = Icons.AutoMirrored.Outlined.HelpOutline, title = stringResource(id = R.string.why), content = wasteItem.why)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.toggleFavorite(wasteItem) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Icon(
                        imageVector = if (wasteItem.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (wasteItem.isFavorite) stringResource(id = R.string.remove_from_favorites) else stringResource(id = R.string.save_to_favorites),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GreenPrimary)
        }
    }
}

@Composable
fun DetailSection(icon: ImageVector, title: String, content: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(imageVector = icon, contentDescription = title, tint = GreenPrimary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content, fontSize = 14.sp, color = Color.Gray, lineHeight = 20.sp)
        }
    }
}
