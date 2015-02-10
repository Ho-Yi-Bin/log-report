rem now path C:\Program Files\VisualSVN Server\
rem %1 = Repository
rem %2 = Revision Number

cd %1\hooks
set /p projectPath=<projectPath.conf

svnlook author -r %2 %1 > %projectPath%\output\tmpFiles\sql_author_%2.txt
svnlook date -r %2 %1 > %projectPath%\output\tmpFiles\sql_date_%2.txt
svnlook log -r %2 %1 > %projectPath%\output\tmpFiles\sql_log_%2.txt
svnlook changed -r %2 %1 > %projectPath%\output\tmpFiles\sql_changed_%2.txt

D:
cd %projectPath%
run.bat SQL POST %projectPath%\output\tmpFiles\sql_author_%2.txt %projectPath%\output\tmpFiles\sql_date_%2.txt %projectPath%\output\tmpFiles\sql_log_%2.txt %projectPath%\output\tmpFiles\sql_changed_%2.txt %2

exit 0