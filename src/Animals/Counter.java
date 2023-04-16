package Animals;

public class Counter {
    static int counter;
    public Counter() {
        counter = 0;
    }
    public void add() {
        counter++;
    }
    public int getCounter() {
        return counter;
    }
}
