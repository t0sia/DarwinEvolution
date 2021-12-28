package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.GrassField;
import agh.ics.oop.Vector2d;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;

public class addGrid {
    static Image imagePlant = new Image("/plant.png");
    static Image imageAnimal = new Image("/animal.png");

    public addGrid() throws FileNotFoundException {
    }

    public GridPane gridPaneMap(int width, int height, GrassField mapBoundaries) {
        GridPane mapGrid = new GridPane();
        for (int i = 0; i < height; i++) {
            RowConstraints row = new RowConstraints(30);
            mapGrid.getRowConstraints().add(row);
        }
        for (int i = 0; i < width; i++) {
            ColumnConstraints col = new ColumnConstraints(30);
            mapGrid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d vector = new Vector2d(i, height - j - 1);
                Button button = new Button();
                if (mapBoundaries.ifJungle(vector)) button.setStyle("-fx-background-color: #7fe75b");
                else button.setStyle("-fx-background-color: #da6c2a");
                if (!mapBoundaries.isOccupied(vector) && mapBoundaries.objectAt(vector) != null) {
                    ImageView imageView = new ImageView(imagePlant);
                    imageView.setFitWidth(15);
                    imageView.setFitHeight(15);
                    button.setGraphic(imageView);
                }
                if (mapBoundaries.getAnimals().containsKey(vector)) {
                    ImageView imageView1 = new ImageView(imageAnimal);
                    imageView1.setFitWidth(15);
                    imageView1.setFitHeight(15);
                    button.setGraphic(imageView1);
                    int energy = 0;
                    for(Animal a : mapBoundaries.getAnimals().get(vector)){
                        if(a.getEnergy() > energy) energy = a.getEnergy();
                    }
                    if(energy <= 10) button.setStyle("-fx-background-color: #8bbacc");
                    if(energy > 10 && energy <= 30) button.setStyle("-fx-background-color: #63b5d3");
                    if(energy > 30 && energy <= 50) button.setStyle("-fx-background-color: #35accb");
                    if(energy > 50 && energy <= 75) button.setStyle("-fx-background-color: #13768d");
                    if(energy > 75 && energy <= 100) button.setStyle("-fx-background-color: #0c4f6b");
                    if(energy > 100) button.setStyle("-fx-background-color: #073444");
                }
                    button.setMaxSize(30, 30);
                    mapGrid.add(button, i, j);
                }
            }
            return mapGrid;
    }

    public GridPane statistics(GrassField map){
        GridPane statGrid = new GridPane();
        statGrid.add(new Label("Dominant Genotype"), 0,0);
        statGrid.add(new Label("Magic Mode Used"), 0,1);
        statGrid.add(new Label(map.getStats().commonGenotype().toString()),1,0 );
        if(!map.getMagic()) statGrid.add(new Label("Magic mode off"), 1, 1);
        else statGrid.add(new Label(Integer.toString(map.getStats().getMagicTimes())), 1, 1);

        return statGrid;
    }
}