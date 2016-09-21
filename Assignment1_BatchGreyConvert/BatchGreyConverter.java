/**
 * Write a description of BatchGreyConverter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;

public class BatchGreyConverter {
    
    public void selectAndSave(){
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource grey = doGreyConversion(inImage);
            String fname = inImage.getFileName();
            String newName = "grey-" + fname;
            grey.setFileName(newName);
            grey.draw();
            grey.save();
    }
}
    
    public ImageResource doGreyConversion(ImageResource inputImage) {
        ImageResource outImage = new ImageResource(inputImage.getWidth(), inputImage.getHeight());
		//for each pixel in outImage
		for (Pixel pixel: outImage.pixels()) {
			//look at the corresponding pixel in inImage
			Pixel inPixel = inputImage.getPixel(pixel.getX(), pixel.getY());
			//compute inPixel's red + inPixel's blue + inPixel's green
			//divide that sum by 3 (call it average)
			int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen())/3;
			//set pixel's red to average
			pixel.setRed(average);
			//set pixel's green to average
			pixel.setGreen(average);
			//set pixel's blue to average
			pixel.setBlue(average);
		}
		//outImage is your answer
		return outImage;
    }
}
