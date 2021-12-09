package agh.ics.oop;

public class OptionsParser {
    public static MoveDirection[] parse(String[] arr){
        MoveDirection[] ans = new MoveDirection[arr.length];
        for(int i = 0; i < arr.length; i++){
            switch(arr[i]){
                case "f", "forward" -> ans[i] = MoveDirection.FORWARD;
                case "b", "backward" -> ans[i] = MoveDirection.BACKWARD;
                case "l", "left" -> ans[i] = MoveDirection.LEFT;
                case "r", "right" -> ans[i] = MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(arr[i] + " is not legal move specification");
            }
        }
        return ans;
    }
}