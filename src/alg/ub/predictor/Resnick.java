/**
 * An class to compute the target user's predicted rating for the target item (user-based CF) using the simple average technique.
 * 
 * Derek Organ
 * 08/02/2012
 */

package alg.ub.predictor;

import java.util.ArrayList;
import java.util.Map;

import alg.ub.neighbourhood.Neighbourhood;
import similarity.SimilarityMap;
import profile.Profile;


public class Resnick implements Predictor {

	/**
	 * constructor - creates a new SimpleAveragePredictor object
	 */
	public Resnick()
	{
		
	}
	
	public Double getPrediction(final Integer userId, final Integer itemId, final Map<Integer,Profile> userProfileMap, final Map<Integer,Profile> itemProfileMap, final Neighbourhood neighbourhood, final SimilarityMap simMap)
	{
		double above = 0;
		double below = 0;
		
		ArrayList<Integer> neighbours = neighbourhood.getNeighbours(userId, itemId, itemProfileMap, simMap); // get the neighbours
		
		// Get the mean rating for the user we are trying to get a prediction for
		Profile u = userProfileMap.get(userId);
		double u_mean = u.getMeanValue();
		
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{
			Double n_rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); // get the neighbour's rating for the target item
			if(n_rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			double n_mean = userProfileMap.get(neighbours.get(i)).getMeanValue(); // get the mean value of this neighbour
			double n_diff = n_rating - n_mean;

			double sim = simMap.getSimilarity(userId,neighbours.get(i));
			
			// added inverse user frequency --- wrong calculation.. should be on each co rated item not the item in to be predicted.. 
			//double inverse = Math.log(userProfileMap.size()/itemProfileMap.get(itemId).getSize());
			//sim = sim * inverse;
			
			above += n_diff * sim;
			below += Math.abs(sim); 
		}
		
		if(neighbours.size() > 7)
			return new Double(u_mean + (above / below));
		else
			return null;
	}
	
}
