package Lab3;

import java.util.HashMap;
import java.util.Map;

public class ReactorTypeHolder {
    private Map<String, ReactorType> reactorMap;

    public ReactorTypeHolder() {
        this.reactorMap = new HashMap<>();
    }
    public void addReactor(String key, ReactorType reactorType) {
        reactorMap.put(key, reactorType);
    }
    public Map<String, ReactorType> getReactorMap() {return reactorMap;}

    public Map<String, Double> getLoadFactorMap() {
        Map<String, Double> loadFactorMap = new HashMap<>();
        loadFactorMap.put("LWGR", 15.0);
        loadFactorMap.put("GCR", 15.0);
        loadFactorMap.put("FBR", 30.0);
        loadFactorMap.put("HTGR", 10.0);
        for (Map.Entry<String, ReactorType> entry : reactorMap.entrySet()) {
            String reactorClass = entry.getKey();
            ReactorType reactorType = entry.getValue();
            double loadFactor = reactorType.getFirstLoad();
            loadFactorMap.put(reactorClass, loadFactor);
        }
        return loadFactorMap;
    }
    }
