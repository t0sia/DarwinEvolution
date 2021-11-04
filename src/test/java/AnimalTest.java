import agh.ics.oop.MoveDirection;
import agh.ics.oop.OptionsParser;
import agh.ics.oop.Animal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    @Test
    public void move_test1()
    {
        Animal testAnimal = new Animal();
        MoveDirection[] testMove = OptionsParser.parse(new String[]{"r", "f", "f", "f", "r", "b", "l"});
        for(int i = 0; i< testMove.length; i++){
            testAnimal.move(testMove[i]);
        }
        assertEquals(testAnimal.toString(),"Position: (4,3) Orientation: Wschod");
    }
    @Test
    public void move_test2_boundLeftRight()
    {
        Animal testAnimal = new Animal();
        MoveDirection[] testMove = OptionsParser.parse(new String[]{"r", "f", "f", "f", "r", "b", "l", "l"});
        for(int i = 0; i< testMove.length; i++){
            testAnimal.move(testMove[i]);
        }
        assertEquals(testAnimal.toString(),"Position: (4,3) Orientation: Polnoc");
    }
    @Test
    public void move_test3_boundUpDown()
    {
        Animal testAnimal = new Animal();
        MoveDirection[] testMove = OptionsParser.parse(new String[]{"b", "b", "b", "b", "l", "l", "f","r","f","f","f"});
        for(int i = 0; i< testMove.length; i++){
            testAnimal.move(testMove[i]);
        }
        assertEquals(testAnimal.toString(),"Position: (0,0) Orientation: Zachod");
    }

}