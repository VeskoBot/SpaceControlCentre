public class LaunchDay {
    private final int day;
    private final double temperature;
    private final double wind;
    private final double humidity;
    private final double precipitation;
    private final  String lightning;
    private final String clouds;

    public LaunchDay(int day, double temperature, double wind, double humidity, double precipitation, String lightning, String clouds) {
        this.day = day;
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.lightning = lightning;
        this.clouds = clouds;
    }

    public int getDay() {
        return day;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWind() {
        return wind;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public String getLightning() {
        return lightning;
    }

    public String getClouds() {
        return clouds;
    }
}
