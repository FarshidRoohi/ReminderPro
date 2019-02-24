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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn com.squareup.**
-dontwarn com.google.**
-dontwarn org.ocpsoft.**
-dontwarn com.itextpdf.**


-keep class com.squareup.**
-keep class com.google.**
-keep class org.self4j.**
-keep class org.ocpsoft.**
-keep class com.itextpdf.**


# Get rid of warnings about unreachable but unused classes referred to by Netty
-dontwarn org.jboss.netty.**

# Needed by commons logging
-keep class org.apache.commons.logging.* {*;}

#Some Factory that seemed to be pruned
-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdater {*;}
-keep class java.util.concurrent.atomic.AtomicReferenceFieldUpdaterImpl{*;}

#Some important internal fields that where removed
-keep class org.jboss.netty.channel.DefaultChannelPipeline{volatile <fields>;}

#A Factory which has a static factory implementation selector which is pruned
-keep class org.jboss.netty.util.internal.QueueFactory{static <fields>;}

#Some fields whose names need to be maintained because they are accessed using inflection
-keepclassmembernames class org.jboss.netty.util.internal.**{*;}


-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keep class org.acra.** { *; }
-dontwarn android.support.v4.app.NotificationCompat*
