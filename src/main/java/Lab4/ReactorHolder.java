package Lab4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReactorHolder {
    private ArrayList<Reactor> reactorList;
    public ReactorHolder(){this.reactorList = new ArrayList<>();
    }
    public void addReactor(Reactor reactor){
        reactorList.add(reactor);
    }
    public ArrayList<Reactor> getReactorList() {return reactorList;}

    public void calculateConsumptionPerYear(){
        for (Reactor reactor: reactorList){
            reactor.calculateConsumptionPerYear();
        }
    }

    public HashMap<String, HashMap<Integer, Double>> agregatePerCountry(){
        HashMap<String, HashMap<Integer, Double>> consumptionPerCountry = new HashMap<>();
        for (Reactor reactor : reactorList) {
            String country = reactor.getCountry();
            HashMap<Integer, Double> consumption = reactor.getConsumptionPerYear();
            HashMap<Integer, Double> countryConsumption = consumptionPerCountry.getOrDefault(country, new HashMap<>());
            for (HashMap.Entry<Integer, Double> entry : consumption.entrySet()) {
                Integer year = entry.getKey();
                Double cons = entry.getValue();
                double updatedConsumption = countryConsumption.getOrDefault(year, 0.0) + cons;
                updatedConsumption = Math.round(updatedConsumption * 100.0) / 100.0;
                countryConsumption.put(year, updatedConsumption);
            }
            consumptionPerCountry.put(country, countryConsumption);
        }
       return consumptionPerCountry;
    }

    public HashMap<String, HashMap<Integer, Double>> agregatePerOperator(){
        HashMap<String, HashMap<Integer, Double>> consumptionPerOperator = new HashMap<>();
        for (Reactor reactor : reactorList) {
            String country = reactor.getOperator();
            HashMap<Integer, Double> consumption = reactor.getConsumptionPerYear();
            HashMap<Integer, Double> countryConsumption = consumptionPerOperator.getOrDefault(country, new HashMap<>());
            for (HashMap.Entry<Integer, Double> entry : consumption.entrySet()) {
                Integer year = entry.getKey();
                Double cons = entry.getValue();
                double updatedConsumption = countryConsumption.getOrDefault(year, 0.0) + cons;
                updatedConsumption = Math.round(updatedConsumption * 100.0) / 100.0;
                countryConsumption.put(year, updatedConsumption);
            }
            consumptionPerOperator.put(country, countryConsumption);
        }
        return consumptionPerOperator;
    }

    public HashMap<String, HashMap<Integer, Double>> agregatePerRegion(){
        HashMap<String, HashMap<Integer, Double>> consumptionPerRegion = new HashMap<>();
        for (Reactor reactor : reactorList) {
            String country = reactor.getRegion();
            HashMap<Integer, Double> consumption = reactor.getConsumptionPerYear();
            HashMap<Integer, Double> countryConsumption = consumptionPerRegion.getOrDefault(country, new HashMap<>());
            for (HashMap.Entry<Integer, Double> entry : consumption.entrySet()) {
                Integer year = entry.getKey();
                Double cons = entry.getValue();
                double updatedConsumption = countryConsumption.getOrDefault(year, 0.0) + cons;
                updatedConsumption = Math.round(updatedConsumption * 100.0) / 100.0;
                countryConsumption.put(year, updatedConsumption);
            }
            consumptionPerRegion.put(country, countryConsumption);
        }
        return consumptionPerRegion;
    }

}
