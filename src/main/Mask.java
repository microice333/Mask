package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class Mask {
	private Layers layers = new Layers();
	private static Random RND = new Random();
	private static final int LAYERS_NUMBER = 6;
	
	private class Layers {
		private static final int LAYER_1 = 0;
		private static final int LAYER_2 = 1;
		private static final int LAYER_3 = 2;
		private static final int LAYER_4 = 3;
		private static final int LAYER_5 = 4;
		private static final int LAYER_6 = 5;
		
		private int[][] layer1 = {{Color.WHITE.getRGB(), Color.BLACK.getRGB()}, {Color.WHITE.getRGB(), Color.BLACK.getRGB()}};
		private int[][] layer2 = {{Color.BLACK.getRGB(), Color.WHITE.getRGB()}, {Color.BLACK.getRGB(), Color.WHITE.getRGB()}};
		private int[][] layer3 = {{Color.BLACK.getRGB(), Color.BLACK.getRGB()}, {Color.WHITE.getRGB(), Color.WHITE.getRGB()}};
		private int[][] layer4 = {{Color.WHITE.getRGB(), Color.WHITE.getRGB()}, {Color.BLACK.getRGB(), Color.BLACK.getRGB()}};
		private int[][] layer5 = {{Color.WHITE.getRGB(), Color.BLACK.getRGB()}, {Color.BLACK.getRGB(), Color.WHITE.getRGB()}};
		private int[][] layer6 = {{Color.BLACK.getRGB(), Color.WHITE.getRGB()}, {Color.WHITE.getRGB(), Color.BLACK.getRGB()}};
		
		private Map<Integer, int[][]> layersMap = new HashMap<>();
		
		private Layers() {
			layersMap.put(LAYER_1, layer1);
			layersMap.put(LAYER_2, layer2);
			layersMap.put(LAYER_3, layer3);
			layersMap.put(LAYER_4, layer4);
			layersMap.put(LAYER_5, layer5);
			layersMap.put(LAYER_6, layer6);
		}
		
		private int[][] getLayer(int option) {
			return layersMap.get(option);
		}
		
		private int[][] getOpositeLayer(int option) {
			if (option % 2 == 0)
				return layersMap.get(option + 1);
			
			return layersMap.get(option - 1);
		}
	}
	
	public static void main(String[] args) {
		Mask mask = new Mask();
		mask.make(args[0]);
	}
	
	private void make(String path) {		
		BufferedImage img;
		
		try {
			img = ImageIO.read(new File(path));
			int w = img.getWidth();
			int h = img.getHeight();
			
			BufferedImage img1 = new BufferedImage(2 * w, 2 * h, BufferedImage.TYPE_INT_RGB);
			BufferedImage img2 = new BufferedImage(2 * w, 2 * h, BufferedImage.TYPE_INT_RGB);

			for (int j = 0 ; j < h; j++) {
				for (int i = 0; i < w; i++) {
					int option = RND.nextInt(LAYERS_NUMBER);
					int[][] lay1 =  layers.getLayer(option);
					int[][] lay2 =  layers.getOpositeLayer(option);
					
					if (img.getRGB(i, j) == Color.BLACK.getRGB()) {
						setColors(img1, lay1, i, j);
						setColors(img2, lay2, i, j);
					} else {
						setColors(img1, lay1, i, j);
						setColors(img2, lay1, i, j);
					}
				}
			}
			String format = Utils.getFormat(path);
			String fileName = Utils.getFileName(path, format);
			
			ImageIO.write(img2, format, new File(Utils.makeImageWithLayerName(fileName, format, 2)));
			ImageIO.write(img1, format, new File(Utils.makeImageWithLayerName(fileName, format, 1)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setColors(BufferedImage img, int[][] layer, int i, int j) {
		img.setRGB(2 * i, 2 * j, layer[0][0]);
		img.setRGB(2 * i + 1, 2 * j, layer[0][1]);
		img.setRGB(2*i, 2*j + 1, layer[1][0]);
		img.setRGB(2*i + 1, 2*j + 1, layer[1][1]);
	}
}
