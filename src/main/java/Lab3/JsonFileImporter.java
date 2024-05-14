package Lab3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class JsonFileImporter extends FileImporter {
    @Override
    public void importFile(File file, ReactorTypeHolder reactorMap) throws IOException {
        if (file.getName().endsWith(".json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(file);

            for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
                String fieldName = it.next();
                JsonNode reactorNode = rootNode.get(fieldName);
                if (reactorNode != null && reactorNode.isObject()) {
                    ReactorType reactorType = parseReactor(reactorNode);
                    reactorMap.addReactor(fieldName, reactorType);
                }
            }
        } else if (next != null) {
            next.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private ReactorType parseReactor(JsonNode reactorNode) {
        // reactorNode имеет вид {"class":"MKER","burnup":30,"kpd":0.352,"enrichment":0.024,"termal_capacity":4250,"electrical_capacity":1500,"life_time":50,"first_load":192};
        String type = reactorNode.has("type") ? reactorNode.get("type").asText() : null;
        String reactorClass = reactorNode.has("class") ? reactorNode.get("class").asText() : null;
        double burnup = reactorNode.has("burnup") ? reactorNode.get("burnup").asDouble() : 0.0;
        double electricalCapacity = reactorNode.has("electrical_capacity") ? reactorNode.get("electrical_capacity").asDouble() : 0.0;
        double enrichment = reactorNode.has("enrichment") ? reactorNode.get("enrichment").asDouble() : 0.0;
        double firstLoad = reactorNode.has("first_load") ? reactorNode.get("first_load").asDouble() : 0.0;
        double kpd = reactorNode.has("kpd") ? reactorNode.get("kpd").asDouble() : 0.0;
        int lifeTime = reactorNode.has("life_time") ? reactorNode.get("life_time").asInt() : 0;
        double thermalCapacity = reactorNode.has("termal_capacity") ? reactorNode.get("termal_capacity").asDouble() : 0.0;

        return new ReactorType(type, reactorClass, burnup, electricalCapacity, enrichment, firstLoad, kpd, lifeTime, thermalCapacity, "JSON");
    }


}
