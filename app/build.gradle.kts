plugins {
    id("com.android.application")
}

android {
    namespace = "org.falldetectives.falldetector"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.falldetectives.falldetector"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



dependencies {
    implementation("com.jjoe64:graphview:4.2.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.readystatesoftware.sqliteasset:sqliteassethelper:2.0.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.android.gms:play-services-location:17.1.0")
    implementation(files("libs\\biolib.sdk.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
