@echo off
setlocal enabledelayedexpansion

set baseProjectPath=%~dp0
set serverProjectPath=%~dp0\demoserver
set jarFile=%baseProjectPath%\server.jar

cd /d %serverProjectPath%

java -jar "%jarFile%"

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
)

pause
endlocal
exit /b