@echo off
set /p reposPath=<output\sqlReposPath.conf

del .\output\outputFiles\*.txt
FOR /L %%G IN (%1,1,%2) DO svnlook author %reposPath% -r %%G > .\output\outputFiles\sql_author_%%G.txt
FOR /L %%G IN (%1,1,%2) DO svnlook date %reposPath% -r %%G > .\output\outputFiles\sql_date_%%G.txt
FOR /L %%G IN (%1,1,%2) DO svnlook log %reposPath% -r %%G > .\output\outputFiles\sql_log_%%G.txt
FOR /L %%G IN (%1,1,%2) DO svnlook changed %reposPath% -r %%G > .\output\outputFiles\sql_changed_%%G.txt