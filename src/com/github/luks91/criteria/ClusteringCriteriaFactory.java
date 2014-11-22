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

package com.github.luks91.criteria;

import com.github.luks91.data.ClusteredDataset;
import com.github.luks91.distance.NodesDistanceFactory.NodesDistanceCalculable;

public class ClusteringCriteriaFactory {

	public static ClusteringCriteriaCalculable createCIndexCriteriaCalculator() {
		return new CIndexCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createModularityCriteriaCalculator() {
		return new ModularityCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createSWC2CriteriaCalculator() {
		return new SilhouetteWidthCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createDBCriteriaCalculator() {
		return new DaviesBouldinCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createVarianceRatioCriteriaCalculator() {
		return new VarianceRatioCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createPointBiserialCriteriaCalculator() {
		return new PointBiserialCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createZStatisticsCriteriaCalculator() {
		return new ZStatisticsCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createDunnIndexCriteriaCalculator() {
		return new DunnIndexCriteriaCalculator();
	}
	
	public static ClusteringCriteriaCalculable createPBMCriteriaCalculator() {
		return new PBMCriteria();
	}
	
	public static interface ClusteringCriteriaCalculable {
		public double calculateCriteria(ClusteredDataset clusteredDataset, 
				NodesDistanceCalculable nodesDistanceCalculator);
	}
}
