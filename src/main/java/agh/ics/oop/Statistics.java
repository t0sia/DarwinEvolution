package agh.ics.oop;
import java.util.HashMap;
import java.util.List;

public class Statistics {
    private int animals;
    private int deadAnimals = 0;
    private int deadDays = 0;
    private int plants;
    private int[] genotype = {1};
    private double avgEnergy;
    private double avgLife = 0;
    private double avgChildren = 0;
    private int MagicMode = 0;
    private HashMap<Genotype, Integer> genotypes = new HashMap<>();

    public Statistics(int animals){
        this.animals = animals;
        this.plants = 0;
        this.avgEnergy = 0;
    }
    public void grassUpdate(){plants += 1;}
    public void animalsUpdate(){animals += 1;}
    public void magicUpdate(){MagicMode += 1; }
    public void deadUpdate(int days){
        deadAnimals += 1;
        deadDays += days;
    }

    public void dailyUpdate(GrassField map){
        int sumEnergy = 0;
        int sumBaby = 0;
        for(Vector2d v : map.getAnimals().keySet()){
            for(Animal a : map.getAnimals().get(v)){
                sumEnergy += a.getEnergy();
                sumBaby += a.getBabies();
            }
        }
        if(map.getAnimals().size() > 0) {
            this.avgEnergy = (double) sumEnergy / map.getAnimals().size();
            this.avgChildren = (double) sumBaby / map.getAnimals().size();
        }
        if(deadAnimals > 0) avgLife = (double) deadDays/deadAnimals;
    }

    public void genotypesUpdate(GrassField map){
        HashMap<Genotype, Integer> genotypesNew = new HashMap<Genotype, Integer>();
        Boolean add = false;
        for(Vector2d v : map.getAnimals().keySet()){
            for(Animal animal : map.getAnimals().get(v)){
                Genotype newGens = new Genotype(animal.getGens());
                add = false;
                for(Genotype g : genotypesNew.keySet()){
                    if (newGens.equals(g)){
                        Integer val = genotypesNew.get(g) + 1;
                        genotypesNew.replace(g, val);
                        add = true;
                        break;
                    }
                }
                if(!add) genotypesNew.put(newGens, 1);
            }
        }
        genotypes = genotypesNew;
    }

    public Genotype commonGenotype(){
        Integer i = 0;
        Genotype genotype = new Genotype(new int[]{1});
        for(Genotype g : this.genotypes.keySet()){
            if(genotypes.get(g) > i){
                i = genotypes.get(g);
                genotype = g;
            }
        }
        return genotype;
    }

    public double getAvgChildren() {
        return avgChildren;
    }

    public double getAvgLife() {
        return avgLife;
    }

    public double getAvgEnergy() {
        return avgEnergy;
    }

    public int getPlants() {
        return plants;
    }

    public int getAnimals() {
        return animals;
    }

    public int getMagicTimes() {
        return MagicMode;
    }
}
