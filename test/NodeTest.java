import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    private Node node;

    @Before
    public void setUp(){
        node = new Tree("root");
        node.depth=1;
        node.parent=null;
    }

    @Test
    public void getPath() {
        String [] result = node.getPath();
        assertEquals(result.length , 1);
        assertEquals(result[0] , "root");

    }
}