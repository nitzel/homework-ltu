#/bin/sh
CLASSPATH=.:$(pwd)/libs/sigar.jar
export CLASSPATH
cd bin
nohup java se.ltu.netprog.javaprog.server.MainServer &
