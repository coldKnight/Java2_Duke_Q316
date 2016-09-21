
/**
 * Write a description of WebLinkFinder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class WebLinkFinder {
    
    public String findLink(String inputWord) {
        String contentLow = inputWord.toLowerCase();
        int ind = contentLow.indexOf("youtube");
        
        if (ind != -1) {
            int start = inputWord.lastIndexOf("\"", ind);
            int stop = inputWord.indexOf("\"", ind);
            return inputWord.substring(start+1, stop);
        }
        else {
            return "";
        }
    }
    
    public void Testing() {
        URLResource weburl = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String word : weburl.words()) {
            String result = findLink(word);
            if (!result.equals("")) {
                System.out.println(result);
            }
        }
    }
    
    public String findHref(String inputLine) {
        int ind = inputLine.indexOf("href=");
        
        if (ind != -1) {
            int start = ind;
            int stop = inputLine.indexOf("\"", start+6);
            return inputLine.substring(start+6, stop);
        }
        else {
            return "";
        }
    }
    
    public StorageResource findURLs(String url){
        URLResource weburl = new URLResource(url);
        StorageResource store = new StorageResource();
        for (String line : weburl.lines()){
            String result = findHref(line);
            if(!result.equals("")){
                store.add(result);
                //System.out.println(result);
            }
        }
        return store;
    }
    
    public void testURLWithStorage(){
        String url = "http://www.dukelearntoprogram.com/course2/data/newyorktimes.html";
        StorageResource urlStore = findURLs(url);
        int start = 0;
        int pos = 0;
        int httpC = 0;
        int secCount = 0;
        int comCount = 0;
        int comEndCount = 0;
        int dotCount = 0;
        for (String line : urlStore.data()){
            System.out.println(line);
            if (line.indexOf("http") != -1){
                httpC += 1;
            }            
            if (line.indexOf("https") != -1){
                secCount += 1;
            }
            if (line.indexOf(".com") != -1){
                comCount += 1;
            }
            if (line.endsWith(".com") || line.endsWith(".com/")){
                comEndCount += 1;
            }
            while(true){
                pos = line.indexOf(".", start);
                if (pos == -1){
                    break;
                }
                dotCount += 1;
                start = pos + 1;
            }
            start = 0;
            pos = 0;
        }
        System.out.println("URLs : " + urlStore.size());
        System.out.println("http : " + httpC);
        System.out.println("secure : " + secCount);
        System.out.println("com : " + comCount);
        System.out.println("com end : " + comEndCount);
        System.out.println("dots : " + dotCount);
    }
}
