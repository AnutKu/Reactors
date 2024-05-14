package Lab3;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class YamlFileImporter extends FileImporter {
    @Override
    public void importFile(File file, ReactorTypeHolder reactorMap) throws IOException {
        if (file.getName().endsWith(".yaml") || file.getName().endsWith(".yml")) {
            try {
                Yaml yaml = new Yaml();
                FileInputStream inputStream = new FileInputStream(file);
                Iterable<Object> objects = yaml.loadAll(inputStream);
                for (Object object : objects) {
                    Map<String, ?> map = (Map<String, ?>) object;
                    for (String key : map.keySet()) {
                        Map<?, ?> innerMap = (Map<?, ?>) map.get(key);
                        ReactorType reactorType = parseDict(innerMap);
                        reactorMap.addReactor(key, reactorType);
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

    private ReactorType parseDict(Map<?, ?> innerMap){
        String type = (String) innerMap.get("type");
        String reactorClass = (String) innerMap.get("class");
        Double burnup = ((Number) innerMap.get("burnup")).doubleValue();
        Double electricalCapacity = ((Number) innerMap.get("electrical_capacity")).doubleValue();
        Double enrichment = ((Number) innerMap.get("enrichment")).doubleValue();
        Double firstLoad = ((Number) innerMap.get("first_load")).doubleValue();
        Double kpd = ((Number) innerMap.get("kpd")).doubleValue();
        Integer lifeTime = (Integer) innerMap.get("life_time");
        Double thermalCapacity = firstLoad = ((Number) innerMap.get("termal_capacity")).doubleValue();
        ReactorType reactorType = new ReactorType(type, reactorClass, burnup, electricalCapacity, enrichment, firstLoad, kpd, lifeTime, thermalCapacity, "YAML");
        return reactorType;
    }


}
