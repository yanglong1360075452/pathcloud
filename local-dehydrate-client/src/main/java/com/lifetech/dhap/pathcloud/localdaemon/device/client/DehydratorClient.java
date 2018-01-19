package com.lifetech.dhap.pathcloud.localdaemon.device.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifetech.dhap.pathcloud.localdaemon.device.dehydrator.ExcelsiorAsRS232Listener;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DehydratorClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(DehydratorClient.class);
	private static final String BASE_URL = "base_url";
	private static final String KEY_SN = "sn";
	private static final String KEY_PORT = "port";
	private static final String KEY_DEVICES = "devices";
	private static final String CFG_FILE = "/cfg.json";
	
	public static void main(String[] args) {
		LOGGER.info("Program started.");
		new DehydratorClient().execute();
		LOGGER.info("Program ended");
	}
	
	public void execute() {
		try {
			loadProperties();
		} catch (Exception e) {
			LOGGER.error("Program exit due to configure error", e);
			return;
		}
		
		for (Map<String, String> device: portCfg) {
			ExcelsiorAsRS232Listener listener = new ExcelsiorAsRS232Listener();
			DehydratorEventHandler handler = new DehydratorEventHandler(baseUrl, device.get(KEY_SN));
			listener.setHandler(handler);
			listener.listen(device.get(KEY_PORT));
		}
	}
	
	private void loadProperties() throws IOException {
		InputStream in = null;
		String jsonStr = null;
		try {
			in = this.getClass().getResourceAsStream(CFG_FILE);
			jsonStr = IOUtils.toString(in, "utf-8");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.error("Failed to close file: " + CFG_FILE);
				}
			}
		}
		
		JSONObject root = JSONObject.fromObject(jsonStr);
		baseUrl = root.getString(BASE_URL);
		
		JSONArray devices = root.getJSONArray(KEY_DEVICES);
		portCfg = new ArrayList<>();
		for (int i=0; i<devices.size(); i++) {
			JSONObject device = devices.getJSONObject(i);
			Map<String, String> unit = new HashMap<>();
			String sn = device.getString(KEY_SN);
			String port = device.getString(KEY_PORT);
			if (sn == null || sn.trim().equals("") || port == null || port.trim().equals("")) {
				throw new IOException("Null device info");
			}
			
			unit.put(KEY_SN, sn);
			unit.put(KEY_PORT, port);
			portCfg.add(unit);
		}
	}
	
	private String baseUrl;
	private List<Map<String, String>> portCfg;
}
