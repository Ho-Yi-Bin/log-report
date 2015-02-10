@echo off

set /p reposPath=<output/codeReposPath.conf
echo The youngest of %reposPath% : 
svnlook youngest %reposPath%

@echo on
set /p startVersion=
set /p endVersion=

@echo off
echo startVersion=%startVersion%, endVersion=%endVersion%
echo Ready to generate report ...
pause
cmd /c run.bat CODE GEN_REPORT Code_Manual %startVersion% %endVersion%
pause