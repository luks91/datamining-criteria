package com.github.luks91.util;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class DataUtilTest extends TestCase {

	private static final String GROUND_TRUTH_FILE_PATH = "./test_data/ground_truth.txt";
	private static final String PAIRS_FILE_PATH = "./test_data/pairs_file.txt";
	
	public void testConsideredFilesExist() {
		File f = new File(GROUND_TRUTH_FILE_PATH);
		assertTrue(f.exists() && !f.isDirectory());
		
		f = new File(PAIRS_FILE_PATH);
		assertTrue(f.exists() && !f.isDirectory());
	}
	
	public void testParseGroundTruthFile() throws IOException {
		int[] readClustering = DataUtil.readGroundTruthClustering(GROUND_TRUTH_FILE_PATH, 
				6);
		
		assertEquals(0, readClustering[0]);
		assertEquals(0, readClustering[1]);
		assertEquals(0, readClustering[3]);
		assertEquals(1, readClustering[2]);
		assertEquals(1, readClustering[4]);
		assertEquals(1, readClustering[5]);
	}
	
	public void testReadAdjacencyMatrixFromPairsFile() throws IOException {
		double[][] adjacencyMatrix = DataUtil.readAdjacencyMatrixFromPairsFile(PAIRS_FILE_PATH, 4);
		assertEquals(0.0d, adjacencyMatrix[0][0]);
		assertEquals(1.0d, adjacencyMatrix[0][1]);
		assertEquals(0.0d, adjacencyMatrix[0][2]);
		assertEquals(1.0d, adjacencyMatrix[0][3]);
		assertEquals(1.0d, adjacencyMatrix[1][0]);
		assertEquals(0.0d, adjacencyMatrix[1][1]);
		assertEquals(1.0d, adjacencyMatrix[1][2]);
		assertEquals(1.0d, adjacencyMatrix[1][3]);
		assertEquals(0.0d, adjacencyMatrix[2][0]);
		assertEquals(1.0d, adjacencyMatrix[2][1]);
		assertEquals(0.0d, adjacencyMatrix[2][2]);
		assertEquals(1.0d, adjacencyMatrix[2][3]);
		assertEquals(1.0d, adjacencyMatrix[3][0]);
		assertEquals(1.0d, adjacencyMatrix[3][1]);
		assertEquals(1.0d, adjacencyMatrix[3][2]);
		assertEquals(0.0d, adjacencyMatrix[3][3]);
	}
}
