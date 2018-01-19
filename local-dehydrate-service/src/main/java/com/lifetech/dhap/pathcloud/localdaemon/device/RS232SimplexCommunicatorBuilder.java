package com.lifetech.dhap.pathcloud.localdaemon.device;

import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author yun.cao
 * create at 20161128
 * 
 * Recommend to use RS232SimplexCommunicatorBuilder.builder() to create this instance.
 * 
 * Default setting:
 * id: <random UUID>(to identify connection caller), portName: null, baudRate: 115200, dataBits: 8, stopBits: 1; parity: 0
 * 
 */
public class RS232SimplexCommunicatorBuilder {
	private String id;
	private String portName;
	private int baudRate = 115200;
	private int dataBits = SerialPort.DATABITS_8;
	private int stopBits = SerialPort.STOPBITS_1;
	private int parity = SerialPort.PARITY_NONE;
	
	public String getId() {
		return id;
	}
	public RS232SimplexCommunicatorBuilder idd(String id) {
		this.id = id;
		return this;
	}
	public String getPortName() {
		return portName;
	}
	public RS232SimplexCommunicatorBuilder portName(String portName) {
		this.portName = portName;
		return this;
	}
	public int getBaudRate() {
		return baudRate;
	}
	public RS232SimplexCommunicatorBuilder baudRate(int baudRate) {
		this.baudRate = baudRate;
		return this;
	}
	public int getDataBits() {
		return dataBits;
	}
	public RS232SimplexCommunicatorBuilder dataBits(int dataBits) {
		this.dataBits = dataBits;
		return this;
	}
	public int getStopBits() {
		return stopBits;
	}
	public RS232SimplexCommunicatorBuilder stopBits(int stopBits) {
		this.stopBits = stopBits;
		return this;
	}
	public int getParity() {
		return parity;
	}
	public RS232SimplexCommunicatorBuilder parity(int parity) {
		this.parity = parity;
		return this;
	}
	
	public SerialPortCommunicator build() {
		RS232SimplexCommunicator communicator = new RS232SimplexCommunicator();
		communicator.setPortName(portName);
		communicator.setBaudRate(baudRate);
		communicator.setDataBits(dataBits);
		communicator.setStopBits(stopBits);
		communicator.setParity(parity);
		communicator.setCallerId(id == null ? UUID.randomUUID().toString() : id);
		return communicator;
	}

	private static class RS232SimplexCommunicator implements SerialPortCommunicator {
		private static final Logger LOGGER = LoggerFactory.getLogger(RS232SimplexCommunicator.class);
		private static final int CONN_INTERVAL = 3000;
		
		private String callerId;
		private String portName;
		private int baudRate;
		private int dataBits;
		private int stopBits;
		private int parity;
		
		private SerialPort conn;
		private volatile boolean connected;
		
		private InputStream in;
		private OutputStream out;
		
		@Override
		public void connect() throws PortCommunicationException {
			if (connected) {
				LOGGER.info("Already connected, port name: {}, caller id: {}.", portName, callerId);
				return;
			}
			
			try {
				CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				conn = (SerialPort) portIdentifier.open(callerId, CONN_INTERVAL);
				connected = true;
				conn.setSerialPortParams(baudRate, dataBits, stopBits, parity);

				in = conn.getInputStream();
				out = conn.getOutputStream();

				LOGGER.info("Successfully connected, port name: {}, caller id: {}.", portName, callerId);
			} catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | IOException e) {
				String errMsg = null;
				if (e instanceof NoSuchPortException) {
					errMsg = "No port found, port name: " + portName + ", caller id: " + callerId;
				} else if (e instanceof PortInUseException) {
					errMsg = "Port is in use, port name: " + portName + ", caller id: " + callerId;
				} else if (e instanceof UnsupportedCommOperationException) {
					errMsg = "Failed to set to specified communication params, portName: " + portName + ", caller id: " + callerId;
				} else if (e instanceof IOException) {
					errMsg = "Failed to open io stream, portName: " + portName + ", caller id: " + callerId;
				}
				
				disconnect();

				LOGGER.error(errMsg, e);
				throw new PortCommunicationException(errMsg, this, e);
			}
		}

		@Override
		public String getCallerId() {
			return callerId;
		}

		public void setCallerId(String callerId) {
			this.callerId = callerId;
		}

		@Override
		public String getPortName() {
			return this.portName;
		}

		@Override
		public char[] read() throws PortCommunicationException {
			if (! connected) {
				String errMsg = "Connection not open, portName: " + portName + ", caller id: " + callerId;
				LOGGER.error(errMsg);
				throw new PortCommunicationException(errMsg, this);
			}

			try {
				//read all available on this turn
				int avail = in.available();
				if (avail == 0) {
					return new char[0];
				}
				
				byte[] buffer = new byte[avail];
				in.read(buffer, 0, avail);
				
				char[] result = new char[avail];
				for(int i=0; i<avail; i++) {
					result[i] = byteToChar(buffer[i]);
				}
				return result;
			} catch (IOException e) {
				String errMsg = "Failed to read data, portName: " + portName + ", caller id: " + callerId;
				LOGGER.error(errMsg);
				throw new PortCommunicationException(errMsg, this, e);
			}
		}

		@Override
		public void write(char data) throws PortCommunicationException {
			try {
				out.write(data);
			} catch (IOException e) {
				String errMsg = "Failed to write data with byte: " + data + ", portName: " + portName + ", caller id: " + callerId;
				LOGGER.error(errMsg);
				throw new PortCommunicationException(errMsg, this, e);
			}
		}

		@Override
		public void write(char[] data) throws PortCommunicationException {
			if (data == null) {
				return;
			}
			
			try {
				byte[] convert = new byte[data.length];
				for (int i=0; i<convert.length; i++) {
					convert[i] = (byte) data[i];
				}
				
				out.write(convert);
			} catch (IOException e) {
				String errMsg = "Failed to write data, size: " + data.length + ", portName: " + portName + ", caller id: " + callerId;
				LOGGER.error(errMsg);
				throw new PortCommunicationException(errMsg, this, e);
			}
		}

		@Override
		public void write(String data) throws PortCommunicationException {
			try {
				out.write(data.getBytes("UTF-8"));
			} catch (IOException e) {
				String errMsg = "Failed to write string: " + data + ", portName: " + portName + ", caller id: " + callerId;
				LOGGER.error(errMsg);
				throw new PortCommunicationException(errMsg, this, e);
			}
		}

		@Override
		public void disconnect() {
			if (! connected) {
				LOGGER.info("Already disconnected, port name: {}, caller id: {}.", portName, callerId);
				return;
			}
			
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e1) {
					LOGGER.error("Failed to close inputstream, portName: " + portName + ", caller id: " + callerId);
				}
			}

			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e1) {
					LOGGER.error("Failed to close outputstream, portName: " + portName + ", caller id: " + callerId);
				}
			}
			
			if (conn != null) {
				conn.close();
				conn = null;
			}
			
			connected = false;
			
			LOGGER.info("Connection disconnected, portName: {}, caller id: {}.", portName, callerId);
		}

		@Override
		public boolean isConnected() {
			return connected;
		}

		public void setPortName(String portName) {
			this.portName = portName;
		}

		public void setBaudRate(int baudRate) {
			this.baudRate = baudRate;
		}


		public void setDataBits(int dataBits) {
			this.dataBits = dataBits;
		}


		public void setStopBits(int stopBits) {
			this.stopBits = stopBits;
		}

		public void setParity(int parity) {
			this.parity = parity;
		}
		
		private char byteToChar(byte b) {
			return (char) (b < 0 ? b + 256 : b);
		}
	}
}
