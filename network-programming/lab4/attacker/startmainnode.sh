#!/bin/bash
#to start the JADE GUI and the Overseer
#has to be starte 
HOST="ec2-52-19-100-57.eu-west-1.compute.amazonaws.com"
PORT=4321
DFMAX="-jade_domain_df_maxresult 100000" # maximum results for searches
OBJ=Gru:lab4.Overseer
java jade.Boot -gui -local-port $PORT -local-host $HOST $DFMAX $OBJ

