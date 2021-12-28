package agh.ics.oop.gui;

import agh.ics.oop.GrassField;
import agh.ics.oop.Statistics;

public class StringToFile {

    static public StringBuilder BuildStringToFile(Statistics statistics, boolean ifBounds){

        StringBuilder sb = new StringBuilder();
        if(ifBounds) sb.append("RIGHT ");
        else sb.append("LEFT ");
        sb.append(",");
        sb.append("Animals: ");
        sb.append(statistics.getAnimals());
        sb.append(",");
        sb.append(" Plants: ");
        sb.append(statistics.getPlants());
        sb.append(",");
        sb.append(" Average Energy: ");
        sb.append(statistics.getAvgEnergy());
        sb.append(",");
        sb.append(" Average Life Length: ");
        sb.append(statistics.getAvgLife());
        sb.append(",");
        sb.append(" Average Children Number: ");
        sb.append(statistics.getAvgChildren());
        sb.append("\n");

        return sb;
    }
}
