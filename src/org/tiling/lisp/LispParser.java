package org.tiling.lisp;

import java.util.StringTokenizer;

public class LispParser {

	StringTokenizer tokenizer;

	public SExpression parse(String s) {
		tokenizer = new StringTokenizer(s.trim(), " ()", true);
		return get();
	}

	SExpression get() {
		String next = nextToken();
		SExpression e = Atom.makeAtom(next);
		if (e == Atom.LEFT_PARENTHESIS) {
			return getList();
		}
		return e;
	}

	SExpression getList() {
		SExpression v = get();
		if (v == Atom.RIGHT_PARENTHESIS) {
			return Atom.NIL;
		}
		SExpression w = getList();
		return v.cons(w);
	}

	String nextToken() {
		String next;
		do {
			next = tokenizer.nextToken();
		} while (next.equals(" "));
		return next;
	}

}