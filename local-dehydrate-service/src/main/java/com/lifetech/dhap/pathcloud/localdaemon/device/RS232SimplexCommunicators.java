package com.lifetech.dhap.pathcloud.localdaemon.device;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPortIdentifier;

/**
 * 
 * @author yun.cao
 * 
 * create at 20161130
 *
 */
public class RS232SimplexCommunicators {
	public static RS232SimplexCommunicatorBuilder builder() {
		return new RS232SimplexCommunicatorBuilder();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> listPorts() {
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		List<String> result = new ArrayList<>();

		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				result.add(portId.getName());
			}
		}

		return result;
	}
}
