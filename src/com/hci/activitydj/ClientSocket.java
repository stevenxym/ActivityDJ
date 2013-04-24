package com.hci.activitydj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import android.util.Log;

public class ClientSocket {
	
	Socket client;
	
	//IP & port of host
	private String hostAddress;
	private final static int hostPort = 9999;
	
	BufferedReader clientReader;
	PrintWriter clientWriter;
	
	public void SendMsg(String str) throws IOException {
		clientWriter = new PrintWriter(client.getOutputStream());
		clientWriter.println(str);
		clientWriter.flush();
	}
	
	public String ReceiveMsg() {
		try {
			clientReader = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
			return clientReader.readLine();
		} catch (UnsupportedEncodingException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		}
		
		return null;
	}
	
	public void CloseClient() {
		try {
			client.close();
		} catch (IOException e) {
			// do nothing
		}
	}
	
	public ClientSocket(String ip_addr) throws Exception {
		hostAddress = ip_addr;
		//System.out.println(ip_addr);
		client = new Socket(hostAddress, hostPort);
	}
}
