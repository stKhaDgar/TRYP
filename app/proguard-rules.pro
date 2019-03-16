# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.Platform$Java8
-keep class com.rdev.tryp.model.CreateUser { *; }
-keep class com.rdev.tryp.model.Data { *; }
-keep class com.rdev.tryp.model.Errors { *; }
-keep class com.rdev.tryp.model.LoginModel { *; }
-keep class com.rdev.tryp.model.LoginResponse { *; }
-keep class com.rdev.tryp.model.SignUpResponse { *; }
-keep class com.rdev.tryp.model.UserPhoneNumber { *; }
-keep class com.rdev.tryp.model.Users { *; }
-keep class com.rdev.tryp.model.login_response.VerifySmsResponse { *; }

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
