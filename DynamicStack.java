package gad.dynamicarray;

import javax.naming.spi.DirStateFactory;

public class DynamicStack implements Stack {
    private DynamicArray array;
    private Result result;
    private int growthFactor;
    private int maxOverhead;
    int size;

    public DynamicStack(int growthFactor, int maxOverhead, Result result) {
        array = new DynamicArray(growthFactor, maxOverhead);
        this.result = result;
        this.growthFactor = growthFactor;
        this.maxOverhead = maxOverhead;
        size  = 0;
    }

    public static void main(String[] args) {
        DynamicStack stack = new DynamicStack(3,4, new StudentResult());
        stack.pushBack(1);
        stack.pushBack(2);
        stack.pushBack(3);
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.popBack());
        System.out.println(stack.isEmpty());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void pushBack(int value){
        if(size >= array.getLength()){
            array.reportUsage(new Interval.NonEmptyInterval(0,size),size+1);
            array.set(size, value);
            array.reportArray(result);
            size++;
            return;
            }
        array.set(size, value);
        size++;
        array.reportArray(result);
    }

    @Override
    public int popBack() {
        int val = array.get(size-1);
        if(size <= 1){
            array.reportUsage(Interval.EmptyInterval.getEmptyInterval(), 0);
            array.reportArray(result);
            array = new DynamicArray(growthFactor,maxOverhead);
            size = 0;
            return val;
        }
        array.reportUsage(new Interval.NonEmptyInterval(0,size-2), size-1);
        size--;
        array.reportArray(result);
        return val;
    }

    @Override
    public String toString() {
        return array + ", length: " + size();
    }

}
