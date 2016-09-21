
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

public class BabyBirths {
    
    private int getTotalBirthsRankedHigher(int year, String name, String gender){
        //total number of births of those names with the same gender and same year who 
        //are ranked higher than name
        //get FileName 
        String fileName = "data/yob"+year+".csv";
        FileResource excelFile = new FileResource(fileName);
        int rankOfName = getRank(year, name, gender);
        int tempRank = 0;
        int totalBirths = 0;
        CSVParser parser = excelFile.getCSVParser(false);
        for(CSVRecord currentRow: parser){
            if(currentRow.get(1).equals(gender)){
                tempRank+=1;
                if(tempRank < rankOfName){
                    totalBirths += Integer.parseInt(currentRow.get(2));
                }
            }   
        }
        
        return totalBirths;
    }
    
    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(1990,"Emily", "F"));
        System.out.println(getTotalBirthsRankedHigher(1990,"Drew", "M"));
    }
    
    public double getAverageRank(String name, String gender){
        double sumOfRank = 0.0;
        double numberOfYears = 0.0;
        double averageRank = -1.0;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            numberOfYears += 1.0;
            FileResource fr = new FileResource(f);
            String currentYearS = f.getName().substring(3,7);
            int currentYear = Integer.parseInt(currentYearS);
            int rank = getRank(currentYear, name, gender);
            if (rank != -1){
                sumOfRank += (double)rank;
            }            
        }
        if(sumOfRank != 0.0){
            averageRank = sumOfRank/numberOfYears;
        }
        return averageRank;
    }
    
    public void testGetAverageRank(){
        System.out.println(getAverageRank("Susan", "F"));
        System.out.println(getAverageRank("Robert", "M"));
    }
    
    public int yearOfHighestRank(String name, String gender){
        int currentMaxRankYear = -1;
        int currentMaxRank = -1;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser(false);
            String currentYearS = f.getName().substring(3,7);
            int currentYear = Integer.parseInt(currentYearS);
            int tempRank = 0;
            int rank = -1;
            for (CSVRecord record: parser){
                if(record.get(1).equals(gender)){
                    tempRank += 1;
                    if(record.get(0).equals(name)){
                        rank = tempRank;
                        break;
                    }
                }
            }
            if (currentMaxRank == -1 && rank != -1){
                currentMaxRank = rank;
                currentMaxRankYear = currentYear;
            } else if(rank<currentMaxRank && rank != -1){
                    currentMaxRank = rank;
                    currentMaxRankYear = currentYear;
            }    
        }
        return currentMaxRankYear;
    }
    
    public void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Genevieve", "F"));
        System.out.println("---------------");
        System.out.println(yearOfHighestRank("Mich", "M"));
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int oldRank = getRank(year, name, gender);
        String newName = getName(newYear, oldRank, gender);
        System.out.println(name + " born in " + year + " would be " + newName + " if born in " + newYear);
    }
    
    public void testWhatIsNameInYear(){
        whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public String getName(int year, int rank, String gender){
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        CSVParser parser = fr.getCSVParser(false);
        int count = 1;
        String naam = "NOT FOUND";
        for(CSVRecord record : parser){
            String sex = record.get(1);
            if (sex.equals(gender)){
                if (count == rank){
                    naam = record.get(0);
                    return naam;
                }
                else{
                    count += 1;
                }
            }
        }
        return naam;
    }
    
    public void testGetName(){
        String result1 = getName(1980, 350, "F");
        System.out.println(result1);
        String result2 = getName(1982, 450, "M");
        System.out.println(result2);
    }
    
    public int getRank(int year, String name, String gender){
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        CSVParser parser = fr.getCSVParser(false);
        boolean notFound = true;
        int rank = 0;
        for(CSVRecord record : parser){
            String naam = record.get(0);
            String sex = record.get(1);
            if (sex.equals(gender)){
                if (naam.equals(name)){
                    rank += 1;
                    return rank;
                }
                else{
                    rank += 1;
                }
            }
        }
        if (notFound){
            rank = -1;
        }
        return rank;
    }
    
    public void testGetRank(){
        //int result = getRank(2012,"Mason","M");
        int result = getRank(1960, "Emily", "F");
        System.out.println(result);
        int result2 = getRank(1971, "Frank", "M");
        System.out.println(result2);
    }
    
    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int uniqueGirls = 0;
        int uniqueBoys = 0;
        for(CSVRecord record : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(record.get(2));
            totalBirths += numBorn;
            
            if(record.get(1).equals("M")){
                totalBoys += numBorn;
                if(!checkForName(record.get(0), "F", fr.getCSVParser(false))){
                    uniqueBoys += 1;
                }
            }
            else{
                totalGirls += numBorn;
                if(!checkForName(record.get(0), "M", fr.getCSVParser(false))){
                    uniqueGirls += 1;
                }
            }
        }
        System.out.println("Total Births: " + totalBirths);
        System.out.println("Total Girls: " + totalGirls);
        System.out.println("Total Boys: " + totalBoys);
        System.out.println("Total Unique Girls: " + uniqueGirls);
        System.out.println("Total Unique Boys: " + uniqueBoys);
    }
    
     public void testTotalBirths () {
        FileResource fr = new FileResource();
        //FileResource fr = new FileResource("data/yob1900.csv");
        totalBirths(fr);
        //System.out.println("======================");
        //FileResource fr1 = new FileResource("data/yob1905.csv");
        //totalBirths(fr1);
    }
    
    public boolean checkForName(String name, String gender, CSVParser parser){
        boolean result = false;
        for (CSVRecord rec: parser){
            
            if(rec.get(1).equals(gender)&&rec.get(0).equals(name)){
                result = true;
            }
        }
        return result;
    }

}
