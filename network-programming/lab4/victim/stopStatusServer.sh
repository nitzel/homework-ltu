#!/bin/bash
#http://askubuntu.com/questions/99232/how-to-make-a-jar-file-run-on-startup-and-when-you-log-out
# Grabs and kill a process from the pidlist that has the word myapp

pid=`ps aux | grep se.ltu.netprog.javaprog.statusserver.MainStatusServer | head -n 1| awk '{print $2}'`
kill -9 $pid
