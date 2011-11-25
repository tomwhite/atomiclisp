package org.tiling.lisp;

import java.math.BigInteger;

public class LispInterpreter {

	public LispInterpreter() {
		Atom.NIL2.bind(Atom.NIL);
	}

	public SExpression eval(SExpression e) {

		if (e.isAtom()) {
			return e.getValue();
		}

		SExpression function = eval(e.car());

		if (function == Atom.QUOTE) {
			return e.cadr();
		}

		if (function == Atom.IF) {
			SExpression predicate = eval(e.cadr());
			return predicate == Atom.FALSE ? eval(e.cadddr()) : eval(e.caddr());
		}

		SExpression args = evalList(e.cdr());
		SExpression arg1 = args.car();
		SExpression arg2 = args.cadr();

		// One argument

		if (function == Atom.SIZE) {
			return arg1.size();
		}

		if (function == Atom.LENGTH) {
			return arg1.length();
		}

		if (function == Atom.ATOM) {
			return arg1.isAtom() ? Atom.TRUE : Atom.FALSE;
		}

		if (function == Atom.CAR) {
			return arg1.car();
		}

		if (function == Atom.CDR) {
			return arg1.cdr();
		}

		// Two arguments

		if (function == Atom.CONS) {
			return arg1.cons(arg2);
		}

		if (function == Atom.PLUS) {
			return new SExpression(arg1.nValue.add(arg2.nValue));
		}

		if (function == Atom.MINUS) {
			return new SExpression(BigInteger.valueOf(0).max(arg1.nValue.subtract(arg2.nValue)));
		}

		if (function == Atom.TIMES) {
			return new SExpression(arg1.nValue.multiply(arg2.nValue));
		}

		// N.B. not multi-precision!
		if (function == Atom.TO_THE_POWER) {
			return new SExpression(arg1.nValue.pow(arg2.nValue.intValue()));
		}

		if (function == Atom.EQUALS) {
			return arg1.equals(arg2) ? Atom.TRUE : Atom.FALSE;
		}

		if (function == Atom.LESS_THAN_OR_EQUAL_TO) {
			return arg1.compareTo(arg2) != 1 ? Atom.TRUE : Atom.FALSE;
		}

		if (function == Atom.GREATER_THAN_OR_EQUAL_TO) {
			return arg1.compareTo(arg2) != -1 ? Atom.TRUE : Atom.FALSE;
		}

		if (function == Atom.LESS_THAN) {
			return arg1.compareTo(arg2) == -1 ? Atom.TRUE : Atom.FALSE;
		}

		if (function == Atom.GREATER_THAN) {
			return arg1.compareTo(arg2) == 1 ? Atom.TRUE : Atom.FALSE;
		}

		// Other

		if (function == Atom.EVAL) {
			Atom.refreshBindings();
			SExpression v = eval(arg1);
			Atom.restoreBindings();
			return v;
		}

		// otherwise lambda
		SExpression vars = function.cadr();
		SExpression body = function.caddr();
		bind(vars, args);
		SExpression v = eval(body);
		unbind(vars);
		return v;

	}

	SExpression evalList(SExpression e) {
		if (e.isAtom()) {
			return Atom.NIL;
		}
		SExpression h = eval(e.car());
		SExpression t = evalList(e.cdr());
		return h.cons(t);
	}

	void bind(SExpression vars, SExpression args) {
		if (vars.isAtom()) {
			return;
		}
		bind(vars.cdr(), args.cdr());
		if (vars.car().isAtom() && !vars.car().isNumber()) {
			vars.car().bind(args.car());
		}
	}

	void unbind(SExpression vars) {
		if (vars.isAtom()) {
			return;
		}
		if (vars.car().isAtom() && !vars.car().isNumber()) {
			vars.car().unbind();
		}
		unbind(vars.cdr());
	}

}