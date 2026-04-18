@echo off
setlocal

@REM Define as pastas
set "DIRNAME=%~dp0"
set "WRAPPER_JAR=%DIRNAME%.mvn\wrapper\maven-wrapper.jar"
set "MAIN_CLASS=org.apache.maven.wrapper.MavenWrapperMain"

@REM Configura a propriedade que o Maven reclamou
set "MAVEN_OPTS=-Dmaven.multiModuleProjectDirectory=%DIRNAME%"

@REM Executa de forma direta
java %MAVEN_OPTS% -cp "%WRAPPER_JAR%" %MAIN_CLASS% %*

if ERRORLEVEL 1 exit /b 1