package com.tripcaddie.frontend.galleries.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GenerateThumbnailFromVideo {

	protected String ffmpegApp;

	public void getThumb(String videoFilename, String thumbFilename, int width,
			int height, int hour, int min, float sec)

	throws IOException, InterruptedException

	{

		ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
				"-i", videoFilename, "-vframes", "1",

				"-ss", hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width
						+ "*" + height, "-an", thumbFilename);

		Process process = processBuilder.start();

		InputStream stderr = process.getErrorStream();

		InputStreamReader isr = new InputStreamReader(stderr);

		BufferedReader br = new BufferedReader(isr);

		String line;

		while ((line = br.readLine()) != null)
			;

		process.waitFor();

	}

	public void GenerateThumbnail(String videoconverterpath, String inputpath,
			String outputpath) throws IOException, InterruptedException

	{
		try{
		this.ffmpegApp = videoconverterpath;
		getThumb(inputpath, outputpath, 180, 140, 0, 0, 10);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
