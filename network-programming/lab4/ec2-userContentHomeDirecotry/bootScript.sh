#/bin/bash
LOG_FILE="creation.log"
target="victim"
logit() 
{
    echo "[${USER}][`date`] - ${*}" >> ${LOG_FILE}
}


logit "Getting files from remote server"
rm -rf ~/homework-ltu
git clone https://github.com/nitzel/homework-ltu.git

logit "create symbolic link"
rm -rf $target
ln -s ~/homework-ltu/network-programming/lab4/$target $target

cd ~/$target/

logit "Make all shell files downloaded executable"
find . -name "*.sh" -exec chmod u+x {} \;

logit "Execute main script"
./mainScript.sh

logit "I'm after ./mainScript"
