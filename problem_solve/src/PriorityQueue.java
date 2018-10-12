
public class PriorityQueue<T> {
    private int MAX_SIZE;
    private T heap[];
    private int heapSize;
	
	@SuppressWarnings("unchecked")
	public PriorityQueue(int capacity) {
		heap = (T[]) new Object[capacity];
		heapSize = 0;
		MAX_SIZE = capacity;
	}
	
	void push(T value) {
		if (heapSize + 1 > MAX_SIZE)
        {
            return;
        }
 
        heap[heapSize] = value;
 
        int current = heapSize;
        while (current > 0 && heap[current] < heap[(current - 1) / 2]) 
        {
            T temp = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = heap[current];
            heap[current] = temp;
            current = (current - 1) / 2;
        }
 
        heapSize = heapSize + 1;
	}
	
	T pop()
    {
        if (heapSize <= 0)
        {
            return null;
        }
 
        T value = heap[0];
        heapSize = heapSize - 1;
 
        heap[0] = heap[heapSize];
 
        int current = 0;
        while (current < heapSize && current * 2 + 1 < heapSize)
        {
            int child;
            if (current * 2 + 2 >= heapSize)
            {
                child = current * 2 + 1;
            }
            else
            {
                child = heap[current * 2 + 1] < heap[current * 2 + 2] ? current * 2 + 1 : current * 2 + 2;
            }
 
            if (heap[current] < heap[child])
            {
                break;
            }
 
            T temp = heap[current];
            heap[current] = heap[child];
            heap[child] = temp;
 
            current = child;
        }
        return value;
    }

}
