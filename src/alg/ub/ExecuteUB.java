/**
 * Used to evaluate the user-based CF algorithm
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ub;

import java.io.File;
import java.util.Map;

import profile.Profile;

import alg.ib.ItemBasedCF;
import alg.ib.neighbourhood.*;
import alg.ib.predictor.*;
import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteUB
{
	public static void main(String[] args)
	{
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		Predictor predictor = new Resnick();
		PredictorItem ipredictor = new ResnickItem();
		Neighbourhood neighbourhood = new NearestNeighbourhood(53);
		NeighbourhoodItem ineighbourhood = new NearestNeighbourhoodItem(22);
		SimilarityMetric metric = new Cosine();
		
		// set the paths and filenames of the item file, train file and test file ...
		String itemFile = "FRT dataset" + File.separator + "r.item";
		String trainFile = "FRT dataset" + File.separator + "r.train";
		String testFile = "FRT dataset" + File.separator + "r.probe";
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "predictions.txt";
		
		////////////////////////////////////////////////
		// Evaluates the CF algorithm (do not change!!):
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - the output file is created
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
		UserBasedCF ubcf = new UserBasedCF(predictor, neighbourhood, metric, reader);
		ItemBasedCF ibcf = new ItemBasedCF(ipredictor, ineighbourhood, metric, reader);
		
		//get user profile map and item profile map so it can be used in the evaluator.
		Map<Integer,Profile> userProfileMap = ubcf.getUserProfiles();
		Map<Integer,Profile> itemProfileMap = ibcf.getItemProfiles();
				
		Evaluator eval = new Evaluator(ubcf,ibcf, reader.getTestData(), userProfileMap, itemProfileMap,3,7);
		
		eval.writeResults(outputFile);
		Double RMSE = eval.getRMSE();
		if(RMSE != null) System.out.println("RMSE: " + RMSE);
		double coverage = eval.getCoverage();
		System.out.println("coverage: " + coverage + "%");
		

	}
}
	