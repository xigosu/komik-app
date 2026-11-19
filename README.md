# KOMIK Android APK starter

Projek Android yang dibungkus sebagai aplikasi APK (Activity Android + WebView tempatan). UI dark premium, katalog contoh dengan cover ilustrasi original, VIP/XP/level/bookmark/search demo.

## Bina APK tanpa Android Studio
1. Upload kandungan folder ini ke root repo GitHub `xigosu/komik-app` (atau repo Android berasingan).
2. Buka tab **Actions** dan jalankan workflow **Build KOMIK APK** (Run workflow), atau push ke branch `main`.
3. Buka run yang berjaya > **Artifacts** > `KOMIK-debug-apk` untuk muat turun APK.
4. Install APK pada Android dan benarkan pemasangan dari sumber tersebut jika diminta.

## Status fungsi
- APK Android boleh dibina melalui GitHub Actions.
- Navigasi, carian, bookmark, XP/level dan VIP masih demo setempat.
- Reader masih paparan demo; tiada manga/manhwa sebenar atau backend akaun/pembayaran. Kandungan sebenar perlu sumber yang mempunyai lesen/izin.
- Ini pakej Android dengan UI WebView tempatan, bukan UI native Jetpack Compose.
