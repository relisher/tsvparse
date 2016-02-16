package ling102.tsvparse;
import com.univocity.parsers.tsv.*;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class App 
{
	public static int levenshteinDistance( String s1, String s2 ) {
	    return dist( s1.toCharArray(), s2.toCharArray() );
	}

	public static int dist( char[] s1, char[] s2 ) {

	    // distance matrix - to memoize distances between substrings
	    // needed to avoid recursion
	    int[][] d = new int[ s1.length + 1 ][ s2.length + 1 ];

	    // d[i][j] - would contain distance between such substrings:
	    // s1.subString(0, i) and s2.subString(0, j)

	    for( int i = 0; i < s1.length + 1; i++ ) {
	        d[ i ][ 0 ] = i;
	    }

	    for(int j = 0; j < s2.length + 1; j++) {
	        d[ 0 ][ j ] = j;
	    }

	    for( int i = 1; i < s1.length + 1; i++ ) {
	        for( int j = 1; j < s2.length + 1; j++ ) {
	            int d1 = d[ i - 1 ][ j ] + 1;
	            int d2 = d[ i ][ j - 1 ] + 1;
	            int d3 = d[ i - 1 ][ j - 1 ];
	            if ( s1[ i - 1 ] != s2[ j - 1 ] ) {
	                d3 += 1;
	            }
	            d[ i ][ j ] = Math.min( Math.min( d1, d2 ), d3 );
	        }
	    }
	    return d[ s1.length ][ s2.length ];
	}
	
    public static void main( String[] args )
    {
    	
    	TsvParserSettings settings = new TsvParserSettings();
    	TsvParser parser = new TsvParser(settings);
    	settings.selectFields("Nearest city");
    	String[] states = {"AL","Alabama","AK","Alaska","AZ","Arizona","AR","Arkansas","CA","California","CO","Colorado","CT","Connecticut","DE","Delaware","DC","FL","Florida","GA","Georgia","HI","Hawaii","ID","Idaho","IL","Illinois","IN","Indiana","IA","Iowa","KS","Kansas","KY","Kentucky","LA","Louisiana","ME","Maine","MD","Maryland","MA","MI","Michigan","MN","Minnesota","MS","Mississippi","MO","Missouri","MT","Montana","NE","Nebraska","NV","Nevada","NH","NJ","New Jersey","NM","New Mexico","NY","New York","NC","North Carolina","ND","North Dakota","OH","Ohio","OK","Oklahoma","OR","Oregon","PA","Pennsylvania","PR","Puerto Rico","RI","Rhode Island","SC","South Carolina","SD","South Dakota","TN","Tennessee","TX","Texas","UT","Utah","VT","Vermont","VA","Virginia","WA","Washington","WV","West Virginia","WI","Wisconsin","WY","Wyoming"};

    	// parses all rows in one go.
    	try {
			List<String[]> allRows = parser.parseAll(new FileReader("FP1_classdata.tsv"));
			int ld = 0;
			int tempLd;
			String tempFinal = "";
			for (String[] locArr : allRows)
			{
               for(String loc : locArr)
               {
            	   for(String st : states)
            	   {
            		   tempLd = levenshteinDistance(loc, st);
            		   if(tempLd > ld)
            		   {
            			 tempFinal = st;
            			 ld = tempLd;
            		   }
            	   }
            	 
            	 if(ld != 0)
            	 {
            		 loc = tempFinal;
            		 System.out.println(loc);
            	 }
            	 tempFinal = "";
            	 tempLd = 0;  
                 ld = 0;   
               }


			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
