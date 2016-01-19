# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xianguangjin/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.google.android.gms.**
-dontwarn android.support.**


-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}

-keepattributes Exceptions,InnerClasses

-keep class io.rong.** {*;}

-dontwarn io.rong.**


-keep class * implements io.rong.imlib.model.MessageContent{*;}

-keepattributes Signature

-keepattributes *Annotation*

-keep class sun.misc.Unsafe { *; }

-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**

-keep class com.ultrapower.** {*;}


-dontwarn com.squareup.okhttp.**

-keep class com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**


-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**


-keepattributes *Annotation*
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# For using GSON @Expose annotation
 -keepattributes *Annotation*

 -keepattributes EnclosingMethod

 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }

 -keepattributes Signature
 -keep class **.R$* {*;}
 -ignorewarnings

 -verbose
 -keepclasseswithmembernames class * {
     native <methods>;
 }

 -keepclasseswithmembernames class * {
     native <methods>;
 }

 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class com.squareup.okhttp.** { *; }
 -keep interface com.squareup.okhttp.** { *; }
 -dontwarn com.squareup.okhttp.**

 -dontwarn rx.**
 -dontwarn retrofit.**
 -keep class retrofit.** { *; }
 -keepclasseswithmembers class * {
     @retrofit.http.* <methods>;
 }

 -dontwarn java.lang.invoke.*

 # 使用注解
 -keepattributes *Annotation*,Signature

 # umeng
 -keepclassmembers class * {
    public <init>(org.json.JSONObject);
 }


