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

class JaccardCoefficientExternalEvaluationCalculator extends
		AbstractExternalEvaluationCalculator implements
		ExternalEvaluationCalculable {

	@Override
	public double calculateExternalEvaluation(
			ClusteredDataset obtainedClustering,
			ClusteredDataset groundTruthClustering) {

		FormulaParameters parameters = super.evaluateFormulaParameters(
				obtainedClustering, groundTruthClustering);

		return (double) parameters.getAmountOfPairsInBothClusters()
				/ calculateSumOfAllAmountsExceptPairsInNone(parameters);
	}

	private double calculateSumOfAllAmountsExceptPairsInNone(
			FormulaParameters parameters) {
		
		return (double) (parameters.getAmountOfPairsInBothClusters()
				+ parameters.getAmountOfPairsInFirstClusterOnly() + parameters
					.getAmountOfPairsInSecondClusterOnly());
	}

	@Override
	public String toString() {
		return "[Jaccard] Jaccard Coefficient External Evaluation";
	}
}