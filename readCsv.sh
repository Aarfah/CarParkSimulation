#!/bin/sh

cat Auto.csv | while read a; 
do 
	echo "$a -- Starting Client in shellscript";
	java ClientMain $a &
done

exit 0;
