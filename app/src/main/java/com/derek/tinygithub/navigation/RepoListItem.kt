package com.derek.tinygithub.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.derek.tinygithub.ui.icon.TgIcons
import com.derek.tinygithub.ui.theme.TinyGitHubTheme
import com.derek.tinygithub.ui.component.DynamicAsyncImage

/**
 *
 * @author: derekzchen@2025/4/25
 */
@Composable
fun RepoListItem(
    name: String,
    description: String,
    avatarUrl: String,
    forks: Int,
    iconModifier: Modifier = Modifier,
) {
    ListItem(
        leadingContent = {
            AvatarIcon(avatarUrl, iconModifier.size(48.dp))
        },
        headlineContent = { Text(text = name) },
        supportingContent = { Text(text = description) },
        trailingContent = { Text(text = "$forks") },
    )
}

@Composable
private fun AvatarIcon(avatarUrl: String, modifier: Modifier = Modifier) {
    if (avatarUrl.isEmpty()) {
        Icon(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp),
            imageVector = TgIcons.Person,
            // decorative image
            contentDescription = null,
        )
    } else {
        DynamicAsyncImage(
            imageUrl = avatarUrl,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun RepoListItemPreview() {
    TinyGitHubTheme {
        Surface {
            RepoListItem(
                name = "freeCodeCamp/freeCodeCamp",
                description = "freeCodeCamp.org's open-source codebase and curriculum. Learn to code for free.",
                avatarUrl = "https://avatars.githubusercontent.com/u/9892522?v=4",
                forks = 39899,
            )
        }
    }
}