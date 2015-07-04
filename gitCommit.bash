#!/bin/bash 

git fetch --all 
git merge upstream/master

read $VAR

git push origin master

