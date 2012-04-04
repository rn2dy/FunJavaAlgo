
public interface Queue          // Constants & Methods common to queue ADTs
{
    // Default maximum queue size
    public final int defMaxQueueSize = 10;
  
    // Queue manipulation operations
    public void enqueue ( Object newElement );  // Enqueue element at rear
    public Object dequeue ( );                  // Dequeue element from front
    public void clear ( );                      // Remove all elements from queue
    
    // Queue status operations
    public boolean isEmpty ( );                 // Is Queue empty?
    public boolean isFull ( );                  // Is Queue full?
    public void showStructure ( );              // Outputs the elements in the stack
                                                // For testing/debugging purposes only
 } // interface Queue
