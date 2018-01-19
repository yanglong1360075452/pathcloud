package com.lifetech.dhap.pathcloud.quartz;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;

/**
 * @author yun.cao
 * 
 * 20161219
 */
public class DehydratorTask {
	private static final long MAX_WAIT = 5 * 60 * 1000;
	
	private Random random = new Random();
	
	@Autowired
	private InstrumentApplication instrumentApplication;
	
    public void init(){}

    public void checkHeartBreak() {
    	try {
			Thread.sleep((long) (MAX_WAIT * random.nextDouble()));
		} catch (InterruptedException e) {
			//Will not happen
		}
    	
    	instrumentApplication.checkHeartBreak();
    }
}
