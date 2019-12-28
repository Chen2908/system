package testSystem;

import org.junit.Before;
import org.junit.Test;
import system.Tree;
import system.TreeStub;

import static org.junit.Assert.*;

public class TreeTest {

    Tree tree;
    TreeStub fakeTreeChild;

    @Before
    public void setUp() {
        tree = new Tree("root");
        fakeTreeChild = new TreeStub("A");
        fakeTreeChild.parent = tree;
        tree.children.put("A",fakeTreeChild);
    }


    @Test
    public void getChildByNameHaveChild() {
        Tree result = tree.GetChildByName("A");
        assertEquals(result, fakeTreeChild);
    }

    @Test
    public void getChildByNamenoChild() {
        Tree result = tree.GetChildByName("B");
        assertEquals(tree.children.get("B"), result);
        assertEquals(result.parent, tree);
    }
}