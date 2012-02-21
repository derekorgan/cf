/**
 * An class to compute the target items's predicted rating for the target user (item-based CF) using Resnick Algorightm.
 * 
 * Derek Organ
 * 08/02/2012
 */

package alg.ib.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ib.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;


public class Resnick implements Predictor {

	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	public Resnick()
	{
		
	}
	
	public Double getPrediction(final Integer itemId, final Integer userId, final Map<Integer,Profile> itemProfileMap, final Map<Integer,Profile> userProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		double below = 0;
		
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(itemId, userId, userProfileMap, simMap); // get the neighbours
		
		// Get the mean rating for the item we are trying to get a prediction for
		Profile i = itemProfileMap.get(itemId);
		double i_mean = i.getMeanValue();
		
		for(int j = 0; j < neighbours.size(); j++) // iterate over each neighbour
		{
			Double n_rating = itemProfileMap.get(neighbours.get(j)).getValue(userId); // get the neighbour's rating for the target user
			if(n_rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			double n_mean = itemProfileMap.get(neighbours.get(j)).getMeanValue(); // get the mean value of this neighbour
			double n_diff = n_rating - n_mean;

			double sim = simMap.getSimilarity(itemId,neighbours.get(j));
			
			above += n_diff * sim;
			below += Math.abs(sim); 
		}
		
		if(neighbours.size() > 0)
			return new Double(i_mean + (above / below));
		else
			return null;
	}
	
}
