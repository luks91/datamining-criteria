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

package com.github.luks91.evaluation;

import java.util.List;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.evaluation.ExternalEvaluationFactory.ExternalEvaluationCalculable;

class AdjustedMutualInformationExternalEvaluationCalculator extends
		AbstractExternalEvaluationCalculator implements
		ExternalEvaluationCalculable {

	@Override
	public double calculateExternalEvaluation(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering) {

		int verticesAmount = obtainedClustering.size();
		double mutualInformation = calculateMutualInformation(
				obtainedClustering, groundTruthClustering);
		double obtainedClusteringEntropy = calculateEntropy(obtainedClustering,
				verticesAmount);
		double groundTruthClusteringEntropy = calculateEntropy(
				groundTruthClustering, verticesAmount);
		double expectedMutualInformation = calculateExpectedMutualInformation(
				obtainedClustering, groundTruthClustering);

		return (mutualInformation - expectedMutualInformation)
				/ (Math.max(obtainedClusteringEntropy,
						groundTruthClusteringEntropy) - expectedMutualInformation);
	}

	private double calculateMutualInformation(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering) {
		
		int verticesCount = obtainedClustering.size();
		double mutualInformation = 0.0d;
		for (int i = 0; i < obtainedClustering.getClustersAmount(); ++i) {
			for (int j = 0; j < groundTruthClustering.getClustersAmount(); ++j) {
				double probabilityBetween = calculateProbabilityBetweenTwoPartitions(
						obtainedClustering, groundTruthClustering, i, j);
				double obtainedProbability = calculateProbability(
						obtainedClustering, i, verticesCount);
				double groundProbability = calculateProbability(
						groundTruthClustering, j, verticesCount);

				mutualInformation += probabilityBetween == 0.0d ? 0.0d : probabilityBetween
						* Math.log(probabilityBetween
								* (obtainedProbability / groundProbability));
			}
		}
		return mutualInformation;
	}
	
	private double calculateProbability(ClusteredDataset dataset, int clusterIndex, 
			int verticesCount) {
		int clusterCount = dataset.getAllVertexesForCluster(clusterIndex).size();
		return 1.0d * clusterCount / verticesCount;
	}
	
	private double calculateProbabilityBetweenTwoPartitions(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering, int i, int j) {
		
		int verticesCount = obtainedClustering.size();
		List<Integer> firstClusterVertices = obtainedClustering.getAllVertexesForCluster(i);
		List<Integer> secondClusterVertices = 
				groundTruthClustering.getAllVertexesForCluster(j);
		
		int commonVertexesCount = 0;
		for (Integer vertex : firstClusterVertices) {
			if (secondClusterVertices.contains(vertex))
				commonVertexesCount++;
		}
		
		return 1.0d * commonVertexesCount / verticesCount;
	}
	
	private double calculateEntropy(ClusteredDataset dataset, int verticesCount) {
		double entropyValue = 0.0d;

		for (int currCluster = 0; currCluster < dataset.getClustersAmount(); ++currCluster) {
			double probability = calculateProbability(dataset, currCluster, verticesCount);
			entropyValue += probability * Math.log(probability);
		}

		return entropyValue;
	}

	private double calculateExpectedMutualInformation(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering) {
		
		int[][] contigencyTable = super.constructContigencyTable(
				obtainedClustering, groundTruthClustering);
		
		double expectedMutualInformation = 0.0d;
		for (int i = 0; i < obtainedClustering.getClustersAmount(); ++i) {
			for (int j = 0; j < groundTruthClustering.getClustersAmount(); ++j) {
				expectedMutualInformation += calculatePartialExpectedMI(
						obtainedClustering, groundTruthClustering, i, j, 
						contigencyTable);
			}
		}
		return expectedMutualInformation;
	}
	
	private double calculatePartialExpectedMI(ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering, int i, int j, 
			int[][] contigencyTable) {
		
		int verticesCount = obtainedClustering.size();
		int ai = calculateExpectedMIai(obtainedClustering, i, contigencyTable);
		int bj = calculateExpectedMIbj(groundTruthClustering, j, contigencyTable);
		int nij = calculateExpectedMInij(ai, bj, verticesCount);
		
		double totalSum = 0.0d;
		for (; nij <= Math.min(ai, bj); ++nij) {
			double firstMultParam = 1.0d * nij / verticesCount;
			double secondMultParam = Math.log(1.0d * (verticesCount * nij) / (ai * bj));
			double thirdMultParam = calculateThirdExpectedMIMultParam(ai, bj, nij, verticesCount);
			
			totalSum += firstMultParam * secondMultParam * thirdMultParam;
		}
		
		return totalSum;
	}
	
	private int calculateExpectedMIai(ClusteredDataset obtainedClustering,
			int i, int[][] contigencyTable) {
		
		int totalSum = 0;
		for (int j=0; j < obtainedClustering.getClustersAmount(); ++j) {
			totalSum += contigencyTable[i][j];
		}
		
		return totalSum;
	}
	
	private int calculateExpectedMIbj(ClusteredDataset groundTruthClustering, 
			int j, int[][] contigencyTable) {

		int totalSum = 0;
		for (int i=0; i < groundTruthClustering.getClustersAmount(); ++i) {
			totalSum += contigencyTable[i][j];
		}
		
		return totalSum;
	}
	
	private int calculateExpectedMInij(int ai, int bj, int verticesCount) {
		return Math.max(1, ai + bj - verticesCount);
	}
	
	private double calculateThirdExpectedMIMultParam(int ai, int bj, 
			int nij, int N) {
		
		double upperValue = 1.0d * factorial(ai) * factorial(bj) * 
				factorial(N - ai) * factorial(N - bj);
		double lowerValue = 1.0d * factorial(N) * factorial(nij)
				* factorial(ai - nij) * factorial(bj - nij)
				* factorial(N - ai - bj + nij);
		
		return upperValue / lowerValue;
	}
}
