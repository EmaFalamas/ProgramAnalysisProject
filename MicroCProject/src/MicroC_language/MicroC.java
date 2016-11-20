

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
	static EquationBuilder eqBuilderRD;

	public static void main(String args[]) throws Exception {
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
		eqBuilderRD = new EquationBuilder(flowGraph);

		eqBuilderRD.buildEquation(EquationBuilder.EquationType.REACHING_DEFINITIONS);
		//eqBuilder.buildEquation(EquationBuilder.EquationType.SIGN_ANALYSIS);

		EquationSolver eqSolver = new EquationSolver(flowGraph, Worklist.WorklistType.LIFO);
		eqSolver.solveEquation(EquationBuilder.EquationType.REACHING_DEFINITIONS,
				eqBuilderRD.getInEquations(), eqBuilderRD.getOutEquations());
	}

}
