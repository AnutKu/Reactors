public class Reactor {
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

    public Reactor(String type, String reactorClass, Double burnup, Double electricalCapacity,
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

    // Геттеры и сеттеры для всех полей

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReactorClass() {
        return reactorClass;
    }

    public void setReactorClass(String reactorClass) {
        this.reactorClass = reactorClass;
    }

    public Double getBurnup() {
        return burnup;
    }

    public void setBurnup(Double burnup) {
        this.burnup = burnup;
    }

    public Double getElectricalCapacity() {
        return electricalCapacity;
    }

    public void setElectricalCapacity(Double electricalCapacity) {
        this.electricalCapacity = electricalCapacity;
    }

    public Double getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(Double enrichment) {
        this.enrichment = enrichment;
    }

    public Double getFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(Double firstLoad) {
        this.firstLoad = firstLoad;
    }

    public Double getKpd() {
        return kpd;
    }

    public void setKpd(Double kpd) {
        this.kpd = kpd;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Integer lifeTime) {
        this.lifeTime = lifeTime;
    }

    public Double getThermalCapacity() {
        return thermalCapacity;
    }

    public void setThermalCapacity(Double thermalCapacity) {
        this.thermalCapacity = thermalCapacity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
                "   Теплоемкость " + thermalCapacity
                ;
    }
}
