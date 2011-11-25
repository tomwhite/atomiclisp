package org.tiling.lisp.test;

import java.math.BigInteger;

import junit.framework.*;

import org.tiling.lisp.*;

public class LispParserTest extends TestCase {

	public LispParserTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(LispParserTest.class);
	}

	public void testParse() {
		SExpression one = new SExpression(BigInteger.valueOf(1));
		SExpression two = new SExpression(BigInteger.valueOf(2));
		SExpression three = new SExpression(BigInteger.valueOf(3));
		SExpression one_plus_two = Atom.PLUS.cons(one.cons(two.cons(Atom.NIL)));
		SExpression three_plus_one_plus_two = Atom.PLUS.cons(three.cons(one_plus_two.cons(Atom.NIL)));
		SExpression one_plus_two_plus_three = Atom.PLUS.cons(one_plus_two.cons(three.cons(Atom.NIL)));
		SExpression quote_one_plus_two = Atom.QUOTE.cons(one_plus_two.cons(Atom.NIL));

		LispParser lisp = new LispParser();
		assertEquals(Atom.NIL, lisp.parse("()"));
		assertEquals(one, lisp.parse("1"));
		assertEquals(one_plus_two, lisp.parse("(+ 1 2)"));
		assertEquals(three_plus_one_plus_two, lisp.parse("(+ 3 (+ 1 2))"));
		assertEquals(one_plus_two_plus_three, lisp.parse("(+ (+ 1 2) 3)"));
		assertEquals(quote_one_plus_two, lisp.parse("(' (+ 1 2))"));

	}

}