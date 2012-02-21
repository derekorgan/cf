/**
 * An class to compute the target user's predicted rating for the target item (user-based CF) using the simple average technique.
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ib.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ub.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;

public class MeanPredictor implements Predictor 
{
	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	public MeanPredictor()
	{
	}
	
	/**
	 * @returns the target user's predicted rating for the target item or null if a prediction cannot be computed
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param userProfileMap - a map containing user profiles
	 * @param itemProfileMap - a map containing item profiles
	 * @param neighbourhood - a Neighbourhood object
	 * @param simMap - a map containing user-user similarities
	 */
	public Double getPrediction(final Integer userId, final Integer itemId, final Map<Integer,Profile> userProfileMap, final Map<Integer,Profile> itemProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(userId, itemId, itemProfileMap, simMap); // get the neighbours
		
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{
			Double rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); // get the neighbour's rating for the target item
			if(rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			
			above += rating.doubleValue();
		}
				
		if(neighbours.size() > 0)
			return new Double(above / neighbours.size());
		else
			return null;
	}
}
