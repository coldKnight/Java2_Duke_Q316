/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 *
 * @author Duke Software Team
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
    public void tester(){
        FileResource fr = new FileResource();

        CSVParser parser1 = fr.getCSVParser();
        countryInfo(parser1, "Nauru");

        CSVParser parser2 = fr.getCSVParser();
        listExportersTwoProducts(parser2, "cotton", "flowers");

        CSVParser parser3 = fr.getCSVParser();
        System.out.println(numberOfExporters(parser3, "cocoa"));

        CSVParser parser4 = fr.getCSVParser();
        bigExporters(parser4, "$999,999,999,999");
    }

    public String countryInfo(CSVParser parser, String country){
        String countryLower = country.toLowerCase();
        String info = "NOT FOUND";

        for (CSVRecord record : parser){
            String csvCountry = record.get("Country");
            String csvCountryLower = csvCountry.toLowerCase();
            if (csvCountryLower.equals(countryLower)){
                info = csvCountry + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
                System.out.println(info);
                return info;
            }
        }
        System.out.println(info);
        return info;
   }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for(CSVRecord record : parser){
            String export = record.get("Exports");
            if(export.contains(exportItem1) && export.contains(exportItem2)){
                System.out.println(record.get("Country"));
            }
        }
    }

    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        for(CSVRecord record : parser){
            String export = record.get("Exports");
            if(export.contains(exportItem.toLowerCase())){
                count += 1;
            }
        }
        return count;
    }

    public void bigExporters(CSVParser parser, String amount){
        int alen = amount.length();
        for(CSVRecord record : parser){
            String dval = record.get("Value (dollars)");
            int dlen = dval.length();
            if (dlen > alen){
                System.out.println(record.get("Country") + " " + dval);
            }
        }
    }

    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            //Look at the "Exports" column
            String export = record.get("Exports");
            //Check if it contains exportOfInterest
            if (export.contains(exportOfInterest)) {
                //If so, write down the "Country" from that row
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public void whoExportsCoffee() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");
    }
}
