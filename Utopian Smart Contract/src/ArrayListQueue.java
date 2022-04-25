import java.util.ArrayList;

public class ArrayListQueue<T extends Block> implements IQueue<T>,Cloneable {
    private ArrayList<T> aryList;
    private Block block;
	public ArrayListQueue() {
		// TODO Auto-generated constructor stub
		aryList = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	   public ArrayListQueue<T> clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		ArrayListQueue<T> clone = (ArrayListQueue<T>)aryList.clone();
		return clone;
	}

	@Override
	public final T dequeue() {
		//Does nothing and returns null since a blockchain doesn't support element removal
		return null;
	}

	@Override
	public void enqueue(T t) {
		// TODO Auto-generated method stub
		aryList.add(size()-1,t);
		
	}
	
	
	@Override
	public T first() {
		// TODO Auto-generated method stub
		if (size()==0) {
			return null;
			
		}
	
		return aryList.get(0);
	}
	public T get(int i) {
		if (aryList==null) {
			return null;
		}
		return aryList.get(i);
	}
    public  T getPreviousElement(T t) {
    	int index = aryList.indexOf(t);
    	return aryList.get(index-1);
    }
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (aryList.size()==0);
	}
  @Override
public int size() {
	// TODO Auto-generated method stub
	return aryList.size();
}
}
