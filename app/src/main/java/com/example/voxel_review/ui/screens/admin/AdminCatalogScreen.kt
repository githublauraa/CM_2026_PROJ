package com.example.voxel_review.ui.screens.admin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.voxel_review.ui.theme.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.text.style.TextOverflow
import com.example.voxel_review.ui.utils.GameImage
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.data.dtos.VideoGameDto
import com.example.voxel_review.ui.theme.VoxelBackground

@Composable
fun AdminCatalogScreen(viewModel: AdminCatalogViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var archiveTarget by remember { mutableStateOf<VideoGameDto?>(null) }
    val draft = state.draft
    BackHandler(enabled = draft != null) { if (!state.busy) viewModel.cancelEdit() }

    MaterialTheme(colorScheme = darkColorScheme(
        primary = VoxelPrimary, onPrimary = VoxelBackground,
        background = VoxelBackground, onBackground = VoxelTextPrimary,
        surface = VoxelSurface, onSurface = VoxelTextPrimary,
        surfaceVariant = VoxelSurfaceVariant, onSurfaceVariant = VoxelTextSecondary,
        outline = VoxelBorder
    )) {
    Surface(color = VoxelBackground, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(VoxelSurface, VoxelBackground))).imePadding().padding(horizontal = 20.dp, vertical = 8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = { if (draft != null) viewModel.cancelEdit() else onBack() }, enabled = !state.busy) {
                    Text(stringResource(R.string.admin_back))
                }
                if (state.isAdmin && draft == null) {
                    TextButton(onClick = viewModel::refresh, enabled = !state.busy) { Text(stringResource(R.string.admin_refresh)) }
                }
            }
            Text(stringResource(if (draft == null) R.string.admin_title else if (draft.id.isEmpty()) R.string.admin_add else R.string.admin_edit),
                style = MaterialTheme.typography.headlineSmall)
            state.message?.let { Text(stringResource(it), modifier = Modifier.padding(vertical = 8.dp)) }
            if (state.busy) LinearProgressIndicator(Modifier.fillMaxWidth().padding(vertical = 8.dp))
            when {
                state.checkingAccess -> Unit
                !state.isAdmin -> {
                    Text(stringResource(R.string.admin_denied), modifier = Modifier.padding(vertical = 16.dp))
                    OutlinedButton(onClick = viewModel::refresh, enabled = !state.busy) { Text(stringResource(R.string.admin_refresh)) }
                }
                draft != null -> {
                    Column(Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        DraftField(R.string.admin_name, draft.name, state.busy) { viewModel.updateDraft(draft.copy(name = it)) }
                        DraftField(R.string.admin_description, draft.description, state.busy, singleLine = false) { viewModel.updateDraft(draft.copy(description = it)) }
                        DraftField(R.string.admin_developer, draft.developer, state.busy) { viewModel.updateDraft(draft.copy(developer = it)) }
                        DraftField(R.string.admin_platforms, draft.platforms, state.busy) { viewModel.updateDraft(draft.copy(platforms = it)) }
                        DraftField(R.string.admin_date, draft.releaseDate, state.busy) { viewModel.updateDraft(draft.copy(releaseDate = it)) }
                        DraftField(R.string.admin_cover, draft.imageUrl, state.busy) { viewModel.updateDraft(draft.copy(imageUrl = it)) }
                        DraftField(R.string.admin_genres, draft.genres, state.busy) { viewModel.updateDraft(draft.copy(genres = it)) }
                        Text(stringResource(R.string.admin_form_hint), style = MaterialTheme.typography.bodySmall)
                        Button(onClick = viewModel::save, enabled = !state.busy && draft.isValid(), modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(R.string.admin_save))
                        }
                        OutlinedButton(onClick = viewModel::cancelEdit, enabled = !state.busy, modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(R.string.admin_cancel))
                        }
                    }
                }
                else -> {
                    Button(onClick = { viewModel.edit() }, enabled = !state.busy, modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                        Text(stringResource(R.string.admin_add))
                    }
                    if (state.games.isEmpty() && !state.busy) Text(stringResource(R.string.admin_empty))
                    LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(state.games, key = { it.videoGameId }) { game ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(24.dp),
                                border = BorderStroke(1.dp, VoxelBorder),
                                colors = CardDefaults.cardColors(containerColor = VoxelSurface)
                            ) {
                                Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    GameImage(
                                        imageUrl = game.imageUrl.orEmpty(),
                                        contentDescription = game.name,
                                        modifier = Modifier.width(90.dp).height(124.dp).clip(RoundedCornerShape(16.dp))
                                    )
                                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(game.name, style = MaterialTheme.typography.titleLarge,
                                            maxLines = 2, overflow = TextOverflow.Ellipsis)
                                        Text(game.developer, color = VoxelTextSecondary,
                                            style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text(game.platform, color = VoxelPrimary,
                                            style = MaterialTheme.typography.labelMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                        if (game.archived) {
                                            Surface(color = VoxelSurfaceVariant, shape = RoundedCornerShape(8.dp)) {
                                                Text(stringResource(R.string.admin_archived),
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                    style = MaterialTheme.typography.labelSmall)
                                            }
                                        }
                                    }
                                }
                                HorizontalDivider(color = VoxelBorder, modifier = Modifier.padding(horizontal = 14.dp))
                                Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    FilledTonalButton(onClick = { viewModel.edit(game) }, enabled = !state.busy,
                                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                                        Text(stringResource(R.string.admin_edit))
                                    }
                                    OutlinedButton(onClick = { archiveTarget = game }, enabled = !state.busy,
                                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                                        Text(stringResource(if (game.archived) R.string.admin_restore else R.string.admin_archive))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    archiveTarget?.let { game ->
        AlertDialog(
            onDismissRequest = { archiveTarget = null },
            title = { Text(stringResource(if (game.archived) R.string.admin_restore else R.string.admin_archive)) },
            text = { Text(stringResource(if (game.archived) R.string.admin_restore_confirm else R.string.admin_archive_confirm, game.name)) },
            confirmButton = {
                TextButton(onClick = { archiveTarget = null; viewModel.archive(game) }) { Text(stringResource(R.string.admin_confirm)) }
            },
            dismissButton = { TextButton(onClick = { archiveTarget = null }) { Text(stringResource(R.string.admin_cancel)) } }
        )
    }
    }
}

@Composable
private fun DraftField(label: Int, value: String, busy: Boolean, singleLine: Boolean = true, onChange: (String) -> Unit) {
    OutlinedTextField(value = value, onValueChange = onChange, label = { Text(stringResource(label) + " *") },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = VoxelTextPrimary, unfocusedTextColor = VoxelTextPrimary,
            focusedContainerColor = VoxelSurface, unfocusedContainerColor = VoxelSurface,
            focusedLabelColor = VoxelPrimary, unfocusedLabelColor = VoxelTextSecondary,
            focusedBorderColor = VoxelPrimary, unfocusedBorderColor = VoxelBorder,
            cursorColor = VoxelPrimary
        ),
        enabled = !busy, singleLine = singleLine, minLines = if (singleLine) 1 else 4, modifier = Modifier.fillMaxWidth())
}
