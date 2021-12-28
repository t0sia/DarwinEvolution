package agh.ics.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Animal {
    private Vector2d position;
    private MapDirection orientation;
    public final int[] gens;
    private int energy;
    final int startEnergy;
    private ArrayList<Animal> babyList = new ArrayList<>();
    private boolean ifDead;
    private int lifeLen;
    private boolean track;
    private int babyTracker;
    private int descTracker;

    public Animal(Vector2d initialPosition, int[] gens, int energy) {
        Random rand = new Random();
        this.position = initialPosition;
        this.gens = gens;
        this.energy = energy;
        this.startEnergy = energy;
        this.ifDead = false;
        this.lifeLen = 0;
        this.track = false;
        this.babyTracker = 0;
        this.descTracker = 0;
        int orientationNumber = rand.nextInt(8);
        switch (orientationNumber){
            case 0:
                orientation = MapDirection.NORTH;
                break;
            case 1:
                orientation = MapDirection.NORTH_EAST;
                break;
            case 2:
                orientation = MapDirection.EAST;
                break;
            case 3:
                orientation = MapDirection.SOUTH_EAST;
                break;
            case 4:
                orientation = MapDirection.SOUTH;
                break;
            case 5:
                orientation = MapDirection.SOUTH_WEST;
                break;
            case 6:
                orientation = MapDirection.WEST;
                break;
            case 7:
                orientation = MapDirection.NORTH_WEST;
                break;
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() { return orientation; }

    public int[] getGens() { return gens; }

    public boolean isTrack() {return track; }

    public int getBabyTracker() {return babyTracker; }

    public int getLifeLen() {return lifeLen; }

    public int getDescTracker() {return descTracker; }

    public int getEnergy() {return energy; }

    public int getBabies() {return babyList.size(); }

    public boolean isAt(Vector2d position) {
        return (position.equals(this.position));
    }


    public void moveNoBoundaries(GrassField map, int moveEnergy) {
        Random rand = new Random();
        Vector2d oldPosition = this.position;
        int gen = gens[rand.nextInt(32)];
        moveDirection(gen);
        this.energy = this.energy - moveEnergy;
        if(position.x < 0) position = new Vector2d(map.width - 1, position.y);
        if(position.y < 0) position = new Vector2d(position.x, map.height - 1);
        if(position.x >= map.width) position = new Vector2d(0, position.y);
        if(position.y >= map.height) position = new Vector2d(position.x, 0);
    }

    public void moveBoundaries(GrassField map, int moveEnergy) {
        Random rand = new Random();
        int gen = gens[rand.nextInt(32)];
        Vector2d oldPosition = this.position;
        moveDirection(gen);
        this.energy = this.energy - moveEnergy;
        if(position.x < 0) position = new Vector2d(0, position.y);
        if(position.y < 0) position = new Vector2d(position.x, 0);
        if(position.x >= map.width) position = new Vector2d(map.width-1, position.y);
        if(position.y >= map.height) position = new Vector2d(position.x, map.height - 1);
    }

    private void moveDirection(int gen) {
        switch (gen) {
            case 0 -> position = position.add(orientation.toUnitVector());
            case 1 -> orientation = orientation.turn45();
            case 2 -> orientation = orientation.turn90();
            case 3 -> orientation = orientation.turn135();
            case 4 -> position = position.subtract(orientation.toUnitVector());
            case 5 -> orientation = orientation.turn180();
            case 6 -> orientation = orientation.turn225();
            case 7 -> orientation = orientation.turn270();
        }
    }

    public Animal baby(Animal first, Animal second){
        Random rand = new Random();
        int ifLeft = rand.nextInt(2);
        int[] babyGens = new int[32];
        int firstGens = (int)(first.energy*32/(first.energy + second.energy));
        if(ifLeft == 1){
            for(int i = 0; i < firstGens; i++) babyGens[i] = first.gens[i];
            for(int i = firstGens; i < 32; i++) babyGens[i] = second.gens[i];
        }
        else{
            for(int i = 0; i < 32 - firstGens; i++) babyGens[i] = second.gens[i];
            for(int i = 32 - firstGens; i < 32; i++) babyGens[i] = first.gens[i];
        }
        int babyEnergy = (int)((first.energy + second.energy)/4);
        first.energy = (int)(first.energy*3/4);
        second.energy = (int)(second.energy*3/4);
        Arrays.sort(babyGens);
        Animal baby = new Animal(first.position, babyGens, babyEnergy);
        if (first.track) first.babyTracker += 1;
        if (second.track) second.babyTracker += 1;
        first.babyList.add(baby);
        second.babyList.add(baby);
        return baby;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
    }

    public void Death(int day){
        this.lifeLen = day;
        this.ifDead = true;
    }

    public void tracking(){
        track = true;
    }
}