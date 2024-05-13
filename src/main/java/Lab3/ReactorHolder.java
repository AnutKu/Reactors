package Lab3;

import Lab3.Reactor;

import java.util.HashMap;
import java.util.Map;

public class ReactorHolder {
    private Map<String, Reactor> reactorMap;

    public ReactorHolder() {
        this.reactorMap = new HashMap<>();
    }
    public void addReactor(String key, Reactor reactor) {
        reactorMap.put(key, reactor);
    }
    public Map<String, Reactor> getReactorMap() {return reactorMap;}

    public Map<String, Double> getLoadFactorMap() {
        Map<String, Double> loadFactorMap = new HashMap<>();
        loadFactorMap.put("LWGR", 15.0);
        loadFactorMap.put("GCR", 15.0);
        loadFactorMap.put("FBR", 30.0);
        loadFactorMap.put("HTGR", 10.0);
        for (Map.Entry<String, Reactor> entry : reactorMap.entrySet()) {
            String reactorClass = entry.getKey();
            Reactor reactor = entry.getValue();
            double loadFactor = reactor.getFirstLoad();
            loadFactorMap.put(reactorClass, loadFactor);
        }
        return loadFactorMap;
    }
    }
