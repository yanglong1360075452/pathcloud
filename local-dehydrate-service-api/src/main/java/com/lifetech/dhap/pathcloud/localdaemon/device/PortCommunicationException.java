package com.lifetech.dhap.pathcloud.localdaemon.device;

/**
 * 
 * @author yun.cao
 * 
 * create at 20161129
 *
 */
public class PortCommunicationException extends Exception {
	private static final long serialVersionUID = 1L;

	public PortCommunicationException(String paramString, SerialPortCommunicator comm) {
		super(paramString);
		if (comm != null) {
			callerId = comm.getCallerId();
			portName = comm.getPortName();
		}
	}
	
	public PortCommunicationException(String paramString, SerialPortCommunicator comm, Throwable e) {
		super(paramString, e);
		
		if (comm != null) {
			callerId = comm.getCallerId();
			portName = comm.getPortName();
		}
	}

	public String getPortName() {
		return portName;
	}
	
	public String getCallerId() {
		return callerId;
	}

	private String callerId;
	private String portName;
}
