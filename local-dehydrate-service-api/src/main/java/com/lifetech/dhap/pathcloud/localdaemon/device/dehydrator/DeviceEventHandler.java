package com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator;

/**
 * 
 * @author yun.cao
 * 
 * create at 20161129
 *
 */
public interface DeviceEventHandler {
	/**
	 * To process each char read from the device, eg. serial port
	 * 
	 * @param c char read from the device
	 */
	void processData(char c);
	
	/**
	 * Will receive this notification while ready to read next data from device, eg. serial port
	 * 
	 */
	void handleHeartBreak();
	
	/**
	 * 
	 * @param listener the listener which call this handler
	 */
	void onError(SerialPortListener listener, String msg);
	
	
	/**
	 * Will receive this notification whenever listener is terminated normally or with error
	 * 
	 * @param listener the listener which call this handler
	 */
	void onStopListening(SerialPortListener listener);
}
