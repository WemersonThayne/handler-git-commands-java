package br.edu.ifpb.git;

import br.edu.ifpb.util.ProcessPushUtil;

public class PushCommit {

    private PushCommit(){}

    public static void push(String pathRepositoryLocal, String remoteRepository, String branch, String originRemote, String useSshKey){
        ProcessPushUtil.execute(pathRepositoryLocal,remoteRepository,branch,originRemote,useSshKey);
    }
}
