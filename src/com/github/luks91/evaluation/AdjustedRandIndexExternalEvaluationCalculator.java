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

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.evaluation.ExternalEvaluationFactory.ExternalEvaluationCalculable;

class AdjustedRandIndexExternalEvaluationCalculator extends
		AbstractExternalEvaluationCalculator implements
		ExternalEvaluationCalculable {

	@Override
	public double calculateExternalEvaluation(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering) {

		int verticesAmount = obtainedClustering.size();
		int[][] contigencyTable = constructContigencyTable(obtainedClustering, 
				groundTruthClustering);
		ContigencyTableSums tableSums = new ContigencyTableSums(contigencyTable);
		
		double totalSum = calculateTotalSum(contigencyTable);
		double verticalSumsNewton = calculateVerticalSumsArgument(tableSums);
		double horizontalSumsNewton = calculateHorizontalSumsArgument(tableSums);
		
		double formulaMultParameter = (verticalSumsNewton * horizontalSumsNewton) /
				calculateNewtonSymbol(verticesAmount, 2);
		double formulaSumParameter = 0.5d * (verticalSumsNewton + horizontalSumsNewton);
		
		return (totalSum - formulaMultParameter) 
				/ (formulaSumParameter - formulaMultParameter);
	}
	
	private double calculateTotalSum(int[][] contigencyTable) {
		double totalSum = 0.0d;
		
		for (int i=0; i < contigencyTable.length; ++i) {
			for (int j=0; j < contigencyTable[0].length; ++j) {
				totalSum += calculateNewtonSymbol(contigencyTable[i][j], 2);
			}
		}
		
		return totalSum;
	}
	
	private double calculateVerticalSumsArgument(ContigencyTableSums sums) {
		return calculateTableNewtonSum(sums.mVerticalSums);
	}
	
	private double calculateTableNewtonSum(int[] table) {
		double returnValue = 0.0d;
		
		for (int i=0; i<table.length; ++i) {
			returnValue += calculateNewtonSymbol(table[i], 2);
		}
		
		return returnValue;
	}
	
	private double calculateHorizontalSumsArgument(ContigencyTableSums sums) {
		return calculateTableNewtonSum(sums.mHorizontalSums);
	}

	@Override
	public String toString() {
		return "[ARI] Adjusted Rank Index External Evaluation";
	}
	
	private static final class ContigencyTableSums {
		private final int[] mVerticalSums;
		private final int[] mHorizontalSums;
		
		public ContigencyTableSums(int[][] contigencyTable) {
			mVerticalSums = new int[contigencyTable.length];
			mHorizontalSums = new int[contigencyTable[0].length];
			
			for (int i=0; i < contigencyTable.length; ++i) {
				for (int j=0; j < contigencyTable[0].length; ++j) {
					mVerticalSums[i] += contigencyTable[i][j];
					mHorizontalSums[j] += contigencyTable[i][j];
				}
			}
		}
	}
}
