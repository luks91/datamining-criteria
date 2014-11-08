package com.github.luks91.util;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections15.Factory;
import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.io.GraphMLMetadata;
import edu.uci.ics.jung.io.GraphMLReader;

public final class DataUtil {

	private DataUtil() {}
	
	public static double[][] readAdjacencyMatrixFromGraphML(String fileName) throws SAXException,
			ParserConfigurationException, IOException {
		
		final UndirectedGraph<Node, Edge> graph = new UndirectedSparseMultigraph<>();
		GraphMLReader<Hypergraph<Node, Edge>, Node, Edge> reader = new GraphMLReader<>(
				new VertexFactory(), new EdgeFactory());
		reader.load(fileName, graph);
		Map<String, GraphMLMetadata<Edge>> edge_meta = reader.getEdgeMetadata(); 

		double[][] returnAdjacencyMatrix = new double[graph.getVertexCount()][graph.getVertexCount()];
		
		for (Node firstNode : graph.getVertices()) {
			for (Node secondNode : graph.getVertices()) {
				if (firstNode.mNodeIndex == secondNode.mNodeIndex)
					continue;
				
				Edge verticesEdge = graph.findEdge(firstNode, secondNode);
				if (verticesEdge == null)
					continue;

				verticesEdge.setEdgeWeight(edge_meta.get("weight").transformer.transform(verticesEdge));
				returnAdjacencyMatrix[firstNode.mNodeIndex][secondNode.mNodeIndex] = verticesEdge.mEdgeWeight;
				returnAdjacencyMatrix[secondNode.mNodeIndex][firstNode.mNodeIndex] = verticesEdge.mEdgeWeight;
			}
		}

		return returnAdjacencyMatrix;
	}

	private static final class Node {
		private final int mNodeIndex;

		public Node(int nodeIndex) {
			mNodeIndex = nodeIndex;
		}
	}

	private static final class Edge {
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
