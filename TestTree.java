import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;


public class TestTree extends TwoThreeTree{
	public static void main(String[] args) throws IOException{
		TwoThreeTree testTree = new TwoThreeTree( );            // Test binary search tree
  
        int inputKey = 0;                           // User input key
        char cmd;                                   // Input command

        //-----------------------------------------------------------------
        // Need to read 'tokens' (namely priority values) input from the keyboard.
        //   In Java this takes instantiation of several classes.  
        //
        // Initialize reader - To read a character at a time
        InputStreamReader reader = new InputStreamReader(System.in);
        
        // Initialize the tokenizer -
        //   To read tokens (words and numbers separated by whitespace)
        StreamTokenizer tokens = new StreamTokenizer(reader);
        
        // Note: use the nextToken( ) method to step through a stream of tokens. 
        //   Use tokenizer's nval to obtain the number read.
        //   Since nval is of type double, cast it to an int.

        System.out.println( );
        System.out.println("Commands:");
        System.out.println("  + key : Insert element");
        System.out.println("  ? key : Search element");
        System.out.println("  - key : Remove element");
        System.out.println("  S key : Successor of the element");
        System.out.println("  T : Inorder Traversal");
        System.out.println("  M : The minimum element");
        System.out.println("  C : Clear the tree");
        System.out.println("  Q    : Quit the test program");
        System.out.println( );

        do
        {
            testTree.output( );                    // Output tree

            System.out.println( );
            System.out.print("Command: ");                  // Read command
            cmd = (char)reader.read( );
            while (Character.isWhitespace(cmd))           // ignore whitespace
                cmd = (char)reader.read();
            if ( cmd == '+'  ||  cmd == 's'  || cmd == 'S' ||
                 cmd == '-'  ||  cmd == '?'     )
            {
                tokens.nextToken( );
                inputKey = (int)tokens.nval;
            }

            switch ( cmd )
            {
            case '+' :                                  // insert
                System.out.println("Insert : key = " + inputKey);
                testTree.insert(inputKey);
                break;

            case '-' :                                  // remove
                System.out.println("Trying to Remove: " + inputKey);
                testTree.delete(inputKey);
                break;
                
            case '?' :                                 
                System.out.println("Search for key : " + inputKey);
                System.out.println("Search Result: " + testTree.search(inputKey));
                break;
                
            case 'S' : case 's' : 
            	TreeNode temp = testTree.successor2(inputKey);
            	if (temp != null ){
            		if (testTree.getSpecial())
            			System.out.println("The successor of* " + inputKey + 
            					" : " + temp.getKey2());
            		else
            			System.out.println("The successor of# " + inputKey + 
            					" : " + temp.getKey1());
            		testTree.setSpecial(false);
            	}
                break;
                
            case 'C' : case 'c' :                      
                System.out.println("Clear the tree");
                testTree.setRoot(null);
                break;

            case 'M' : case 'm' :                   
                System.out.println("The minimum key = " + testTree.minimum());
                break;

            case 'T' : case 't':                             
                System.out.println("Traverse the tree ");
                testTree.sort();
                System.out.println( );
                break;

            case 'Q' : case 'q' :                   // Quit test program
                break;

            default :                               // Invalid command
                System.out.println("Inactive or invalid command");
            }
        } while ( ( cmd != 'Q' ) && ( cmd != 'q' ) );
		
	}
}
