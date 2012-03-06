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
		double u_sd = u.getStandardDeviation();
		
		for(int i = 0; i < neighbours.size(); i++) // iterate over each neighbour
		{
			Double n_rating = userProfileMap.get(neighbours.get(i)).getValue(itemId); // get the neighbour's rating for the target item
			if(n_rating == null)
			{
				System.out.println("Error - rating is null!"); // this error should never occur since all neighbours by definition have rated the target item!
				System.exit(1);
			}
			double n_mean = userProfileMap.get(neighbours.get(i)).getMeanValue(); // get the mean value of this neighbour
			double n_sd =   userProfileMap.get(neighbours.get(i)).getStandardDeviation(); 
			
			if(n_sd == 0) // want to avoid division by zero 
				n_sd = 0.0001;
			
			double n_diff = n_rating - n_mean;

			double sim = simMap.getSimilarity(userId,neighbours.get(i));
			
			above += n_diff * sim / n_sd;
			below += Math.abs(sim); 
			
		}
		
		if(neighbours.size() > 7) {
			//System.out.println(u_mean + " + ("+u_sd+" * ("+ above + "/"+ below +"))");
			return new Double(u_mean + (u_sd * (above / below)));
		}
		else
			return null;
	}
	
}
