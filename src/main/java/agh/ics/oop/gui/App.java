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
            finalGrid.add(leftGridMap, 0, 0);
            finalGrid.add(rightGridMap, 2, 0);
            finalGrid.add(leftGridStat,0,2);
            finalGrid.add(rightGridStat, 2, 2);
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
                            Thread.sleep(800);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        simulationOnButtonRight.setOnAction(stop->{
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
                            rightGridMap.add(use.gridPaneMap(width, height, mapBoundaries), 2, 0);
                            rightGridStat.add(use.statistics(mapBoundaries),2,2 );
                            StringBuilder sb = new StringBuilder();
                            sb.append("RIGHT ");
                            sb.append("Animals: ");
                            sb.append(mapBoundaries.getStats().getAnimals());
                            sb.append(" Plants: ");
                            sb.append(mapBoundaries.getStats().getPlants());
                            sb.append(" Average Energy: ");
                            sb.append(mapBoundaries.getStats().getAvgEnergy());
                            sb.append(" Average Life Length: ");
                            sb.append(mapBoundaries.getStats().getAvgLife());
                            sb.append(" Average Children Number: ");
                            sb.append(mapBoundaries.getStats().getAvgChildren());
                            sb.append("\n");
                            try {
                                outputFile.append(sb);
                                outputFile.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            day += 1;
                        });
                    }
                });
                Thread simulationLeft = new Thread(() -> {
                    while(submitOn) {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        simulationOnButtonLeft.setOnAction(stop->{
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
                            leftGridMap.add(use.gridPaneMap(width, height, mapNoBoundaries), 0, 0);
                            leftGridStat.add(use.statistics(mapNoBoundaries), 0,2);
                            StringBuilder sb = new StringBuilder();
                            sb.append("LEFT ");
                            sb.append("Animals: ");
                            sb.append(mapNoBoundaries.getStats().getAnimals());
                            sb.append(" Plants: ");
                            sb.append(mapNoBoundaries.getStats().getPlants());
                            sb.append(" Average Energy: ");
                            sb.append(mapNoBoundaries.getStats().getAvgEnergy());
                            sb.append(" Average Life Length: ");
                            sb.append(mapNoBoundaries.getStats().getAvgLife());
                            sb.append(" Average Children Number: ");
                            sb.append(mapNoBoundaries.getStats().getAvgChildren());
                            sb.append("\n");
                            try {
                                outputFile.append(sb);
                                outputFile.flush();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            day += 1;
                        });
                    }
                });
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