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
    public Map<String, Reactor> getReactorMap() {
        return reactorMap;
    }
}
