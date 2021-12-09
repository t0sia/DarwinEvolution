package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    public void start(Stage primaryStage) {
        try {
            MoveDirection[] directions = OptionsParser.parse(getParameters().getRaw().toArray(new String[0]));
            GrassField map = new GrassField(10);
            Vector2d[] positions = {new Vector2d(10, 8), new Vector2d(3, 2)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            System.out.println(map);
            Label label = new Label("Zwierzak");

            GridPane grid = new GridPane();
            grid.setGridLinesVisible(true);
            grid.add(new Button("x/y"), 0, 0, 1, 1);

            Vector2d down = map.getDownLeft();
            Vector2d up = map.getUpRight();

            for(int i = down.x; i <= up.x; i++)grid.add(new Button(Integer.toString(i)), i+1, 0);
            for(int i = down.y; i <= up.y; i++)grid.add(new Button(Integer.toString(i)), 0, up.y - i + 1);

            for(int i = down.x; i <= up.x; i++){
                for(int j = down.y; j <= up.y; j++){
                    if(map.objectAt(new Vector2d(i,j)) != null){
                        grid.add(new Button("*"), i+1, up.y - j + 1);
                    }
                }
            }
            grid.setHalignment(label, HPos.CENTER);
            Scene scene = new Scene(grid, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
            }
            catch (Exception exception) {
            System.out.println(exception.toString());
        }
    }

}
