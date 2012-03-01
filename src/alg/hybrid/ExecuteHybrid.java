/**
 * Used to evaluate the user-based and item-based hybrid CF algorithm
 * 
 * Derek Organ
 * 27/02/2012
 */

package alg.hybrid;

import java.io.File;

import alg.ib.neighbourhood.*;
import alg.ib.predictor.*;
import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteHybrid
{
	public static void main(String[] args)
	{
		double bestRMSE = 1000;
		for (int i=5; i < 101; i=i+5) {
			for (int j=5; j < 101; j=j+5) {
	
		
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
		Predictor userPredictor = new Resnick();
		Neighbourhood userNeighbourhood = new NearestNeighbourhood(53);
		SimilarityMetric userMetric = new Pearson(i);
		
		// configure the item-based CF alogrithm - set the predictor, neighbourhood and similarity metric ...
		PredictorItem itemPredictor = new ResnickItem();
		NeighbourhoodItem itemNeighbourhood = new NearestNeighbourhoodItem(22);
		SimilarityMetric itemMetric = new Pearson(j);	
		
		//Hybrid Weights for average of user and item based algorithm
		int userWeight = 3;
		int itemWeight = 7;
		
		// set the paths and filenames of the item file, train file and test file ...
		String itemFile = "FRT dataset" + File.separator + "r.item";
		String trainFile = "FRT dataset" + File.separator + "r.train";
		String testFile = "FRT dataset" + File.separator + "r.probe";
		
		// set the path and filename of the output file ...
		//String outputFile = "results" + File.separator + "predictions.txt";
		
		////////////////////////////////////////////////
		// Evaluates the CF algorithm (do not change!!):
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - the output file is created
		DatasetReader reader = new DatasetReader(itemFile, trainFile, testFile);
		HybridCF hybrid = new HybridCF(userPredictor, itemPredictor, userNeighbourhood, itemNeighbourhood, userMetric, itemMetric, reader, userWeight, itemWeight);
		Evaluator eval = new Evaluator(hybrid, reader.getTestData());
		
		//eval.writeResults(outputFile);
		Double RMSE = eval.getRMSE();
		if(RMSE != null) System.out.println("RMSE: " + RMSE + "     User Significance:" + i + "    Item Signifcance: " + j);
		//double coverage = eval.getCoverage();
		//System.out.println("coverage: " + coverage + "%");
		
		if (RMSE < bestRMSE) bestRMSE = RMSE;
		
			}
		}
		
		System.out.println("Best RMSE: " + bestRMSE);
		
	}
}
	