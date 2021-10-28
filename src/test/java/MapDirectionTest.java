import agh.ics.oop.MapDirection;
import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void testToString(){
        assertEquals(MapDirection.SOUTH.toString(),"Poludnie");
        assertEquals(MapDirection.NORTH.next(),MapDirection.EAST);
        assertEquals(MapDirection.SOUTH.previous(),MapDirection.EAST);
        assertEquals(MapDirection.SOUTH.toUnitVector(),new Vector2d(0,-1));
    }
}