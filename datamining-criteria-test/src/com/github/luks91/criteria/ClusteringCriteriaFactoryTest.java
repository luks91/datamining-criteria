package com.github.luks91.criteria;

import junit.framework.TestCase;

import com.github.luks91.criteria.ClusteringCriteriaFactory.ClusteringCriteriaCalculable;

public class ClusteringCriteriaFactoryTest extends TestCase {

	public void testCreateCIndexCriteriaCalculator() {
		ClusteringCriteriaCalculable createdCalculable = 
				ClusteringCriteriaFactory.createCIndexCriteriaCalculator();
		
		assertNotNull(createdCalculable);
		assertTrue(createdCalculable instanceof CIndexCriteriaCalculator);
	}
	
	public void testCreateModularityCriteriaCalculator() {
		ClusteringCriteriaCalculable createdCalculable = 
				ClusteringCriteriaFactory.createModularityCriteriaCalculator();
		
		assertNotNull(createdCalculable);
		assertTrue(createdCalculable instanceof ModularityCriteriaCalculator);
	}
	
	public void testCreatePBMCriteriaCalculator() {
		ClusteringCriteriaCalculable createdCalculable = 
				ClusteringCriteriaFactory.createPBMCriteriaCalculator();
		
		assertNotNull(createdCalculable);
		assertTrue(createdCalculable instanceof PBMCriteria);
	}
	
	public void testCreateSWC2CriteriaCalculator() {
		ClusteringCriteriaCalculable createdCalculable = 
				ClusteringCriteriaFactory.createSWC2CriteriaCalculator();
		
		assertNotNull(createdCalculable);
		assertTrue(createdCalculable instanceof SilhouetteWidthCriteriaCalculatorTest);
	}
	
	public void testCreateDBCriteriaCalculator() {
		ClusteringCriteriaCalculable createdCalculable = 
				ClusteringCriteriaFactory.createDBCriteriaCalculator();
		
		assertNotNull(createdCalculable);
		assertTrue(createdCalculable instanceof DaviesBouldinCriteriaCalculator);
	}
}
