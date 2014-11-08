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
import java.util.Random;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import com.github.luks91.criteria.ClusteringCriteriaFactory;
import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;
import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.data.DatasetContentFactory;
import com.github.luks91.data.DatasetContentFactory.IDatasetContent;
import com.github.luks91.distance.NodesDistanceFactory;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;
import com.github.luks91.evaluation.ExternalEvaluationFactory;
import com.github.luks91.evaluation.ExternalEvaluationFactory.ExternalEvaluationCalculable;

class CriteriaComparingExperiment {

	private static final int RANDOM_CLUSTERINGS_AMOUNT = 30;
	
	private final Random mRandomizer = new Random(); 
	
	public void perform() throws Exception {

		List<ClusteringCriteriaCalculable> criteriaCalculators = allTheConsideredCriterias();
		List<NodesDistanceCalculable> nodesDistanceCalculators = allTheDistanceCalculators();
		
		for (IDatasetContent datasetContent : consideredDatasets()) {
			System.out.println("");
			System.out.println("########### DATASET ###########");
			System.out.println("########### "	 + datasetContent.toString() + " ############");
			
			List<ClusteredDataset> performedClusterings = allTheConsideredClusterings(datasetContent);
			
			for (ExternalEvaluationCalculable correlationCalculator : allTheExternalEvaluators()) {
				System.out.println("For external evaluation: " + correlationCalculator.toString());
				
				double[] clusteringExternalEvaluations = new double[RANDOM_CLUSTERINGS_AMOUNT];

				for (int i = 0; i < RANDOM_CLUSTERINGS_AMOUNT; ++i) {
					ClusteredDataset currentClustering = performedClusterings.get(i);
					// compute correlation
					clusteringExternalEvaluations[i] = correlationCalculator
							.calculateExternalEvaluation(currentClustering,
									datasetContent.getGroundTruthDataset());
				}
				
				double[] criteriaScores = new double[criteriaCalculators.size() * nodesDistanceCalculators.size()];
				int currentTestedCriteria = -1;
				
				//for all the considered criterias
				for (ClusteringCriteriaCalculable criteria : criteriaCalculators) {
					System.out.println("    For criteria : " + criteria.toString());
					
					//for all the considered nodes distance calculators
					for (NodesDistanceCalculable nodesDistanceCalculator : nodesDistanceCalculators) {
						
						double[] criteriaValues = new double[performedClusterings.size()];
						ClusteredDataset performedClustering = null;
						
						//for all the clustered datasets
						for (int i=0; i < performedClusterings.size(); ++i) {
							performedClustering = performedClusterings.get(i);
							//calculate criteria value
							criteriaValues[i] = criteria.calculateCriteria(performedClustering, nodesDistanceCalculator);
						}
						// compute correlation
						criteriaScores[++currentTestedCriteria] = new SpearmansCorrelation().correlation(
								criteriaValues, clusteringExternalEvaluations);
						
						System.out.println("        For distance: " + nodesDistanceCalculator.toString());
						System.out.println("            Spearman correlation: " + new SpearmansCorrelation().correlation(
								criteriaValues, clusteringExternalEvaluations));
						System.out.println("            Pearson correlation: " + new PearsonsCorrelation().correlation(
								criteriaValues, clusteringExternalEvaluations));
					}
				}
			}
		}
	}

	private List<ClusteredDataset> allTheConsideredClusterings(IDatasetContent datasetToCluster) throws Exception {
		List<ClusteredDataset> returnList = new ArrayList<>();
		ClusteredDataset groundTruthDataset = datasetToCluster.getGroundTruthDataset();
		int verticesAmount = groundTruthDataset.size();

		for (int currentDataset = 0; currentDataset < RANDOM_CLUSTERINGS_AMOUNT; ++currentDataset) {
			int clustersAmount = mRandomizer.nextInt(5) + 1;

			int[] verticesToClusters = new int[verticesAmount];
			for (int i = 0; i < verticesAmount; ++i) {
				verticesToClusters[i] = mRandomizer.nextInt(clustersAmount);

			}
			returnList.add(new ClusteredDataset(groundTruthDataset.getAdjacencyMatrix(), 
					verticesToClusters));
		}

		return returnList;
	}
	
	private List<ClusteringCriteriaCalculable> allTheConsideredCriterias() {
		List<ClusteringCriteriaCalculable> consideredCriterias = new ArrayList<>();
		consideredCriterias.add(ClusteringCriteriaFactory.createModularityCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createCIndexCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createDBCriteriaCalculator());
		return consideredCriterias;
	}
	
	private List<NodesDistanceCalculable> allTheDistanceCalculators() {
		List<NodesDistanceCalculable> consideredDistanceCalculators = new ArrayList<>();
		consideredDistanceCalculators.add(NodesDistanceFactory.createNeighbourOverlapDistanceCalculator());
		consideredDistanceCalculators.add(NodesDistanceFactory.createEdgePathDistanceCalculator());
		consideredDistanceCalculators.add(NodesDistanceFactory.createAdjacencyRelationDistanceCalculator());
		consideredDistanceCalculators.add(NodesDistanceFactory.createPearsonCorrelationDistanceCalculator());
		return consideredDistanceCalculators;
	}

	private List<ExternalEvaluationCalculable> allTheExternalEvaluators() {
		List<ExternalEvaluationCalculable> consideredExternalEvaluators = new ArrayList<>();
		consideredExternalEvaluators.add(ExternalEvaluationFactory.createAdjustedRandIndexCalculator());
		consideredExternalEvaluators.add(ExternalEvaluationFactory.createJaccardCoefficientCalculator());
		return consideredExternalEvaluators;
	}

	private List<IDatasetContent> consideredDatasets() {
		List<IDatasetContent> returnList = new ArrayList<>();
		returnList.add(DatasetContentFactory.createZaharyWeightenedKarateDataset("dataset/karate.GraphML"));
		return returnList;
	}
}
