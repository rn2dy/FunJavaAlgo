
public class QueueNode                // Facilitator class for the Queue class
{
    // Data members
    private Object element;         // Queue element
    private QueueNode next;         // Pointer to the next element

    // because there are no access labels (public, private or protected),
    // access is limited to the package where these methods are declared

    // Constructor
    QueueNode ( Object elem, QueueNode nextPtr )
    {                   
    	element = elem;
    	next = nextPtr;
    }

    // Class methods --
    // LQueue needs to know about next and element
    // must be able to set the nextPtr as needed
    QueueNode getNext( )
    {                  
    	return next;
    }
    Object getElement( )
    {
    	return element;
    }   
    void setNext( QueueNode nextPtr)
    {                  
    	next = nextPtr;
    }

} // Class QueueNode
