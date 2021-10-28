import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2dTest {
    @Test
    public void testToString(){
        assertEquals(new Vector2d(1,1).toString(),"(1,1)");
        assertEquals(new Vector2d(3,4).toString(),"(3,4)");
        assertEquals(new Vector2d(4,-2).toString(),"(4,-2)");
        assertEquals(new Vector2d(-2,-5).toString(),"(-2,-5)");
    }
}
