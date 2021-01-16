@echo off

call mvn clean javadoc:jar deploy -P prod -Darguments="gpg.passphrase=fubluesky"

pause