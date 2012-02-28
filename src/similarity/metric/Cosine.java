/**
 * Compute the Cosine similarity between profiles
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;

public class Cosine implements SimilarityMetric
{
	private double max; // max value in significance weighting 
	
	/**
	 * constructor - creates a new CosineMetric object
	 */
	public Cosine(double max)
	{
		this.max = max;
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
		return (n1 > 0 && n2 > 0) ? (dotProduct / (n1 * n2)) * (Math.min(Math.abs((double)common.size()), max)/max )  : 0;
	}

	
	
}
