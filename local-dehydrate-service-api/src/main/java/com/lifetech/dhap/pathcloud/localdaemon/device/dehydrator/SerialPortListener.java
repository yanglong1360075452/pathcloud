package com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator;

/**
 * 
 * @author yun.cao
 * 
 * create at 20161129
 *
 */
public interface SerialPortListener {
	void listen(String portName);
	void stopListen();
	boolean isConnected();
	DeviceEventHandler getHandler();
	void setHandler(DeviceEventHandler handler);
}
