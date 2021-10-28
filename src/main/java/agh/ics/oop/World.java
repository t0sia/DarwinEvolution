package agh.ics.oop;

public class World {
    public static void main(String[] args){
        Animal animal = new Animal();
        System.out.println(animal.toString());
        String[] arg = new String[] {"f", "b", "forward", "l", "r", "r", "forward"};
        MoveDirection[] arrMove = OptionsParser.parse(arg);
        System.out.println(animal.toString());
    }
    public static Direction[] convert(String[] direct){
        int l = direct.length;
        Direction[] arr = new Direction[l];
        for(int i = 0; i < direct.length; i++){
            switch (direct[i]){
                case "f" : arr[i] = Direction.FORWARD; break;
                case "b" : arr[i] = Direction.BACKWARD; break;
                case "l" : arr[i] = Direction.LEFT; break;
                default : arr[i] = Direction.RIGHT; break;
            }
        }
        return arr;
    }
    public static void run(Direction[] directions){
        for(Direction argument: directions){
            String message = switch(argument){
                case FORWARD -> "Zwierzak idzie do przodu";
                case BACKWARD -> "Zwierzak idzie do tyłu";
                case LEFT -> "Zwierzak idzie w lewo";
                default -> "Zwierzak idzie w prawo";
            };
            System.out.println(message);
        };
    }
}
