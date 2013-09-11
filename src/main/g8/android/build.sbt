import android.Keys._
import android.Dependencies.{apklib,aar}

// load the android plugin into the build
android.Plugin.androidBuild
 
// project name, completely optional
name := "$name$Android"

organization := "$organization$"

// pick the version of scala you want to use
scalaVersion := "$scalaVersion$"
 
// scala 2.10 flag for feature warnings
scalacOptions in Compile += "-feature"

javacOptions in compile ++= Seq("-source", "1.6",  "-target", "1.6")
 
// for non-ant-based projects, you'll need this for the specific build target:
platformTarget in Android := "android-17"
 
// call install and run without having to prefix with android:
run <<= run in Android
 
install <<= install in Android

resolvers += Resolver.url("testrepo2",url("https://raw.github.com/chototsu/testrepo/master/ivy2/")) ( Patterns(false,"[organisation]/[module]/[revision]/[type]s/[artifact].[ext]") )

libraryDependencies += "info.projectkyoto" % "mikumikustudio-lib-android" % "0.2-SNAPSHOT"

// test data(Amaha Sora, koshihuri dance)
libraryDependencies += "info.projectkyoto" % "mmstestdata" % "0.1-SNAPSHOT"

useProguard in Android := true

useSdkProguard in Android := false

proguardOptions in Android ++= Seq(
  "-ignorewarnings",
  "-keep class com.jme3.math.*",
  "-keepclassmembers class com.jme3.math.** {<methods>;}",
  "-keepclassmembers class com.jme3.bullet.** {<methods>;}",
  "-keepnames class com.jme3.** {*;}",
  "-keep public class * extends com.jme3.app.Application",
  "-keep class scala.runtime.BoxedUnit"
)
