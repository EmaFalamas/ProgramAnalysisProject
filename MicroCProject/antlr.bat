@echo off
for %%X in (javac.exe) do (set FOUND=%%~$PATH:X)
IF not defined FOUND (echo Make sure jdk is installed and is put in the environment variable
	goto :eof)
set incorrect_input=false
IF [%1] == [] set incorrect_input=true 
IF [%2] == [] set incorrect_input=true
IF %incorrect_input%==true (
	echo Calls should be on form "antlr MicroC test.microC"
	goto :eof

) ELSE (
	java -jar %~dp0lib\antlr-4.5.3-complete.jar .\src\MicroC_language\parsing\MicroC.g4
	javac -sourcepath .\src -cp .\lib\*.jar .\src\MicroC_language\MicroC.java .\src\MicroC_language\parsing\*.java -d bin
	java -cp .\lib\antlr-4.5.3-complete.jar;.\bin MicroC ".\tests\%2"
)
