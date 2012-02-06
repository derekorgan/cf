/**
 * Compute the Pearson Correlation similarity between profiles
 * 
 * Derek Organ
 * 06/02/2012
 */


package similarity.metric;

import java.util.Set;

import profile.Profile;

public class Pearson {

	/**
	 * constructor - creates a new CosineMetric object
	 */
	public Pearson()
	{
	}
	
	/**
	 * computes the similarity between profiles
	 * @param profile 1 
	 * @param profile 2
	 */
	public double getSimilarity(final Profile p1, final Profile p2)
	{
        double dotProduct = 0;
        
        Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			dotProduct += r1 * r2;
		}

		double n1 = p1.getNorm();
		double n2 = p2.getNorm();
		return (n1 > 0 && n2 > 0) ? dotProduct / (n1 * n2) : 0;
	}
	
	
}
