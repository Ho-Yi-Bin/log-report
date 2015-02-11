@echo off

set /p reposPath=<output/sqlReposPath.conf
echo The youngest of %reposPath% : 
svnlook youngest %reposPath%

@echo on
set /p startVersion=
set /p endVersion=

@echo off
echo startVersion=%startVersion%, endVersion=%endVersion%
echo Ready to generate report ...
pause
cmd /c run.bat SQL GEN_REPORT Manual %startVersion% %endVersion%
pause