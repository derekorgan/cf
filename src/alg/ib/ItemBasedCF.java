/**
 * A class to implement the item-based collaborative filtering algorithm
 * 
 * Derek Organ
 * 20/02/2012
 */

package alg.ib;

import alg.CFAlgorithm;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.Predictor;
import similarity.SimilarityMap;
import similarity.metric.SimilarityMetric;
import util.reader.DatasetReader;

public class ItemBasedCF implements CFAlgorithm
{
	private Predictor predictor; // the predictor technique  
	private Neighbourhood neighbourhood; // the neighbourhood technique
	private DatasetReader reader; // dataset reader
	private SimilarityMap simMap; // similarity map - stores all item-item similarities
	
	/**
	 * constructor - creates a new UserBasedCF object
	 * @param predictor - the predictor technique
	 * @param neighbourhood - the neighbourhood technique
	 * @param metric - the user-user similarity metric
	 * @param reader - dataset reader
	 */
	public ItemBasedCF(final Predictor predictor, final Neighbourhood neighbourhood, final SimilarityMetric metric, final DatasetReader reader)
	{
		this.predictor = predictor;
		this.neighbourhood = neighbourhood;
		this.reader = reader;
		this.simMap = new SimilarityMap(reader.getItemProfiles(), metric); // compute all item-item similarities
	}
	
	/**
	 * @returns the target item's predicted rating for the target user or null if a prediction cannot be computed
	 * @param itemId - the target item ID
	 * @param userId - the target user ID

	 */
	public Double getPrediction(final Integer userId, final Integer itemId)
	{	
		
		return predictor.getPrediction(itemId, userId, reader.getItemProfiles(), reader.getUserProfiles(), neighbourhood, simMap);

	}
}