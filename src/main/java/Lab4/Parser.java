package Lab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Parser {
    HashMap<String, String> countries = new HashMap<>();
    HashMap<String , String> reactorsInCountries = new HashMap<>();
    List<List<String>> dataList = new ArrayList<>();

    public void start() throws IOException {
        reactorsInCountries.put("GRAFENRHEINFELD", "GERMANY");
        reactorsInCountries.put("PHILIPPSBURG-2", "GERMANY");
        reactorsInCountries.put("GUNDREMMINGEN-B", "GERMANY");
        reactorsInCountries.put("GROHNDE", "GERMANY");
        reactorsInCountries.put("GUNDREMMINGEN-C", "GERMANY");
        reactorsInCountries.put("ISAR-2", "GERMANY");
        reactorsInCountries.put("BROKDORF", "GERMANY");
        reactorsInCountries.put("EMSLAND", "GERMANY");
        reactorsInCountries.put("NECKARWESTHEIM-2", "GERMANY");
        reactorsInCountries.put("CHINSHAN-1", "TAIWAN");
        reactorsInCountries.put("CHINSHAN-2", "TAIWAN");
        reactorsInCountries.put("KUOSHENG-1", "TAIWAN");
        reactorsInCountries.put("KUOSHENG-2", "TAIWAN");
        reactorsInCountries.put("MAANSHAN-1", "TAIWAN");
        reactorsInCountries.put("MAANSHAN-2", "TAIWAN");
        readCountriesFromPage();
        readReactorsFromCountries();
        parseReactors();

    }

    public void readCountriesFromPage() throws IOException {
        Document doc = Jsoup
                .connect("https://pris.iaea.org/PRIS/WorldStatistics/OperationalReactorsByCountry.aspx")
                .get();
        String id = "";
        String country;
        int counter = 0;
        while(true){
            id = "MainContent_MainContent_ucReport_rptReport_hypCountry_"
                    + String.valueOf(counter);
            if (doc.getElementById(id) != null){
                Element element = doc.getElementById(id);
                country = element.text();
                String[] href = element.attr("href").split("");
                countries.put(country, href[href.length-2]+href[href.length-1]);
                // System.out.println(country + " " + href[href.length-2]+href[href.length-1]);
            }
            else {
                break;
            }
            counter += 1;
        }
    }

    public void readReactorsFromCountries() throws IOException {
        int allrec = 0;
        for(Map.Entry<String, String> country: countries.entrySet()) {
            String url =
                    "https://pris.iaea.org/PRIS/CountryStatistics/CountryDetails.aspx?current=" +
                            country.getValue();
            Document doc = Jsoup.connect(url).get();
            int counter = 0;
            while (true) {
                if (doc.getElementById("MainContent_MainContent_rptCountryReactors_hypReactorName_"
                        + counter) == null) break;
                Element element = doc
                        .getElementById("MainContent_MainContent_rptCountryReactors_hypReactorName_"
                                + counter);
                Element elem = element.parent();
                // System.out.println(country.getKey());
                counter += 1;
                allrec += 1;
                // System.out.println(elem.text());
                // System.out.println(elem.text() + " " + country.getValue());
                reactorsInCountries.put(elem.text(), country.getKey());
            }
        }

        System.out.println("ВСЕГО РЕАКТОРОВ" + ": " + allrec);
    }

    public void parseReactors() throws IOException{
        System.out.println(reactorsInCountries);
        countries.put("GERMANY", "DE");
        countries.put("TAIWAN", "TWN");
        int allpars = 0;
        for (Integer i = 1; i < 1000; i++) {
            try {
                List<String> rowData = new ArrayList<>();
                String url = "https://pris.iaea.org/PRIS/CountryStatistics/ReactorDetails.aspx?current=" + i;
                Document doc = Jsoup.connect(url).get();
                String status = doc.getElementById("MainContent_MainContent_lblReactorStatus") != null ? doc.getElementById("MainContent_MainContent_lblReactorStatus").text() : null;
                if ((status.equals("Operational")) || (status.equals("Permanent Shutdown")) || (status.equals("Suspended Operation"))){
                    String name = doc.getElementById("MainContent_MainContent_lblReactorName") != null ? doc.getElementById("MainContent_MainContent_lblReactorName").text() : null;
                    String country = name != null ? reactorsInCountries.get(name) : null;
                    String type = doc.getElementById("MainContent_MainContent_lblType") != null ? doc.getElementById("MainContent_MainContent_lblType").text() : null;
                    String owner = doc.getElementById("MainContent_MainContent_hypOwnerUrl") != null ? doc.getElementById("MainContent_MainContent_hypOwnerUrl").text() : null;
                    String operator = doc.getElementById("MainContent_MainContent_hypOperatorUrl") != null ? doc.getElementById("MainContent_MainContent_hypOperatorUrl").text() : null;
                    String thermalCapacity = doc.getElementById("MainContent_MainContent_lblThermalCapacity") != null ? doc.getElementById("MainContent_MainContent_lblThermalCapacity").text() : null;
                    String firstGridConnection = doc.getElementById("MainContent_MainContent_lblGridConnectionDate") != null ? doc.getElementById("MainContent_MainContent_lblGridConnectionDate").text().split(",")[1].trim() : null;
                    String loadFactor = doc.getElementById("MainContent_MainContent_lblLoadFactor") != null ? doc.getElementById("MainContent_MainContent_lblLoadFactor").text().split("%")[0].trim() : null;
                    String suspenedData = null;
                    Boolean flag = true;
                    if (status.equals("Suspended Operation")) {
                        suspenedData = doc.getElementById("MainContent_MainContent_lblLongTermShutdownDate") != null ? doc.getElementById("MainContent_MainContent_lblLongTermShutdownDate").text().split(",")[1].trim() : null;
                        int susdata = Integer.parseInt(suspenedData);
                        if (susdata <= 2014){
                            flag = false;
                        }
                    } else {
                        suspenedData = null;
                    }
                    String permanentData = null;
                    if (status.equals("Permanent Shutdown")) {
                        permanentData = doc.getElementById("MainContent_MainContent_lblPermanentShutdownDate") != null ? doc.getElementById("MainContent_MainContent_lblPermanentShutdownDate").text().split(",")[1].trim() : null;
                        int permdata = Integer.parseInt(permanentData);
                        if (permdata <= 2014){
                            flag = false;
                        }
                    } else {
                        permanentData = null;
                    }
                    rowData.add(name);
                    rowData.add(country);
                    rowData.add(status);
                    rowData.add(type);
                    rowData.add(owner);
                    rowData.add(operator);
                    rowData.add(thermalCapacity);
                    rowData.add(firstGridConnection);
                    rowData.add(loadFactor);
                    rowData.add(suspenedData);
                    rowData.add(permanentData);
                    if (flag){
                        dataList.add(rowData);
                    }
                    //System.out.println(rowData);
                    allpars += 1;}
            } catch (IOException e) {
            }
        }
        System.out.println(dataList.size());

    }
    public List<List<String>> getDataList(){
        return  dataList;
    }
}




