package models;

import java.util.ArrayList;

/**
 * Created by Ema on 24/9/2016.
 */
public class FlowGraph {
    private ArrayList<Block> blocks;

    public FlowGraph()
    {
        blocks = new Arraylist<int>();
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public addBlock(Block block)
    {
        blocks.add(block);
    }
}
