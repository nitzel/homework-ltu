#!/bin/bash
SID=$RANDOM
HOST="ec2-52-19-100-57.eu-west-1.compute.amazonaws.com"
PORT=4321
OBJ=Spawner$SID:lab4.MinionSpawner
java -Xmx1g jade.Boot -container -container-name $SID -port $PORT -host $HOST  $OBJ
