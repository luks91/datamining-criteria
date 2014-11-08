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

class AbstractExternalEvaluationCalculator {

	protected FormulaParameters evaluateFormulaParameters(ClusteredDataset resultDataset,
			ClusteredDataset idealDataset) {

		FormulaParameters returnObject = new FormulaParameters();
		int vertexesAmount = resultDataset.size();

		for (int i = 0; i < vertexesAmount - 1; ++i) {
			for (int j = i + 1; j < vertexesAmount; ++j) {
				evaluateForVerticesPair(resultDataset, idealDataset, i, j, returnObject);
			}
		}
		
		return returnObject;
	}
	
	private void evaluateForVerticesPair(ClusteredDataset resultDataset,
			ClusteredDataset idealDataset, int i, int j,
			FormulaParameters returnParameters) {
		
		if (verticesAreInTheSameCluster(resultDataset, i, j)) {
			if (verticesAreInTheSameCluster(idealDataset, i, j))
				returnParameters.mAmountOfPairsInBothClusters++;
			else
				returnParameters.mAmountOfPairsInFirstClusterOnly++;
		} else {
			if (verticesAreInTheSameCluster(idealDataset, i, j))
				returnParameters.mAmountOfPairsInSecondClusterOnly++;
			else
				returnParameters.mAmountOfPairsInNoneClusters++;
		}
	}

	private boolean verticesAreInTheSameCluster(ClusteredDataset dataset,
			int i, int j) {
		
		return dataset.getClusterIndex(i) == dataset.getClusterIndex(j);
	}

	protected static final class FormulaParameters {
		private int mAmountOfPairsInBothClusters;
		private int mAmountOfPairsInNoneClusters;
		private int mAmountOfPairsInSecondClusterOnly;
		private int mAmountOfPairsInFirstClusterOnly;
		
		public int getAmountOfPairsInBothClusters() {
			return mAmountOfPairsInBothClusters;
		}
		
		public int getAmountOfPairsInNoneClusters() {
			return mAmountOfPairsInNoneClusters;
		}
		
		public int getAmountOfPairsInFirstClusterOnly() {
			return mAmountOfPairsInFirstClusterOnly;
		}
		
		public int getAmountOfPairsInSecondClusterOnly() {
			return mAmountOfPairsInSecondClusterOnly;
		}
	}
}
