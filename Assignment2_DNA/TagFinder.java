
/**
 * Write a description of TagFinder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class TagFinder {
    
    public int findStopIndex(String dna, int index){
        int stop1 = 0;
        int stop2 = 0;
        int stop3 = 0;
        
        stop1 = dna.indexOf("tag", index+3);
        if ((stop1 == -1) || ((stop1-index)%3 != 0)){
            stop1 = dna.length();
        }
        
        stop2 = dna.indexOf("tga", index+3);
        if ((stop2 == -1) || (stop2-index)%3 != 0){
            stop2 = dna.length();
        }
        
        stop3 = dna.indexOf("taa", index+3);
        if ((stop3 == -1) || (stop3-index)%3 != 0){
            stop3 = dna.length();
        }
      
        return Math.min(stop1, Math.min(stop2, stop3));
    }
    
    public void printAll(String DNA){
        String dna = DNA.toLowerCase();
        int start = 0;
        int stop = 0;
        int pos = 0;
        int len = dna.length();
        while(true){
            pos = dna.indexOf("atg", start);
            
            if (pos == -1){
                break;
            }
            
            stop = findStopIndex(dna, pos+3);
            
            if (stop != len){
                String gene = DNA.substring(pos, stop+3);
                System.out.println(gene);
                start = stop+3;
            }
            else{
                start = start + 3;
            }
            
        }
    }
    
    public StorageResource storeAll(String DNA){
        StorageResource store = new StorageResource();
        String dna = DNA.toLowerCase();
        int start = 0;
        int stop = 0;
        int pos = 0;
        int len = dna.length();
        while(true){
            pos = dna.indexOf("atg", start);
            
            if (pos == -1){
                break;
            }
            
            stop = findStopIndex(dna, pos+3);
            
            if (stop != len){
                String gene = DNA.substring(pos, stop+3);
                store.add(gene);
                start = stop+3;
            }
            else{
                start = start + 3;
            }
        }
        return store;
    }
    
    public void testStorageFinder(){
        FileResource file = new FileResource();
        String dna = file.asString();
        StorageResource genes = storeAll(dna);
        System.out.println(genes.size());
        printGenes(genes);
        ctgCount(dna);
        longestGene(genes);
    }
    
    public float cgRatio(String DNA){
        String dna = DNA.toLowerCase();
        int len = dna.length();
        int countcg = 0;
        for(int i=0; i<len; i++){
            if(dna.charAt(i) == 'c' || dna.charAt(i) == 'g'){
                    countcg += 1;
            }
        }
        float ratio = ((float)countcg)/len;
        return ratio;
    }
    
    public void printGenes(StorageResource sr){
        int sc = 0;
        int cgs = 0;
        for (String gene : sr.data()){
            if (gene.length() > 60){
                //System.out.println("g60 : " + gene);
                sc = sc + 1;
            }
            if (cgRatio(gene) > 0.35){
                //System.out.println("g35 : " + gene);
                cgs = cgs + 1;
            }
        }        
        System.out.println("sc : "+sc);
        System.out.println("cgs : "+cgs);
    }
    
    public void ctgCount(String DNA){
        String dna = DNA.toLowerCase();
        int start = 0;
        int pos = 0;
        int count = 0;
        while (true){
            pos = dna.indexOf("ctg", start);
            if (pos == -1){
                break;
            }
            count += 1;
            start = pos + 1;
        }
        System.out.println(count);
    }
    
    public void longestGene(StorageResource sr){
        int max = 0;
        for (String gene : sr.data()){
            if (gene.length() > max){
                max = gene.length();
            }
        }
        System.out.println(max);
    }
    
    public void testFinder(String dna){
        System.out.println("DNA String is:");
        System.out.println(dna);
        System.out.println("Gene found is:");
        printAll(dna);
    }
    
    public String findProtein(String DNA) {
        String dna = DNA.toLowerCase();
        int start = dna.indexOf("atg");
        if (start == -1) {
            return "";
        }
        int stop = dna.indexOf("tag", start+3);
        int stop2 = dna.indexOf("tga", start+3);
        int stop3 = dna.indexOf("taa", start+3);
        if ((stop - start) % 3 == 0) {
            return DNA.substring(start, stop+3);
        }
        else if ((stop2 - start) % 3 == 0){
            return DNA.substring(start, stop2 + 3);   
        }
        else if ((stop3 - start) % 3 == 0){
            return DNA.substring(start, stop3 + 3);
        }
        else {
            return "";
        }
    }
       
    public void testing() {
        String a = "AAATGCCCTAACTAGATTGAAACC";
        String ap = "";
        String apLow = ap.toLowerCase();
        //String a = "atgcctag";
        //String ap = "";
        //String a = "ATGCCCTAG";
        //String ap = "ATGCCCTAG";
        String result = findProtein(a);
        if (apLow.equals(result)) {
            System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }

    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();
            System.out.println("read " + s.length() + " characters");
            String result = findProtein(s);
            System.out.println("found " + result);
        }
    }
}
