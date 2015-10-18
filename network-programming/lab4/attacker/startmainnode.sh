#!/bin/bash
#to start the JADE GUI and the Overseer
#has to be starte 
PORT=4321
DFMAX="-jade_domain_df_maxresult 100000" # maximum results for searches
OBJ=Gru:lab4.Overseer
java jade.Boot -gui -local-port $PORT $DFMAX $OBJ

