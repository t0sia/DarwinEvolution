package agh.ics.oop;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SimulationEngine implements IEngine{

    private MoveDirection[] moves;
    private IWorldMap map;
    private ArrayList<Animal> animals;

    public SimulationEngine( MoveDirection[] moves, IWorldMap map, Vector2d[] positions){
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();
        for(Vector2d vector2d: positions){
            Animal animal = new Animal(map, vector2d);
            animals.add(animal);
            map.place(animal);
        }
    }

    public void run() {
        for(int i = 0; i < moves.length; i++){
            animals.get(i%animals.size()).move(moves[i]);
            System.out.println(map);
        }
    }

    public ArrayList<Animal> list_animals(){
        return animals;
    }
}