

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import models.*;


public class MicroC {

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
	}

	public constructFlowGraph(Node abstractSyntaxTree)
	{
		FlowGraph flowGraph = new FlowGraph();


	}
}
