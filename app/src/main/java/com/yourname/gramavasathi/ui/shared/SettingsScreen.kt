package com.yourname.gramavasathi.ui.shared

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.yourname.gramavasathi.R

@Composable
fun SettingsScreen(onBack: () -> Unit) {

    val currentLocales = AppCompatDelegate.getApplicationLocales()
    val currentLangCode = if (currentLocales.isEmpty) "en" else currentLocales.get(0)?.language ?: "en"

    val localeCodes = mapOf(
        "English" to "en",
        "ಕನ್ನಡ" to "kn",
        "हिंदी" to "hi"
    )

    var selectedLanguage by remember { 
        mutableStateOf(localeCodes.entries.find { it.value == currentLangCode }?.key ?: "English") 
    }

    val languages = listOf("English", "ಕನ್ನಡ", "हिंदी")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F2))
    ) {

        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4A7C59))
//                .padding(8.dp),
                .padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = stringResource(R.string.language_label),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5F5E5A)
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    languages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedLanguage = language
                                    val code = localeCodes[language] ?: "en"
                                    AppCompatDelegate.setApplicationLocales(
                                        LocaleListCompat.forLanguageTags(code)
                                    )
                                }
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 14.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = language,
                                fontSize = 14.sp,
                                color = Color(0xFF2C2C2A)
                            )
                            if (selectedLanguage == language) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF4A7C59),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        if (language != languages.last()) {
                            HorizontalDivider(
                                color = Color(0xFFE2DDD5),
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "App version: 1.0.0",
                fontSize = 12.sp,
                color = Color(0xFF888780)
            )
        }
    }
}