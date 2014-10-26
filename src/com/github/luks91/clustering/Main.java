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

package com.github.luks91.clustering;

import java.io.File;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

public class Main {
	public static void main(String[] args) throws Exception {

		Dataset data = FileHandler.loadDataset(new File("dataset/karate2.txt"), 34, " ");
		double[][] adjacencyMatrix = new double[34][34]; 
		int[] clusterIndexes = new int[34];
		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(data);


		for (Dataset dataset : clusters) {
			System.out.println(dataset);
		}

		for(int i = 0; i < 34; i++){
			for(int j = 0; j < 34; j++)
				adjacencyMatrix[i][j] = data.get(i).value(j);
			
		}

		
											
		for(int clustersIndex = 0; clustersIndex < clusters.length; clustersIndex++){
			System.out.println("Cluster is: "+ clustersIndex);
			for(int nodeIndex = 0; nodeIndex < clusters[clustersIndex].size(); nodeIndex++){
				
				System.out.println(clusters[clustersIndex].get(nodeIndex).getID());
				clusterIndexes[clusters[clustersIndex].get(nodeIndex).getID()] = clustersIndex;
			}

		}
		for (int i = 0; i < 34; i++) {
			System.out.println(clusterIndexes[i]);
		}
		
		
	}
}
