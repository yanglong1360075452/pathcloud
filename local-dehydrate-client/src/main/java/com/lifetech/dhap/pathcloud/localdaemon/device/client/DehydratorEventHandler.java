package com.lifetech.dhap.pathcloud.localdaemon.device.client;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentEventType;
import com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator.DeviceEventHandler;
import com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator.SerialPortListener;

public class DehydratorEventHandler implements DeviceEventHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DehydratorEventHandler.class);
	private static final int HTTP_POST = 0;
	private static final int HTTP_PUT = 1;
	
	private static final String PARENT_PATH = "/dehydrator/local-msg";
	private static final String HEARTBREAK_PATH = "/heartbreak";
	private static final String EVENT_CODE_PATH = "/eventcode";
	private static final String EVENT_MSG_PATH = "/eventmsg";
	
	public DehydratorEventHandler(String baseUrl, String sn) {
		if (baseUrl == null || sn == null) {
			throw new NullPointerException();
		}
		this.baseUrl = baseUrl;
		this.sn = sn;
		
		client = HttpClients.createDefault();
	}
	
	@Override
	public void processData(char c) {
		String url = baseUrl + PARENT_PATH + EVENT_CODE_PATH + "/" + sn;
		send(url, "" + c, null, null, HTTP_POST);
	}

	@Override
	public void handleHeartBreak() {
		String url = baseUrl + PARENT_PATH + HEARTBREAK_PATH + "/" + sn;
		send(url, null, null, null, HTTP_PUT);
	}

	@Override
	public void onError(SerialPortListener listener, String msg) {
		String url = baseUrl + PARENT_PATH + EVENT_MSG_PATH + "/" + sn;
		send(url, null, "Error occurs, message: ." + msg, InstrumentEventType.ERR, HTTP_POST);
	}

	@Override
	public void onStopListening(SerialPortListener listener) {
		String url = baseUrl + PARENT_PATH + EVENT_MSG_PATH + "/" + sn;
		send(url, null, "Stop listening.", InstrumentEventType.INFO, HTTP_POST);
	}
	
	private void send(String url, String code, String msg, InstrumentEventType severe, int httpMethod) {
		LOGGER.info("send " + code + " " + msg + " to " + url);
		HttpEntityEnclosingRequestBase entityReq = null;
		int retry = 4;
		while (retry-- > 0) {
			try {
				if (httpMethod == HTTP_POST) {
					entityReq = new HttpPost(url);
				} else if (httpMethod == HTTP_PUT) {
					entityReq = new HttpPut(url);
				} else {
					throw new IllegalArgumentException("Invalid method: " + httpMethod);
				}

				List<BasicNameValuePair> formData = new ArrayList<>();
				formData.add(new BasicNameValuePair("eventTimestamp", "" + new Date().getTime()));
				if (code != null) {
					formData.add(new BasicNameValuePair("code", "" + code));
				}
				if (msg != null && severe != null) {
					formData.add(new BasicNameValuePair("msg", "" + msg));
					formData.add(new BasicNameValuePair("severe", "" + severe.getCode()));
				}

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formData, "utf-8");

				entityReq.setEntity(entity);

				client.execute(entityReq);
				break;
			} catch (SocketException e) {
				LOGGER.error("Failed to send data to server " + url + ", will retry " + retry + " more times.", e);
				try {
					if (retry > 0) {
						Thread.sleep(10000l * (4 - retry));
					}
				} catch (InterruptedException exp) {
					LOGGER.error("Catch InterruptedException", exp);
				}
			} catch (Exception e) {
				LOGGER.error("Failed to send data to server " + url, e);
				break;
			} finally {
				if (entityReq != null) {
					entityReq.releaseConnection();
				}
			}
		}
	}

	private CloseableHttpClient client;
	private String baseUrl;
	private String sn;
}
