@echo off
setlocal enabledelayedexpansion

set serverProjectPath=%~dp0ClientWebDemo
set jarFile=%serverProjectPath%\build\libs\client.jar

echo Esecuzione del file JAR
java -jar "%jarFile%"

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
)

pause
endlocal
exit /b