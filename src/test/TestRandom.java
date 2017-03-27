package test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;

public class TestRandom {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testIntegerList() {
		Random rand1 = new Random(1);
		Random rand2 = new Random(1);
		int[] l1 = new int[10];
		int[] l2 = new int[10];
		for(int i = 0 ; i < l1.length; i++){
			l1[i] = rand1.nextInt(100);
		}
		
		for(int i = 0 ; i < l1.length; i++){
			l2[i] = rand2.nextInt(100);
		}
	

		assertArrayEquals(l1, l2);
	}

}
