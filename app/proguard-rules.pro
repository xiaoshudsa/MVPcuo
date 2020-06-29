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
#-keep	防止类和成员被移除或者被重命名
#-keepnames	防止类和成员被重命名
#-keepclassmembers	防止成员被移除或者被重命名
#-keepclassmembersname	防止成员被重命名
#-keepclasseswithmembers	防止拥有该成员的类和成员被移除或者被重命名
#-keepclasseswithmembernames	防止拥有该成员的类和成员被重命名
#类通配符 *	匹配任意长度字符，但不含包名分隔符(.)
#类通配符 **	匹配任意长度字符，并且包含包名分隔符(.)
#类extends	即可以指定类的基类
#类implements	匹配实现了某接口的类
#类$	内部类
#成员(方法)通配符 *	匹配任意长度字符，但不含包名分隔符(.)
#成员(方法)通配符 **	匹配任意长度字符，并且包含包名分隔符(.)
#成员(方法)通配符 ***	匹配任意参数类型
#成员(方法)通配符 ...	匹配任意长度的任意类型参数
#成员(方法)通配符 <>	匹配方法名,eg. <init>


#-------------------------公共部分 start-------------------------------
#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
#优化  不优化输入的类文件
-dontoptimize
-printmapping proguardMapping.txt

-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保留了继承自Activity、Application这些类的子类
# 因为这些子类有可能被外部调用
# 比如第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
#-ignorewarning
#如果引用了v4或者v7包,
-dontwarn android.support.**
-keep class android.support.** {*;}
-dontwarn java.lang.annotation.Annotation

-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable
-keepnames class * implements android.os.Parcelable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  public <fields>;
  private <fields>;
}

-keep class **.R$* {
 *;
}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

-assumenosideeffects class android.util.Log{
    public static boolean isLoggable(java.lang.String,int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
#proguard 6.0以上版本才有，需要项目gradle强制依赖高版本
-assumenoexternalsideeffects class java.lang.StringBuilder {
    public java.lang.StringBuilder();
    public java.lang.StringBuilder(int);
    public java.lang.StringBuilder(java.lang.String);
    public java.lang.StringBuilder append(java.lang.Object);
    public java.lang.StringBuilder append(java.lang.String);
    public java.lang.StringBuilder append(java.lang.StringBuffer);
    public java.lang.StringBuilder append(char[]);
    public java.lang.StringBuilder append(char[], int, int);
    public java.lang.StringBuilder append(boolean);
    public java.lang.StringBuilder append(char);
    public java.lang.StringBuilder append(int);
    public java.lang.StringBuilder append(long);
    public java.lang.StringBuilder append(float);
    public java.lang.StringBuilder append(double);
    public java.lang.String toString();
}

-assumenoexternalreturnvalues public final class java.lang.StringBuilder {
    public java.lang.StringBuilder append(java.lang.Object);
    public java.lang.StringBuilder append(java.lang.String);
    public java.lang.StringBuilder append(java.lang.StringBuffer);
    public java.lang.StringBuilder append(char[]);
    public java.lang.StringBuilder append(char[], int, int);
    public java.lang.StringBuilder append(boolean);
    public java.lang.StringBuilder append(char);
    public java.lang.StringBuilder append(int);
    public java.lang.StringBuilder append(long);
    public java.lang.StringBuilder append(float);
    public java.lang.StringBuilder append(double);
}

#-------------------------公共部分 end-------------------------------


#因为我们目前的分包规则，将所有的实体类全部放到了一个包下，而实体类有是不能被混淆的，所以我们通过添加
#以下混淆规则，即为该包下的所有类不被混淆，来避免项目运行过程中由于实体类被混淆导致的一系列空指针
#但是大家到公司接手项目时，涉及到代码混淆的时候，一定要注意你们的分包方式，如果实体类存放非常分散，不要使用该方法
-keep public class com.zxp.data.**{*;}

#xml解析混淆xstream
-keep class org.xmlpull.v1.** { *;}
-dontwarn org.xmlpull.v1.**
#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

#baidu
-dontwarn com.baidu.**                                                      #去掉警告
-dontwarn com.baidu.mapapi.**
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}                                 #过滤BaiduLBS_Android.jar
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.baidu.platform.**{*;}
-keep class com.baidu.location.**{*;}
-keep class com.baidu.vi.**{*;}

-dontwarn com.hjq.permissions.**
-keep class com.hjq.permissions.** {*;}

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
#piccasso
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#yuntongxun
-keep class com.yuntongxun.ecsdk.** {*;}

-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn rx.*
#科大讯飞语音识别的
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}

#eventbus代码混淆
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
#状态栏混淆
 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**
#拍照相册混淆
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**
-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**
-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class com.tencent.** { *; }
-keep class com.tencent.ysdk.**{*;}

#shareSdk
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.mob.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**

-keep public class your.domain.package.* {
  public *** get*();
  public void set*(***);
}

#jackson
-dontskipnonpubliclibraryclassmembers
-keepattributes *Annotation*,EnclosingMethod
-keepnames class org.codehaus.jackson.** { *; }
-dontwarn javax.xml.**
-dontwarn javax.xml.stream.events.**
-dontwarn com.fasterxml.jackson.databind.**

-keep class com.actionbarsherlock.** {*;}
-keep class org.holoeverywhere.** {*;}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder