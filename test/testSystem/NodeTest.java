package testSystem;

import org.junit.Before;
import org.junit.Test;
import system.Node;
import system.Tree;

import static org.junit.Assert.*;

public class NodeTest {

    private Node node1;

    @Before
    public void setUp(){
        node1 = new Tree("root");
        node1.parent=null;
    }

    @Test
    public void getPathRoot() {
        String[] result = node1.getPath();
        assertTrue(result.length==0);
    }

}