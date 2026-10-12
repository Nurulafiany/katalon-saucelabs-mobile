#!/usr/bin/env bash
#
# Runs the Sauce Labs "Swag Labs" mobile regression suite headlessly and produces an HTML report.
#
# The APK lives inside the project (./Apps) and is resolved at RUNTIME from the project
# directory by the MobileCommon custom keyword (RunConfiguration.getProjectDir()),
# so nothing here hardcodes an absolute path to the application.
#
# Prerequisites:
#   * Katalon Studio Enterprise installed (path below, override with KATALON_BIN).
#   * A running Appium server + a connected Android device/emulator.
#   * The Android device selected once in Katalon Studio > Project > Settings > Execution > Mobile
#     (or pass it with DEVICE_ID below).
#
# Usage:
#   DEVICE_ID=emulator-5554 ./run_mobile_tests.sh
#
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_FILE="${PROJECT_DIR}/katalon-saucelabs-mobile.prj"
KATALON_BIN="${KATALON_BIN:-/Applications/Katalon Studio Enterprise.app/Contents/MacOS/katalon}"
SUITE_PATH="Test Suites/TS_SwagLabs_Mobile_Regression"
EXECUTION_PROFILE="${EXECUTION_PROFILE:-default}"
DEVICE_ID="${DEVICE_ID:-}"
REPORT_FOLDER="${PROJECT_DIR}/Reports"

APK_PATH="${PROJECT_DIR}/Apps/sauce-labs.2.7.1.apk"

if [[ ! -f "${KATALON_BIN}" ]]; then
  echo "ERROR: Katalon executable not found at '${KATALON_BIN}'. Set KATALON_BIN to override." >&2
  exit 1
fi

if [[ ! -f "${APK_PATH}" ]]; then
  echo "ERROR: APK not found inside the project at '${APK_PATH}'." >&2
  exit 1
fi

ARGS=(
  -noSplash
  -runMode=console
  -projectPath="${PROJECT_FILE}"
  -retry=0
  -testSuitePath="${SUITE_PATH}"
  -executionProfile="${EXECUTION_PROFILE}"
  -browserType="Android"
  -reportFolder="${REPORT_FOLDER}"
)

if [[ -n "${DEVICE_ID}" ]]; then
  ARGS+=(-deviceId="${DEVICE_ID}")
fi

echo "Running suite '${SUITE_PATH}' from project '${PROJECT_FILE}' ..."
echo "Reports will be written to: ${REPORT_FOLDER}"
"${KATALON_BIN}" "${ARGS[@]}"
