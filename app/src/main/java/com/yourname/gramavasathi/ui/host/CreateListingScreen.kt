//
//
//package com.yourname.gramavasathi.ui.host
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.FilterChip
//import androidx.compose.material3.FilterChipDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.yourname.gramavasathi.data.model.Listing
//import com.yourname.gramavasathi.util.ScoreCalculator
//import com.yourname.gramavasathi.viewmodel.HostViewModel
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun CreateListingScreen(
//    onListingPublished: () -> Unit,
//    onBack: () -> Unit,
//    viewModel: HostViewModel = hiltViewModel()
//) {
//    val score by viewModel.readinessScore.collectAsState()
//    val isPublishing by viewModel.isPublishing.collectAsState()
//
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var villageName by remember { mutableStateOf("") }
//    var district by remember { mutableStateOf("") }
//    var taluk by remember { mutableStateOf("") }
//    var nearestLandmark by remember { mutableStateOf("") }
//    var hostName by remember { mutableStateOf("") }
//    var hostBio by remember { mutableStateOf("") }
//    var priceText by remember { mutableStateOf("") }
//    var showSuccess by remember { mutableStateOf(false) }
//    val selectedActivities = remember { mutableStateListOf<String>() }
//    val selectedAmenities = remember { mutableStateListOf<String>() }
//    val selectedImageUris = remember { mutableStateListOf<Uri>() }
//
//    val activities = listOf(
//        "cow_milking" to "🐄 Cow Milking",
//        "birdwatching" to "🦜 Birdwatching",
//        "local_cooking" to "🍛 Local Cooking",
//        "field_plowing" to "🌾 Field Plowing",
//        "fishing" to "🎣 Fishing",
//        "nature_walk" to "🌿 Nature Walk",
//        "folk_interaction" to "🎭 Folk Interaction"
//    )
//    val amenities = listOf(
//        "safe_water" to "💧 Safe Water",
//        "western_toilet" to "🚿 Western Toilet",
//        "food_included" to "🍽 Food Included",
//        "family_friendly" to "👨‍👩‍👧 Family Friendly"
//    )
//
//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetMultipleContents()
//    ) { uris ->
//        uris.take(6 - selectedImageUris.size).forEach { uri ->
//            if (uri !in selectedImageUris) selectedImageUris.add(uri)
//        }
//    }
//
//    val canPublish = title.isNotBlank() &&
//            villageName.isNotBlank() &&
//            hostName.isNotBlank() &&
//            score >= 50
//
//    // Success dialog
//    if (showSuccess) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.5f)),
//            contentAlignment = Alignment.Center
//        ) {
//            Card(
//                colors = CardDefaults.cardColors(
//                    containerColor = Color.White
//                ),
//                shape = RoundedCornerShape(16.dp)
//            ) {
//                Column(
//                    modifier = Modifier.padding(28.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text("🎉", fontSize = 48.sp)
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Text(
//                        "Stay added successfully!",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF2C2C2A)
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(
//                        "Your listing is now live and\nvisible to guests.",
//                        fontSize = 13.sp,
//                        color = Color(0xFF5F5E5A)
//                    )
//                    Spacer(modifier = Modifier.height(20.dp))
//                    Button(
//                        onClick = { onListingPublished() },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0xFF4A7C59)
//                        ),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Go to Dashboard")
//                    }
//                }
//            }
//        }
//        return
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFFAF7F2))
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF4A7C59))
//                .padding(
//                    top = 40.dp,
//                    start = 8.dp,
//                    end = 8.dp,
//                    bottom = 8.dp
//                ),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = onBack) {
//                Icon(
//                    Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.White
//                )
//            }
//            Text(
//                text = "Create Your Listing",
//                color = Color.White,
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium
//            )
//        }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // Score indicator
//            Card(
//                colors = CardDefaults.cardColors(
//                    containerColor = if (score >= 50)
//                        Color(0xFFEAF3DE)
//                    else
//                        Color(0xFFFCEBEB)
//                )
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(14.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = if (score >= 50)
//                            "✓ Readiness score: $score%"
//                        else
//                            "⚠ Score too low: $score% (need 50%+)",
//                        fontSize = 13.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = if (score >= 50)
//                            Color(0xFF3B6D11)
//                        else
//                            Color(0xFFA32D2D)
//                    )
//                }
//            }
//
//            SectionLabel("Basic Information")
//
//            OutlinedTextField(
//                value = title,
//                onValueChange = { title = it },
//                label = { Text("Listing title *") },
//                placeholder = { Text("eg. Nandi Hills Sunrise Farm") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            OutlinedTextField(
//                value = hostName,
//                onValueChange = { hostName = it },
//                label = { Text("Your name *") },
//                placeholder = { Text("eg. Suresh Gowda") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            OutlinedTextField(
//                value = hostBio,
//                onValueChange = { hostBio = it },
//                label = { Text("About you") },
//                placeholder = {
//                    Text("Short bio — who you are, your farm story")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 2
//            )
//            OutlinedTextField(
//                value = description,
//                onValueChange = { description = it },
//                label = { Text("Description") },
//                placeholder = {
//                    Text("Describe your farm stay experience")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 3
//            )
//            OutlinedTextField(
//                value = priceText,
//                onValueChange = {
//                    priceText = it.filter { c -> c.isDigit() }
//                },
//                label = { Text("Price per night (₹)") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                prefix = { Text("₹") }
//            )
//
//            SectionLabel("Location Details")
//
//            OutlinedTextField(
//                value = villageName,
//                onValueChange = { villageName = it },
//                label = { Text("Village name *") },
//                placeholder = { Text("eg. Chikkaballapura") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            OutlinedTextField(
//                value = taluk,
//                onValueChange = { taluk = it },
//                label = { Text("Taluk") },
//                placeholder = { Text("eg. Nandi") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            OutlinedTextField(
//                value = district,
//                onValueChange = { district = it },
//                label = { Text("District") },
//                placeholder = { Text("eg. Chikkaballapura") },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//            OutlinedTextField(
//                value = nearestLandmark,
//                onValueChange = { nearestLandmark = it },
//                label = { Text("Nearest landmark") },
//                placeholder = {
//                    Text("eg. 3km from Nandi Hills")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//
//            SectionLabel("Activities offered")
//            FlowRow(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                activities.forEach { (key, label) ->
//                    FilterChip(
//                        selected = key in selectedActivities,
//                        onClick = {
//                            if (key in selectedActivities)
//                                selectedActivities.remove(key)
//                            else
//                                selectedActivities.add(key)
//                        },
//                        label = { Text(label, fontSize = 12.sp) },
//                        colors = FilterChipDefaults.filterChipColors(
//                            selectedContainerColor = Color(0xFF4A7C59),
//                            selectedLabelColor = Color.White
//                        )
//                    )
//                }
//            }
//
//            SectionLabel("Amenities available")
//            FlowRow(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                amenities.forEach { (key, label) ->
//                    FilterChip(
//                        selected = key in selectedAmenities,
//                        onClick = {
//                            if (key in selectedAmenities)
//                                selectedAmenities.remove(key)
//                            else
//                                selectedAmenities.add(key)
//                        },
//                        label = { Text(label, fontSize = 12.sp) },
//                        colors = FilterChipDefaults.filterChipColors(
//                            selectedContainerColor = Color(0xFF4A7C59),
//                            selectedLabelColor = Color.White
//                        )
//                    )
//                }
//            }
//
//            SectionLabel("Photos of your stay (up to 6)")
//            Text(
//                text = "Add photos of your room, farm, food, and bathroom",
//                fontSize = 12.sp,
//                color = Color(0xFF888780)
//            )
//
//            FlowRow(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                selectedImageUris.forEachIndexed { index, _ ->
//                    Box(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(10.dp))
//                            .background(Color(0xFFEAF3DE))
//                    ) {
//                        Text(
//                            text = "📷",
//                            fontSize = 28.sp,
//                            modifier = Modifier.align(Alignment.Center)
//                        )
//                        Box(
//                            modifier = Modifier
//                                .size(20.dp)
//                                .align(Alignment.TopEnd)
//                                .background(
//                                    Color(0xFFD85A30),
//                                    RoundedCornerShape(10.dp)
//                                )
//                                .clickable {
//                                    selectedImageUris.removeAt(index)
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                Icons.Default.Close,
//                                contentDescription = "Remove",
//                                tint = Color.White,
//                                modifier = Modifier.size(14.dp)
//                            )
//                        }
//                    }
//                }
//                if (selectedImageUris.size < 6) {
//                    Box(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .clip(RoundedCornerShape(10.dp))
//                            .border(
//                                1.dp,
//                                Color(0xFF4A7C59),
//                                RoundedCornerShape(10.dp)
//                            )
//                            .clickable { imagePicker.launch("image/*") },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text("+", fontSize = 24.sp, color = Color(0xFF4A7C59))
//                            Text(
//                                "Add photo",
//                                fontSize = 10.sp,
//                                color = Color(0xFF4A7C59)
//                            )
//                        }
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            if (!canPublish) {
//                Card(
//                    colors = CardDefaults.cardColors(
//                        containerColor = Color(0xFFFAEEDA)
//                    )
//                ) {
//                    Text(
//                        text = buildString {
//                            append("Cannot publish yet:\n")
//                            if (title.isBlank()) append("• Title required\n")
//                            if (villageName.isBlank()) append("• Village name required\n")
//                            if (hostName.isBlank()) append("• Your name required\n")
//                            if (score < 50) append("• Complete checklist to reach 50%+ score")
//                        },
//                        fontSize = 12.sp,
//                        color = Color(0xFF854F0B),
//                        modifier = Modifier.padding(12.dp)
//                    )
//                }
//            }
//
//            Button(
//                onClick = {
//                    val listing = Listing(
//                        title = title,
//                        villageName = villageName,
//                        district = district,
//                        hostName = hostName,
//                        hostBio = hostBio,
//                        description = description,
//                        nearestLandmark = nearestLandmark,
//                        pricePerNight = priceText.toIntOrNull() ?: 0,
//                        activities = selectedActivities.toList(),
//                        amenities = selectedAmenities.toList(),
//                        readinessScore = score,
//                        badges = ScoreCalculator.getBadges(
//                            score,
//                            selectedAmenities.toList()
//                        )
//                    )
//                    viewModel.publishListing(listing) {
//                        showSuccess = true
//                    }
//                },
//                enabled = canPublish && !isPublishing,
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF4A7C59),
//                    disabledContainerColor = Color(0xFFE2DDD5)
//                ),
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                if (isPublishing) {
//                    CircularProgressIndicator(
//                        modifier = Modifier.size(20.dp),
//                        color = Color.White,
//                        strokeWidth = 2.dp
//                    )
//                } else {
//                    Text(
//                        text = "Publish Listing",
//                        fontSize = 15.sp,
//                        modifier = Modifier.padding(vertical = 4.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
//
//@Composable
//private fun SectionLabel(text: String) {
//    Text(
//        text = text,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.Medium,
//        color = Color(0xFF4A7C59),
//        modifier = Modifier.padding(top = 8.dp)
//    )
//}

package com.yourname.gramavasathi.ui.host

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourname.gramavasathi.R
import com.yourname.gramavasathi.data.model.Listing
import com.yourname.gramavasathi.util.ScoreCalculator
import com.yourname.gramavasathi.viewmodel.HostViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateListingScreen(
    onListingPublished: () -> Unit,
    onContinueToChecklist: () -> Unit,
    onBack: () -> Unit,
    viewModel: HostViewModel = hiltViewModel()
) {
    val draftListing by viewModel.draftListing.collectAsState()
    val score by viewModel.readinessScore.collectAsState()
    val isPublishing by viewModel.isPublishing.collectAsState()

    var title by remember { mutableStateOf(draftListing.title) }
    var description by remember { mutableStateOf(draftListing.description) }
    var villageName by remember { mutableStateOf(draftListing.villageName) }
    var taluk by remember { mutableStateOf(draftListing.taluk) }
    var district by remember { mutableStateOf(draftListing.district) }
    var nearestLandmark by remember { mutableStateOf(draftListing.nearestLandmark) }
    var hostName by remember { mutableStateOf(draftListing.hostName) }
    var hostBio by remember { mutableStateOf(draftListing.hostBio) }
    var priceText by remember { mutableStateOf(if (draftListing.pricePerNight > 0) draftListing.pricePerNight.toString() else "") }
    var showSuccess by remember { mutableStateOf(false) }
    val selectedActivities = remember { mutableStateListOf<String>().apply { addAll(draftListing.activities) } }
    val selectedAmenities = remember { mutableStateListOf<String>().apply { addAll(draftListing.amenities) } }
    val selectedImageUris = remember { mutableStateListOf<Uri>().apply { addAll(draftListing.imageUrls.map { Uri.parse(it) }) } }

    val activities = listOf(
        "cow_milking" to "🐄 Cow Milking",
        "birdwatching" to "🦜 Birdwatching",
        "local_cooking" to "🍛 Local Cooking",
        "field_plowing" to "🌾 Field Plowing",
        "fishing" to "🎣 Fishing",
        "nature_walk" to "🌿 Nature Walk",
        "folk_interaction" to "🎭 Folk Interaction"
    )
    val amenities = listOf(
        "safe_water" to "💧 Safe Water",
        "western_toilet" to "🚿 Western Toilet",
        "food_included" to "🍽 Food Included",
        "family_friendly" to "👨‍👩‍👧 Family Friendly"
    )

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        uris.take(6 - selectedImageUris.size).forEach { uri ->
            if (uri !in selectedImageUris) selectedImageUris.add(uri)
        }
    }

    val canPublish = title.isNotBlank() &&
            villageName.isNotBlank() &&
            hostName.isNotBlank() &&
            score >= 50

    // Success overlay
    if (showSuccess) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎉", fontSize = 56.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Stay added successfully!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C2C2A)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your listing is now live.\nGuests can discover and book it.",
                        fontSize = 14.sp,
                        color = Color(0xFF5F5E5A),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { onListingPublished() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A7C59)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Go to Dashboard",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
        return
    }

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
                .padding(
                    top = 40.dp, start = 8.dp,
                    end = 8.dp, bottom = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
            Column {
                Text(
                    text = stringResource(R.string.create_listing),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Fill all details to publish your stay",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Score card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (score >= 50)
                        Color(0xFFEAF3DE) else Color(0xFFFCEBEB)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (score >= 50)
                            "✓ Readiness score: $score% — Ready to publish!"
                        else
                            "⚠ Score: $score% — Need 50%+ to publish",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (score >= 50)
                            Color(0xFF3B6D11) else Color(0xFFA32D2D)
                    )
                }
            }

            // Section: Basic Info
            SectionHeader(stringResource(R.string.basic_info))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.create_listing)) }, // Using create_listing as label if appropriate or add new
                placeholder = { Text("eg. Nandi Hills Sunrise Farm") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = hostName,
                onValueChange = { hostName = it },
                label = { Text("Your name *") },
                placeholder = { Text("eg. Suresh Gowda") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = hostBio,
                onValueChange = { hostBio = it },
                label = { Text("About you") },
                placeholder = {
                    Text("Tell guests about yourself and your farm")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Stay description") },
                placeholder = {
                    Text("Describe your farm stay — what guests will experience")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            OutlinedTextField(
                value = priceText,
                onValueChange = {
                    priceText = it.filter { c -> c.isDigit() }
                },
                label = { Text("Price per night (₹)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("₹  ") }
            )

            // Section: Location
            SectionHeader(stringResource(R.string.location_details))

            OutlinedTextField(
                value = villageName,
                onValueChange = { villageName = it },
                label = { Text(stringResource(R.string.villages_covered)) }, // Approximation
                placeholder = { Text("eg. Chikkaballapura") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = taluk,
                onValueChange = { taluk = it },
                label = { Text("Taluk") },
                placeholder = { Text("eg. Nandi") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = district,
                onValueChange = { district = it },
                label = { Text("District") },
                placeholder = { Text("eg. Chikkaballapura") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = nearestLandmark,
                onValueChange = { nearestLandmark = it },
                label = { Text("Nearest landmark") },
                placeholder = {
                    Text("eg. 3km from Nandi Hills viewpoint")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Section: Activities
            SectionHeader(stringResource(R.string.activities_offered))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                activities.forEach { (key, label) ->
                    FilterChip(
                        selected = key in selectedActivities,
                        onClick = {
                            if (key in selectedActivities)
                                selectedActivities.remove(key)
                            else
                                selectedActivities.add(key)
                        },
                        label = { Text(label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A7C59),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Section: Amenities
            SectionHeader(stringResource(R.string.amenities))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                amenities.forEach { (key, label) ->
                    FilterChip(
                        selected = key in selectedAmenities,
                        onClick = {
                            if (key in selectedAmenities)
                                selectedAmenities.remove(key)
                            else
                                selectedAmenities.add(key)
                        },
                        label = { Text(label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4A7C59),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Section: Photos
            SectionHeader(stringResource(R.string.photos_label))
            Text(
                text = stringResource(R.string.photos_hint),
                fontSize = 12.sp,
                color = Color(0xFF888780)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedImageUris.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEAF3DE))
                    ) {
                        Text(
                            text = "📷",
                            fontSize = 28.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.TopEnd)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color(0xFFD85A30))
                                .clickable {
                                    selectedImageUris.removeAt(index)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
                if (selectedImageUris.size < 6) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.5.dp,
                                Color(0xFF4A7C59),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { imagePicker.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "+",
                                fontSize = 26.sp,
                                color = Color(0xFF4A7C59),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Add photo",
                                fontSize = 10.sp,
                                color = Color(0xFF4A7C59)
                            )
                        }
                    }
                }
            }

            // Validation hints
            if (!canPublish) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFAEEDA)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "Before you can publish:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF854F0B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        if (title.isBlank()) {
                            Text(
                                "• Add a listing title",
                                fontSize = 12.sp,
                                color = Color(0xFF854F0B)
                            )
                        }
                        if (villageName.isBlank()) {
                            Text(
                                "• Add village name",
                                fontSize = 12.sp,
                                color = Color(0xFF854F0B)
                            )
                        }
                        if (hostName.isBlank()) {
                            Text(
                                "• Add your name",
                                fontSize = 12.sp,
                                color = Color(0xFF854F0B)
                            )
                        }
                        if (score < 50) {
                            Text(
                                "• Complete checklist to reach 50%+ score (currently $score%)",
                                fontSize = 12.sp,
                                color = Color(0xFF854F0B)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    val listing = draftListing.copy(
                        title = title,
                        villageName = villageName,
                        taluk = taluk,
                        district = district,
                        hostName = hostName,
                        hostBio = hostBio,
                        description = description,
                        nearestLandmark = nearestLandmark,
                        pricePerNight = priceText.toIntOrNull() ?: 0,
                        activities = selectedActivities.toList(),
                        amenities = selectedAmenities.toList(),
                        readinessScore = score,
                        imageUrls = selectedImageUris.map { it.toString() },
                        isPublished = false,
                        badges = ScoreCalculator.getBadges(
                            score,
                            selectedAmenities.toList()
                        )
                    )
                    viewModel.updateDraft(listing)
                    onContinueToChecklist()
                },
                enabled = title.isNotBlank() && villageName.isNotBlank() && hostName.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A7C59),
                    disabledContainerColor = Color(0xFFE2DDD5)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.fill_and_continue),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4A7C59),
        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
    )
}