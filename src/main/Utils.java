package main;

public class Utils {
	public static String makeImageWithLayerName(String fileName, String format, int imageNumber) {
		return fileName + imageNumber + "." + format;
	}
	
	public static String getFormat(String path) {
		String[] splitPath = path.split("\\.");

		return splitPath[splitPath.length - 1];
	}
	
	public static String getFileName(String path, String format) {
		int lastCharIndex = path.lastIndexOf(format);
		
		return path.substring(0, lastCharIndex - 1);
	}
	
	public static String makeMergedImageName(String fileName, String format) {
		return fileName.substring(0, fileName.length() - 1) + "_new" + "." + format;
	}
}
