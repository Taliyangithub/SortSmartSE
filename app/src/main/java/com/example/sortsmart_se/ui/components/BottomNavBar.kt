package com.example.sortsmart_se.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.sortsmart_se.R
import com.example.sortsmart_se.ui.theme.GreenPrimary

data class BottomNavItem(
    val titleResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem(R.string.nav_home, Icons.Filled.Home, Icons.Outlined.Home, "home"),
    BottomNavItem(R.string.nav_scan, Icons.Outlined.QrCodeScanner, Icons.Outlined.QrCodeScanner, "scan"),
    BottomNavItem(R.string.nav_tips, Icons.Outlined.Lightbulb, Icons.Outlined.Lightbulb, "tips"),
    BottomNavItem(R.string.nav_profile, Icons.Filled.Person, Icons.Outlined.Person, "profile")
)

@Composable
fun SortSmartBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = GreenPrimary
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute.startsWith(item.route)
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = stringResource(id = item.titleResId)
                    )
                },
                label = { Text(stringResource(id = item.titleResId)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = GreenPrimary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = GreenPrimary,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
