package org.tiling.lisp;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

public class Atom {

	static HashMap atoms = new HashMap();

	public static final SExpression NIL = makeAtom("");
	public static final SExpression NIL2 = makeAtom("nil");

	public static final SExpression LEFT_PARENTHESIS = makeAtom("(");
	public static final SExpression RIGHT_PARENTHESIS = makeAtom(")");

	public static final SExpression QUOTE = makeAtom("'");

	public static final SExpression IF = makeAtom("if");

	public static final SExpression LAMBDA = makeAtom("lambda");

	public static final SExpression TRUE = makeAtom("true");
	public static final SExpression FALSE = makeAtom("false");

	public static final SExpression CAR = makeAtom("car");
	public static final SExpression CDR = makeAtom("cdr");
	public static final SExpression CONS = makeAtom("cons");

	public static final SExpression ATOM = makeAtom("atom");
	public static final SExpression EQUALS = makeAtom("=");

	public static final SExpression PLUS = makeAtom("+");
	public static final SExpression MINUS = makeAtom("-");
	public static final SExpression TIMES = makeAtom("*");
	public static final SExpression TO_THE_POWER = makeAtom("^");

	public static final SExpression LESS_THAN_OR_EQUAL_TO = makeAtom("<=");
	public static final SExpression GREATER_THAN_OR_EQUAL_TO = makeAtom(">=");
	public static final SExpression LESS_THAN = makeAtom("<");
	public static final SExpression GREATER_THAN = makeAtom(">");

	public static final SExpression SIZE = makeAtom("size");
	public static final SExpression LENGTH = makeAtom("length");

	public static final SExpression EVAL = makeAtom("eval");

//	public static final SExpression DISPLAY = makeAtom("display");

	public static SExpression makeAtom(String s) {
		SExpression atom = (SExpression) atoms.get(s);
		if (atom == null) {
			if (isNumber(s)) {
				atom = new SExpression(new BigInteger(s));
			} else {
				atom = new SExpression(s);
				atoms.put(s, atom);
			}
		}
		return atom;
	}

	static boolean isNumber(String s) {
		if (s.length() == 0) { // NIL case
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static void refreshBindings() {
		for (Iterator i = atoms.values().iterator(); i.hasNext(); ) {
			SExpression atom = (SExpression) i.next();
			atom.bind(atom);
		}
		Atom.NIL2.unbind();
		Atom.NIL2.bind(Atom.NIL);
	}

	public static void restoreBindings() {
		for (Iterator i = atoms.values().iterator(); i.hasNext(); ) {
			SExpression atom = (SExpression) i.next();
			atom.unbind();
			if (!atom.isBound()) {
				atom.bind(atom);
			}
		}
	}

}