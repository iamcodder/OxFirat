![oxfirat](https://user-images.githubusercontent.com/25854605/67428844-9f472080-f5e7-11e9-8090-c655c7235b8c.png)

# OxFırat - Fırat Üniversitesi Uygulaması


## İçerdikleri

* Fırat Üniversitesi haberleri
* Fırat Üniversitesi duyurları
* Fırat Üniversitesi etkinlikleri
* Fırat Üniversitesi yemekhanedeki yemek menüsü
* Akademik Takvim
* Üniversite telefon numaraları



[![N|play_store](https://user-images.githubusercontent.com/25854605/67431801-b12bc200-f5ed-11e9-9475-4ad1202ec2f1.png)](https://play.google.com/store/apps/details?id=com.mymoonapplab.oxfirat)


## Kullanım

```Android Studio
Proje için Android Studio Hedgehog veya üzeri sürüm kullanılmalıdır.
Uygulama için minimum Android sürümü API 21 (Lollipop) olmalıdır.
Hedef Android sürümü: API 34 (Android 14)
JDK 17 veya üzeri gereklidir (Java 21 önerilir).
```

## 🔄 Güncelleme Notları (2025)

### Proje Modernizasyonu

Bu proje, eski bağımlılıklar ve deprecated kütüphaneler nedeniyle modern Android sürümlerinde derlenmiyordu. Aşağıdaki kapsamlı güncellemeler yapılmıştır:

### ⚙️ Build Araçları ve Temel Yapı

| Bileşen | Eski Versiyon | Yeni Versiyon |
|---------|---------------|---------------|
| Gradle Wrapper | 7.6.4 | 8.5 (stable) |
| Android Gradle Plugin | 7.4.2 | 8.2.2 |
| Kotlin | 1.8.22 | 1.9.20 |
| compileSdk | 33 (Android 13) | 34 (Android 14) |
| targetSdk | 33 | 34 |
| minSdk | 21 (Lollipop) | 21 (Lollipop) |
| Java Compatibility | JDK 11 | JDK 17 (Java 21 önerilir) |

### 📚 Kütüphane Güncellemeleri

#### AndroidX ve Material Components
```gradle
androidx.appcompat: 1.1.0 → 1.6.1
androidx.constraintlayout: 1.1.3 → 2.1.4
androidx.core:core-ktx: 1.1.0 → 1.10.1
com.google.android.material:material: 1.1.0 → 1.9.0
androidx.media:media: - → 1.6.0
```

#### Diğer Kütüphaneler
```gradle
org.jsoup:jsoup: 1.7.3 → 1.16.2
com.github.bumptech.glide:glide: 4.9.0 → 4.15.1
junit:junit: 4.12 → 4.13.2
```

### 🔧 Deprecated Kütüphanelerin Modernizasyonu

#### 1. BubbleNavigation → Material BottomNavigationView
**Sorun:** `com.gauravk.bubblenavigation` kütüphanesi JitPack'ten kaldırılmış ve artık erişilebilir değil.

**Çözüm:** Material Design'ın yerleşik `BottomNavigationView` ile değiştirildi.

**Değiştirilen Dosyalar:**
- `res/layout/content_main.xml` - Layout güncellendi
- `res/menu/bottom_navigation_menu.xml` - Yeni menu resource dosyası oluşturuldu
- `HomeActivity.java` - Navigation listener API'si güncellendi

**Kod Değişikliği:**
```java
// Eski
BubbleNavigationConstraintView bottomBar;
bottomBar.setNavigationChangeListener(new BubbleNavigationChangeListener() {...});

// Yeni
BottomNavigationView bottomBar;
bottomBar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {...});
```

#### 2. android-pdf-viewer → WebView
**Sorun:** `com.github.barteksc:android-pdf-viewer` kütüphanesi JitPack'ten erişilemez durumda.

**Çözüm:** Android'in yerleşik `WebView` komponenti ile PDF görüntüleme.

**Değiştirilen Dosyalar:**
- `res/layout/fragment_akademik_takvim.xml`
- `fragment/fragment_akademik_takvim.java`

**Kod Değişikliği:**
```java
// Eski
PDFView pdfView;
pdfView.fromAsset("file.pdf").load();

// Yeni
WebView pdfView;
pdfView.getSettings().setJavaScriptEnabled(true);
pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfPath);
```

#### 3. FireworkyPullToRefresh → SwipeRefreshLayout
**Sorun:** `com.cleveroad:fireworkypulltorefresh` kütüphanesi artık mevcut değil.

**Çözüm:** AndroidX'in standart `SwipeRefreshLayout` kullanıldı.

**Değiştirilen Dosyalar:**
- `res/layout/fragment_haberler.xml` & `fragment/fragment_haberler.java`
- `res/layout/fragment_duyurular.xml` & `fragment/fragment_duyurular.java`
- `res/layout/fragment_etkinlik.xml` & `fragment/fragment_etkinlik.java`
- `fragment/fragment_yemek.java`

**Kod Değişikliği:**
```java
// Eski
FireworkyPullToRefreshLayout pullToRefresh;
pullToRefresh.getConfig().setFireworkStyle(...);
pullToRefresh.getConfig().setRocketAnimDuration(...);

// Yeni
SwipeRefreshLayout pullToRefresh;
pullToRefresh.setOnRefreshListener(() -> {...});
```

### 🆕 Modern Android Özellikleri

#### Deprecated Özellikler Kaldırıldı
- ❌ `kotlin-android-extensions` plugin'i kaldırıldı
- ✅ `viewBinding` eklendi

#### Android 12+ Uyumluluğu
- `android:exported` attribute'u tüm aktivitelere eklendi
- `package` attribute'u AndroidManifest'ten kaldırıldı, `namespace` build.gradle'a taşındı

#### Gradle Yapılandırması
```gradle
// Modern Gradle yapısı
android {
    namespace 'com.mymoonapplab.oxfirat'
    compileSdk 34

    buildFeatures {
        viewBinding true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = '17'
    }
}
```

### 📝 Gradle Ayarları

**gradle.properties** güncellemesi:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m -Dfile.encoding=UTF-8
android.useAndroidX=true
android.enableJetifier=true
# Android Studio'nun kendi JBR'ı kullanılıyor (Java 21)
org.gradle.java.home=C:\\Program Files\\Android\\Android Studio\\jbr
```

**settings.gradle** basitleştirildi:
```gradle
rootProject.name = "OxFirat"
include ':app'
```

**build.gradle (Project)** repositories yapısı:
```gradle
buildscript {
    ext.kotlin_version = '1.9.20'

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.2.2'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### ✅ Derleme Durumu

Proje artık başarıyla derleniyor:
```bash
./gradlew clean assembleDebug
BUILD SUCCESSFUL in 23s
35 actionable tasks: 35 executed

./gradlew assembleRelease
BUILD SUCCESSFUL in 12s
39 actionable tasks: 39 executed
```

APK çıktı konumları:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

### 🛠️ Gradle ve JDK Uyumluluk Sorunları Çözüldü

Bu güncelleme sürecinde karşılaşılan ve çözülen sorunlar:

#### Sorun 1: jlink.exe Hatası
**Hata:** `Error while executing process jlink.exe with arguments`
**Neden:** Gradle 8.5 ile eski AGP versiyonları arasında uyumsuzluk
**Çözüm:** AGP 8.2.2'ye yükseltildi ve Java 17+ uyumluluğu sağlandı

#### Sorun 2: DependencyHandler.module() Hatası
**Hata:** `Unable to find method 'org.gradle.api.artifacts.Dependency org.gradle.api.artifacts.dsl.DependencyHandler.module(java.lang.Object)'`
**Neden:** Gradle 9.0-milestone (beta) ile AGP 7.4.2 arasında API uyumsuzluğu
**Çözüm:** Stable Gradle 8.5 + AGP 8.2.2 kombinasyonu kullanıldı

#### Sorun 3: JDK Versiyon Uyumsuzluğu
**Hata:** `Android Gradle plugin requires Java 17 to run. You are currently using Java 11`
**Neden:** AGP 8.x Java 17+ gerektiriyor
**Çözüm:** Android Studio'nun kendi JBR'ı (Java 21) kullanılarak çözüldü

### 🔍 Bilinen Sorunlar ve Sınırlamalar

1. **PDF Görüntüleme:** WebView ile Google Docs Viewer kullanıldığından internet bağlantısı gerektirir. Offline alternatif için native bir PDF kütüphanesi eklenebilir.

2. **Bottom Navigation Animasyon:** BubbleNavigation'daki özel animasyonlar Material BottomNavigationView'da bulunmamaktadır.

3. **JitPack Kütüphaneleri:** Bazı eski JitPack kütüphaneleri artık mevcut olmadığından güncel alternatifleri kullanılmıştır.

### 🚀 Gelecek Geliştirmeler

- [ ] Jetpack Compose'a geçiş
- [ ] MVVM mimarisi implementasyonu
- [ ] Room Database kullanımı
- [ ] Kotlin Coroutines ve Flow entegrasyonu
- [ ] Material Design 3 güncellemeleri
- [ ] Offline-first mimari

### 📦 Sistem Gereksinimleri

**Geliştirme Ortamı:**
- Android Studio Hedgehog (2023.1.1) veya üzeri
- Gradle 8.5
- JDK 17+ (Java 21 önerilir - Android Studio JBR)
- Android SDK 34 (Android 14)
- Minimum desteklenen Android sürümü: API 21 (Android 5.0 Lollipop)

**Build Araçları:**
- Android Gradle Plugin 8.2.2
- Kotlin 1.9.20
- Gradle Wrapper 8.5

---

**Son Güncelleme:** 28 Ekim 2025
**Güncelleme Sebebi:** Gradle 8.5 ve AGP 8.2.2 uyumluluğu, JDK 21 desteği, Android 14 hedefleme

## Katkı
Pull request ile işleme başlayabilirsiniz. Büyük değişiklikler için öncelikle tartışma oluşturun.
Değişiklik sonrası test etmeyi unutmayın.

## Lisans Hakkı
```License
Copyright 2019 Süleyman Sezer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
```

