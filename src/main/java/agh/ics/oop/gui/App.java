package agh.ics.oop.gui;
import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class App extends Application {
    public boolean submitOn = false;
    public boolean simulationOnLeft = true;
    public boolean simulationOnRight = true;
    public int day = 1;

    public void start(Stage primaryStage) {
        try {
            Button submit = new Button("Submit");

            TextField TxtWidth = new TextField ("15");
            TextField TxtHeight = new TextField("15");
            TextField TxtJungleRatio = new TextField("0.3");
            TextField TxtNOAnimals = new TextField("30");
            TextField TxtStartEnergy = new TextField("100");
            TextField TxtMoveEnergy = new TextField("3");
            TextField TxtPlantEnergy = new TextField("100");

            TxtWidth.setPrefWidth(50);
            TxtHeight.setPrefWidth(50);
            TxtJungleRatio.setPrefWidth(50);
            TxtNOAnimals.setPrefWidth(50);
            TxtStartEnergy.setPrefWidth(50);
            TxtMoveEnergy.setPrefWidth(50);
            TxtPlantEnergy.setPrefWidth(50);

            GridPane grid = new GridPane();
            grid.add(submit, 1, 12);

            grid.add(TxtWidth, 2, 1);
            grid.add(new Label("    "), 0,0);
            grid.add(TxtHeight, 2, 2);
            grid.add(TxtJungleRatio, 2, 3);
            grid.add(TxtNOAnimals, 2, 4);
            grid.add(TxtStartEnergy, 2, 5);
            grid.add(TxtPlantEnergy, 2, 6);
            grid.add(TxtMoveEnergy, 2, 7);

            grid.add(new Label("Width"), 1, 1);
            grid.add(new Label("Height"), 1, 2);
            grid.add(new Label("Jungle Ratio"), 1, 3);
            grid.add(new Label("Number of animals"), 1, 4);
            grid.add(new Label("Start Energy"), 1, 5);
            grid.add(new Label("Plant energy"), 1, 6);
            grid.add(new Label("Move energy"), 1, 7);

            CheckBox leftMagicModeOn = new CheckBox("Left Magic Mode On");
            CheckBox rightMagicModeOn = new CheckBox("Right Magic Mode On");
            grid.add(leftMagicModeOn, 1, 9);
            grid.add(rightMagicModeOn, 1, 10);
            grid.add(new Label(" "), 1, 11);
            grid.add(new Label(" "), 1, 8);

            GridPane finalGrid = new GridPane();
            addGrid use = new addGrid();
            GridPane leftGridMap = new GridPane();
            GridPane rightGridMap = new GridPane();
            GridPane rightGridStat = new GridPane();
            GridPane leftGridStat = new GridPane();
            GridPane leftGridChart = new GridPane();
            GridPane rightGridChart = new GridPane();
            finalGrid.add(leftGridMap, 0, 0);
            finalGrid.add(rightGridMap, 2, 0);
            finalGrid.add(leftGridStat,0,2);
            finalGrid.add(rightGridStat, 2, 2);
            finalGrid.add(leftGridChart, 3, 0);
            finalGrid.add(rightGridChart, 3, 1);
            FileWriter outputFile = new FileWriter("output.csv", true);

            submit.setOnAction(e-> {
                submitOn = true;
                synchronized (this) {
                    notifyAll();
                }

                int width = Integer.parseInt(TxtWidth.getText());
                int height = Integer.parseInt(TxtHeight.getText());
                double jungleRatio = Double.parseDouble(TxtJungleRatio.getText());
                int numberOfAnimals = Integer.parseInt(TxtNOAnimals.getText());
                int startEnergy = Integer.parseInt(TxtStartEnergy.getText());
                int plantEnergy = Integer.parseInt(TxtPlantEnergy.getText());
                int moveEnergy = Integer.parseInt(TxtMoveEnergy.getText());

                Random rand = new Random();

                GrassField mapNoBoundaries = new GrassField(width, height, jungleRatio, false, numberOfAnimals, leftMagicModeOn.isSelected());
                GrassField mapBoundaries = new GrassField(width, height, jungleRatio, true, numberOfAnimals, rightMagicModeOn.isSelected());
                for (int i = 0; i < numberOfAnimals; i++) {
                    int[] gens = new int[32];
                    for (int k = 0; k < 32; k++) {
                        int t = rand.nextInt(8);
                        gens[k] = t;
                    }
                    Arrays.sort(gens);
                    Animal animal = new Animal(new Vector2d(rand.nextInt(width), rand.nextInt(height)), gens, startEnergy);
                    mapNoBoundaries.place(animal);
                    mapBoundaries.place(animal);
                }
                LineChartGrid mapBoundariesChart = new LineChartGrid(mapBoundaries);
                LineChartGrid mapNoBoundariesChart = new LineChartGrid(mapNoBoundaries);
                rightGridMap.add(use.gridPaneMap(width, height, mapBoundaries), 2, 0);
                rightGridStat.add(use.statistics(mapBoundaries),2,2 );
                leftGridMap.add(use.gridPaneMap(width, height, mapNoBoundaries), 0, 0);
                leftGridStat.add(use.statistics(mapNoBoundaries), 0,2);
                finalGrid.add(new Label("  "), 1,0);
                Button simulationOnButtonLeft = new Button("Pause Left Simulation");
                finalGrid.add(simulationOnButtonLeft,0,1 );
                simulationOnLeft = true;
                Button simulationOnButtonRight = new Button("Pause Right Simulation");
                finalGrid.add(simulationOnButtonRight,2,1 );
                simulationOnRight = true;
                Scene scene = new Scene(finalGrid, 1900, 1500);
                Stage engineStage = new Stage();
                engineStage.setScene(scene);
                engineStage.setFullScreen(true);
                engineStage.show();
                Thread simulationRight = new Thread(() -> {
                    while(submitOn) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        simulationOnButtonRight.setOnAction(stop->{
                            Button fileSave = new Button("Save to file");
                            finalGrid.add(fileSave, 2,7);
                            fileSave.setOnAction(ev->{
                                StringBuilder sb = StringToFile.BuildStringToEndFile(mapBoundaries.getStats(), true, day);
                                try {
                                    outputFile.append(sb);
                                    outputFile.flush();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });
                            Button genomTrack = new Button("Show animals with the most common gens");
                            finalGrid.add(genomTrack, 2, 6);
                            genomTrack.setOnAction(ev->{
                                rightGridMap.add(use.genotypeFollow(width,height, mapBoundaries, mapBoundaries.getStats().commonGenotype()),2,0);
                            });

                            simulationOnButtonRight.setText("Start Right Simulation");
                            simulationOnRight = false;
                        });
                        synchronized (this){
                            while(!simulationOnRight){
                                try {
                                    wait(500);
                                    simulationOnButtonRight.setOnAction(stop->{
                                        simulationOnButtonRight.setText("Stop Right Simulation");
                                        simulationOnRight = true;
                                    });
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        mapBoundaries.simulationDay(startEnergy, plantEnergy, moveEnergy, day);
                        Platform.runLater(() -> {
                            rightGridMap.getChildren().clear();
                            rightGridStat.getChildren().clear();
                            rightGridChart.getChildren().clear();
                            rightGridMap.add(use.gridPaneMap(width, height, mapBoundaries), 2, 0);
                            rightGridStat.add(use.statistics(mapBoundaries),2,2 );
                            mapBoundariesChart.chartUpdate(mapBoundaries.getStats(), day);
                            rightGridChart.add(mapBoundariesChart.getLineChart(),3,1);
                            StringBuilder sb = StringToFile.BuildStringToFile(mapBoundaries.getStats(), true);
                            try {
                                outputFile.append(sb);
                                outputFile.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                });
                Thread simulationLeft = new Thread(() -> {
                    while(submitOn) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        simulationOnButtonLeft.setOnAction(stop->{
                            Button fileSave = new Button("Save to file");
                            finalGrid.add(fileSave, 0,7);
                            fileSave.setOnAction(ev->{
                                StringBuilder sb = StringToFile.BuildStringToEndFile(mapNoBoundaries.getStats(), true, day);
                                try {
                                    outputFile.append(sb);
                                    outputFile.flush();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });
                            Button genomTrack = new Button("Show animals with the most common gens");
                            finalGrid.add(genomTrack, 0, 6);
                            genomTrack.setOnAction(ev->{
                                leftGridMap.add(use.genotypeFollow(width,height, mapNoBoundaries, mapNoBoundaries.getStats().commonGenotype()),0,0);
                            });
                            simulationOnButtonLeft.setText("Start Left Simulation");
                            simulationOnLeft = false;
                        });
                        synchronized (this){
                            while(!simulationOnLeft){
                                try {
                                    wait(500);
                                    simulationOnButtonLeft.setOnAction(stop->{
                                        simulationOnButtonLeft.setText("Stop Left Simulation");
                                        simulationOnLeft = true;
                                    });
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        mapNoBoundaries.simulationDay(startEnergy, plantEnergy, moveEnergy, day);
                        Platform.runLater(() -> {
                            leftGridMap.getChildren().clear();
                            leftGridStat.getChildren().clear();
                            leftGridChart.getChildren().clear();
                            leftGridMap.add(use.gridPaneMap(width, height, mapNoBoundaries), 0, 0);
                            leftGridStat.add(use.statistics(mapNoBoundaries), 0,2);
                            mapNoBoundariesChart.chartUpdate(mapNoBoundaries.getStats(), day);
                            leftGridChart.add(mapNoBoundariesChart.getLineChart(),3,0);
                            StringBuilder sb = StringToFile.BuildStringToFile(mapNoBoundaries.getStats(), false);
                            try {
                                outputFile.append(sb);
                                outputFile.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        day += 1;
                    }
                });
                simulationRight.setDaemon(true);
                simulationLeft.setDaemon(true);
                simulationLeft.start();
                simulationRight.start();
            });

            Scene scene = new Scene(grid, 400, 400);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

}