import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class YamlFileImporter extends FileImporter {
    @Override
    public void importFile(File file, ReactorHolder reactorMap) throws IOException {
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            try {
                Yaml yaml = new Yaml();
                FileInputStream inputStream = new FileInputStream(file);
                Iterable<Object> objects = yaml.loadAll(inputStream);
                for (Object object : objects) {
                    Map<String, ?> map = (Map<String, ?>) object;
                    String type = null;
                    String reactorClass = null;
                    Double burnup = null;
                    Double electricalCapacity = null;
                    Double enrichment = null;
                    Double firstLoad = null;
                    Double kpd = null;
                    Integer lifeTime = null;
                    Double thermalCapacity = null;
                    for (String key : map.keySet()) {
                        Map<?, ?> innerMap = (Map<?, ?>) map.get(key);
                        type = (String) innerMap.get("type");
                        reactorClass = (String) innerMap.get("class");
                        burnup = ((Number) innerMap.get("burnup")).doubleValue();
                        electricalCapacity = ((Number) innerMap.get("electrical_capacity")).doubleValue();
                        enrichment = ((Number) innerMap.get("enrichment")).doubleValue();
                        firstLoad = ((Number) innerMap.get("first_load")).doubleValue();
                        kpd = ((Number) innerMap.get("kpd")).doubleValue();
                        lifeTime = (Integer) innerMap.get("life_time");
                        thermalCapacity = firstLoad = ((Number) innerMap.get("termal_capacity")).doubleValue();
                        Reactor reactor = new Reactor(type, reactorClass, burnup, electricalCapacity, enrichment, firstLoad, kpd, lifeTime, thermalCapacity, "YAML");
                        reactorMap.addReactor(key, reactor);
                    }
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (next != null) {
            next.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }
}
