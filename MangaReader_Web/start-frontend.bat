@echo off
chcp 65001 >nul
cd /d %~dp0\manga-web
echo ========================================
echo   MangaReader Frontend Startup Script
echo ========================================
echo.
echo [1/2] Installing dependencies...
call npm install
echo.
echo [2/2] Starting frontend dev server...
echo.
call npm run dev
pause
