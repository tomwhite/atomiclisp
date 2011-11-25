package org.tiling.lisp.test;

import java.math.BigInteger;

import junit.framework.*;

import org.tiling.lisp.*;

public class LispInterpreterTest extends TestCase {

	public LispInterpreterTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(LispInterpreterTest.class);
	}

	public void testEval() {
		SExpression one = new SExpression(BigInteger.valueOf(1));
		SExpression two = new SExpression(BigInteger.valueOf(2));
		SExpression three = new SExpression(BigInteger.valueOf(3));
		SExpression six = new SExpression(BigInteger.valueOf(6));
		SExpression one_plus_two = Atom.PLUS.cons(one.cons(two.cons(Atom.NIL)));
		SExpression three_plus_one_plus_two = Atom.PLUS.cons(three.cons(one_plus_two.cons(Atom.NIL)));
		SExpression one_plus_two_plus_three = Atom.PLUS.cons(one_plus_two.cons(three.cons(Atom.NIL)));
		SExpression quote_one_plus_two = Atom.QUOTE.cons(one_plus_two.cons(Atom.NIL));

		LispInterpreter lisp = new LispInterpreter();
		assertEquals(three, lisp.eval(one_plus_two));
		assertEquals(six, lisp.eval(one_plus_two_plus_three));
		assertEquals(six, lisp.eval(three_plus_one_plus_two));
		assertEquals(one_plus_two, lisp.eval(quote_one_plus_two));
	}

}