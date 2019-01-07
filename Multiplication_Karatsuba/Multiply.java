import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
    	int[] result = new int[2];
    	int m,a,b,c,d,e,f,g,h;
    	
    	//System.out.println("("+size + " and " + x +" and S" + y + ")");
    	if (size == 1) {
    		result[0] = x*y;
    		result[1] = 1;
    	} else {
    		m =(size%2 == 0) ? size/2 : (size/2)+1;
    		
    		a = (int) (x/Math.pow(2, m));
    		b = (int) ((int) x%Math.pow(2, m));
    		
    		c = (int) (y/Math.pow(2, m));
    		d = (int) ((int) y%Math.pow(2, m));
    		
    		e = naive(m,a,c)[0];
    		result[1] = result[1]+naive(m,a,c)[1];
    		//System.out.println("after e" + Arrays.toString(result));
    		
    		f = naive(m,b,d)[0];
    		result[1] = result[1]+naive(m,b,d)[1];
    		//System.out.println("after f" + Arrays.toString(result));
    		
    		g = naive(m,b,c)[0];
    		result[1] = result[1]+naive(m,b,c)[1];
    		//System.out.println("after g" + Arrays.toString(result));
    		
    		h = naive(m,a,d)[0];
    		result[1] = result[1]+naive(m,a,d)[1];
    		//System.out.println("after h" + Arrays.toString(result));
    		
    		result[0] = (int)Math.pow(2, 2*m)*e + (int)Math.pow(2, m)*(g+h)+f;
    		result[1] = result[1]+3*size;
    		
    	}
    	//System.out.println(Arrays.toString(result));
        return result;
        
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
    	int[] result = new int[2];
    	int m,a,b,c,d,e,f,g;
    	
    	if (size == 1) {
    		result[0] = x*y;
    		result[1] = 1;
    	} else {
    	
	    	m =(size%2 == 0) ? size/2 : (size/2)+1;
			
			a = (int) (x/Math.pow(2, m));
			b = (int) ((int) x%Math.pow(2, m));
			
			c = (int) (y/Math.pow(2, m));
			d = (int) ((int) y%Math.pow(2, m));
			
			e = karatsuba(m,a,c)[0];
			result[1] = result[1]+karatsuba(m,a,c)[1];
			//System.out.println("after e" + Arrays.toString(result));
			
			f = karatsuba(m,b,d)[0];
			result[1] = result[1]+karatsuba(m,b,d)[1];
			//System.out.println("after f" + Arrays.toString(result));
			
			g = karatsuba(m,a-b,c-d)[0];
			result[1] = result[1]+ karatsuba(m,a-b,c-d)[1];
			//System.out.println("after g" + Arrays.toString(result));
			
			result[0] = (int)Math.pow(2, 2*m)*e + (int)Math.pow(2, m)*(e+f-g)+f;
    		result[1] = result[1]+6*size;
    	}
        
        return result;
        
    }
    
    public static void main(String[] args){
    	/*int x = randomInt(2);
        int y = randomInt(2);
    	System.out.println(x+ " and " + y);
    	System.out.println(Arrays.toString(naive(2,x,y)));
    	System.out.println(Arrays.toString(karatsuba(2,x,y)));
    	
    	for (int size=1; size<=4; size++) {
	        int x = randomInt(size);
	        int y = randomInt(size);
	        System.out.println(x+ " and " + y);
	    	System.out.println(Arrays.toString(naive(size,x,y)));
	    	System.out.println(Arrays.toString(karatsuba(2,x,y)));
	    	System.out.println();
    	}*/
    	
    	
        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
