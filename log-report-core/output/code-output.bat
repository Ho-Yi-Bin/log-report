@echo off
set /p reposPath=<output\codeReposPath.conf

del output\outputFiles\*.txt
FOR /L %%G IN (%1,1,%2) DO svnlook author %reposPath% -r %%G > output\outputFiles\code_author_%%G.txt
FOR /L %%G IN (%1,1,%2) DO svnlook date %reposPath% -r %%G > output\outputFiles\code_date_%%G.txt 
FOR /L %%G IN (%1,1,%2) DO svnlook log %reposPath% -r %%G > output\outputFiles\code_log_%%G.txt
FOR /L %%G IN (%1,1,%2) DO svnlook changed %reposPath% -r %%G > output\outputFiles\code_changed_%%G.txt