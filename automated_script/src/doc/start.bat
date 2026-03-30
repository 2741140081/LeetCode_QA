@echo off
:: 请求管理员权限
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo 正在请求管理员权限...
    powershell -Command "Start-Process '%~f0' -Verb RunAs"
    exit /b
)

echo ========================================
echo   自动化脚本启动器 (管理员模式)
echo ========================================
echo.

:: 设置脚本所在目录
set SCRIPT_DIR=%~dp0
set JAR_PATH=%SCRIPT_DIR%target\automated_script-1.0-SNAPSHOT-jar-with-dependencies.jar

:: 检查 jar 文件是否存在
if not exist "%JAR_PATH%" (
    echo [错误] 找不到 jar 包: %JAR_PATH%
    echo 请先执行 mvn clean package 进行打包
    pause
    exit /b 1
)

:: 使用指定的 JDK 17 启动
set JAVA_EXE=C:\Users\xxxxx\.jdks\ms-17.0.16\bin\java.exe

:: 检查 Java 是否存在
if not exist "%JAVA_EXE%" (
    echo [错误] 找不到 Java: %JAVA_EXE%
    echo 请确认 JDK 路径是否正确
    pause
    exit /b 1
)

echo [信息] 正在启动应用...
echo [信息] Jar 包路径：%JAR_PATH%
echo [信息] Java 路径：%JAVA_EXE%
echo.

:: 启动应用
"%JAVA_EXE%" -jar "%JAR_PATH%"

:: 如果程序退出，暂停以便查看错误信息
if %errorLevel% neq 0 (
    echo.
    echo [错误] 应用启动失败，错误码：%errorLevel%
    pause
)
