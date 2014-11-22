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
import java.util.Arrays;
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
			printCurrentDataset(datasetContent);
			
			List<ClusteredDataset> performedClusterings = allTheConsideredClusterings(datasetContent);
			
			for (ExternalEvaluationCalculable correlationCalculator : allTheExternalEvaluators()) {
				double[] clusteringExternalEvaluations = calculateClusteringExternalEvaluations(
						performedClusterings, datasetContent, correlationCalculator);

				printExternalEvaluationCalculator(correlationCalculator, clusteringExternalEvaluations);
				
				for (ClusteringCriteriaCalculable criteria : criteriaCalculators) {
					System.out.println("    For criteria : " + criteria.toString());
					
					for (NodesDistanceCalculable nodesDistanceCalculator : nodesDistanceCalculators) {
						double[] criteriaValues = calculateCriteriaValues(performedClusterings, criteria, 
								nodesDistanceCalculator);
						printCriteriaCorrelations(nodesDistanceCalculator, criteriaValues, 
								clusteringExternalEvaluations);
					}
				}
			}
		}
	}
	
	private void printExternalEvaluationCalculator(ExternalEvaluationCalculable correlationCalculator,
			double[] clusteringExternalEvaluations) {
		
		System.out.println("For external evaluation: " + correlationCalculator.toString());
		System.out.println("With values: " 
				+ Arrays.toString(clusteringExternalEvaluations));
		System.out.println("With average: " + calculateAverage(clusteringExternalEvaluations));
	}
	
	private double calculateAverage(double[] values) { 
		double average = 0.0d;
		for (int i=0; i < values.length; ++i) {
			average += values[i];
		}
		
		return 1.0d * average / values.length;
	}
	
	private void printCurrentDataset(IDatasetContent datasetContent) {
		System.out.println("");
		System.out.println("########### DATASET ###########");
		System.out.println("########### "	 + datasetContent.toString() + " ############");
	}
	
	private double[] calculateClusteringExternalEvaluations(
			List<ClusteredDataset> performedClusterings, IDatasetContent datasetContent, 
			ExternalEvaluationCalculable correlationCalculator) throws Exception {
		
		double[] clusteringExternalEvaluations = new double[RANDOM_CLUSTERINGS_AMOUNT];

		for (int i = 0; i < RANDOM_CLUSTERINGS_AMOUNT; ++i) {
			clusteringExternalEvaluations[i] = correlationCalculator
					.calculateExternalEvaluation(performedClusterings.get(i),
							datasetContent.getGroundTruthDataset());
		}
		
		return clusteringExternalEvaluations;
	}
	
	private double[] calculateCriteriaValues(List<ClusteredDataset> performedClusterings,
			ClusteringCriteriaCalculable criteria,
			NodesDistanceCalculable nodesDistanceCalculator) {
		
		double[] criteriaValues = new double[performedClusterings.size()];
		ClusteredDataset performedClustering = null;
		
		for (int i=0; i < performedClusterings.size(); ++i) {
			performedClustering = performedClusterings.get(i);
			criteriaValues[i] = criteria.calculateCriteria(performedClustering, 
					nodesDistanceCalculator);
		}
		
		return criteriaValues;
	}

	private void printCriteriaCorrelations(NodesDistanceCalculable nodesDistanceCalculator,
			double[] criteriaValues, double[] clusteringExternalEvaluations) {
		
		System.out.println("        For distance: " + nodesDistanceCalculator.toString());
		System.out.println("            With values: " + Arrays.toString(criteriaValues));
		
		try {
			System.out.println("            Spearman correlation: " + new SpearmansCorrelation().correlation(
				criteriaValues, clusteringExternalEvaluations));
		} catch (Exception e) {
			System.out.println("            Spearman correlation: Could not be resolved");
		}
		
		try {
			System.out.println("            Pearson correlation: " + new PearsonsCorrelation().correlation(
				criteriaValues, clusteringExternalEvaluations));
		} catch (Exception e) {
			System.out.println("            Pearson correlation: Could not be resolved");
		}
	}
	
	private List<ClusteredDataset> allTheConsideredClusterings(IDatasetContent datasetToCluster) 
			throws Exception {
		
		List<ClusteredDataset> returnList = new ArrayList<>();
		ClusteredDataset groundTruthDataset = datasetToCluster.getGroundTruthDataset();
		int verticesAmount = groundTruthDataset.size();

		for (int currentDataset = 0; currentDataset < RANDOM_CLUSTERINGS_AMOUNT; ++currentDataset) {
			int clustersAmount = mRandomizer.nextInt(5) + 2;

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
		consideredCriterias.add(ClusteringCriteriaFactory.createSWC2CriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createVarianceRatioCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createPBMCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createDunnIndexCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createZStatisticsCriteriaCalculator());
		consideredCriterias.add(ClusteringCriteriaFactory.createPointBiserialCriteriaCalculator());
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
		consideredExternalEvaluators.add(ExternalEvaluationFactory.createJaccardCoefficientCalculator());
		//consideredExternalEvaluators.add(ExternalEvaluationFactory.createAdjustedRandIndexCalculator());
		return consideredExternalEvaluators;
	}

	private List<IDatasetContent> consideredDatasets() {
		List<IDatasetContent> returnList = new ArrayList<>();
		returnList.add(DatasetContentFactory.createZaharyWeightenedKarateDataset("dataset/karate.GraphML", 
				"dataset/karate.GroundTruth"));
		returnList.add(DatasetContentFactory.createSawmillStrikeDataset("dataset/sawmill.Pairs", 
				"dataset/sawmill.GroundTruth"));
		returnList.add(DatasetContentFactory.createNCAAFootballDataset("dataset/ncaaFootball.Pairs", 
				"dataset/ncaaFootball.GroundTruth"));
		return returnList;
	}
}
