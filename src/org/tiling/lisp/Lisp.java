package org.tiling.lisp;

public class Lisp {

	static LispParser parser = new LispParser();
	static LispInterpreter interpreter = new LispInterpreter();

	public static String eval(String s) {
		return interpreter.eval(parser.parse(s)).toString();
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage:\n\tLisp <lisp expression to evaluate>");
			System.exit(1);
		}
		System.out.println(eval(args[0]));
	}

}