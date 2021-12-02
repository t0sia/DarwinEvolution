import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;

class RunTest {

    @Test
    public void test1() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "r", "f", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(4, 5);
        Vector2d[] positions = {new Vector2d(0, 0)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Vector2d[] test_vectors = {new Vector2d(1, 0)};
        List<Animal> animals = engine.list_animals();

        for (int i = 0; i < test_vectors.length; i++)
            assertTrue(animals.get(i).isAt(test_vectors[i]));
    }

    @Test
    public void test2() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"f", "f", "b", "b", "f", "f", "b", "b", "l", "r", "l", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(5, 5);
        Vector2d[] positions = {new Vector2d(2, 3), new Vector2d(3, 3), new Vector2d(2, 2), new Vector2d(3, 2)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Vector2d[] test_vectors = {new Vector2d(0, 4), new Vector2d(4, 4), new Vector2d(0, 0), new Vector2d(4, 0)};
        List<Animal> animals = engine.list_animals();

        for (int i = 0; i < test_vectors.length; i++)
            assertTrue(animals.get(i).isAt(test_vectors[i]));
    }

    @Test
    public void test3() {
        MoveDirection[] directions = OptionsParser.parse(new String[]{"r", "l", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(8, 7);
        Vector2d[] positions = {new Vector2d(0, 5), new Vector2d(7, 5)};
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Vector2d[] test_vectors = {new Vector2d(3, 5), new Vector2d(4, 5)};
        List<Animal> animals = engine.list_animals();

        for (int i = 0; i < test_vectors.length; i++)
            assertTrue(animals.get(i).isAt(test_vectors[i]));
    }
}
