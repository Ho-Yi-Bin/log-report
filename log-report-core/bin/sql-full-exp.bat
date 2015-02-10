@echo off

set /p reposPath=<output/sqlReposPath.conf
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
cmd /c run.bat SQL FULL_EXP %startVersion% %endVersion% %skipExp%
pause