#!/bin/bash
LOG_FILE="creation.log"
target="attacker"
logit() 
{
    echo "[${USER}][`date`] - ${*}" >> ${LOG_FILE}
}
cd /home/ec2-user/
COUNTER=1
git clone https://github.com/nitzel/homework-ltu.git
while :
do
	echo "runde $COUNTER" >> runde.txt
	logit "Executing script round number $COUNTER"
	logit "Getting files from remote server"
	cd /home
	cd /home/ec2-user/homework-ltu
	git stash
	git pull 
	
	logit "create symbolic link"
	rm -rf $target
	ln -s /home/ec2-user/homework-ltu/network-programming/lab4/$target/ $target

	cd /home/ec2-user/$target/

	logit "Make all shell files downloaded executable"
	find . -name "*.sh" -exec chmod u+x {} \;

	logit "Execute main script"
	./mainScript.sh
	((COUNTER++))

	sleep 10
done

