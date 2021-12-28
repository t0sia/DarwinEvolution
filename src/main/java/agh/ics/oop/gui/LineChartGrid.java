package agh.ics.oop.gui;

import agh.ics.oop.Grass;
import agh.ics.oop.GrassField;
import agh.ics.oop.Statistics;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartGrid {

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    private LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
    private XYChart.Series animals = new XYChart.Series();
    private XYChart.Series plants = new XYChart.Series();
    private XYChart.Series avgLifeLen = new XYChart.Series();
    private XYChart.Series avgEnergy = new XYChart.Series();
    private XYChart.Series avgChildren = new XYChart.Series();
    private GrassField map;

    public LineChartGrid(GrassField map){
        animals.setName("Animals");
        plants.setName("Plants");
        avgChildren.setName("Avg Children");
        avgEnergy.setName("Avg Energy");
        avgLifeLen.setName("Avg Life Length");
        this.map = map;
        animals.getData().add(new XYChart.Data(0,0));
        plants.getData().add(new XYChart.Data(0,0));
        avgChildren.getData().add(new XYChart.Data(0,0));
        avgEnergy.getData().add(new XYChart.Data(0,0));
        avgLifeLen.getData().add(new XYChart.Data(0,0));

        lineChart.getData().addAll(animals,plants,avgLifeLen,avgChildren,avgEnergy);
    }


    public void chartUpdate(Statistics statistics, int day){
        animals.getData().add(new XYChart.Data(day, statistics.getAnimals()));
        plants.getData().add(new XYChart.Data(day, statistics.getPlants()));
        avgChildren.getData().add(new XYChart.Data(day, statistics.getAvgChildren()));
        avgEnergy.getData().add(new XYChart.Data(day, statistics.getAvgEnergy()));
        avgLifeLen.getData().add(new XYChart.Data(day, statistics.getAvgLife()));
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
