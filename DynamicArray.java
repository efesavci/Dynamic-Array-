package gad.dynamicarray;

import java.util.Arrays;

public class DynamicArray {
    private int[] elements;
    private int growthFactor;
    private int maxOverhead;

    public DynamicArray(int growthFactor, int maxOverhead) {
        if(growthFactor < 1 || growthFactor >= maxOverhead){
            throw new IllegalArgumentException();
        }
        this.growthFactor = growthFactor;
        this.maxOverhead  = maxOverhead;
        elements = new int[0];
    }

    public static void main(String[] args) {
        DynamicArray array = new DynamicArray(3,4);

        System.out.println(array.reportUsage(Interval.EmptyInterval.getEmptyInterval(), 1));
        array.set(0,1);
        System.out.println(array);
        System.out.println(array.get(1));
        array.set(1,2);
        System.out.println(array);
        array.set(2,3);
        System.out.println(array);
        System.out.println(array.reportUsage(new Interval.NonEmptyInterval(0,2), 4));
        array.set(3,4);
        System.out.println(array);
        System.out.println(array.reportUsage(new Interval.NonEmptyInterval(1,3), 3));
        System.out.println(array);


    }

    public int getLength() {
        return elements.length;
    }


    public Interval reportUsage(Interval usage, int minSize){
        if (minSize > elements.length){
            if (elements.length == 0){
                elements = new int[growthFactor];
            }
            int start;
            int end;
            int[] newArray = new int[minSize * growthFactor];
            if(usage.isEmpty()){
            elements = newArray;
            return Interval.EmptyInterval.getEmptyInterval();
            }else {
                start = usage.getFrom();
                end = usage.getTo()+1;
                if (usage.getFrom() > usage.getTo()) {
                    end = (usage.getFrom() + (elements.length-usage.getFrom()+ usage.getTo()+1));
                }
                int idx = 0;
                for (int i = start; i < end; i++) {
                    int elemIndex = i % elements.length;
                    newArray[idx] = elements[elemIndex];
                    idx++;
                }
                elements = newArray;
            }
            return new Interval.NonEmptyInterval(0, (end-1)-start);
        }else if(minSize*maxOverhead < elements.length){
            if (elements.length == 0){
                elements = new int[growthFactor];
            }
            int start;
            int end;
            int[] newArray = new int[minSize * growthFactor];
            if(usage.isEmpty()){
                elements = newArray;
                return Interval.EmptyInterval.getEmptyInterval();
            }else {
                start = usage.getFrom();
                end = usage.getTo()+1;
                if (usage.getFrom() > usage.getTo()) {
                    end = (usage.getFrom() + (elements.length-usage.getFrom()+usage.getTo()+1));
                }
                int idx = 0;
                for (int i = usage.getFrom(); i < end; i++){
                    int elemIndex = i % elements.length;
                    newArray[idx] = elements[elemIndex];
                    idx++;
                }
                elements = newArray;
            }
            return new Interval.NonEmptyInterval(0, (end-1)-start);
        }
        return usage;
    }

    public int get(int index) {
        if (index >= getLength()){
            return -1;
        }
        return elements[index];
    }

    public void set(int index, int value) {
        if (index >= this.getLength()){
            System.out.println("Invalid index, the given index is not in the given array.");
            return;
        }
        elements[index] = value;
    }

    public void reportArray(Result result) {
        result.logArray(elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
