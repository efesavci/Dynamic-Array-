package gad.dynamicarray;

public class RingQueue implements Queue {
    private DynamicArray array;
    private Result result;
    private int size;
    private int from;
    private int to;

    public RingQueue(int growthFactor, int maxOverhead, Result result) {
        array = new DynamicArray(growthFactor,maxOverhead);
        this.result = result;
        size = 0;
        from= 0;
        to = 0;
    }

    public static void main(String[] args) {
        RingQueue queue = new RingQueue(2, 4, new StudentResult());
       queue.pushBack(1);
       queue.pushBack(2);
       queue.pushBack(3);
        System.out.println(queue.popFront());
        queue.pushBack(4);
        queue.pushBack(5);
        queue.pushBack(6);
        queue.pushBack(7);
        queue.pushBack(8);

    }

    @Override
    public int size(){
        return size;
    }

    @Override
        public void pushBack(int value){
            if (size == 0){
                array.reportUsage(Interval.EmptyInterval.getEmptyInterval(), size+1);
                from = 0;
                to = 0;
            }
           else if (size == array.getLength()){
               Interval interval = array.reportUsage(new Interval.NonEmptyInterval(from, (to-1+array.getLength())%array.getLength()),size+1);
               to = (interval.getTo()+1)%(array.getLength()+1);
               from = interval.getFrom();
               if (interval.isEmpty()){
                   to = 0;
                   from = 0;
               }
           }
           array.set(to,value);
           to = (to+1)%array.getLength();
           size++;
           array.reportArray(result);
        }

        @Override
        public int popFront(){
            if (size == 1){
                int val = array.get(from);
                array.reportUsage(Interval.EmptyInterval.getEmptyInterval(),size-1);
                size--;
                array.reportArray(result);
                return val;
            }
            int val = array.get(from);

            from = (from+1)%array.getLength();
            Interval interval = array.reportUsage(new Interval.NonEmptyInterval(from, (to-1+array.getLength())%array.getLength()),size -1);
            size--;
            from = interval.getFrom();
            to = (interval.getTo()+1)%array.getLength();
            array.reportArray(result);
            return val;
        }

    @Override
    public String toString() {
        return array + ", size: " + size();
    }
}
