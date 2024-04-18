import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonFileImporter extends FileImporter{
    @Override
    public void importFile(File file, Map<String, Reactor> reactorMap) throws IOException {
        if (file.getName().endsWith(".json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(file);

            // Итерируемся по всем полям JSON-объекта
            for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                JsonNode reactorNode = rootNode.get(fieldName);
                if (reactorNode != null && reactorNode.isObject()) {
                    Reactor reactor = parseReactor(reactorNode);
                    reactorMap.put(fieldName, reactor);
                }
            }
        } else if (successor != null) {
            successor.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private Reactor parseReactor(JsonNode reactorNode) {
        String type = reactorNode.get("type") != null ? reactorNode.get("type").asText() : null;
        String reactorClass = reactorNode.get("class") != null ? reactorNode.get("class").asText() : null;
        double burnup = reactorNode.get("burnup") != null ? reactorNode.get("burnup").asDouble() : 0.0;
        double electricalCapacity = reactorNode.get("electrical_capacity") != null ? reactorNode.get("electrical_capacity").asDouble() : 0.0;
        double enrichment = reactorNode.get("enrichment") != null ? reactorNode.get("enrichment").asDouble() : 0.0;
        double firstLoad = reactorNode.get("first_load") != null ? reactorNode.get("first_load").asDouble() : 0.0;
        double kpd = reactorNode.get("kpd") != null ? reactorNode.get("kpd").asDouble() : 0.0;
        int lifeTime = reactorNode.get("life_time") != null ? reactorNode.get("life_time").asInt() : 0;
        double thermalCapacity = reactorNode.get("termal_capacity") != null ? reactorNode.get("termal_capacity").asDouble() : 0.0;

        return new Reactor(type, reactorClass, burnup, electricalCapacity, enrichment, firstLoad, kpd, lifeTime, thermalCapacity, "JSON");
    }

}
