package com.example.sortsmart_se.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.sortsmart_se.R
import com.example.sortsmart_se.ui.theme.GreenPrimary
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.util.concurrent.Executors
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.objects.ObjectDetector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(onNavigateToSearch: () -> Unit = {}, onNavigateToSearchWithQuery: (String) -> Unit = {}) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCameraPermission = granted }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    var recognizedItem by remember { mutableStateOf("") }
    var rawLabel by remember { mutableStateOf("") }
    
    // Initialize ML Kit clients once
    val labeler = remember { ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS) }
    val objectDetector = remember { 
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
        ObjectDetection.getClient(options)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Bar
        TopAppBar(
            title = { Text(stringResource(id = R.string.scan_title), color = Color.White, fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
            actions = {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(imageVector = Icons.Default.FlashOn, contentDescription = stringResource(id = R.string.flash), tint = Color.White)
                }
                IconButton(onClick = { /* TODO */ }) {
                    Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = stringResource(id = R.string.switch_camera), tint = Color.White)
                }
            }
        )

        // Viewfinder Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (hasCameraPermission) {
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx).apply {
                            this.scaleType = PreviewView.ScaleType.FILL_CENTER
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }

                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()

                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val imageAnalyzer = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                                .also {
                                    it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                                        processImageWithMLKit(imageProxy, labeler, objectDetector) { displayLabel, actualLabel ->
                                            recognizedItem = displayLabel
                                            rawLabel = actualLabel
                                        }
                                    }
                                }

                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageAnalyzer
                                )
                            } catch (e: Exception) {
                                Log.e("ScanScreen", "Camera binding failed", e)
                            }
                        }, ContextCompat.getMainExecutor(ctx))

                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("Camera permission is required.", color = Color.White)
            }

            // Scanner Frame Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(16.dp)
                    .border(3.dp, GreenPrimary, RoundedCornerShape(32.dp))
            )
            
            // Detected Item Label
            if (recognizedItem.isNotEmpty()) {
                Surface(
                    color = GreenPrimary.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 150.dp)
                        .clickable { onNavigateToSearchWithQuery(rawLabel) }
                ) {
                    Text(
                        text = "$recognizedItem  🔍",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
            
            Text(
                text = stringResource(id = R.string.point_camera),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
            )
        }

        // Bottom Controls
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = onNavigateToSearch,
                modifier = Modifier
                    .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Not sure? Search manually", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
private fun processImageWithMLKit(
    imageProxy: ImageProxy, 
    labeler: ImageLabeler, 
    detector: ObjectDetector,
    onLabelDetected: (String, String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        
        // Use Object Detection to find the most prominent object first
        detector.process(image)
            .addOnSuccessListener { objects ->
                // If we found objects, we can prioritize labels within those bounding boxes 
                // but for now, we'll just use the fact that an object was detected to increase confidence
                
                labeler.process(image)
                    .addOnSuccessListener { labels ->
                        // Keywords to prioritize if detected
                        val wasteKeywords = listOf(
                            "Plastic", "Bottle", "Glass", "Can", "Paper", "Cardboard", "Metal", 
                            "Trash", "Waste", "Container", "Box", "Cup", "Bag", "Wrapper", "Packaging",
                            "Carton", "Jug", "Jar", "Tin", "Foil", "Battery", "Electronics", 
                            "Food", "Fruit", "Vegetable", "Organic", "Liquid", "Beverage", "Milk", "Juice",
                            "Drinkware", "Tableware", "Kitchenware", "Crockery", "Aluminum", "Steel"
                        )
                        
                        // Labels to completely ignore
                        val ignoredLabels = setOf(
                            "Chair", "Table", "Desk", "Room", "Hand", "Arm", "Person", "Finger", "Furniture",
                            "Floor", "Wall", "Ceiling", "Building", "Sky", "Tree", "Plant", "Wood", "Lighting",
                            "House", "Property", "Window", "Door", "Vehicle", "Car", "Nail", "Joint", "Smile",
                            "Eye", "Face", "Hair", "Skin", "Leg", "Foot", "Shoe", "Clothing", "Shirt", "Pants",
                            "Jeans", "Watch", "Glasses", "Couch", "Sofa", "Bed", 
                            "Animal", "Cow", "Dog", "Cat", "Bird", "Pet", "Livestock", "Canine", "Puppy", "Carnivore", "Mammal",
                            "Man", "Woman", "Human", "Male", "Female", "Adult", "Boy", "Girl", "Child", "Beard", "Mustache",
                            "Text", "Font", "Logo", "Brand", "Graphic design", "Art", "Drawing", "Illustration", "Graphics"
                        )

                        // Mapper for generic labels to specific app categories
                        val labelMapper = mapOf(
                            "Plastic" to "Plastic Packaging",
                            "Bottle" to "Plastic bottle",
                            "Glass" to "Glass bottle",
                            "Can" to "Soda can",
                            "Tin" to "Soda can",
                            "Aluminum" to "Metal Packaging",
                            "Steel" to "Metal Packaging",
                            "Cardboard" to "Cardboard box",
                            "Carton" to "Paper Packaging",
                            "Box" to "Paper Packaging",
                            "Milk" to "Paper Packaging",
                            "Juice" to "Paper Packaging",
                            "Cup" to "Coffee Cups (Takeaway)",
                            "Bag" to "Plastic bag",
                            "Electronics" to "Small Electronics",
                            "Food" to "Food Waste",
                            "Fruit" to "Food Waste",
                            "Battery" to "Batteries",
                            "Metal" to "Metal Packaging",
                            "Trash" to "Residual Waste",
                            "Drinkware" to "Plastic bottle",
                            "Tableware" to "Plastic Packaging",
                            "Kitchenware" to "Metal Packaging",
                            "Crockery" to "Ceramic (Residual)"
                        )

                        // 1. Try to find labels that match priority waste keywords
                        val priorityLabels = labels.filter { label -> 
                            wasteKeywords.any { label.text.contains(it, ignoreCase = true) } 
                        }

                        // 2. Filter out ignored labels
                        val validLabels = labels.filter { label ->
                            ignoredLabels.none { label.text.contains(it, ignoreCase = true) }
                        }

                        // 3. Choose the best label
                        // If we have priority labels, take the best one. Otherwise take the best valid label.
                        val topLabel = priorityLabels.maxByOrNull { it.confidence } 
                            ?: validLabels.maxByOrNull { it.confidence }

                        // Lowered threshold slightly to 0.45f but added a boost if an object was detected in the frame
                        val detectionBoost = if (objects.isNotEmpty()) 0.1f else 0f
                        val threshold = 0.45f - detectionBoost

                        if (topLabel != null && topLabel.confidence > threshold) {
                            val rawText = topLabel.text
                            val mappedText = labelMapper.entries.firstOrNull { it.key.equals(rawText, ignoreCase = true) }?.value ?: rawText
                            onLabelDetected("$mappedText — ${(topLabel.confidence * 100).toInt()}%", mappedText)
                        }
                    }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}
