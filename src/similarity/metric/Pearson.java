/**
 * Compute the Pearson Correlation similarity between profiles
 * 
 * Derek Organ
 * 06/02/2012
 */

package similarity.metric;

import java.util.Set;

import profile.Profile;

public class Pearson implements SimilarityMetric
{

	/**
	 * constructor - creates a new PearsonMetric object
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
       
		double top = 0;
		double base1 = 0;
		double base2 = 0;
		
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue() - p1.getMeanValue();
			double r2 = p2.getValue(id).doubleValue() - p2.getMeanValue();
			top += r1 * r2;
			base1 += Math.pow(r1, 2);
			base2 += Math.pow(r2, 2);
		}
		
		return (base1 > 0 && base2 > 0) ? top / (Math.sqrt(base1) * Math.sqrt(base2)) : 0;
	}
	
	
}
