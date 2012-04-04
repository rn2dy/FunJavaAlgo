

public class LQueue implements Queue
{
    // Data members
    private QueueNode front,        // Reference to the front element
                       rear;        // Reference to the rear element

    // Constructors
    public LQueue ( )               // Constructor: default
    {                   
    	setup();
    }
    public LQueue ( int size )      // Constructor: ignore size
    {               
    	setup();
    }
    
    // Class methods
    private void setup( )           // Called by Constructors only
    {           
    	front = null;
    	rear = null;
    }           // Initializes front and rear to null
    
	
	public void clear() {
		setup();
	}
	
	public Object dequeue() {
		if (!isEmpty()){
			Object temp = front.getElement();
			front = front.getNext();
			if (front == null){
				rear = null;
			}
			return temp;
		}
		else
			return null;
	}
	
	public void enqueue(Object newElement) {
		QueueNode newNode = new QueueNode(newElement , null);
		if (isEmpty())
			front = newNode;
		else
			rear.setNext(newNode);
		rear = newNode;
		
	}
	public boolean isEmpty() {
		if (front == null)
			return true;
		else
			return false;
	}
	public boolean isFull() {
		return false;
	}
	@Override
	public void showStructure() {
		// TODO Auto-generated method stub
		
	}
	

} // Class LQueue





