@echo off
chcp 65001 >nul
cd /d %~dp0
echo ========================================
echo   MangaReader Backend Startup Script
echo ========================================
echo.
echo [1/2] Building backend (Maven package)...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo Build failed! Please check Maven errors.
    pause
    exit /b 1
)
echo.
echo [2/2] Starting backend server on port 8080...
echo.
java -jar target\MangaReader_Web-1.0-SNAPSHOT.jar
pause
