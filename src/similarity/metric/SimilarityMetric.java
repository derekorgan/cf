/**
 * An interface to compute the similarity between profiles
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package similarity.metric;

import profile.Profile;

public interface SimilarityMetric 
{
	/**
	 * @returns the similarity between two profiles
	 * @param p1
	 * @param p2
	 */
	public double getSimilarity(final Profile p1, final Profile p2);
}
