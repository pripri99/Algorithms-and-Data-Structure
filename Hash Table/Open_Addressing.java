package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int hash;
    	hash = myModulo((h(key) + i), m);
    	
        return hash;
    }
    
    // Compute the hash function h(k) on the key
    // helper method for probe
    public int h(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int h;
    	h = myModulo(A*key, power2(w)) >> (w - r);
        return h;
    }
    
  //modulo helper method
    public static int myModulo (int A, int B) {
    	int result = A%B;
    	
    	if (result < 0) result += B;
    	return result;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }
    
   
    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int collision = 0; 
    	int i = 0;
    	
        int v = probe(key, i); // v for value returned by hash function
        
        while (!isSlotEmpty(v)) {
        	// when the table is full
        	if (i >= m) return collision;
        	
        	i++;
        	v = probe(key, i);
        	collision++;
        	
        	
        }
        Table[v] = key;
        return collision;   
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int temp;
    	boolean keyIsRemoved = false;
    	int i = 0;
    	int collision = 0; 
    	int v = probe(key, i); // v for value returned by hash function
    	while (keyIsRemoved == false) {
    		
    		 temp = Table[v];
    		 
    		// when the table is full and key not found
         	if (collision >= m) return collision;
         	
    		// when key is not found
          	if (isSlotEmpty(v)) {
          		collision++;
          		return collision;
          	}
    		 if (temp == key) {
    			 Table[v] = -2; // -2 value for deleted element
    			 keyIsRemoved = true;
    			 return collision;
    		 }
    		 i ++;
    		 v = probe(key, i);
    		 collision++;
    		
         }
    	return collision;
    }

}
