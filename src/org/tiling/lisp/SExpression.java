package org.tiling.lisp;

import java.math.BigInteger;
import java.util.Stack;

public class SExpression implements Comparable {

	SExpression car;
	SExpression cdr;

	boolean atom;
	boolean number;

	String printValue;
	BigInteger nValue;

	Stack stack;

	public SExpression(String s) {
		car = this;
		cdr = this;
		atom = true;
		number = false;
		printValue = new String(s);
		nValue = BigInteger.valueOf(0);
		stack = new Stack();
		bind(this);
	}

	public SExpression(BigInteger n) {
		car = this;
		cdr = this;
		atom = true;
		number = true;
		printValue = n.toString();
		nValue = n;
		stack = new Stack();
		bind(this);
	}

	public SExpression(SExpression h, SExpression t) {
		car = h;
		cdr = t;
		atom = false;
		number = false;
		printValue = new String("");
		nValue = BigInteger.valueOf(0);
		stack = null;
	}

	public SExpression cons(SExpression t) {
		if (t.isAtom() && t != Atom.NIL) {
			return this;
		}
		return new SExpression(this, t);
	}

	public SExpression car() {
		return car;
	}

	public SExpression cdr() {
		return cdr;
	}

	public SExpression cadr() {
		return cdr.car;
	}

	public SExpression caddr() {
		return cdr.cdr.car;
	}

	public SExpression cadddr() {
		return cdr.cdr.cdr.car;
	}

	public SExpression length() {
		long l = 0;
		SExpression s = this;
		while (!s.isAtom()) {
			l++;
			s = s.cdr();
		}
		return new SExpression(BigInteger.valueOf(l));
	}

	public SExpression size() {
		return new SExpression(BigInteger.valueOf(toString().length()));
	}

	public boolean isAtom() {
		return atom;
	}

	public boolean isNumber() {
		return number;
	}

	public boolean isBound() {
		return !stack.isEmpty();
	}

	public void bind(SExpression s) {
		stack.push(s);
	}

	public SExpression getValue() {
		return (SExpression) stack.peek();
	}

	public void unbind() {
		stack.pop();
	}

	public boolean equals(Object o) {
		if (o instanceof SExpression) {
			SExpression s = (SExpression) o;
			if (number && s.number) {
				return nValue.equals(s.nValue);
			}
			if (number || s.number) {
				return false;
			}
			if (atom && s.atom) {
				return this == s;
			}
			if (atom || s.atom) {
				return false;
			}
			return car.equals(s.car) && cdr.equals(s.cdr);
		} else {
			return false;
		}
	}

	public String toString() {
		sb = new StringBuffer();
		toS(this);
		return sb.toString();
	}

	StringBuffer sb;

	void toS(SExpression s) {
		if (s.isAtom() && !s.printValue.equals("")) {
			sb.append(s.printValue);
			return;
		}
		sb.append('(');
		while (!s.isAtom()) {
			toS(s.car);
			s = s.cdr;
			if (!s.isAtom()) {
				sb.append(' ');
			}
		}
		sb.append(')');
	}

	public int compareTo(Object o) {
		SExpression s = (SExpression) o;
		return nValue.compareTo(s.nValue);
	}

}