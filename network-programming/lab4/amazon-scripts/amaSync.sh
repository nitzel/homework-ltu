#/bin/sh
IP=$(head -n 1 ~/Documents/amazon-scripts/serverip)
rsync --numeric-ids -avz -e "ssh -i /home/cm/Documents/15_LP1_KEY_D7001D_icoami-5.pem" ~/workspace ec2-user@$IP:/home/ec2-user/ 
