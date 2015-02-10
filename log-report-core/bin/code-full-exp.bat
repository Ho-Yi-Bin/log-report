@echo off

set /p reposPath=<output/codeReposPath.conf
echo The youngest of %reposPath% : 
svnlook youngest %reposPath%

@echo on
set /p startVersion=
set /p endVersion=
set /p skipExp=

@echo off
echo startVersion=%startVersion%, endVersion=%endVersion%, skipExp=%skipExp%
echo Ready to full exp ...
pause
cmd /c run.bat CODE FULL_EXP %startVersion% %endVersion% %skipExp%
pause