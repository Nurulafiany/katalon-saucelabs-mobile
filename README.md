# katalon-saucelabs-mobile

Automation test project (Katalon Studio Enterprise, type **MOBILE**) untuk aplikasi Android
**Sauce Labs "Swag Labs"** yang APK-nya disimpan langsung di dalam project.

---

## Aplikasi yang diuji

| Item | Nilai |
|------|-------|
| Nama | Sauce Labs "Swag Labs" (React Native demo app) |
| APK | `Apps/sauce-labs.2.7.1.apk` |
| Package | `com.swaglabsmobileapp` |
| Main activity | `com.swaglabsmobileapp.MainActivity` |
| Versi | 2.7.1 |

> Catatan: aplikasi ini mengekspos setiap automation id sebagai native accessibility label,
> yaitu `content-desc = "test-" + label` (contoh: `test-Username`, `test-LOGIN`, `test-PRODUCTS`).
> Semua locator di project ini dibangun dari nilai tersebut.

### Kredensial

| Skenario | Username | Password |
|----------|----------|----------|
| Valid | `standard_user` | `secret_sauce` |
| Locked out | `locked_out_user` | `secret_sauce` |
| Invalid | `invalid_user` | `wrong_password` |

---

## Prasyarat

- Katalon Studio Enterprise (project dibuat dengan 10.4.3).
- Appium server yang berjalan + Android device/emulator (atau Sauce Labs cloud).
- Device **berbahasa English** — karena id aplikasi ter-lokalisasi mengikuti locale device.
- Pada Katalon Studio: *Project > Settings > Execution > Mobile* pilih device/Appium.

---

## Struktur project

```
katalon-saucelabs-mobile/
├── Apps/
│   └── sauce-labs.2.7.1.apk              # APK disimpan di dalam project
├── Keywords/com/mydemoapp/
│   └── MobileCommon.groovy               # keyword reusable (path dinamis + locator)
├── Profiles/default.glbl                 # Global Variables (tanpa hardcode)
├── Test Cases/*.tc                       # metadata test case
├── Scripts/<TCName>/Script*.groovy       # script Groovy test case
├── Test Suites/TS_SwagLabs_Mobile_Regression.ts
├── Libs/internal/GlobalVariable.groovy   # auto-generated loader Global Variable
└── run_mobile_tests.sh                   # runner CLI + generate report
```

---

## Test Cases

| ID | Nama | Tipe | Yang diverifikasi |
|----|------|------|-------------------|
| TC01 | `TC01_Login_Valid` | **Positif** | Login user valid → screen Products tampil |
| TC02 | `TC02_Login_Invalid_Credentials` | **Negatif** | Pesan "Username and password do not match any user in this service." + tetap di layar login |
| TC03 | `TC03_Login_Empty_Credentials` | **Negatif** | Pesan "Username is required" |
| TC04 | `TC04_Login_Locked_Out` | **Negatif** | Pesan "Sorry, this user has been locked out." |
| TC05 | `TC05_Catalog_Product_List` | **Positif** | Catalog & produk "Sauce Labs Backpack" tampil |
| TC06 | `TC06_Add_To_Cart` | **Positif** | Add to cart → tombol item berubah menjadi "REMOVE" |

Semua test case dijalankan lewat **Test Suite** `TS_SwagLabs_Mobile_Regression`.

---

## Cara menjalankan

### 1. Lewat command line (menghasilkan report)

```bash
cd katalon-saucelabs-mobile
DEVICE_ID="emulator-5554" ./run_mobile_tests.sh
```

- Override lokasi Katalon bila perlu: `KATALON_BIN="/path/ke/katalon"`.
- Override execution profile: `EXECUTION_PROFILE="default"`.

### 2. Lewat Katalon Studio (GUI)

1. Buka project `katalon-saucelabs-mobile.prj`.
2. Set device di *Project > Settings > Execution > Mobile*.
3. Jalankan `Test Suites/TS_SwagLabs_Mobile_Regression`.

### Report

Report HTML/CSV/JUnit otomatis tertulis ke folder `Reports/` (di-ignore oleh git).

---

## Catatan desain

- **Tidak memakai Record Mobile.** Semua elemen dibangun secara programatik
  (`TestObject.addProperty('accessibilityId', ...)` / `xpath`) di `MobileCommon.groovy`.
- **Path APK dinamis / project-relative.** Resolusi dilakukan saat runtime lewat
  `RunConfiguration.getProjectDir() + "/Apps/<APP_FILE>"` — tidak ada path absolut yang di-hardcode.
- **Tanpa hardcode data.** Username, password, pesan error, dan timeout dibaca dari Global Variables
  (`Profiles/default.glbl`).
- Untuk menjalankan di **Sauce Labs cloud**, ganti sumber aplikasi menjadi `sauce-storage:<nama>` dan
  isi capabilities/remote WebDriver di *Project Settings*, atau beri tahu saya untuk menyiapkan versi remote-nya.
