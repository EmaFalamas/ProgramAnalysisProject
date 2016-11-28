

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import models.*;
import business.*;


public class MicroC {

    static FlowGraph flowGraph;
	static FlowGraphBuilder fgBuilder;
	static EquationBuilder eqBuilder;

	public static void main(String args[]) throws Exception {
		long startTime = System.nanoTime();
		if (args.length == 0) {
			System.out.println("Error: No program specified.");
			return;
		}
        MicroCLexer lex = new MicroCLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        MicroCParser parser = new MicroCParser(tokens);
		ParseTree tree = parser.program(); //This command parses the program.

	    ParseTreeWalker walker = new ParseTreeWalker();
		Walker w = new Walker();
		walker.walk(w, tree);

		Node abstractSyntaxTree = w.currentNode;
		fgBuilder = new FlowGraphBuilder(abstractSyntaxTree);
		flowGraph = fgBuilder.constructFlowGraph();

		eqBuilder = new EquationBuilder(flowGraph);
		EquationBuilder.EquationType et = EquationBuilder.EquationType.SIGN_ANALYSIS;
		eqBuilder.buildEquation(et);
		EquationSolver eqSolver = new EquationSolver(flowGraph, Worklist.WorklistType.LIFO);
		System.out.println("-- Solving " + et + " using a " + eqSolver.getWorklistType() + " data structure");
		eqSolver.solveEquation(et, eqBuilder);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;
		System.out.println("_____________ Execution time: " + duration + " milliseconds _____________");
	}

}
