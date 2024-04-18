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
