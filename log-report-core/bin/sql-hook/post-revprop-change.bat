rem now path C:\Program Files\VisualSVN Server\
rem %1 = Repository 
rem %2 = Revision Number
rem %3 = User
rem %4 = Property Name
rem %5 = Action

cd %1\hooks
set /p projectPath=<projectPath.conf

svnlook log -r %2 %1 > %projectPath%\output\tmpFiles\modify_log_%2.txt

D:
cd %projectPath%
run.bat SQL UPDATE %projectPath%\output\tmpFiles\modify_log_%2.txt %2

exit 0