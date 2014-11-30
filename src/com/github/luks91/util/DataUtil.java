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

package com.github.luks91.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Factory;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.io.GraphMLMetadata;
import edu.uci.ics.jung.io.GraphMLReader;

public final class DataUtil {

	private static final String GROUND_TRUTH_COMMUNITY_START_PREFIX = "#";

	private DataUtil() {
	}

	public static double[][] readAdjacencyMatrixFromGraphML(String fileName)
			throws SAXException, ParserConfigurationException, IOException {

		final UndirectedGraph<Node, Edge> graph = new UndirectedSparseMultigraph<>();
		GraphMLReader<Hypergraph<Node, Edge>, Node, Edge> reader = new GraphMLReader<>(
				new VertexFactory(), new EdgeFactory());
		reader.load(fileName, graph);
		Map<String, GraphMLMetadata<Edge>> edge_meta = reader.getEdgeMetadata();

		double[][] returnAdjacencyMatrix = new double[graph.getVertexCount()][graph
				.getVertexCount()];

		for (Node firstNode : graph.getVertices()) {
			for (Node secondNode : graph.getVertices()) {
				if (firstNode.mNodeIndex == secondNode.mNodeIndex)
					continue;

				Edge verticesEdge = graph.findEdge(firstNode, secondNode);
				if (verticesEdge == null)
					continue;

				verticesEdge.setEdgeWeight(edge_meta.get("weight").transformer
						.transform(verticesEdge));
				returnAdjacencyMatrix[firstNode.mNodeIndex][secondNode.mNodeIndex] = 
						verticesEdge.mEdgeWeight;
				returnAdjacencyMatrix[secondNode.mNodeIndex][firstNode.mNodeIndex] = 
						verticesEdge.mEdgeWeight;
			}
		}

		return returnAdjacencyMatrix;
	}
	
	public static UndirectedGraph<Node, Edge> readGraphFromGraphML(String fileName)
			throws SAXException, ParserConfigurationException, IOException {
		
		final UndirectedGraph<Node, Edge> graph = new UndirectedSparseMultigraph<>();
		GraphMLReader<Hypergraph<Node, Edge>, Node, Edge> reader = new GraphMLReader<>(
				new VertexFactory(), new EdgeFactory());
		reader.load(fileName, graph);
		Map<String, GraphMLMetadata<Edge>> edge_meta = reader.getEdgeMetadata();

		for (Node firstNode : graph.getVertices()) {
			for (Node secondNode : graph.getVertices()) {
				if (firstNode.mNodeIndex == secondNode.mNodeIndex)
					continue;

				Edge verticesEdge = graph.findEdge(firstNode, secondNode);
				if (verticesEdge == null)
					continue;

				verticesEdge.setEdgeWeight(edge_meta.get("weight").transformer
						.transform(verticesEdge));
			}
		}

		return graph;
	}

	public static int[] readGroundTruthClustering(String filePath, int verticesCount)
			throws IOException {
		int[] returnArray = new int[verticesCount];
		Path path = Paths.get(filePath);
		try (Stream<String> filteredLines = Files.lines(path).filter(
				s -> s != null && !s.isEmpty())) {
			int currentCluster = -1;
			for (String fileLine : filteredLines.collect(Collectors.toList())) {
				if (isCommunityStartPrefix(fileLine)) {
					currentCluster++;
					continue;
				}
				returnArray[Integer.valueOf(fileLine) - 1] = currentCluster;
			}
			return returnArray;
		}
	}

	private static boolean isCommunityStartPrefix(String line) {
		return line.startsWith(GROUND_TRUTH_COMMUNITY_START_PREFIX);
	}
	
	public static double[][] readAdjacencyMatrixFromPairsFile(String pairsFilePath, 
			int verticesCount) throws IOException {
		
		double[][] adjacencyMatrix = new double[verticesCount][verticesCount];
		Path path = Paths.get(pairsFilePath);
		try (Stream<String> filteredLines = Files.lines(path).filter(
				s -> s != null && !s.isEmpty())) {
			
			for (String fileLine : filteredLines.collect(Collectors.toList())) {
				String[] consideredVertices = fileLine.split(" ");
				int firstVertex = Integer.valueOf(consideredVertices[0]) - 1;
				int secondVertex = Integer.valueOf(consideredVertices[1]) - 1;
				adjacencyMatrix[firstVertex][secondVertex] = 1.0d;
				adjacencyMatrix[secondVertex][firstVertex] = 1.0d;
			}
			return adjacencyMatrix;
		}
	}

	public static final class Node {
		private final int mNodeIndex;

		public Node(int nodeIndex) {
			mNodeIndex = nodeIndex;
		}
		
		public int getNodeIndex() {
			return mNodeIndex;
		}
	}

	public static final class Edge {
		private final int mEdgeIndex;
		private int mEdgeWeight;

		public Edge(int edgeIndex) {
			mEdgeIndex = edgeIndex;
		}

		public void setEdgeWeight(String weight) {
			mEdgeWeight = Integer.parseInt(weight);
		}
	}

	private static final class VertexFactory implements Factory<Node> {
		private int mCurrentIndex = 0;

		@Override
		public Node create() {
			return new Node(mCurrentIndex++);
		}
	}

	private static final class EdgeFactory implements Factory<Edge> {
		private int mCurrentIndex = 0;

		@Override
		public Edge create() {
			return new Edge(mCurrentIndex++);
		}
	}
}
