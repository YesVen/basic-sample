package com.optsd.basic.sample.io;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessing {

	public void readImageFromFile() {
		try {
			ImageIO.read(new File("~/Desktop/cat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
