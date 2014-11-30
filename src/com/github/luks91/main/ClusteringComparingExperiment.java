/*
 * Copyright (C) 2014 Lukasz Szczyglowski, Radoslaw Trzcionkowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.luks91.main;

import java.util.ArrayList;
import java.util.List;

import com.github.luks91.clustering.NetworkClustererFactory;
import com.github.luks91.clustering.NetworkClustererFactory.NetworkGraphClusterable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.DatasetContentFactory;
import com.github.luks91.data.DatasetContentFactory.IDatasetContent;
import com.github.luks91.evaluation.ExternalEvaluationFactory;
import com.github.luks91.evaluation.ExternalEvaluationFactory.ExternalEvaluationCalculable;


class ClusteringComparingExperiment {

	private static final int CLUSTERINGS_AMOUNT = 5;
	
	public void perform() throws Exception {
		for (IDatasetContent consideredDataset : allTheConsideredDatasets()) {
			System.out.println("###### DATASET: " + consideredDataset.toString() + " #####"); 
			for (NetworkGraphClusterable consideredClusterer : allTheConsideredClusterers()) {
				System.out.println("For clustering: " + consideredClusterer.getDescription());
				
				List<ClusteredDataset> performedClusterings = new ArrayList<ClusteredDataset>();
				for (int i = 0; i < CLUSTERINGS_AMOUNT; i++) {
					performedClusterings.add(consideredClusterer
							.performClustering(consideredDataset.getFilePath(),
									consideredDataset.getVerticesAmount()));
				}
				
				for (ExternalEvaluationCalculable consideredExternalEvaluation : allTheExternalEvaluators()) {
					double[] externalEvaluations = new double[CLUSTERINGS_AMOUNT];
					for (int i=0; i < performedClusterings.size(); ++i) {
						ClusteredDataset obtainedClustering = performedClusterings.get(i);
						consideredExternalEvaluation.calculateExternalEvaluation(
								obtainedClustering, consideredDataset.getGroundTruthDataset());
					}
					
					System.out.print("    " + consideredExternalEvaluation.toString() + ": ");
					System.out.println(calculateAverage(externalEvaluations));
				}
			}
		}
	}
	
	private double calculateAverage(double[] values) { 
		double average = 0.0d;
		for (int i=0; i < values.length; ++i) {
			average += values[i];
		}
		
		return 1.0d * average / values.length;
	}
	
	
	public Iterable<NetworkGraphClusterable> allTheConsideredClusterers() {
		List<NetworkGraphClusterable> returnList = new ArrayList<NetworkGraphClusterable>();
		returnList.add(NetworkClustererFactory.createJungBicomponentClusterer());
		returnList.add(NetworkClustererFactory.createJungEdgeBetweennessClusterer());
		returnList.add(NetworkClustererFactory.createJungVoltageClusterer());
		return returnList;
	}
	
	private Iterable<IDatasetContent> allTheConsideredDatasets() {
		List<IDatasetContent> returnList = new ArrayList<>();
		returnList.add(DatasetContentFactory.createZaharyWeightenedKarateDataset("dataset/karate.GraphML", 
				"dataset/karate.GroundTruth"));
		return returnList;
	}
	
	private Iterable<ExternalEvaluationCalculable> allTheExternalEvaluators() {
		List<ExternalEvaluationCalculable> consideredExternalEvaluators = new ArrayList<>();
		consideredExternalEvaluators.add(ExternalEvaluationFactory.createJaccardCoefficientCalculator());
		//consideredExternalEvaluators.add(ExternalEvaluationFactory.createAdjustedRandIndexCalculator());
		//consideredExternalEvaluators.add(ExternalEvaluationFactory.createAdjustedMutualInformationCalculator());
		return consideredExternalEvaluators;
	}
}
