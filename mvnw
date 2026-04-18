@echo off
setlocal

set "DIRNAME=%~dp0"
set "MAVEN_PROJECTBASEDIR=%DIRNAME%"
set "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"

@REM Encontra o Java
if not "%JAVA_HOME%" == "" set "JAVACMD=%JAVA_HOME%\bin\java.exe"
if "%JAVACMD%" == "" set JAVACMD=java

@REM Roda o Maven Wrapper usando ClassPath (-cp) em vez de Módulo (-p)
"%JAVACMD%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*

if ERRORLEVEL 1 exit /b 1