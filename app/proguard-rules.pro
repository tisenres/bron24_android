-renamesourcefileattribute SourceFile

# Keep Application class for Hilt
-keep class com.bron24.bron24_android.app.Bron24Application { *; }
-keep class com.bron24.bron24_android.**_Hilt { *; }

# Keep model classes
-keep class com.bron24.bron24_android.model.** { *; }

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# Gson (remove if not used alongside Moshi)
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Moshi
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**
-keepclasseswithmembers class * {
    @com.squareup.moshi.Json <fields>;
}
-keep class **JsonAdapter { *; }  # For Moshi codegen

# Hilt
-keep class dagger.** { *; }
-keep class **_HiltModules { *; }
-dontwarn dagger.**

# Optimization
-optimizationpasses 5
-allowaccessmodification