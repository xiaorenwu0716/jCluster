package edu.lsus.streetman.kmeans;
/**********************************************************************************************************************************************
Robert Streetman
LSUS - CST 790 (Dr Celebi)
Fall 2012
Class: Normalize
Description: This class holds functions for normalizing data ( ArrayList< double[] > dataPoints ) in several methods.
Included is Min-Max, Z-Score, ...
**********************************************************************************************************************************************/
import java.util.ArrayList;

public class Normalize {
	
	/******************************************************************************************************************************************
	Method: Constructor
	Descr: The constructor is private because no Normalize object is meant to exist. Just call methods.
	******************************************************************************************************************************************/
	private Normalize() {
	}
	
	/******************************************************************************************************************************************
	Method: minMax
	Descr: This method accepts a list of data points, finds the min and max of each attribute, and returns a list of the 
	normalized points. The points are normalized by subtracting each point by the min, then dividing by the range (min - max).
	******************************************************************************************************************************************/
	public static ArrayList< double[] > minMax( ArrayList< double[] > data ) {
		int dimen = data.get( 0 ).length;
		double[] min = new double[ dimen ];		//List of the lowest value of each attribute
		double[] max = new double[ dimen ];		//List of the highest value of each attribute
		//Set the min and max of each attribute to the first point in the data set
		for( int a = 0; a < dimen; a++ ) {
			min[ a ] = max[ a ] = data.get( 0 )[ a ];
		}
		//Find the highest and lowest value of each attribute in the set
		for( double[] pt : data ) {
			for( int a = 0; a < dimen; a++ ) {
				//See if the min-max values need to be adjusted
				if( pt[ a ] < min[ a ] ) {
					min[ a ] = pt[ a ];
				}
				if( pt[ a ] > max[ a ] ) {
					max[ a ] = pt[ a ];
				}
			}
		}
		//Create normalized list of data points by subtracting minimum attr. value from attr. value, then dividing by attr. range
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( double[] pt : data ) {
			double[] newPoint = new double[ dimen ];
			for( int a = 0; a < dimen; a++ ) {
				if( min[ a ] == max[ a ] ) {
					newPoint[ a ] = 0.0;
				} else {
					newPoint[ a ] = ( pt[ a ] - min[ a ] ) / ( max[ a ] - min[ a ] );
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
	
	/******************************************************************************************************************************************
	Method: maxNorm
	Descr: This method accepts a list of data points, normalizes the values in relation to that attribute's max value, and 
	returns the normalized points. For the new point, the value of each attribute is the value of the old attribute divided by
	the max value found for that attribute.
	******************************************************************************************************************************************/
	public static ArrayList< double[] > maxNorm( ArrayList< double[] > data ) {
		int dimen = data.get( 0 ).length;
		double[] max = new double[ dimen ];		//List of the highest value in each attribute
		//Set the max of each attribute to the first point in the set
		for( int a = 0; a < dimen; a++ ) {
			max[ a ] = data.get( 0 )[ a ];
		}
		//Find the highest value of each attribute in the set
		for( double[] pt : data ) {
			for( int a = 0; a < dimen; a++ ) {
				if( pt[ a ] > max[ a ] ) {
					max[ a ] = pt[ a ];
				}
			}
		}
		//Create normalized list of data points by dividing attr. value by attr. maximum
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( double[] pt : data ) {
			double[] newPoint = new double[ dimen ];
			for( int a = 0; a < dimen; a++ ) {
				if( max[a] == 0.0 ) {
					newPoint[a] = 0.0;
				} else {
					newPoint[ a ] = pt[ a ] / max[ a ];
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
	
	/******************************************************************************************************************************************
	Method: rangeNorm
	Descr: This method accepts a list of data points, normalizes the values in relation to that attribute's range, and returns 
	the normalized points. For the new point, the value of each attribute is the value of the old attribute, divided by the
	difference between the max and min values for that attribute ( range ).
	******************************************************************************************************************************************/
	public static ArrayList< double[] > rangeNorm( ArrayList< double[] > data ) {
		int dimen = data.get( 0 ).length;
		double[] min = new double[ dimen ];		//List of the lowest value in each attribute
		double[] max = new double[ dimen ];		//List of the highest value in each attribute
		//Set the min and max of each attribute to the first point in the set
		for( int a = 0; a < dimen; a++ ) {
			min[ a ] = max[ a ] = data.get( 0 )[ a ];
		}
		//Find the highest and lowest value of each attribute in the set
		for( double[] pt : data ) {
			for( int a = 0; a < dimen; a++ ) {
				//See if the min-max values need to be adjusted
				if( pt[ a ] < min[ a ] ) {
					min[ a ] = pt[ a ];
				}
				if( pt[ a ] > max[ a ] ) {
					max[ a ] = pt[ a ];
				}
			}
		}
		//Create normalized list of data points by dividing attr. value by attr. range
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( double[] pt : data ) {
			double[] newPoint = new double[ dimen ];
			for( int a = 0; a < dimen; a++ ) {
				if( min[ a ] == max[ a ] ) {
					newPoint[ a ] = 0.0;
				} else {
					newPoint[ a ] = pt[ a ] / ( max[ a ] - min[ a ] );
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
	
	/******************************************************************************************************************************************
	Method: zScore
	Descr: This method accepts a list of data points, finds the mean and standard deviation of each attribute, and returns a 
	list of the normalized points. Points are normalized by each attribute, where the value of that attribute is subtracted by
	the mean of all attribute values, and this difference is divided by the standard deviation of all attributes.
	******************************************************************************************************************************************/
	public static ArrayList< double[] > zScore( ArrayList< double[] > data ) {
		int dimen = data.get( 0 ).length;
		int N = data.size();
		double[] mean = new double[ dimen ];		//List of the mean value of each attribute
		double[] sums = new double[ dimen ];		//List of the sum of values of each attribute
		double[] stdDev = new double[ dimen ];		//List of the standard deviation of each attribute
		double[] sumSquared = new double[ dimen ];	//List of the variance of each attribute
		//Find the sum of all values for each attribute in the data set
		for( double[] pt : data ) {
			for( int a = 0; a < dimen; a++ ) {
				sums[ a ] += pt[ a ];
			}
		}
		//Calculate the mean value of each attribute in the data set
		for( int a = 0; a < dimen; a++ ) {
			mean[ a ] = sums[ a ] / N;
		}
		//Find the variance of each attribute in the data set
		for( double[] pt : data ) {
			for( int a = 0; a < dimen; a++ ) {
				sumSquared[ a ] += ( pt[ a ] - mean[ a ] ) * ( pt[ a ] - mean[ a ] );
			}
		}
		//Calculate standard deviation of each attribute in the data set
		for( int a = 0; a < dimen; a++ ) {
			stdDev[ a ] = Math.sqrt( sumSquared[ a ] / ( N - 1 ) );
		}
		//Create normalized list of data points by subtracting attr. mean from attr. value, then dividing by attr. std. dev.
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( double[] pt : data ) {
			double[] newPoint = new double[ dimen ];
			for( int a = 0; a < dimen; a++ ) {
				if( stdDev[ a ] == 0.0 ) {
					newPoint[ a ] = 0.0;
				} else {
					newPoint[ a ] = ( pt[ a ] - mean[ a ] ) / stdDev[ a ];
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
	
	/******************************************************************************************************************************************
	Method: euclidNorm
	Descr: This method accepts a list of data points, normalizes the values in relation to that attribute's Euclidean norm, and 
	returns the normalized points. For the new point, the value of each attribute is the old value divided by the Euclidean
	norm for that point, which is the square root of the sum of all the values, squared, of that attribute for each point.
	******************************************************************************************************************************************/
	public static ArrayList< double[] > euclidNorm( ArrayList< double[] > data ) {
		int dimen = data.get( 0 ).length;
		double[] euclidNorm = new double [ dimen ];			//List of the Euclid. norm of each attribute
		//Calculate the Euclidean norm of each attribute
		for( int a = 0; a < dimen; a++ ) {
			double sumSquare = 0.0;
			for( double[] pt : data ) {
				sumSquare += pt[ a ] * pt[ a ];
			}
			euclidNorm[ a ] = Math.sqrt( sumSquare );
		}
		//Create the normalized list of data points by dividing attr. value by attr. Euclid. norm
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( double[] pt : data ) {
			double[] newPoint = new double[ dimen ];
			for( int a = 0; a < dimen; a++ ) {
				if( euclidNorm[ a ] == 0.0 ) {
					newPoint[ a ] = 0.0;
				} else {
					newPoint[ a ] = pt[ a ] / euclidNorm[ a ];
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
	
	/******************************************************************************************************************************************
	Method: rankedNorm
	Descr: This method accepts a list of data points, ranks the values of each attribute, and returns normalized points. The
	values of the attributes of the new point are the ranks of each of those values, in decreasing? order. If there are ties
	among values of an attributes, all points with tying ranks are assigned the average of the sum of the ranks.
	******************************************************************************************************************************************/
	public static ArrayList< double[] > rankedNorm( ArrayList< double[] > data ) {
		double[] tmp = data.get( 0 );
		int dimen = tmp.length;
		int size = data.size();
		double[][] rankings = new double[ dimen ][ size ];	//List of rankings for each attr. value of each point in data set
		//Seed the rankings table with the values from the first point in the data set
		for( int a = 0; a < dimen; a++) {
			rankings[ a ][ 0 ] = tmp[ a ];
		}
		//Rank the data with an insertion sort. This code will fill out a D x N table of double values, in ascending order.
		for( int p = 1; p < size; p++ ) {
			tmp = data.get( p );
			for( int a = 0; a < dimen; a++ ) {
				//
				//Use insertion sort to put attribute in its place. Based on pseudocode in Intro to Algorithms 3rd Ed. (Cormen, et al.) p.18
				//
				double key = tmp[ a ];	//The value to be inserted
				int i = p - 1;			//This corresponds to 'j - 1'.
				while( i >= 0 && rankings[ a ][ i ] > key ) {
					rankings[ a ][ i + 1 ] = rankings[ a ][ i ];	//Move this element up one rank
					i--;											//Decrement the index
				}
				rankings[ a ][ i + 1 ] = key;	//Set the key in its new correct position
			}
		}
		//Create list of normalized points by finding the rank of every one of a point's attr. values
		ArrayList< double[] > norm = new ArrayList< double[] >();	//List of normalized data points
		for( int p = 0; p < size; p++ ) {
			tmp = data.get( p );
			double[] newPoint = new double[ dimen ];
			int rank;						//Lowest rank with this value
			int maxRank;					//Highest rank with this value
			for( int a = 0; a < dimen; a++ ) {
				rank = 0;		//Lowest index with matching value
				maxRank = 0;	//Highest index with matching value
				//Find the lowest rank matching that attribute value
				while( rank < size && tmp[ a ] > rankings[ a ][ rank ] ) {
					rank++;
					maxRank++;
				}
				while( maxRank < size && tmp[ a ] == rankings[ a ][ maxRank ] ) {
					maxRank++;
				}
				//If only one ranking has that value, return the ranking
				if( maxRank == rank ) {
					newPoint[ a ] = rank;
				//If more than one ranking has that value, sum the value of rankings and divide by number
				} else {
					double sum = 0.0;	//Sum the rankings
					int count = 0;		//Count how many rankings there are with this value
					for( int i = 0; i <  ( maxRank - rank ); i++ ) {
						sum += ( rank + i );
						count++;
					}
					newPoint[ a ] = sum / count;
				}
			}
			norm.add( newPoint.clone() );
		}
		return norm;
	}
}
