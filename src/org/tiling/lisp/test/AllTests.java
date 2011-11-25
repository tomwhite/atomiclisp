package org.tiling.lisp.test;

import junit.framework.*;

public class AllTests extends TestCase {

	public AllTests(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTest(SExpressionTest.suite());
		suite.addTest(LispInterpreterTest.suite());
		suite.addTest(LispParserTest.suite());
		suite.addTest(LispTest.suite());

		return suite;
	}

}
