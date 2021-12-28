package agh.ics.oop;

import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GrassField{

    final int width;
    final int height;
    final Vector2d jungleDL;
    final Vector2d jungleUR;
    final int jungleWidth;
    final int jungleHeight;
    final boolean ifBoundaries;
    final boolean ifMagic;
    public Animal track;
    private Statistics stats;
    private ArrayListMultimap<Vector2d, Animal> animals;
    protected HashMap<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int width, int height, double jungleRatio, boolean ifBoundaries, int startAnimals, boolean ifMagic){
        this.track = null;
        this.width = width;
        this.height = height;
        this.ifBoundaries = ifBoundaries;
        this.ifMagic = ifMagic;
        this.jungleWidth = (int)Math.round(jungleRatio * width);
        this.jungleHeight = (int)Math.round(jungleRatio * height);
        this.jungleDL = new Vector2d(((width - jungleWidth) / 2), ((height - jungleHeight) / 2));
        this.jungleUR = new Vector2d(((width - jungleWidth) / 2) + jungleWidth - 1, ((height - jungleHeight) / 2) + jungleHeight - 1);
        this.animals = ArrayListMultimap.create();
        this.stats = new Statistics(startAnimals);
    }

    public HashMap<Vector2d, Grass> getGrasses() { return grasses; }

    public ArrayListMultimap<Vector2d, Animal> getAnimals() { return animals; }

    public Statistics getStats() {return stats; }

    public boolean getMagic() { return ifMagic; }

    public boolean isOccupied(Vector2d position) { return animals.containsKey(position); }

    public Object objectAt(Vector2d position) {
        if(isOccupied(position)) return animals.get(position);
        if(grasses.containsKey(position)) return grasses.get(position);
        return null;
    }

    public void place(Animal animal) { animals.put(animal.getPosition(), animal); }

    public boolean ifJungle(Vector2d vector2d) {
        return vector2d.x <= jungleUR.x && vector2d.x >= jungleDL.x && vector2d.y <= jungleUR.y && vector2d.y >= jungleDL.y;
    }

    public void newGrass(){
        Random rand = new Random();
        ArrayList<Grass> newJungleGrass = new ArrayList<>();
        ArrayList<Grass> newStepGrass = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(objectAt(new Vector2d(i,j)) == null){
                    Grass grass = new Grass(new Vector2d(i,j));
                    if(ifJungle(grass.getPosition())) newJungleGrass.add(grass);
                    else newStepGrass.add(grass);
                }
            }
        }
        if(newJungleGrass.size() > 0){
            int j = rand.nextInt(newJungleGrass.size());
            grasses.put(newJungleGrass.get(j).getPosition(), newJungleGrass.get(j));
        }
        if(newStepGrass.size() > 0){
            int s = rand.nextInt(newStepGrass.size());
            grasses.put(newStepGrass.get(s).getPosition(), newStepGrass.get(s));
        }
    }

    public void removeGrass(Vector2d vector2d){
        grasses.remove(vector2d);
    }

    public void simulationDay(int startEnergy, int plantEnergy, int moveEnergy, int day){
        Random rand = new Random();
        ArrayListMultimap<Vector2d, Animal> pom = ArrayListMultimap.create(animals);
        for(Vector2d p: pom.keySet()){
            for(Animal a: pom.get(p)){
                if(a.getEnergy() <= 0){
                    a.Death(day);
                    this.stats.genotypesUpdate(this);
                    animals.remove(p, a);
                }
            }
        }
        if(animals.size() == 5 && ifMagic && stats.getMagicTimes() < 4){
            this.stats.magicUpdate();
            for(Vector2d p: pom.keySet()){
                for(Animal a: pom.get(p)){
                    Animal animal = new Animal(new Vector2d(rand.nextInt(width), rand.nextInt(height)), a.getGens(), startEnergy);
                    this.place(animal);
                }
            }
        }
        pom = ArrayListMultimap.create(animals);
        for(Vector2d p: pom.keySet()){
            for(Animal a: pom.get(p)){
                animals.remove(a.getPosition(), a);
                if(this.ifBoundaries) a.moveBoundaries(this, moveEnergy);
                else a.moveNoBoundaries(this, moveEnergy);
                animals.put(a.getPosition(), a);
            }
        }
        for(Vector2d p: animals.keySet()){
            if(getGrasses().containsKey(p)){
                if(animals.get(p).size() == 1) for (Animal a : animals.get(p)) a.changeEnergy(plantEnergy);
                else{
                    int[] g = {1};
                    Animal P1 = new Animal(new Vector2d(0,0), g, -100);
                    for(Animal a : animals.get(p)){
                        if(a.getEnergy() > P1.getEnergy()) P1 = a;
                    }
                    P1.changeEnergy(plantEnergy);
                }
                removeGrass(p);
            }
        }
        pom = ArrayListMultimap.create(animals);
        for(Vector2d p: pom.keySet()) {
            if (pom.get(p).size() > 1) {
                int[] g = {1};
                Animal P1 = new Animal(new Vector2d(0,0), g, 0);
                Animal P2 = new Animal(new Vector2d(0,0), g, 0);
                for (Animal a : animals.get(p)) {
                    if(a.getEnergy() > P1.getEnergy()) P1 = a;
                    else if(a.getEnergy() > P2.getEnergy()) P2 = a;
                }
                if(2*P1.getEnergy() > startEnergy && 2* P2.getEnergy() > startEnergy) {
                    Animal baby = P1.baby(P1, P2);
                    place(baby);
                    this.stats.genotypesUpdate(this);
                }
            }
        }
        newGrass();
        this.stats.dailyUpdate(this);
    }
}