package edu.umbc.cmsc678.hw1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

import edu.umbc.cmsc678.hw1.utils.Data;

public class KmeansTest {

	SimpleKMeans k = new SimpleKMeans();

	@Test
	public void TestDistance(){
		double [] vector =  {12,12,34,45};
		double [] centroid1 = {12,34,56,78}; //45
		double [] centroid2 = {23,56,14,67}; //54
		double [] centroid3 = {87,23,1,8}; //90
		List<double[]> centroids = new ArrayList<double[]>(3);
		centroids.add(centroid3);
		centroids.add(centroid2);
		centroids.add(centroid1);
		int id = k.getClusterId(centroids, vector);
		assertEquals(2, id);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void TestUpdatedVector(){
		double [] vector1 =  {12,90,36,56};
		double [] vector2 =  {13,91,37,57};
		double [] vector3 =  {14,92,38,58};
		
		double [] vector4 =  {14,12,34,45};
		double [] vector5 =  {15,13,35,46};
		double [] vector6 =  {16,14,36,47};
		
		double [] vector7 =  {13.0,91.0,37.0,57.0};
		double [] vector8 =  {15.0,13.0,35.0,46.0};
		
		Data d1 = new Data(vector1,1);
		Data d2 = new Data(vector2,1);
		Data d3 = new Data(vector3,1);
		Data d4 = new Data(vector4,2);
		Data d5 = new Data(vector5,2);
		Data d6 = new Data(vector6,2);
		Map<Integer, List<Data>> clusters = new HashMap<Integer, List<Data>>();
		List<Data> d7 = new ArrayList<Data>();
		d7.add(d1);
		d7.add(d2);
		d7.add(d3);
		clusters.put(1, d7);
		d7 = new ArrayList<Data>();
		d7.add(d4);
		d7.add(d5);
		d7.add(d6);
		clusters.put(2, d7);
		List<double[]> newCentroids = k.updateCentoid(clusters);
		int j=0;
		for (double i : newCentroids.get(0)) {
			assertEquals(vector7[j],i,0.2);
			j++;
		}
		j=0;
		for (double i : newCentroids.get(1)) {
			assertEquals(vector8[j],i,0.2);
			j++;
		}
	}
	
	@Test
	public void testVectorEquality(){
		double [] centroid1 = {12,34,56,78}; //45
		double [] centroid2 = {23,56,14,67}; //54
		double [] centroid3 = {87,23,1,8}; //90
		List<double[]> centroids = new ArrayList<double[]>(3);
		centroids.add(centroid1);
		centroids.add(centroid2);
		centroids.add(centroid3);
		assertEquals(true,k.isConverged(centroids,centroids));
	}

}
