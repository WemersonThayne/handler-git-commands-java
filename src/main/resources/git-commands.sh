#!/bin/sh

#./src/main/resources/git-commands.sh
# home/wemerson-porto/test-git/
# https://github.com/WemersonThayne/teste.git
# master
# git@github.com:WemersonThayne/teste.git
# no

#GIT=`which git`
#cd ${REPO_DIR}
#REPO_DIR=/home/wemerson-porto/test-git/
#
#${GIT} remote add origin https://github.com/WemersonThayne/teste.git
#${GIT} branch -M master
#${GIT} push git@github.com:WemersonThayne/teste.git master
#git push -u origin main

REPO_DIR=$1
REMOTE=$2
BRANCH_DESTINY=$3
ORIGIN_REMOTE=$4
IS_USE_SSH_KEY=$5

echo "### Init push repository ###"
echo ">> Path local repository: $REPO_DIR"
echo ">> Remote repository: $REMOTE"
echo ">> Remote branch: $BRANCH_DESTINY"
echo ">> Remote origin: $ORIGIN_REMOTE"
echo ">> Use SSH to push: $IS_USE_SSH_KEY"
#
#GIT=`which git`
#cd ${REPO_DIR}
#
#${GIT} remote add origin $REMOTE
#${GIT} branch -M $BRANCH_DESTINY
#${GIT} push -u $ORIGIN_REMOTE $BRANCH_DESTINY
#
#if [ $IS_USE_SSH_KEY = "yes" ]; then
#  ${GIT} push -u $ORIGIN_REMOTE $BRANCH_DESTINY
#  exit 1
#else
#  ${GIT} push -u origin $BRANCH_DESTINY
#  exit 1
#fi

echo ">> Push repository complete"