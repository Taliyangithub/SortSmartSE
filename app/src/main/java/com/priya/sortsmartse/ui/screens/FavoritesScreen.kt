package com.priya.sortsmartse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.priya.sortsmartse.R
import com.priya.sortsmartse.model.WasteItem
import com.priya.sortsmartse.viewmodel.WasteViewModel
import com.priya.sortsmartse.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: WasteViewModel,
    onItemClick: (WasteItem) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(R.string.favorites, R.string.recent)

    val favoriteItems by viewModel.favoriteItems.collectAsState(initial = emptyList())

    val itemsToShow = if (selectedTabIndex == 0) {
        favoriteItems
    } else {
        emptyList() // Recent items mocked for now
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.favorites_and_history),
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = GreenPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = GreenPrimary
                )
            }
        ) {
            tabs.forEachIndexed { index, titleResId ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(stringResource(id = titleResId), fontWeight = FontWeight.SemiBold) },
                    selectedContentColor = GreenPrimary,
                    unselectedContentColor = Color.Gray
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (itemsToShow.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(id = R.string.no_items_found), color = Color.Gray)
                    }
                }
            } else {
                items(itemsToShow) { item ->
                    WasteItemRow(item = item, onClick = { onItemClick(item) })
                }
            }
        }
    }
}
