package org.hoods.forecastly.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.hoods.forecastly.ui.components.NavigationType
import org.hoods.forecastly.ui.daily.DailyScreen
import org.hoods.forecastly.ui.home.HomeScreen

private data class NavigationItemContent(
    val icon: ImageVector,
    val text: String
)

@Composable
fun ForecastNavigation(modifier: Modifier = Modifier, navigationType: NavigationType) {
    val (selectedIndex, setSelectedIndex) = rememberSaveable { mutableIntStateOf(0) }
    val navigationContentList = listOf(
        NavigationItemContent(icon = Icons.Default.Home, "Home"),
        NavigationItemContent(icon = Icons.Default.DateRange, "Forecast")
    )
    ForecastNavContent(
        currentTab = selectedIndex,
        navigationType = navigationType,
        onTabPress = {setSelectedIndex(it)},
        navigationItemContentList = navigationContentList,
        modifier = modifier
    )

}

@Composable
private fun ForecastNavContent(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    onTabPress: ((Int) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    currentTab: Int
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                when (currentTab) {
                    0 -> HomeScreen(navigationType = navigationType,modifier = Modifier.weight(1f))
                    1 -> DailyScreen(modifier = Modifier.weight(1f))
                }
            } else {
                Row {
                    HomeScreen(navigationType = navigationType, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(32.dp))
                    DailyScreen(modifier = Modifier.weight(1f))
                }
            }
            AnimatedVisibility(
                visible = navigationType == NavigationType.BOTTOM_NAVIGATION
            ){
                ForecastBottomNavigation(
                    currentTab = currentTab,
                    onTabPress = onTabPress,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
private fun ForecastBottomNavigation(
    modifier: Modifier = Modifier,
    currentTab: Int,
    onTabPress: (Int) -> Unit,
    navigationItemContentList: List<NavigationItemContent>
) {
    NavigationBar(modifier) {
        navigationItemContentList.forEachIndexed { index, navigationItemContent ->
            NavigationBarItem(
                selected = currentTab == index,
                onClick = { onTabPress(index) },
                icon = {
                    Icon(
                        imageVector = navigationItemContent.icon,
                        contentDescription = navigationItemContent.text
                    )
                }
            )
        }
    }

}







