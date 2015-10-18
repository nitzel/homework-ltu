#!/bin/bash
SID=$RANDOM
HOST=52.19.100.57
PORT=4321
OBJ=Spanner$SID:lab4.MinionSpawner
java jade.Boot -container -container-name $SID -port $PORT -host $HOST  $OBJ
