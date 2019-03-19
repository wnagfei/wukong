package com.wukong.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.wukong.utils.FastDFSClient;

public class TestFastDFS {
	
	public void uploadFile()throws Exception{
		ClientGlobal.init("D:/workspace/eclipse-workspace/wukong-manager-web/src/main/resources/resource/client.conf");
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		String[] strings = storageClient.upload_file("C:/Users/yuanbao/Desktop/20170420144151203.jpg", "jpg", null);
		for(String string :strings){
			System.out.println(string);
		}
	}
	@Test
	public void testFastFDSClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("D:/workspace/eclipse-workspace/wukong-manager-web/src/main/resources/resource/client.conf");
		String string = fastDFSClient.uploadFile("C:/Users/yuanbao/Desktop/2cf5e0fe9925bc319ed9f8be52df8db1cb137014.jpg");
		System.out.println(string);
	}
}
