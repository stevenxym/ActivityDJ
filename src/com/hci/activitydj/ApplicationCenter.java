package com.hci.activitydj;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ApplicationCenter extends Application {

	private NetworkCommunication network;
	private Handler handler;				// tmp handler to update UI thread
	private Handler networkHandler;
	
	private DJManager dj;
	private KineticParser kinetic;
	
	@Override
	public void onCreate() {
	}
	
	@Override
	public void onTerminate() {
		// do nothing
	}
	
	public void setHandler(Handler h) {
		handler = h;
	}
	
	public void setContext(Context context) {
		dj.setContextChanged(context);
	}
	
	public void initApplication(Context context) {
		dj = new DJManager(context);
		kinetic = new KineticParser(dj);
		
		networkHandler = new Handler() {
			@Override
			public void handleMessage (Message msg) {
				Message newMsg = new Message();
				newMsg.obj = msg.obj;
				if (handler != null) {
					handler.sendMessage(newMsg);
					kinetic.parseKineticCommand(msg.obj.toString());
				}
			}
		};
	}
	
	public void change(actionState s) {
		dj.changeSong(s);
	}
	
	public void stop() {
		dj.stop();
	}
	
	/**
	 * Network Communication API
	 */
	
	/* connect to network and start running the daemon */
	public boolean setupNetwork(String ip, Context c) {
		//if (handler == null)
		//	return false;
		
		network = new NetworkCommunication(networkHandler, ip);
		network.start();		// start daemon
		
		if (!this.checkConnectionSuccess()) {
			network.showNetworkExceptionAlert(c);
			return false;
		}
		
		return true;
	}
	
	private boolean checkConnectionSuccess() {
		
		try {
			Thread.sleep(500);				// wait for 0.5s for network thread setup connection
		} catch (InterruptedException e) {
			// do nothing
		}
		
		if (!network.getNetworkStatus()) {
			try {
				Thread.sleep(500);			// wait for another 0.5s for possible asynchronization
			} catch (InterruptedException e1) {
				// do nothing
			}
			
			if (!network.getNetworkStatus())
				return false;
		}
		
		return true;
	}
	
	/* disconnect and delete the object */
	public void disconnectNetwork() {
		if (network != null) {
			network.stopCommunication();
			network = null;
		}
	}
	
	/* check current network state */
	public boolean networkRunning() {
		return network != null;
	}
}
