@echo off
setlocal enabledelayedexpansion

set serverProjectPath=%~dp0\demoserver
set jarFile=%serverProjectPath%\build\libs\server.jar

cd /d %serverProjectPath%

echo Esecuzione del file JAR
java -jar "%jarFile%"

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
)

pause
endlocal
exit /b