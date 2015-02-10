rem now path C:\Program Files\VisualSVN Server\
rem %1 = Repository
rem %2 = Revision Number
rem %3 = User
rem %4 = Property Name
rem %5 = Action

if %PROPNAME%=="svn:log" goto MODIFY
goto CANNOT_DOIT

:MODIFY
if %ACTION%=="M" goto DOIT
goto CANNOT_DOIT

:DOIT
exit 0

:CANNOT_DOIT
echo Error Message : >&2
echo Changing revision properties other than svn:log is prohibited >&2
exit 1