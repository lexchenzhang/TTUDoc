#!/bin/sh

echo "test for input 2 with code_v1"
echo `./code_v1 2`
sleep 2
echo "test for input 4 with code_v1"
echo `./code_v1 4`
sleep 2
echo "test for input 8 with code_v1"
echo `./code_v1 8`
sleep 2

echo "test for input 2 with code_v2"
echo `./code_v2 2`
sleep 2
echo "test for input 4 with code_v2"
echo `./code_v2 4`
sleep 2
echo "test for input 8 with code_v2"
echo `./code_v2 8`
sleep 2