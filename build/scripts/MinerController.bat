@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  MinerController startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and MINER_CONTROLLER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\MinerController-0.1.0.jar;%APP_HOME%\lib\org.apache.commons.codec-1.8.jar;%APP_HOME%\lib\bitcoinj-core-0.14.5.jar;%APP_HOME%\lib\bridj-0.7.0.jar;%APP_HOME%\lib\javase-2.0.jar;%APP_HOME%\lib\json-simple-1.1.1.jar;%APP_HOME%\lib\webcam-capture-0.3.11.jar;%APP_HOME%\lib\javase-3.3.1.jar;%APP_HOME%\lib\core-3.1.1.jar;%APP_HOME%\lib\commons-codec-1.8.jar;%APP_HOME%\lib\core-1.51.0.0.jar;%APP_HOME%\lib\protobuf-java-2.6.1.jar;%APP_HOME%\lib\guava-18.0.jar;%APP_HOME%\lib\jsr305-2.0.1.jar;%APP_HOME%\lib\jcip-annotations-1.0.jar;%APP_HOME%\lib\scrypt-1.4.0.jar;%APP_HOME%\lib\orchid-1.2.1.jar;%APP_HOME%\lib\okhttp-2.7.2.jar;%APP_HOME%\lib\dx-1.7.jar;%APP_HOME%\lib\core-2.0.jar;%APP_HOME%\lib\jfreesvg-2.1.jar;%APP_HOME%\lib\junit-4.10.jar;%APP_HOME%\lib\abi-3.1.1.jar;%APP_HOME%\lib\crypto-3.1.1.jar;%APP_HOME%\lib\tuples-3.1.1.jar;%APP_HOME%\lib\jnr-unixsocket-0.15.jar;%APP_HOME%\lib\okhttp-3.8.1.jar;%APP_HOME%\lib\logging-interceptor-3.8.1.jar;%APP_HOME%\lib\rxjava-1.2.4.jar;%APP_HOME%\lib\hamcrest-core-1.1.jar;%APP_HOME%\lib\utils-3.1.1.jar;%APP_HOME%\lib\rlp-3.1.1.jar;%APP_HOME%\lib\jackson-databind-2.8.5.jar;%APP_HOME%\lib\jnr-ffi-2.1.2.jar;%APP_HOME%\lib\jnr-constants-0.9.6.jar;%APP_HOME%\lib\jnr-enxio-0.14.jar;%APP_HOME%\lib\jnr-posix-3.0.33.jar;%APP_HOME%\lib\bcprov-jdk15on-1.54.jar;%APP_HOME%\lib\jackson-annotations-2.8.0.jar;%APP_HOME%\lib\jackson-core-2.8.5.jar;%APP_HOME%\lib\jffi-1.2.14.jar;%APP_HOME%\lib\jffi-1.2.14-native.jar;%APP_HOME%\lib\asm-5.0.3.jar;%APP_HOME%\lib\asm-commons-5.0.3.jar;%APP_HOME%\lib\asm-analysis-5.0.3.jar;%APP_HOME%\lib\asm-tree-5.0.3.jar;%APP_HOME%\lib\asm-util-5.0.3.jar;%APP_HOME%\lib\jnr-x86asm-1.0.2.jar;%APP_HOME%\lib\jcommander-1.72.jar;%APP_HOME%\lib\jai-imageio-core-1.3.1.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\okio-1.13.0.jar;%APP_HOME%\lib\core-3.3.1.jar

@rem Execute MinerController
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MINER_CONTROLLER_OPTS%  -classpath "%CLASSPATH%" MinerControllerApplication %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable MINER_CONTROLLER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%MINER_CONTROLLER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
