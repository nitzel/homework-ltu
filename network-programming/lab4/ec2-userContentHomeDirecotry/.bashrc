# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions
export CLASSPATH=~/jade/lib/commons-codec/commons-codec-1.3.jar:$CLASSPATH
export CLASSPATH=~/jade/lib/jadeExamples.jar:$CLASSPATH
export CLASSPATH=~/jade/lib/jade.jar:$CLASSPATH

