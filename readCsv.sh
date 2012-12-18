#!/bin/sh

cat Auto.csv | while read a; 
do 
	echo "$a -- Starting Client";
	java ClientMain $a;
done

exit 0;
