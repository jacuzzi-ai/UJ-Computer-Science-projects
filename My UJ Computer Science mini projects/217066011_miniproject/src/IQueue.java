
public interface IQueue<T>{
	
	public T dequeue();
	public void enqueue(T e);
	public T first();
	public boolean isEmpty();
	public int size();
}
