package com.benhagy.gamefinder3.presentation.game_details_screen.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benhagy.gamefinder3.R
import com.benhagy.gamefinder3.presentation.game_details_screen.viewmodel.GameDetailsScreenViewModel
import com.benhagy.gamefinder3.presentation.ui.theme.Typography
import com.benhagy.gamefinder3.presentation.ui.theme.montserratFonts
import com.benhagy.gamefinder3.util.parseWebsiteAsHyperlink

@Composable
fun WeblinkDetailsItem(
    viewModel: GameDetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val context = LocalContext.current // for the website intent

    state.gameDetails?.let { gameDetails ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(4.dp)

        ) {
            Icon(
                imageVector = Icons.Rounded.Public,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp)
            )
            if (!gameDetails.website.isNullOrEmpty()) {
                ClickableText(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    text = parseWebsiteAsHyperlink(gameDetails.website),
                    style = TextStyle(
                        fontFamily = montserratFonts,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.2.sp
                    ),
                    softWrap = true,
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(gameDetails.website)
                            )
                        )
                    }
                )
            } else {
                Text(
                    text = stringResource(R.string.null_website_text),
                    style = Typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}