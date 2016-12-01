

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
		//get the start time of the run
		long startTime = System.nanoTime();
		if (args.length == 0) {
			System.out.println("Error: No program specified.");
			return;
		}
        MicroCLexer lex = new MicroCLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        MicroCParser parser = new MicroCParser(tokens);
		ParseTree tree = parser.program(); //This command parses the program.

		//initialize the walker and build the abstract syntax tree
	    ParseTreeWalker walker = new ParseTreeWalker();
		Walker w = new Walker();
		walker.walk(w, tree);

		//get the abstract syntax tree
		Node abstractSyntaxTree = w.currentNode;
		//initialize the flow graph builder and construct the flow graph
		fgBuilder = new FlowGraphBuilder(abstractSyntaxTree);
		flowGraph = fgBuilder.constructFlowGraph();

		//initialize the equation builder
		eqBuilder = new EquationBuilder(flowGraph);
		EquationBuilder.EquationType et = EquationBuilder.EquationType.SIGN_ANALYSIS;
		//build the equations
		eqBuilder.buildEquation(et);
		//initialize the equation solver
		EquationSolver eqSolver = new EquationSolver(flowGraph, Worklist.WorklistType.LIFO);
		System.out.println("-- Solving " + et + " using a " + eqSolver.getWorklistType() + " data structure");
		//solve the equations
		eqSolver.solveEquation(et, eqBuilder);

		//get the end time of the run
		long endTime = System.nanoTime();
		//calculate the duration of the run
		long duration = (endTime - startTime)/1000000;
		System.out.println("_____________ Execution time: " + duration + " milliseconds _____________");
	}

}
