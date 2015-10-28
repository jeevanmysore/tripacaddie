package com.tripcaddie.frontend.galleries.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FlvConverter {

	 private String ffmpegApp;
	    public FlvConverter(String ffmpegApp) {
	        this.ffmpegApp = ffmpegApp;
	    }
	    public void convert(String filenameIn, String filenameOut, int width, int height) throws IOException, InterruptedException {
	        convert(filenameIn, filenameOut, width, height, -1);
	    }
	    public int convert(String filenameIn, String filenameOut, int width, int height, int quality)
	            throws IOException, InterruptedException {
	        ProcessBuilder processBuilder;
	        if (quality > -1) {
	            processBuilder = new ProcessBuilder(ffmpegApp, "-i", filenameIn, "-ar", "44100",
	                    "-s", width + "*" + height, "-qscale", quality + "", filenameOut);
	        } else {
	            processBuilder = new ProcessBuilder(ffmpegApp, "-i", filenameIn, "-ar", "44100",
	                    "-s", width + "*" + height, filenameOut);
	        }
	        Process process = processBuilder.start();
	        InputStream stderr = process.getErrorStream();
	        InputStreamReader isr = new InputStreamReader(stderr);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        while ((line = br.readLine()) != null) ;
	        {
	        }
	        return process.waitFor();
	    }
}
