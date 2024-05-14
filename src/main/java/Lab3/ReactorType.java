package Lab3;

public class ReactorType {
    private String type;
    private String reactorClass;
    private Double burnup;
    private Double electricalCapacity;
    private Double enrichment;
    private Double firstLoad;
    private Double kpd;
    private Integer lifeTime;
    private Double thermalCapacity;
    private String source;

    public ReactorType(String type, String reactorClass, Double burnup, Double electricalCapacity,
                       Double enrichment, Double firstLoad, Double kpd, Integer lifeTime,
                       Double thermalCapacity, String source) {
        this.type = type;
        this.reactorClass = reactorClass;
        this.burnup = burnup;
        this.electricalCapacity = electricalCapacity;
        this.enrichment = enrichment;
        this.firstLoad = firstLoad;
        this.kpd = kpd;
        this.lifeTime = lifeTime;
        this.thermalCapacity = thermalCapacity;
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public String getReactorClass() {
        return reactorClass;
    }

    public Double getBurnup() {
        return burnup;
    }

    public Double getElectricalCapacity() {
        return electricalCapacity;
    }

    public Double getEnrichment() {
        return enrichment;
    }

    public Double getFirstLoad() {
        return firstLoad;
    }

    public Double getKpd() {
        return kpd;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public Double getThermalCapacity() {
        return thermalCapacity;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return  "   Класс реактора " + reactorClass  + "\n" +
                "   Выгорание " + burnup + "\n" +
                "   Электрическая мощность " + electricalCapacity + "\n" +
                "   Обогащение " + enrichment + "\n" +
                "   Первая загрузка " + firstLoad + "\n" +
                "   КПД " + kpd + "\n" +
                "   Продолжительность жизни " + lifeTime + "\n" +
                "   Теплоемкость " + thermalCapacity + "\n" +
                "   Ресурс: " + source + "Тип" + type;
    }
}
