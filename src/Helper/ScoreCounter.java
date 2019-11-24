package Helper;

import objects.Puzzle;

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
        long score = (size*size - steps) * size;
        return score>0?score:0;
    }
}
