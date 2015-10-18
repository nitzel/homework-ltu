#!/bin/sh
#
### BEGIN INIT INFO
# Provides: myJAVAServer
# Required-Start: $remote_fs $syslog
# Required-Stop: $remote_fs $syslog
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: Initialize MyJavaServer for amazonaws
# Description: starts myjavaserver
### END INIT INFO
#Put the script to start with the system (using SysV). 
#http://www.cyberciti.biz/faq/rhel5-update-rcd-command/
# sudo chmod +x /etc/init.d/serverBootScript
#sudo chkconfig --add serverBootScript
#sudo chkconfig serverBootScript on 

#update-rc.d myscript defaults 
cd /home/ec2-user/workspace/Lab3_LoadBalancing/

case "$1" in
	 start)
			echo Starting JavaServer

			/bin/bash ./startServer.sh 
			;;
	 stop)
			echo Stopping JavaServer
			/bin/bash ./stopServer.sh
			;;
	restart)
			echo Restarting JavaServer
			/bin/bash ./stopServer.sh
			/bin/bash ./startServer.sh 
			;;
	 *)
			echo Usage: \$0 "{start|stop}"
			exit 1
esac
exit 0
