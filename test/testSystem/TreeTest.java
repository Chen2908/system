package testSystem;

import org.junit.Before;
import org.junit.Test;
import system.Tree;

import static org.junit.Assert.*;

public class TreeTest {

    Tree tree;

    @Before
    public void setUp() {
        tree = new Tree("root");
    }


    @Test
    public void getChildByNameHaveChild() {
        Tree child = tree.GetChildByName("A");
        Tree result = tree.GetChildByName("A");
        assertEquals(result, child);
    }

    @Test
    public void getChildByNamenoChild() {
        Tree result = tree.GetChildByName("B");
        assertEquals(tree.children.get("B"), result);
        assertEquals(result.parent, tree);
    }

    @Test
    public void getPathRoot() {
        Tree child = tree.GetChildByName("A");
        String[] result = child.getPath();
        assertEquals(result.length , 1);
        assertEquals(result[0] , "A");

    }
}