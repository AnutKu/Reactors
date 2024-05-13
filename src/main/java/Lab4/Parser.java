package Lab4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    HashMap<String, String> countries = new HashMap<>();
    HashMap<String , String> reactorsInCountries = new HashMap<>();
    List<List<String>> dataList = new ArrayList<>();
    WriteLoadFactor loadwriter = new WriteLoadFactor();


    public void start(Map<String, Double> loadFactorMap) throws IOException {
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
        loadwriter.createTable();
        readCountriesFromPage();
        readReactorsFromCountries();
        parseReactors(loadFactorMap);

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
                reactorsInCountries.put(elem.text(), country.getKey());
            }
        }

        System.out.println("ВСЕГО РЕАКТОРОВ" + ": " + allrec);
    }

    public void parseReactors(Map<String, Double> loadFactorMapEntry) throws IOException{
        System.out.println(reactorsInCountries);
        countries.put("GERMANY", "DE");
        countries.put("TAIWAN", "TWN");
        int allpars = 0;
        for (Integer i = 1; i < 1000; i++) {
            try {
                List<String> rowData = new ArrayList<>();
                String url = "https://pris.iaea.org/PRIS/CountryStatistics/ReactorDetails.aspx?current=" + i;
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tr");
                String h5owner = "";
                String h5operator = "";
                if (rows.size() >= 2) {
                    // Получаем второй по счету <tr>
                    Element secondRow = rows.get(1);
                    // Получаем все элементы <td> внутри второго <tr>
                    Elements cells = secondRow.select("td");
                    // Проверяем, что внутри второго <tr> есть как минимум четыре элемента <td>
                    if (cells.size() >= 4) {
                        Element thirdCell = cells.get(2);
                        Element h5Element = thirdCell.selectFirst("h5");
                        h5owner = h5Element.text();
                        Element fourthCell = cells.get(3);
                        Element h5Element2 = fourthCell.selectFirst("h5");
                        h5operator = h5Element2.text();
                    }
                   }
                String status = doc.getElementById("MainContent_MainContent_lblReactorStatus") != null ? doc.getElementById("MainContent_MainContent_lblReactorStatus").text() : null;
                if ((status.equals("Operational")) || (status.equals("Permanent Shutdown")) || (status.equals("Suspended Operation"))){
                    String name = doc.getElementById("MainContent_MainContent_lblReactorName") != null ? doc.getElementById("MainContent_MainContent_lblReactorName").text() : null;
                    String country = name != null ? reactorsInCountries.get(name) : null;
                    String type = doc.getElementById("MainContent_MainContent_lblType") != null ? doc.getElementById("MainContent_MainContent_lblType").text() : null;
                    String owner = doc.getElementById("MainContent_MainContent_hypOwnerUrl") != null ? doc.getElementById("MainContent_MainContent_hypOwnerUrl").text().split(",")[0].trim() : null;
                    if (owner == null){owner = h5owner;}
                    String operator = doc.getElementById("MainContent_MainContent_hypOperatorUrl") != null ? doc.getElementById("MainContent_MainContent_hypOperatorUrl").text().split(",")[0].trim() : null;
                    if (operator == null){operator = h5operator;}
                    String thermalCapacity = doc.getElementById("MainContent_MainContent_lblThermalCapacity") != null ? doc.getElementById("MainContent_MainContent_lblThermalCapacity").text() : null;
                    String firstGridConnection = doc.getElementById("MainContent_MainContent_lblGridConnectionDate") != null ? doc.getElementById("MainContent_MainContent_lblGridConnectionDate").text().split(",")[1].trim() : null;
                    String loadFactor = doc.getElementById("MainContent_MainContent_lblLoadFactor") != null ? doc.getElementById("MainContent_MainContent_lblLoadFactor").text().split("%")[0].trim() : null;
                    String suspenedData = null;
                    Boolean flag = true;
                    if (status.equals("Suspended Operation")) {
                        suspenedData = doc.getElementById("MainContent_MainContent_lblLongTermShutdownDate") != null ? doc.getElementById("MainContent_MainContent_lblLongTermShutdownDate").text().split(",")[1].trim() : null;
                        int susdata = Integer.parseInt(suspenedData);
                        if (susdata < 2014){
                            flag = false;
                        }
                    } else {
                        suspenedData = null;
                    }
                    String permanentData = null;
                    if (status.equals("Permanent Shutdown")) {
                        permanentData = doc.getElementById("MainContent_MainContent_lblPermanentShutdownDate") != null ? doc.getElementById("MainContent_MainContent_lblPermanentShutdownDate").text().split(",")[1].trim() : null;
                        int permdata = Integer.parseInt(permanentData);
                        if (permdata < 2014){
                            flag = false;
                        }
                    } else {
                        permanentData = null;
                    }
                    if (flag) {
                    HashMap<Integer, Double> loadMap = new HashMap<>();
                    for (int j = 2014; j <= 2024; j++) {
                        loadMap.put(j, 0.0);
                    }

                    Elements tables = doc.select("table");
                    if (tables.size() >= 3) {
                        Element table = tables.get(2);
                        Elements rowsload = table.select("tr");
                        List<Integer> years = new ArrayList<>();
                        for (int z = 2014; z <= 2024; z++) {
                            years.add(z);
                        }
                        for (Element row : rowsload) {
                            Elements tds = row.select("td");
                            if (tds.size() >= 2) {
                                Element firstTd = tds.first();
                                Element penultimateTd = tds.get(tds.size() - 2);
                                Integer year = Integer.parseInt(firstTd.text());
                                if (year >= 2014){
                                    String loadtext = penultimateTd.text();
                                    Double load = 0.0;
                                    if (loadtext.length() > 0){ load = Double.parseDouble(penultimateTd.text());}
                                    else{load = 85.0;}

                                    years.remove(Integer.valueOf(year));
                                    if (status.equals("Permanent Shutdown")) {
                                        if (Integer.parseInt(permanentData) >= year){
                                            loadMap.put(year, load);}}
                                    else if(status.equals("Suspended Operation")){
                                            if (Integer.parseInt(suspenedData) >= year){
                                                loadMap.put(year, load);}
                                        }
                                    else{if (year > Integer.parseInt(firstGridConnection)){
                                        loadMap.put(year, load);}
                                        else if (year == Integer.parseInt(firstGridConnection)){Double loadfromfile = loadFactorMapEntry.get(type);
                                        loadMap.put(year, loadfromfile);}}
                                }
                                if ((!years.isEmpty()) && status.equals("Operational")) {
                                    for (Integer yr : years) {
                                        loadMap.put(yr, 85.0);
                                    }
                                }
                            }
                        }
                    }
                    loadwriter.addNewRow(name, loadMap);
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




