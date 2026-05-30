package com.example.lumira.ui.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumira.ui.onboarding.LumiraButton
import com.example.lumira.ui.theme.*
import com.example.lumira.viewmodel.ReadingViewModel

@Composable
fun QuestionScreen(
    readingViewModel: ReadingViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val isDark = isNightTime()
    val bg = if (isDark) DarkBackground else LightBackground
    val textPrimary = if (isDark) DarkTextPrimary else LightTextPrimary
    val textSecondary = if (isDark) DarkTextSecondary else LightTextSecondary
    val primary = if (isDark) DarkPrimary else LightPrimary
    val primaryDark = if (isDark) DarkPrimaryDark else LightPrimaryDark
    val accentFill = if (isDark) DarkAccentFill else LightAccentFill
    val accentBorder = if (isDark) DarkAccentBorder else LightAccentBorder
    val border = if (isDark) DarkBorder else LightBorder
    val surface = if (isDark) DarkSurface else LightSurface

    val selectedStyle by readingViewModel.selectedStyle.collectAsState()
    val selectedArea by readingViewModel.selectedArea.collectAsState()
    val selectedMood by readingViewModel.selectedMood.collectAsState()

    var question by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val suggestions = listOf(
        "What should I focus on today?",
        "How can I improve my $selectedArea?",
        "What energy am I carrying right now?",
        "What do I need to let go of?",
        "What opportunity am I missing?"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 20.sp,
                color = textSecondary,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "Your question",
                style = MaterialTheme.typography.titleMedium,
                color = textPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ask anything about your $selectedArea. The more specific, the better.",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = question,
            onValueChange = { if (it.length <= 200) question = it },
            placeholder = {
                Text(
                    text = "What's on your mind?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primary,
                unfocusedBorderColor = border,
                focusedContainerColor = surface,
                unfocusedContainerColor = surface,
                focusedTextColor = textPrimary,
                unfocusedTextColor = textPrimary,
                cursorColor = primary
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${question.length}/200",
            style = MaterialTheme.typography.bodyMedium,
            color = textSecondary,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "SUGGESTIONS",
            style = MaterialTheme.typography.labelSmall,
            color = textSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        suggestions.forEach { suggestion ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(surface)
                    .border(0.5.dp, border, RoundedCornerShape(10.dp))
                    .clickable { question = suggestion }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = suggestion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textSecondary,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        LumiraButton(
            text = "Get my reading",
            onClick = {
                if (question.isNotBlank()) {
                    keyboardController?.hide()
                    readingViewModel.setQuestion(question.trim())
                    onContinue()
                }
            },
            backgroundColor = if (question.isBlank()) border else primaryDark,
            textColor = if (question.isBlank()) textSecondary else if (isDark) DarkTextPrimary else LightBackground
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}