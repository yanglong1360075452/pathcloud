package com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifetech.dhap.pathcloud.localdaemon.device.PortCommunicationException;
import com.lifetech.dhap.pathcloud.localdaemon.device.RS232SimplexCommunicatorBuilder;
import com.lifetech.dhap.pathcloud.localdaemon.device.RS232SimplexCommunicators;
import com.lifetech.dhap.pathcloud.localdaemon.device.SerialPortCommunicator;

/**
 * 
 * @author yun.cao
 *
 * create at 20161130
 *
 */
public class ExcelsiorAsRS232Listener implements SerialPortListener {
	public static final int EVENT_POOL_SIZE = 10;	//max pool size to avoid permanent block thread
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelsiorAsRS232Listener.class);
	
	private ThreadPoolExecutor eventPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(EVENT_POOL_SIZE);
	private volatile SerialPortCommunicator comm;
	private Thread executor;
	private DeviceEventHandler handler;
	
	@Override
	public void listen(final String portName) {
		if (isConnected()) {
			LOGGER.info("{} already listening.", portName);
			return;
		}
		
		if (handler == null) {
			String errMsg = "No handler to process data for port: " + portName;
			LOGGER.error(errMsg);
			throw new NullPointerException();
		}
		
		executor = new Thread(new Runnable() {
			private static final long ITERVAL = 1000 * 60 * 1;
			private static final int THREAD_CHK_ITV_COUNT = 100;

			private int htBrkCount = 0;
			
			@Override
			public void run() {
				//connect
				RS232SimplexCommunicatorBuilder builder = RS232SimplexCommunicators.builder();
				comm = builder.portName(portName).build();
				
				try {
					comm.connect();
				} catch (PortCommunicationException e) {
					LOGGER.error("Cannot connect to the port: " + comm.getPortName(), e);
					comm = null;
					return;
				}
				
				//listen
				try {
					while (true) {
						//check thread status
						htBrkCount++;
						if (htBrkCount > THREAD_CHK_ITV_COUNT) {
							htBrkCount = 0;
							if (eventPool.getActiveCount() >= EVENT_POOL_SIZE) {
								LOGGER.error("Thread full of use.");
							}
						}
						
						//handle heart break
						if (eventPool.getActiveCount() < EVENT_POOL_SIZE) {
							//in case temporary block to avoid next loop to execute
							eventPool.execute(new Runnable() {
	
								@Override
								public void run() {
									try {
										try {
											handler.handleHeartBreak();
										} catch (Exception e) {
											LOGGER.error("Error occurs while handler processing heart break, port: " + comm.getPortName(), e);
										}
										
										char[] data = comm.read();
										for (int i = 0; i < data.length; i++) {
											//process data
											try {
												handler.processData(data[i]);
											} catch (Exception e) {
												LOGGER.error("Error occurs while handler processing char: " + data[i] + ", on port: " + comm.getPortName(), e);
											}
										}
									} catch (Exception e) {
										//in case of runtime exception
										LOGGER.error("Unexpected error occurs while processing event", e);
									}
								}
								
							});
						}

						//wait for terminate signal
						try {
							Thread.sleep(ITERVAL);
						} catch (InterruptedException e) {
							LOGGER.info("Recieved signal for stopping listening, port: {}.", comm.getPortName());
							break;
						}
					}
				} catch (Exception e) {
					LOGGER.error("Error occurs while reading data from port: " + comm.getPortName() + ", stop listening", e);
					//process error event
					try {
						handler.onError(ExcelsiorAsRS232Listener.this, e.getMessage());
					} catch (Exception e1) {
						LOGGER.error("Error occurs while handler processing exception, port: " + comm.getPortName(), e1);
					}
				}
				
				//exit listening loop, do terminate process
				doTerminate();
				LOGGER.info("{} stop listening successfully.", portName);
				//will invoke process whenever the connection is normally terminated or on error
				try {
					handler.onStopListening(ExcelsiorAsRS232Listener.this);
				} catch (Exception e) {
					LOGGER.error("Error occurs while handler processing termination, port: " + comm.getPortName(), e);
				}
			}
		});
		
		executor.start();
	}
	
	@Override
	public void stopListen() {
		if (! isConnected()) {
			LOGGER.info("{} is not listening.", comm.getPortName());
			return;
		}
		
		executor.interrupt();
		LOGGER.info("Prepare to stop listen, port: {}.", comm.getPortName());
	}
	@Override
	public boolean isConnected() {
		return comm != null && comm.isConnected();
	}
	
	@Override
	public DeviceEventHandler getHandler() {
		return handler;
	}

	@Override
	public void setHandler(DeviceEventHandler handler) {
		this.handler = handler;
	}

	private void doTerminate() {
		if (! isConnected()) {
			return;
		}
		
		comm.disconnect();
		comm = null;
	}
}
