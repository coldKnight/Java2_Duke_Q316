/**
 * Write a description of BatchInversions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;

public class BatchInversions {
    public ImageResource makeInversion(ImageResource inputImage){
        ImageResource outImage = new ImageResource(inputImage.getWidth(), inputImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inputImage.getPixel(pixel.getX(), pixel.getY());
            int reverse = (255-inPixel.getRed()) + (255-inPixel.getBlue()) + (255-inPixel.getGreen());
            
            pixel.setRed(255-inPixel.getRed());
            pixel.setGreen(255-inPixel.getGreen());
            pixel.setBlue(255-inPixel.getBlue());
        }
        return outImage;
    }
    
    public void selectAndConvert(){
        DirectoryResource dir = new DirectoryResource();
        for (File f : dir.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource reverseIm = makeInversion(inImage);
            
            String fname = inImage.getFileName();
            String newName = "inverted-" + fname;
            reverseIm.setFileName(newName);
            reverseIm.draw();
            reverseIm.save();
        }           
    }
}
