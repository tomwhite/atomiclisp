package org.tiling.lisp.test;

import java.math.BigInteger;

import junit.framework.*;

import org.tiling.lisp.*;

public class SExpressionTest extends TestCase {

	public SExpressionTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SExpressionTest.class);
	}

	public void testToString() {
		SExpression one = new SExpression(BigInteger.valueOf(1));
		SExpression two = new SExpression(BigInteger.valueOf(2));
		SExpression three = new SExpression(BigInteger.valueOf(3));
		SExpression one_plus_two = Atom.PLUS.cons(one.cons(two.cons(Atom.NIL)));
		assertEquals("(+ 1 2)", one_plus_two.toString());
		assertEquals("(+ (+ 1 2) 3)", Atom.PLUS.cons(one_plus_two.cons(three.cons(Atom.NIL))).toString());
		assertEquals("(+ 3 (+ 1 2))", Atom.PLUS.cons(three.cons(one_plus_two.cons(Atom.NIL))).toString());
	}

}