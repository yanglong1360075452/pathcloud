package com.thermofisher.sts.demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class VoiceFileViewService {
	public void listFiles(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		PrintWriter writer = rsp.getWriter();
		File dir = new File(config.getValue("save_path"));
		
		writer.println("<html>");
		File[] sortedFiles = dir.listFiles();
		Arrays.sort(sortedFiles, new Comparator<File>(){
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		for (File f: dir.listFiles()) {
			if (f.getName().contains(".tmp")) {
				continue;
			}
			writer.println(generateHtmlRow(f));
		}
		
		writer.println("</html>");
	}
	
	private String generateHtmlRow(File file) {
		StringBuilder html = new StringBuilder();
		
		String fileName = file.getName();
		html.append("<p>");
		html.append("<a href=\"" + contextPath + "/server/download?name=" + fileName + "\">");
		html.append(fileName);
		html.append("</a>");
		html.append("</p>");
		
		return html.toString();
	}
	
	public void outputFile(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		String fileName = req.getParameter("name").replace("/", "");
		File file = new File(config.getValue("save_path"), fileName);
		rsp.setContentType("audio/x-wav");
		rsp.addHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		FileUtils.copyFile(file, rsp.getOutputStream());
	}
	
	public ConfigUtil getConfig() {
		return config;
	}

	public void setConfig(ConfigUtil config) {
		this.config = config;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	private ConfigUtil config;
	private String contextPath;
}
