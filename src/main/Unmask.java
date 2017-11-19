package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Unmask {
	public static void main(String[] args) {
		Unmask unmask = new Unmask();
		unmask.make(args[0], args[1]);
	}
	
	private void make(String firstImagePath, String secondImagePath) {
		BufferedImage img1;
		BufferedImage img2;
		
		try {
			img1 = ImageIO.read(new File(firstImagePath));
			img2 = ImageIO.read(new File(secondImagePath));
			int w = img1.getWidth();
			int h = img1.getHeight();
			
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			for (int j = 0 ; j < h; j++) {
				for (int i = 0; i < w; i++) {
					if (img1.getRGB(i, j) == img2.getRGB(i, j)) {
						img.setRGB(i, j, img1.getRGB(i, j));
					} else {
						img.setRGB(i, j, Color.BLACK.getRGB());
					}
				}
			}
			
			String format = Utils.getFormat(firstImagePath);
			String fileName = Utils.getFileName(firstImagePath, format);
			ImageIO.write(img, format, new File(Utils.makeMergedImageName(fileName, format)));
		} catch (IOException e) {
			System.out.println("Nie ma takiego pliku");
		}
	}
}
