rem now path C:\Program Files\VisualSVN Server\
rem %1 = Repository
rem %2 = Transcation Number

cd %1\hooks
set /p projectPath=<projectPath.conf

svnlook author -t %2 %1 > %projectPath%\output\tmpFiles\sql_author_%2.txt
svnlook log -t %2 %1 > %projectPath%\output\tmpFiles\sql_log_%2.txt

D:
cd %projectPath%
run.bat SQL PRE %projectPath%\output\tmpFiles\sql_author_%2.txt %projectPath%\output\tmpFiles\sql_log_%2.txt

if %errorlevel% gtr 0 (goto err) else (goto success)

:success
exit 0

:err
exit 1