# TurkMusicSystem

Kotlin ile yazılmış, Android tabletler için tam özellikli müzik ve medya oynatma sistemi.

## Özellikler

- 🎵 **MP3 Oynatıcı**: Ekolayzer destekli müzik oynatma
- 📻 **Radyo Oynatıcı**: Canlı radyo programları dinleme
- 🎬 **Video Oynatıcı**: Video dosyaları oynatma
- 🔊 **Ekolayzer**: MP3 çalarken ses kalitesi ayarları (Normal, Bass Boost, Treble Boost, Canlı)
- 📡 **Bluetooth Bağlantısı**: Kablosuz hoparlörlere bağlanma
- 📶 **WiFi Ayarları**: İnternet bağlantı yönetimi
- 🎨 **Tablet Optimized UI**: Geniş ekranlar için tasarlanmış arayüz

## Gereksinimler

- Android Studio (Electric Eel veya üstü)
- Kotlin 1.8+
- Android SDK 28+ (Android 9)
- Gradle 7.x
- Java 17

## Kurulum

1. Repository'yi clone edin:
```bash
git clone https://github.com/fb5056818593-pixel/TurkMusicSystem.git
cd TurkMusicSystem
```

2. Android Studio'da açın

3. Gradle dependency'lerini indirin

4. Emülatör veya fiziksel cihazda çalıştırın

## Proje Yapısı

```
TurkMusicSystem/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/turk/musicsystem/
│   │   │   │   ├── MainActivity.kt                    # Ana ekran
│   │   │   │   ├── RadioPlayerActivity.kt            # Radyo oynatıcı
│   │   │   │   ├── MusicPlayerActivity.kt            # Müzik oynatıcı + Ekolayzer
│   │   │   │   ├── VideoPlayerActivity.kt            # Video oynatıcı
│   │   │   │   ├── services/
│   │   │   │   │   ├── MusicPlayerService.kt         # Müzik servisi
│   │   │   │   │   └── RadioPlayerService.kt         # Radyo servisi
│   │   │   │   └── utils/
│   │   │   │       └── EqualizerManager.kt            # Ekolayzer yöneticisi
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       └── values/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
└── settings.gradle.kts
```

## Bağımlılıklar

- **AndroidX**: Core, AppCompat, ConstraintLayout
- **Material Design**: UI bileşenleri
- **ExoPlayer**: Video oynatıcı
- **OkHttp**: Ağ istekleri
- **Glide**: Görsel yükleme

## Android İzinleri

- INTERNET, BLUETOOTH, BLUETOOTH_ADMIN
- BLUETOOTH_SCAN, BLUETOOTH_CONNECT (Android 12+)
- ACCESS_NETWORK_STATE, CHANGE_NETWORK_STATE
- READ_EXTERNAL_STORAGE, READ_MEDIA_AUDIO, READ_MEDIA_VIDEO
- MODIFY_AUDIO_SETTINGS

## Ekranlar

### Ana Ekran
- Bluetooth ve WiFi ayarları butonları
- Radyo, Müzik ve Video oynatıcı butonları

### Radyo Oynatıcı
- Radyo istasyonu adı ve görselleştirmesi
- Oynat/Duraklat, Önceki/Sonraki İstasyon butonları
- Ses seviyesi kaydırıcısı

### Müzik Oynatıcı
- Şarkı adı, sanatçı ve album kapağı
- İlerleme çubuğu ve zaman gösterimi
- Ekolayzer presetleri (Normal, Bass, Treble, Canlı)

### Video Oynatıcı
- ExoPlayer tabanlı oynatıcı
- Tam ekran desteği

## Lisans

MIT License
