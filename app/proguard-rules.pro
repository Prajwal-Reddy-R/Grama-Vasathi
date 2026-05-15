# Grama-Vasathi ProGuard Rules

# Keep data models from being obfuscated (essential for Firestore)
-keep class com.yourname.gramavasathi.data.model.** { *; }

# Firebase Keep Rules
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Hilt Keep Rules
-keep class com.yourname.gramavasathi.di.** { *; }
-keep class com.yourname.gramavasathi.**_HiltComponents* { *; }
-keep class dagger.hilt.android.internal.** { *; }

# Compose Keep Rules
-keep class androidx.compose.** { *; }

# General Kotlin Keep Rules
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keepattributes *Annotation*, InnerClasses, Signature, SourceFile, LineNumberTable
