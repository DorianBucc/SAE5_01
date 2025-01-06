package com.etu.sae_501.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etu.sae_501.data.model.ScannedObject
import com.etu.sae_501.data.database.DatabaseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Accès à la base de données et récupération des données
    val database = DatabaseProvider.getDatabase(context)
    val scannedObjectDao = database.scannedObjectDao()

    // Liste des objets scannés
    var historyItems by remember { mutableStateOf<List<ScannedObject>>(emptyList()) }

    // Récupérer les objets scannés depuis la base de données
    LaunchedEffect(true) {
        coroutineScope.launch {
            scannedObjectDao.getAllObjects().collect { objects ->
                historyItems = objects
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Historique", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            historyItems.forEach { item ->
                HistoriqueItem(
                    iconRes = android.R.drawable.ic_menu_gallery, // Placeholder icon
                    title = item.name,
                    subtitle = "Date de scan: ${java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(item.timestamp))}",
                    progress = item.confidence.toInt(),
                    onClick = {
                        Toast.makeText(context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
                    },
                    onDelete = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                scannedObjectDao.deleteObject(item)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HistoriqueItem(
    iconRes: Int,
    title: String,
    subtitle: String,
    progress: Int,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val progressColor = when {
        progress < 20 -> Color.Red
        progress < 70 -> Color(0xFFFFCC02)
        else -> Color(0xFF35C759)
    }

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(color = progressColor, shape = CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$progress %",
                        color = if (progressColor == Color(0xFFFFCC02) || progressColor == Color(0xFF35C759)) Color.Black else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
        }

        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = (-256).dp, y = 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Supprimer") },
                onClick = {
                    expanded = false
                    onDelete()
                }
            )
        }
    }
}
