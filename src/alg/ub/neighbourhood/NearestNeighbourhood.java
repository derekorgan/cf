/**
 * A class that implements the "nearest neighbourhood" technique (user-based CF)
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ub.neighbourhood;

import similarity.SimilarityMap;
import util.ScoredThingDsc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map;

import profile.Profile;

public class NearestNeighbourhood implements Neighbourhood 
{
	private final int k; // the number of neighbours in the neighbourhood
	
	/**
	 * constructor - creates a new NearestNeighbourhood object
	 * @param k - the number of neighbours in the neighbourhood
	 */
	public NearestNeighbourhood(final int k)
	{
		this.k = k;
	}
	
	/**
	 * @returns the neighbour IDs
	 * @param userId - the numeric ID of the target user
	 * @param itemId - the numerid ID of the target item
	 * @param itemProfileMap - a map containing item profiles
	 * @param simMap - a map containing user-user similarities
	 */
	public ArrayList<Integer> getNeighbours(final Integer userId, final Integer itemId, final Map<Integer,Profile> itemProfileMap, final SimilarityMap simMap)
	{
		SortedSet<ScoredThingDsc> ss = new TreeSet<ScoredThingDsc>(); // store all user IDs in order of descending similarity in a sorted set

		if(itemProfileMap.containsKey(itemId))
		{
			for(Iterator<Integer> it = itemProfileMap.get(itemId).getIds().iterator(); it.hasNext(); ) // iterate over each user in the item profile
			{
				Integer id = it.next();
				double sim = simMap.getSimilarity(userId, id);
				if(sim > 0)
                    ss.add(new ScoredThingDsc(sim, id));		
			}
		}
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(); // get the k most similar neighbours
		int counter = 0;
		
		for(Iterator<ScoredThingDsc> it = ss.iterator(); it.hasNext() && counter < k; )
		{
			ScoredThingDsc st = it.next();
			neighbours.add((Integer)st.thing);
			counter++;
		}
		
		return neighbours;
	}
}
