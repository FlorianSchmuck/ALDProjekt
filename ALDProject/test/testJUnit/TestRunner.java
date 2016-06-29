package testJUnit;

import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import testGraph.TestGraphAlgorithms;

public class TestRunner {
	   public static void main(String[] args) {
		   //run test
	      TestGraphAlgorithms algo = new TestGraphAlgorithms();
	      try {
			algo.testFindInGraph();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	}
