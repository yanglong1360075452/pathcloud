package com.thermofisher.sts.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class VoiceRecognizeService {
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss.ms");
	private static final Logger LOGGER = Logger.getLogger(VoiceRecognizeService.class);
	private static final String APPID = "57e35477";
	private static final String FILE_SUFFIX = ".wav";
	
	public void process(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
		String saveName = TIME_FORMAT.format(new Date()) + FILE_SUFFIX;
		File saveFile = new File(new File(config.getValue("save_path")), saveName);
		try {
			FileUtils.copyInputStreamToFile(req.getInputStream(), saveFile);
		} catch (Exception e) {
			rsp.setStatus(500);
			LOGGER.error("Failed to save stream to file.", e);
			return;
		}
		
		String rspStr = null;
		try {
			String text = convertVoiceToText(saveFile);
			rspStr = "{\"code\": 0, \"content\": \"" + text + "\"}";
		} catch (Exception e) {
			rspStr = "{\"code\": -1, \"err_msg\": \"" + e.getMessage() + "\"}";
			LOGGER.error("Failed to output the result.", e);
			return;
		}

		rsp.setHeader("Access-Control-Allow-Origin", "*");
		rsp.setCharacterEncoding("utf8");
		BufferedWriter writer = new BufferedWriter(rsp.getWriter());
		writer.write(rspStr);
		writer.flush();
	}
	

	
	private String convertVoiceToText(File voiceFile) throws IOException, IllegalArgumentException, InputFormatException, EncoderException {
		SpeechUtility.createUtility("appid=" + APPID);

		if (SpeechRecognizer.getRecognizer() == null) {
			SpeechRecognizer.createRecognizer();
		}
		mResult.delete(0, mResult.length());
		recogComplete = false;
		
		File formattedFile = new File(voiceFile.getParentFile(), voiceFile.getName() + ".tmp");
		formatAudio(voiceFile, formattedFile);
		byte[] voiceBuffer = FileUtils.readFileToByteArray(formattedFile);

		if (0 == voiceBuffer.length) {
			mResult.append("no audio avaible!");
		} else {
			SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
			recognizer.setParameter(SpeechConstant.DOMAIN, "medical");
			recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
			recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
			recognizer.setParameter(SpeechConstant.VAD_EOS, "5000");
			recognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain");
			recognizer.startListening(recListener);
			ArrayList<byte[]> buffers = splitBuffer(voiceBuffer, voiceBuffer.length, 4800);
			for (int i = 0; i < buffers.size(); i++) {
				// 每次写入msc数据4.8K,相当150ms录音数据
				recognizer.writeAudio(buffers.get(i), 0, buffers.get(i).length);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			recognizer.stopListening();
		}

		while (!recogComplete) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return mResult.toString();
	}
	
	private void formatAudio(File src, File dest) throws IllegalArgumentException, InputFormatException, EncoderException {
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("pcm_s16le");
		audio.setBitRate(16);
		audio.setChannels(1);
		audio.setSamplingRate(16000);
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("wav");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(src, dest, attrs);
	}
	
	private ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize) {
		ArrayList<byte[]> array = new ArrayList<byte[]>();
		if (spsize <= 0 || length <= 0 || buffer == null
				|| buffer.length < length)
			return array;
		int size = 0;
		while (size < length) {
			int left = length - size;
			if (spsize < left) {
				byte[] sdata = new byte[spsize];
				System.arraycopy(buffer, size, sdata, 0, spsize);
				array.add(sdata);
				size += spsize;
			} else {
				byte[] sdata = new byte[left];
				System.arraycopy(buffer, size, sdata, 0, left);
				array.add(sdata);
				size += left;
			}
		}
		return array;
	}
	
	private RecognizerListener recListener = new RecognizerListener() {

		public void onBeginOfSpeech() {
		}

		public void onEndOfSpeech() {
		}

		public void onVolumeChanged(int volume) {

		}

		public void onResult(RecognizerResult result, boolean islast) {
			String txt = result.getResultString();
			mResult.append(txt);
			
			if (islast) {
				recogComplete = true;
			}
		}

		public void onError(SpeechError error) {
			recogComplete = true;
			mResult.delete(0, mResult.length());
			mResult.append("error: " + error.toString());
			LOGGER.error("error: " + error.toString());
		}

		public void onEvent(int eventType, int arg1, int agr2, String msg) {
			LOGGER.info("event: " + msg);
		}

	};

	public ConfigUtil getConfig() {
		return config;
	}

	public void setConfig(ConfigUtil config) {
		this.config = config;
	}

	private StringBuffer mResult = new StringBuffer();
	private ConfigUtil config;
	private volatile boolean recogComplete;
}
