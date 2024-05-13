package gad.dynamicarray;

import java.awt.font.FontRenderContext;

public class StackyQueue implements Queue {
    private DynamicStack first;
    private DynamicStack second;
    private int growthFactor;
    private int maxOverhead;
    private int size;

    public static void main(String[] args) {
        StackyQueue stackyQueue = new StackyQueue(3,4, new StudentResult(), new StudentResult());
        stackyQueue.pushBack(1);
        stackyQueue.pushBack(2);
        stackyQueue.pushBack(3);
        stackyQueue.pushBack(4);
        System.out.println(stackyQueue.popFront());
        System.out.println(stackyQueue.popFront());
        System.out.println(stackyQueue.popFront());
        stackyQueue.pushBack(5);
        stackyQueue.pushBack(6);
        stackyQueue.popFront();
    }

    public StackyQueue(int growthFactor, int maxOverhead, Result firstResult, Result secondResult) {
        first = new DynamicStack(growthFactor, maxOverhead, firstResult);
        second = new DynamicStack(growthFactor, maxOverhead, secondResult);
        this.growthFactor = growthFactor;
        this.maxOverhead = maxOverhead;
        size = 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void pushBack(int value){
        first.pushBack(value);
        size++;
    }

    @Override
    public int popFront(){
        int tempSize = first.size;
        if (second.isEmpty()){
            for (int i = 0; i < tempSize; i++){
                second.pushBack(first.popBack());
            }
        }
        int val = second.popBack();
        size--;
        return val;
    }

    @Override
    public String toString() {
        return first + ", " + second;
    }
}
