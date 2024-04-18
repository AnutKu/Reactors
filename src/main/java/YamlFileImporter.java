import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class YamlFileImporter extends FileImporter {
    @Override
    public void importFile(File file, Map<String, Reactor> reactorMap) throws IOException {
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            try {
                Yaml yaml = new Yaml();
                FileInputStream inputStream = new FileInputStream(file);
                Iterable<Object> objects = yaml.loadAll(inputStream);
                for (Object object : objects) {
                        Map<String, ?> map = (Map<String, ?>) object;
                        for (String key : map.keySet()){
                        Map<?, ?> innerMap = (Map<?, ?>) map.get(key);
                        Reactor reactor = parseReactor(innerMap);
                        reactorMap.put(key, reactor);}

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (successor != null) {
            successor.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private Reactor parseReactor(Map<?, ?> map) {
        String type = getStringValue(map, "type");
        String reactorClass = getStringValue(map, "class");
        Double burnup = getDoubleValue(map, "burnup");
        Double electricalCapacity = getDoubleValue(map, "electrical_capacity");
        Double enrichment = getDoubleValue(map, "enrichment");
        Double firstLoad = getDoubleValue(map, "first_load");
        Double kpd = getDoubleValue(map, "kpd");
        Integer lifeTime = getIntValue(map, "life_time");
        Double thermalCapacity = getDoubleValue(map, "termal_capacity");

        return new Reactor(type, reactorClass, burnup, electricalCapacity, enrichment, firstLoad, kpd, lifeTime, thermalCapacity, "YAML");
    }

    private String getStringValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private Double getDoubleValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : null;
    }

    private Integer getIntValue(Map<?, ?> map, String key) {
        Object value = map.get(key);
        return value instanceof Number ? ((Number) value).intValue() : null;
    }
}
