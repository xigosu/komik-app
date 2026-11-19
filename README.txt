KOMIK — PWA demo

CARA RUN:
1. Extract folder KOMIK.
2. Upload SEMUA kandungan folder ini ke public_html / wwwroot / folder hosting anda.
3. Pastikan index.html berada di root folder web.
4. Buka domain anda. Untuk fungsi install/offline, hosting perlu HTTPS.

CIRI YANG SUDAH BOLEH DIGUNAKAN:
- UI responsif gaya ungu pastel seperti rujukan.
- Halaman Utama, Carian, Perpustakaan, Reader demo, VIP, Level, Profil dan Tetapan.
- Bookmark, progress bab, XP dan level disimpan dalam localStorage browser.
- PWA installable dan cache asas.

PENTING:
Ini ialah frontend demo yang boleh terus dihoskan. Ia BELUM mempunyai backend/server API,
akaun/login sebenar, pangkalan data, pembayaran VIP, atau sumber imej/bab manga/manhwa.
Reader menggunakan placeholder; sambungkan API/katalog dan CDN gambar milik anda di app.js
sebelum melancarkan kepada pengguna. Jangan host atau edar kandungan berhak cipta tanpa lesen.

Jika hosting anda menggunakan Node/PHP backend, folder ini masih boleh dihidangkan sebagai
static site; tiada npm install atau build diperlukan.

PUBLISH PERCUMA MELALUI GITHUB PAGES (ANDROID + TERMUX):
1. Cipta repository baru di GitHub, contoh nama: komik-app. Pilih Public dan jangan tambah README.
2. Di Termux jalankan:
   termux-setup-storage
   pkg update -y && pkg install git unzip -y
   cd ~/storage/downloads
   unzip KOMIK-Website-Ready.zip
   cd KOMIK
   git init
   git add .
   git commit -m "Publish KOMIK"
   git branch -M main
   git remote add origin https://github.com/USERNAME/REPO.git
   git push -u origin main
   Gantikan USERNAME dan REPO dengan nama akaun dan repository GitHub anda.
3. GitHub > repository > Settings > Pages.
4. Pada Build and deployment pilih Deploy from a branch.
5. Branch: main, folder: /(root), kemudian Save.
6. Tunggu beberapa minit. Website akan muncul di:
   https://USERNAME.github.io/REPO/

Nota: GitHub Pages hanya host frontend statik. Backend/login database/pembayaran sebenar perlu servis backend berasingan.
