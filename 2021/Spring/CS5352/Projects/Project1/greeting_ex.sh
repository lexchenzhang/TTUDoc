#!/bin/bash

temph=`date | cut -c12-13`
dat=`date +"%A %d in %B of %Y (%r)"`
file="greetingmsg.log"

if [ $temph -lt 12 ]
then
    msg="Good Morning $LOGNAME, Have nice day!"
elif [ $temph -ge 12 -a $temph -le 16 ]
then
    msg="Good Afternoon $LOGNAME"
elif [ $temph -gt 16 -a $temph -le 20 ]
then
    msg="Good Evening $LOGNAME"
else
	msg="Hello LOGNAME!"
fi

# remove the file if exist
if [ -f "$file" ] ; then
    rm "$file"
fi

exec 1>> $file 2>&1

#echo $temph
#echo $dat
echo -e "$msg\nThis is $dat"
