package Helpers;

public class ScoreCounter {
    private int size;
    private int steps;

    public ScoreCounter(int size) {
        this.size = size;
        steps = 0;
    }

    public void increaseStep(){
        steps++;
    }

    public long getScore(){
        long score = (size - steps) * size/2;
        return score>0?score:0;
    }
}