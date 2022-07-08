rem TODO: create linux version too
@echo OFF

setlocal enableextensions enabledelayedexpansion

set /a COUNT=0
set VERSION=""
for /f "tokens=3 delims=<>" %%a in (
	'find /i "<version>" "..\pom.xml"'
) do (	
	if /i !COUNT!==1 (
		set "VERSION=%%a"
		goto foundversion
	)
	set /a COUNT += 1
)

endlocal

:foundversion
cls

echo -------------------------------------------------------------------------------
echo [92mSTART DEPLOYMENT WITH VERSION: %VERSION%[0m
echo -------------------------------------------------------------------------------

set ABORTED="false"
if not exist ..\target\arrowhead-application-library-java-spring-%VERSION%.jar goto nojar
if not exist ..\target\arrowhead-application-library-java-spring-%VERSION%-sources.jar goto nosource
if not exist ..\target\arrowhead-application-library-java-spring-%VERSION%-javadoc.jar goto nojavadoc
>nul copy ..\pom.xml ..\target\arrowhead-application-library-java-spring-%VERSION%.pom

cd ..\target

call mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh -DpomFile=arrowhead-application-library-java-spring-%VERSION%.pom -Dfile=arrowhead-application-library-java-spring-%VERSION%.jar || goto mvnerror

call mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh  -DpomFile=arrowhead-application-library-java-spring-%VERSION%.pom -Dfile=arrowhead-application-library-java-spring-%VERSION%-sources.jar -Dclassifier=sources || goto mvnerror

call mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=ossrh  -DpomFile=arrowhead-application-library-java-spring-%VERSION%.pom -Dfile=arrowhead-application-library-java-spring-%VERSION%-javadoc.jar -Dclassifier=javadoc || goto mvnerror

cd ..\deploy
goto finished

:nojar
echo ERROR: arrowhead-application-library-java-spring-%VERSION%.jar not found
set ABORTED="true"
goto finished

:nosource
echo ERROR: arrowhead-application-library-java-spring-%VERSION%-sources.jar not found
set ABORTED="true"
goto finished

:nojavadoc
echo ERROR: arrowhead-application-library-java-spring-%VERSION%-javadoc.jar not found
set ABORTED="true"
goto finished

:mvnerror
echo ERROR: maven failure
set ABORTED="true"
goto finished

:finished
echo -------------------------------------------------------------------------------
if %ABORTED%=="false" ( echo [92mDEPLOYMENT SUCCESS[0m ) else ( echo [91mDEPLOYMENT FAILURE[0m )
echo -------------------------------------------------------------------------------