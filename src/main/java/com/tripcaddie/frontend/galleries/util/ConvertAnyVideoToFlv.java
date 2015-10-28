package com.tripcaddie.frontend.galleries.util;

import java.io.IOException;

public class ConvertAnyVideoToFlv {
	
	public void convertvideo(String videoconverterpath , String inputpath , String outputpath) throws IOException, InterruptedException {
		FlvConverter flvConverter = new FlvConverter(videoconverterpath);
		flvConverter.convert(inputpath, outputpath, 320, 200, 5);
	}
}
