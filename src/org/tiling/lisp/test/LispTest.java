package org.tiling.lisp.test;

import junit.framework.*;

import org.tiling.lisp.*;

public class LispTest extends TestCase {

	public LispTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(LispTest.class);
	}

	public void testEval() {

		assertEquals("()", Lisp.eval("()"));
		assertEquals("1", Lisp.eval("1"));
		assertEquals("a", Lisp.eval("a"));

		assertEquals("3", Lisp.eval("(+ 1 2)"));
		assertEquals("1", Lisp.eval("(- 2 1)"));
		assertEquals("0", Lisp.eval("(- 1 2)"));
		assertEquals("6", Lisp.eval("(* 2 3)"));
		assertEquals("1024", Lisp.eval("(^ 2 10)"));

		assertEquals("true", Lisp.eval("(= 1 1)"));
		assertEquals("false", Lisp.eval("(= 1 2)"));
		assertEquals("true", Lisp.eval("(= a a)"));
		assertEquals("false", Lisp.eval("(= a b)"));

		assertEquals("true", Lisp.eval("(<= 1 1)"));
		assertEquals("true", Lisp.eval("(<= 1 2)"));
		assertEquals("false", Lisp.eval("(<= 2 1)"));
		assertEquals("true", Lisp.eval("(>= 1 1)"));
		assertEquals("false", Lisp.eval("(>= 1 2)"));
		assertEquals("true", Lisp.eval("(>= 2 1)"));

		assertEquals("false", Lisp.eval("(< 1 1)"));
		assertEquals("true", Lisp.eval("(< 1 2)"));
		assertEquals("false", Lisp.eval("(< 2 1)"));
		assertEquals("false", Lisp.eval("(> 1 1)"));
		assertEquals("false", Lisp.eval("(> 1 2)"));
		assertEquals("true", Lisp.eval("(> 2 1)"));

		assertEquals("(^ 2 10)", Lisp.eval("(cons ^ (cons 2 (cons 10 nil)))"));
		assertEquals("1024", Lisp.eval("(eval (cons ^ (cons 2 (cons 10 nil))))"));
		assertEquals("(+ 10 10)", Lisp.eval("(' (+ 10 10))"));

		assertEquals("a", Lisp.eval("(car (' (a b c)))"));
		assertEquals("(b c)", Lisp.eval("(cdr (' (a b c)))"));
		assertEquals("(a)", Lisp.eval("(car (' ((a) (b) (c))))"));
		assertEquals("((b) (c))", Lisp.eval("(cdr (' ((a) (b) (c))))"));
		assertEquals("a", Lisp.eval("(car (' (a)))"));
		assertEquals("()", Lisp.eval("(cdr (' (a)))"));
		assertEquals("(a b c)", Lisp.eval("(cons (' a) (' (b c)))"));
		assertEquals("((a) (b) (c))", Lisp.eval("(cons (' (a)) (' ((b) (c))))"));
		assertEquals("(a)", Lisp.eval("(cons (' a) (' ()))"));
		assertEquals("(a)", Lisp.eval("(cons (' a) nil)"));
		assertEquals("(a)", Lisp.eval("(cons a nil)"));

		assertEquals("abc", Lisp.eval("(if (= 10 10) abc def)"));
		assertEquals("def", Lisp.eval("(if (= 10 20) abc def)"));
		assertEquals("777", Lisp.eval("(if (atom nil)          777 888)"));
		assertEquals("888", Lisp.eval("(if (atom (cons a nil)) 777 888)"));
		assertEquals("X", Lisp.eval("(if (= a a) X Y)"));
		assertEquals("Y", Lisp.eval("(if (= a b) X Y)"));

		assertEquals("3", Lisp.eval("(length (' (a b c)))"));
		assertEquals("7", Lisp.eval("(size (' (a b c)))"));

		assertEquals("(B A)", Lisp.eval("((' (lambda (x y) (cons y (cons x nil)))) A B)"));
		assertEquals("(B A)", Lisp.eval("((' (lambda (f) (f A B))) (' (lambda (x y) (cons y (cons x nil)))))"));

		assertEquals("24", Lisp.eval("((' (lambda (fact) (fact 4))) (' (lambda (N) (if (= N 0) 1 (* N (fact (- N 1)))))))"));

		// Proofs

		assertEquals("true", Lisp.eval("((' (lambda (member?) (member? 1 (' (1 2 3))))) (' (lambda (e set) (if (atom set) false (if (= e (car set)) true (member? e (cdr set)))))))"));
		assertEquals("false", Lisp.eval("((' (lambda (member?) (member? 4 (' (1 2 3))))) (' (lambda (e set) (if (atom set) false (if (= e (car set)) true (member? e (cdr set)))))))"));

		// Fixed point
		assertEquals("((' x) (' x))", Lisp.eval("((' (lambda (f) (f x))) (' (lambda (x) ((' (lambda (y) (cons y (cons y nil)))) (cons ' (cons x nil)))))))"));

		String ff = "((' (lambda (f) (f f))) (' (lambda (x) ((' (lambda (y) (cons y (cons y nil)))) (cons ' (cons x nil)))))))";
		String evalff = "(eval " + ff + ")";
		assertEquals(Lisp.eval(ff), Lisp.eval(evalff));
		assertEquals("true", Lisp.eval("(= " + Lisp.eval(ff) + " " + Lisp.eval(evalff) + ")"));

		// Goedel
		assertEquals("(is-unprovable (value-of ((' x) (' x))))", Lisp.eval("((' (lambda (g) (g x))) (' (lambda (x) ((' (lambda (L) (L is-unprovable (L value-of (L (L ' x) (L ' x)))))) (' (lambda (x y) (cons x (cons y nil))))))))"));

		String gg = "((' (lambda (g) (g g))) (' (lambda (x) ((' (lambda (L) (L is-unprovable (L value-of (L (L ' x) (L ' x)))))) (' (lambda (x y) (cons x (cons y nil))))))))";
		String cadrcadrgg = "(car (cdr (car (cdr " + Lisp.eval(gg) + "))))";
		String evalcadrcadrgg = "(eval " + cadrcadrgg + ")";
		assertEquals("true", Lisp.eval("(= " + Lisp.eval(cadrcadrgg) + " " + Lisp.eval(evalcadrcadrgg) + ")"));

	}

}