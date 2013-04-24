package com.hci.activitydj;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

public class NetworkCommunication extends Thread {
	
	private boolean isRunning = false;   //indicator of thread
	//private int timer;
	private Handler sysHandler;   //receiver of the handler of current view
	private ClientSocket client;
	private Queue<String> MsgQueue = new LinkedList<String>();
	
	private String IPAdress = null;
	
	@Override
	public void run() {
		
		try {
			client = new ClientSocket(IPAdress);
			isRunning = true;
		} catch (Exception e) {
			System.out.println(e);
			isRunning = false;
		}
		
		//String MsgToSend = "-N";
		
		while (isRunning) {
			//try {
				//MsgToSend = "-N";
				
				// receive message
				Message msg = new Message();
				msg.obj = client.ReceiveMsg();
				
				if (msg.obj != null) {
					sysHandler.sendMessage(msg);
				}
				
				// send commands to controller
				//if ( MsgQueue.size() != 0 )
				//	MsgToSend = MsgQueue.poll();
					
				//client.SendMsg(MsgToSend);
					
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
		}
	}
	
	public NetworkCommunication(Handler h, String ip_addr) {
		IPAdress = ip_addr;
		sysHandler = h;
		//this.start();
	}
	
	public void getMsgFromUsr (String s) {
		MsgQueue.offer(s);
	}
	
	public void stopCommunication () {
		isRunning = false;
		if (client != null) {
			client.CloseClient();
			client = null;   //delete current socket object
		}
	}
	
	public boolean getNetworkStatus() {
		return isRunning;
	}
	
	public void showNetworkExceptionAlert(Context context) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Fail to Connect to Server");
		alert.setMessage("Please make sure you have entered correct IP address");
		alert.setPositiveButton("OK", null);
		AlertDialog alertDialog = alert.create();
        alertDialog.show();
	}
}