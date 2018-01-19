package com.thermofisher.sts.demo;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(DispatcherServlet.class);
	
	@Override
	public void init() {
		config = new ConfigUtil();
		try {
			config.init();
		} catch (IOException e) {
			LOGGER.error("Failed to load config.", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		String path = req.getPathInfo();
		if (isPathMatch("/voice_files", path)) {
			VoiceFileViewService fileService = new VoiceFileViewService();
			fileService.setConfig(config);
			fileService.setContextPath(req.getContextPath());
			fileService.listFiles(req, rsp);
		} else if (isPathMatch("/download", path)) {
			VoiceFileViewService fileService = new VoiceFileViewService();
			fileService.setConfig(config);
			fileService.outputFile(req, rsp);
		} else {
			rsp.sendError(404);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		String path = req.getPathInfo();
		if (isPathMatch("/recognize", path)) {
			VoiceRecognizeService recogServ = new VoiceRecognizeService();
			recogServ.setConfig(config);
			recogServ.process(req, rsp);
		} else {
			rsp.sendError(404);
		}
	}
	
	private boolean isPathMatch(String pattern, String path) {
		return pattern.equals(path) || (pattern + "/").equals(path);
	}
	
	private ConfigUtil config;
}
