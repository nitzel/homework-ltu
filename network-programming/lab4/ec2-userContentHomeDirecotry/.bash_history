cd workspace/Lab2_4-Simple-Messaging-Architecture/
ls
./startDDN
./startDNSService.sh
cd ..
cd Lab2_5-Java-Security/
ls
./2_Startserver.sh
ls
sudo vi /etc/init.d/java_server_launch.sh
cd workspace/
ls
cd Lab3_LoadBalancing/
ls
./startServer.sh 
pwd
sudo vi /etc/init.d/java_server_launch.sh
update-rc.d java_server_launch.sh defaults
chkconfig java_server_launch.sh on
ls
./startServer.sh 
ps -aux
pidof se.ltu.netprog.javaprog.server.MainServer
echo(pidof se.ltu.netprog.javaprog.server.MainServer)
echo $(pidof se.ltu.netprog.javaprog.server.MainServer)
echo $(pidof java se.ltu.netprog.javaprog.server.MainServer)
/home/ec2-user/workspace/Lab3_LoadBalancing/startServer.sh 
cd /home/ec2-user/workspace/Lab3_LoadBalancing/
./startServer.sh 
sudo vi /etc/init.d/java_server_launch.sh 
sudo chmod u+x /etc/init.d/java_server_launch.sh 
chkconfig /etc/init.d/java_server_launch.sh on
cloud-init-cfg 
sudo vi /etc/init.d/cloud-init
ls
cd workspace/
ls
cd Lab2_2-Socket-Threads/
ls
./startTCPEchoServer.sh
./startUDPEchoServer.sh
cd ..
ls
cd Lab2_3-Client-server-Application/
ls
./startServer.sh
cd ..
cd Lab2_4-Simple-Messaging-Architecture/
ls
./startDNSService.sh
ls
./startDateService.sh
ls
vi startDNSService.sh
./startDNSService.sh
ls
vi startDNSService.sh
./startDNSService.sh
sdf
ls
cd bin/
ls
cd ..
ls
./startDNSService.sh
dig heise.de
vi startDateService.sh
vi startDNSService.sh
java -version
./startDNSService.sh
./startDateService.sh
ls
./startDNSService.sh
ls
./startDNSService2
./startDNSService2.sh
cd ..
ls
cd Lab2_2-Socket-Threads/
ls
./startTCPEchoServer.sh
./startUDPEchoServer.sh
cd ..
cd Lab2_3-Client-server-Application/
ls
./startServer.sh
cd ..
ls
cd Lab2_4-Simple-Messaging-Architecture/
ls
./startDNSService2
./startDNSService2.sh
cd ..
ls
cd Lab2_5-Java-Security/
ls
top
cd workspace/Lab3_LoadBalancing
ls
./startServer.sh 
man upstart
ls
sudo mv serverBootScript.sh /etc/init.d/serverBootScript
update-rc.d serverBootScript defaults
chkconfig --add serverBootScript
sudo chkconfig --add serverBootScript
sudo serverBootScript on
sudo chkconfig serverBootScript on
chckconfig -list serverBootScript
chkconfig -list serverBootScript
chkconfig --list serverBootScript
sudo reboot
hostname
ifconfig
nc localhost 2223
netstat -naput
ls
rm LoadBalancing.jar 
ls
cd workspace/Lab3_
cd workspace/Lab3_LoadBalancing
ls
service serverBootScript.sh start
service serverBootScript start
sudo service serverBootScript start
cd ..
sudo chkconfig serverBootScript off
sudo chkconfig serverBootScript offf
sudo chkconfig serverBootScript on
sudo service serverBootScript start
sudo chmod +x /etc/init.d/serverBootScript 
sudo service serverBootScript start
sudo vi /etc/init.d/serverBootScript 
sudo service serverBootScript start
sudo reboot
/usr/bin/rsync -azvv -e ssh amazon@jtf.at:/home/amazon/share share
ls -altr /var/log
less /var/log/secure
sudo su
exit
sudo yum update
ls
cd .ssh/
ls
less authorized_keys 
cat authorized_keys 
ls
ssh-keygen -t rsa
ls
cat id_rsa.pub 
ssh amazon@jtf.at
ls
cd ~
ls
rm 1_Generate_TrustStore.sh 
rm 2_Startserver.sh 
rm ClientLogFile.txt 
ls
rm SSLStore 
ls
rm jdk-8u60-linux-x64.rpm*
ls
rm ServerLogFile.txt 
ls
cd .ssh/
ls
cd ~
vi bootScript.sh
chmod u+x bootScript.sh 
./bootScript.sh 
cat .ssh/id_rsa
ls
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
rsync --delete -avze --numeric-ids ssh amazon@jtf.at:/home/amazon/distribute ~/distribute
rsync -avze --numeric-ids ssh amazon@jtf.at:/home/amazon/distribute ~/distribute
ssh amazon@jtf.at
ls
mkdir distribute
rsync -avze --numeric-ids ssh amazon@jtf.at:/home/amazon/distribute ~/distribute
rsync -avze --numeric-ids ssh amazon@jtf.at:/home/amazon/ ~/distribute
rsync -avze --numeric-ids 'ssh amazon@jtf.at:/home/amazon/distribute' ~/distribute
rsync --numeric-ids -avze 'ssh amazon@jtf.at:/home/amazon/distribute' ~/distribute
ls
cd distribute/
ls
cd ..
rm distribute/
rm -rf distribute/
rsync --numeric-ids -avze 'ssh amazon@jtf.at:/home/amazon/share' ~/share
ls
mkdir share
rsync --numeric-ids -avze 'ssh amazon@jtf.at:/home/amazon/share' ~/share
cd share/
ls
ls -al
cd ..
ls
rsync --numeric-ids -avze 'ssh amazon@jtf.at:/home/amazon/share' ~/share
ls
cd share/
ls
rsync --numeric-ids -avz -ee 'ssh amazon@jtf.at:/home/amazon/share' ~/share
rsync --numeric-ids -avz -e 'ssh amazon@jtf.at:/home/amazon/share' ~/share
ls
ll
rsync --numeric-ids -avz -e 'ssh amazon@jtf.at:/home/amazon/share' ~/share
ls
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share' ~/share
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share/' ~/share
ls
ll
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share/*' ~/share
ls
ll
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share/
ll
ls
ls -al
ls
touch dudanein
rsync -avz -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share/
ls
rsync -chavzP -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share/
ls
ll
rsync -ravz -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share/
ls
rsync -rav -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share/
ls
rsync -rav -e 'ssh amazon@jtf.at:/home/amazon/share' /home/ec2-user/share
ls
rsync -r -a -v -e 'ssh -l amazon' --delete jtf.at:/home/amazon/share/ /home/ec2-user/share
rsync -rav -e 'ssh -l amazon' --delete jtf.at:/home/amazon/share/ /home/ec2-user/share
cd ..
rsync -avx --timeout=30 --delete-excluded amazon@jtf.at:/home/amazon/share share
rsync
amazon
ls
rsync -av --progress -e ssh amazon@jtf.at:/home/amazon/share/ share
rsync -av --progress -e 'ssh amazon@jtf.at:/home/amazon/share/' share
ls
cd share/
ll
rsync -av --progress -e 'ssh amazon@jtf.at:/home/amazon/share/' share
rsync -av -e 'ssh amazon@jtf.at:/home/amazon/share/' share
rsync -av amazon@jtf.at:/home/amazon/share/ share
scp -r amazon@jtf.at:/home/amazon/share share
ls
cd share/
ls
rm *
cd ..
rsync -a amazon@jtf.at:/home/amazon/share/ share
/usr/bin/rsync 
/usr/bin/rsync -a amazon@jtf.at:/home/amazon/share/ share
rsync -azvv -e ssh amazon@jtf.at:/home/amazon/share share
sudo find / -name rsync
/usr/bin/rsync -azvv -e ssh amazon@jtf.at:/home/amazon/share share
sh
/usr/bin/rsync -azvv -e ssh amazon@jtf.at:/home/amazon/share share
sudo reboot
/usr/bin/rsync -azvv -e ssh amazon@jtf.at:/home/amazon/share share
/usr/bin/rsync -azvv -e "ssh" amazon@jtf.at:/home/amazon/share share
rsync -azv -e "ssh" amazon@jtf.at:/home/amazon/share share
rsync -avz -e "ssh" amazon@jtf.at:/home/amazon/share share
rsync -avz -e "ssh" amazon@jtf.at:/home/amazon/share/ share
rsync -av --progress -e 'ssh amazon@jtf.at:/home/amazon/share/' share
ls
cd share/
ls
cd share/
ls
cd ..
rsync -av  -e 'ssh amazon@jtf.at:/home/amazon/server/' share
ls
cd share/
ls
ll
cd ..
ls
ll
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" share
rsync -av  -e "ssh " amazon@jtf.at:/home/amazon/server/ share
rsync -av  -e "ssh -l amazon" jtf.at:/home/amazon/server/ share
rsync -av  -e "ssh amazon@jtf.at" /home/amazon/server/ share
rsync -av  -e "ssh amazon@jtf.at" :/home/amazon/server/ share
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" share
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/ share"
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" share
ls
cd share/
ls
cd ..
ls
rm -rf share/
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" share
ls
mkdir share
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" share
sudo su
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" ~/share
ls
cd share/
ls
ll
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" ~/share
ls
cd ..
ls
cd share/
rsync -av  -e "ssh amazon@jtf.at:/home/amazon/server/" .
ls
rsync -avz  -e "ssh amazon@jtf.at:/home/amazon/server/" .
ls
rsync -avz  -e "ssh" amazon@jtf.at:/home/amazon/server/ .
ls
rsync -avz  -e "ssh" amazon@jtf.at:/home/amazon/server/ .
ls
rsync -avz --delete -e "ssh" amazon@jtf.at:/home/amazon/server/ .
ls
rsync -avz --delete -e "ssh" amazon@jtf.at:/home/amazon/server/ /home/ec2-user/server
ls
cd ~
ls
cd server/
ls
cd ..
rm -rf server/
rsync -avz --delete -e "ssh" amazon@jtf.at:/home/amazon/server/ /home/ec2-user/server
ls
rm -rf server/ share/
ls
vi bootScript.sh 
ls
vi bootScript.sh 
ls
./bootScript.sh 
ls
cd server/
ls
cd ..
ls
vi /etc/init.d/bootScript
vi /etc/init.d/serverBootScript 
ls
vi bootScript.sh 
vi /etc/init.d/serverBootScript 
ls
vi bootScript.sh 
cd server/
ls
cd ..
ls
./bootScript.sh 
ls
rm server/
rm -rf server/
sudo reboot
ls
ls -altr /var/log
less /var/log/lastlog 
ls
vi bootScript.sh 
./bootScript.sh 
ls
rm server/ -rf
vi /etc/init.d/serverBootScript 
/etc/init.d/serverBootScript start
ls
chmod +x bootScript.sh 
rm -rf server/
sudo reboot
ls
vi /etc/init.d/serverBootScript 
/etc/init.d/serverBootScript 
/etc/init.d/serverBootScript start
ls
less log 
rm log 
rm -rf server/
sudo reboot
ls
less log 
vi bootScript.sh 
./bootScript.sh 
ls
chown bootScript.sh 
ll
cd server/
ls
ll
cd ..
ls
chown ec2-usser log
chown ec2-user log
sudo chown ec2-user log
sudo vi /etc/init.d/serverBootScript 
ls
rm -rf server/
sudo reboot
ls
cat log
ls
vi log 
sudo reboot
ls
cat log 
ls
vi bootScript.sh 
ls
sudo reboot
ls
cat creation.log 
ls -altr /var/log
ls
./bootScript.sh 
ls
ll
vi bootScript.sh 
vi /etc/init.d/serverBootScript 
ls
sudo rm creation.log 
sudo rm server/ -rf
ls
rm log 
sudo reboot
ls
netstat -naput
yum install httpd
sudo yum install httpd
sudo chconfig httpd on
sudo chkconfig httpd on
sudo httpd start
sudo apachectl start
ls
vi /var/www/
ls
cd /var/www/
ls
cd html/
ls
vi /etc/httpd/conf/httpd.conf 
cd /etc/httpd/
ls
cd run/
ls
cd conf.d/
ls
less README 
ls
vi welcome.conf 
vi /etc/httpd/conf/httpd.conf 
ls
rm welcome.conf 
sudo remove welcome.conf 
sudo rm welcome.conf 
ls
vi notrace.conf 
cd ..
ls
cd modules/
ls
cd ..
ls
service httpd reload
less /var/log/
ls -altr /var/log
less /var/log/httpd/
sudo su
cd ~
ls
vi bootScript.sh 
less log 
ls
less creation.log 
vi /etc/init.d/serverBootScript 
ls
vi bootScript.sh 
ls
rm log 
ls
rm -rf server/
rm -rf workspace/
ls
sudo reboot
ls
cat creation.log 
ls
rm -rf creation.log 
rm -rf server/
ls
sudo yum install git git-core
ls
git clone https://github.com/nitzel/homework-ltu.git
ls
cd homework-ltu/
ls
cd ..
ls
rm -rf homework-ltu/
ls
vi bootScript.sh 
ls
vi bootScript.sh 
./bootScript.sh 
ls
cd server/
ls
cd ..
ls
ln -s homework-ltu/network-programming/lab4/ test
ls
cd test/
ls
cd ..
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
rm -rf homework-ltu/
./bootScript.sh 
ls
vi bootScript.sh 
./bootScript.sh 
ls
vi bootScript.sh 
cd test/
ls
cd ..
vi bootScript.sh 
./bootScript.sh 
ls
cd attacker/
ls
cd ..
ls
cd server/
ls
cd ..
ls
vi bootScript.sh 
cd homework-ltu/
find . -name *.sh -exec chmod u+x {} \;
ls
touch test.sh
ll
cd network-programming/
ls
touch bla.sh
cd ..
ls
find . -name *.sh -exec chmod u+x {} \;
ll
cd network-programming/
ll
cd ..
ls
touch opel.sh
find . -name *.sh -exec chmod u+x {} \;
find . -name "*.sh" -exec chmod u+x {} \;
ll
cd network-programming/
ll
cd ..
ls
cd ..
ls
vi bootScript.sh 
cd attacker/
ls
cd ..
ls
vi bootScript.sh
ls
./bootScript.sh 
ls
du -sh homework-ltu/
ls
cd server/
ls
cd ..
ls
vi bootScript.sh 
ls
./bootScript.sh 
ls
cat /dev/urandom
ls
vi bootScript.sh 
rm -rf attacker/
ls
./bootScript.sh 
man nohup
ls
vi bootScript.sh 
rm -rf attacker/
ls
cd victim/
ls
java jade
java jade.Boot
java -version
ls
java -version
ls
netstat -naput
ls
cd ..
ls
top
ls
vi .bashrc 
wget http://jade.tilab.com/dl.php?file=JADE-all-4.3.3.zip
ls
unhzip dl.php\?file\=JADE-all-4.3.3.zip 
uhzip dl.php\?file\=JADE-all-4.3.3.zip 
unzip dl.php\?file\=JADE-all-4.3.3.zip 
ls
mkdir jade
mv JADE-* jade/
ls
rm dl.php\?file\=JADE-all-4.3.3.zip 
ls
cd jade/
ls
unzip JADE-bin-4.3.3.zip 
ls
cd jade/
ls
cd ..
ls
unzip JADE-*
ls
cd jade/
ls
cd lib/
ls
cd ..
ls
unzip JADE-src-4.3.3.zip 
ls
cd jade/
ls
cd lib/
ls
cd ..
ls
unzip JADE-examples-4.3.3.zip 
ls
unzip JADE-doc-4.3.3.zip 
ls
rm *.zip
ls
cd jade/
ls
mv * ..
ls
cd ..
ls
rm -rf jade/
ls
cd ..
bash
java jade.Boot
cd ..
ls
cd ec2-user/
ls
vi /etc/init.d/serverBootScript 
ls
sudo reboot
cat .ssh/authorized_keys 
ls
cd attacker/
ls
cd ..
ls
vi bootScript.sh 
cd victim/
ls
vi mainScript.sh 
ifconfig
hostname
ls
cd ..
cd victim/
ls
vi startServer.sh 
vi startStatusServer.sh 
cd ..
ls
cd attacker/
ls
cd ..
ls
cp bootScript.sh bootScriptVictim.sh 
vi bootScript
vi bootScript.sh 
ls
rm bootScript
rm bootScript.sh 
vi bootScriptVictim.sh
./bootScriptVictim.sh 
netstat -naput | grep 2223
ls
cp bootScriptVictim.sh bootScript.sh
vi bootScript
vi bootScript.sh 
ls
cd attacker/
ls
vi mainScript.sh 
ls
cd ..
ls
./bootScript
ls
./bootScript.sh 
ls
./bootScript.sh 
vi bootScript.sh 
cd attacker 
ls
cd attacker 
vi bootScript.sh 
./bootScript
./bootScript.sh 
ls
rm -rf attacker 
vi bootScript
vi bootScript.sh 
./bootScript.sh 
ls
vi bootScript.sh 
./bootScript.sh 
ls
cd attacker/
ls
vi mainScript.sh 
cd ..
ls
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
ls
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
ll
ls
vi bootScript.sh 
./bootScript.sh 
ls
./bootScript.sh 
ls
./bootScript.sh 
ls
vi bootScript.sh 
ls
git clone https://github.com/nitzel/homework-ltu.git
ls
rm -rf homework-ltu/
git clone https://github.com/nitzel/homework-ltu.git
ls
git pull https://github.com/nitzel/homework-ltu.git
ls
vi bootScript.sh 
./bootScript.sh 
ls
vi bootScript.sh 
less creation.log 
vi bootScript.sh 
less creation.log 
vi bootScript.sh 
less creation.log 
vi bootScript.sh 
./bootScript
./bootScript.sh 
ls
less creation.log 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
ls
cat creation.log 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
ls
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
ls
less creation.log 
vi bootScript.sh 
./bootScript.sh 
ls
less creation.log 
tail -f creation.log 
ls
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
./bootScript.sh 
vi bootScript.sh 
ls
cd attacker 
vi bootScript.sh 
./bootScript.sh 
ls
cd attacker/
ls
cat mainScript.sh 
cat startspawnernode.sh 
cd ~
ls
vi bootScript.sh 
ls
./bootScript.sh 
ls
sudo vi /etc/init.d/serverBootScript 
sudo reboot
ls
ps aux 
ls
sudo vi bootScriptVictim.sh 
cd victim/
ls
vi startServer.sh 
ps aux
ls
ps aux | grep java
ls
ll
vi bootScript.sh 
sudo ls -altr /var/log/
less /var/log/secure
sudo less /var/log/secure
less /var/log/cloud-init.log 
ls
vi bootScript.sh 
./bootScript.sh 
ls
vi /etc/init.d/serverBootScript 
sudo reboot
ps aux
ls
netstat -naput
ps aux
vi /etc/init.d/serverBootScript 
ls
cp bootScript.sh mainScript.sh
vi bootScript.sh 
ls
chmod +x mainScript.sh 
chmod +x bootScript
chmod +x bootScript.sh 
ls
vi mainScript.sh 
sudo rm -rf attacker/
ls
sudo rm -rf attacker/
ls
ll
ls
sudo rm -rf homework-ltu/
ls
sudo rm -rf attacker 
ls
sudo rm -rf victim 
ls
less runde.txt 
sudo reboot
ps aux
ls
cat nohup.out 
tail -f nohup.out 
ps aux
kill 3279
ps aux
ls
./bootScript.sh 
ls
ps aux
ls
./mainScript.sh 
ls
cat mainScript.sh 
ps aux
kill  COUNTER=$[$COUNTER +1]
kill 3997
kill 4027
ps aux
ls
vi bootScript.sh 
sudo reboot
ps aux
ls
kill 2145
ps aux
ps aux | grep bash
kill 2239
kill 2240
ps aux | grep bash
ls
ps aux
kill 2241
ps aux
ls
cd attacker/
ls
cat mainScript.sh 
./startspawnernode.sh 
java -version
cd ~
ls
cat mainScript.sh 
ls
cd attacker/
ls
./startspawnernode.sh 
vi startspawnernode.sh 
./startspawnernode.sh 
clear
./startspawnernode.sh 
ls
vi startspawnernode.sh 
nc 52.19.100.57 4321
telnet 52.19.100.57 4321
nc 52.19.100.57 4321
nc 172.30.0.27 4321
ping 172.30.0.27
ping 52.19.100.57
ssh 172.30.0.27
ssh ubuntu@172.30.0.27
ls
./startspawnernode.sh 
ls
vi startspawnernode.sh 
./startspawnernode.sh 
vi startspawnernode.sh 
./startspawnernode.sh 
cd ~
ls
vi bootScript.sh 
vi mainScript.sh 
sudo reboot
vi bootScript.sh 
ps aux
ls
tail -f nohup.out 
ls
rm -rf attacker/
ls
rm -rf homework-ltu/
ls
rm -rf attacker 
rm -rf server/
ls
rm -rf homework-ltu/
ls
ls -al
ll
ls
ps aux
ps aux | grep bash
kill 2145
kill 15551
ls
ps aux | grep bash
ls
vi mainScript.sh 
./mainScript.sh 
ls
vi mainScript.sh 
./mainScript.sh 
ls
vi bootScriptVictim.sh 
sudo reboot
netstat -naput
ps aux
ls
mv bootScript.sh bootScriptAttacker.sh
mv bootScriptVictim.sh bootScript.sh
ls
vi bootScript.sh 
./bootScript.sh 
ls
