import org.junit.Test;
import system.FileSystem;
import system.Leaf;
import system.OutOfSpaceException;
import system.SpaceStub;
import static junit.framework.TestCase.assertEquals;

public class LeafTest {

    private system.SpaceStub SpaceStub;
    private Leaf leaf;

    @Test
    public void setUpHaveSpace() throws OutOfSpaceException {
        int initialSize = 5;
        SpaceStub = new SpaceStub(initialSize);
        FileSystem.fileStorage = SpaceStub;
        int size = 2;
        leaf = new Leaf("Ab", size);
        assertEquals(SpaceStub.countFreeSpace(), initialSize-size);
    }

    @Test (expected = NullPointerException.class)   //supposed to be outOfSpace?
    public void setUpNoSpace() throws Exception {
        int initialSize = 5;
        SpaceStub = new SpaceStub(initialSize);
        FileSystem.fileStorage = SpaceStub;
        int size = 10;
        leaf = new Leaf("Ac", size);
    }


}