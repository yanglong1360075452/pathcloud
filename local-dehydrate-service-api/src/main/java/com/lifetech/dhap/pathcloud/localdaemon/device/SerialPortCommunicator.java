package com.lifetech.dhap.pathcloud.localdaemon.device;

/**
 * 
 * @author yun.cao
 * 
 * create at 20161129
 *
 */
public interface SerialPortCommunicator {
	/**
	 * Id that called this communicator
	 * 
	 * @return
	 */
	String getCallerId();
	
	/**
	 * 
	 * @return Port name to connect, eg. COM1
	 */
	String getPortName();
	
	/**
	 * 
	 * @throws PortCommunicationException Will throw while any exception occurs, eg. open, change connection parameter, get stream
	 */
	void connect() throws PortCommunicationException;
	
	/**
	 * 
	 * @return Read available bytes from the port
	 * @throws PortCommunicationException 
	 * @throws InterruptedException 
	 */
	char[] read() throws PortCommunicationException;
	
	/**
	 * 
	 * @param data
	 * @throws PortCommunicationException 
	 * 
	 */
	void write(char data) throws PortCommunicationException;
	
	/**
	 * 
	 * @param data
	 * @throws PortCommunicationException 
	 * 
	 */
	void write(char[] data) throws PortCommunicationException;
	
	/**
	 * String will be encoded as utf-8
	 * 
	 * @param data
	 * @throws PortCommunicationException 
	 * 
	 */
	void write(String data) throws PortCommunicationException;
	
	void disconnect();
	
	boolean isConnected();
}
