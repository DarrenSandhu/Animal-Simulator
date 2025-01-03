package com.example.simulator;

public class SimulationStatus {
    private int step;
    private Weather weather;
    private Day day;
    private int diseaseCount;
    private Field field;
    private FieldStats fieldStats;
    private Simulator simulator;

    public SimulationStatus(Simulator simulator, int step, Weather weather, Day day, int diseaseCount, FieldStats fieldStats, Field field) {
        this.simulator = simulator;
        this.step = step;
        this.weather = weather;
        this.day = day;
        this.diseaseCount = diseaseCount;
        this.fieldStats = fieldStats;
        this.field = field;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getWeather() {
        return weather.getWeather();
    }

    public String getTimeOfDay() {
        return day.getTimeOfDay();
    }

    public int getDiseaseCount() {
        return diseaseCount;
    }

    public String getPopulationCount() {
        return fieldStats.getPopulationDetails(field);
    }

    public String getSimulationStatus() {
        return "Step: " + step + ", Weather: " + getWeather() + ", Time of Day: " + getTimeOfDay() + ", Disease Count: " + diseaseCount + ", "  + getPopulationCount();
    }
}
