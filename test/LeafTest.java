import org.junit.Test;
import static org.junit.Assert.*;

public class LeafTest {

    private stubSpace stubSpace;
    private Leaf leaf;

    @Test
    public void setUpHaveSpace() throws OutOfSpaceException {
        int initialSize = 5;
        stubSpace = new stubSpace(initialSize);
        FileSystem.fileStorage = stubSpace;
        int size = 2;
        leaf = new Leaf("Ab", size);
        assertEquals(stubSpace.countFreeSpace(), initialSize-size);
    }

    @Test (expected = NullPointerException.class)   //supposed to be outOfSpace?
    public void setUpNoSpace() throws Exception {
        int initialSize = 5;
        stubSpace = new stubSpace(initialSize);
        FileSystem.fileStorage = stubSpace;
        int size = 10;
        leaf = new Leaf("Ac", size);
    }


}