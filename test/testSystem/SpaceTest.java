package testSystem;

import system.FileSystem;
import Stubs.LeafStub;
import Stubs.SpaceStub;
import Stubs.TreeStub;
import org.junit.Before;
import org.junit.Test;
import system.*;

import static org.junit.Assert.*;

public class SpaceTest {

    private Space space;
    private LeafStub leafStub;
    private TreeStub parentOfLeafStub ;
    private Stubs.SpaceStub SpaceStub;
    public static final int SIZE = 10;


    @Before
    public void setUp() throws OutOfSpaceException {
        space = new Space(SIZE);
        SpaceStub = new SpaceStub(SIZE);
        FileSystem.fileStorage = SpaceStub;
        parentOfLeafStub = new TreeStub("A");
        leafStub = new LeafStub("Aa", 2);
        leafStub.parent=parentOfLeafStub;

    }


    @Test
    public void allocGood() throws OutOfSpaceException{
        int filesize = 2;
        space.Alloc(filesize, leafStub);
        assertEquals(leafStub.allocations.length, filesize);
        assertEquals(space.countFreeSpace(), SIZE - filesize);
        Leaf[] allocs = space.getAlloc();
        for (int i =0; i<leafStub.allocations.length; i++) {
            assertEquals(allocs[leafStub.allocations[i]], leafStub);
        }
    }

    @Test
    public void allocTooBig() throws OutOfSpaceException{
       Exception exception = null;
        int filesize = 11;
        try {
            space.Alloc(filesize, leafStub);
        }
        catch (Exception e){
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(exception.getClass(), OutOfSpaceException.class);
    }

    @Test
    public void dealloc() throws Exception {
        int prevSpace = space.countFreeSpace();
        space.Alloc(2, leafStub);
        space.Dealloc(leafStub);
        assertEquals(prevSpace, space.countFreeSpace());
        assertFalse(leafStub.parent.children.containsKey("Aa"));
    }

    @Test
    public void countFreeSpace() throws Exception{
        assertEquals(space.countFreeSpace(), SIZE);
        int size=2;
        space.Alloc(size, leafStub);
        assertEquals(SIZE-size, space.countFreeSpace());
    }

    @Test
    public void getAlloc() throws Exception{
        int filesize = 2;
        space.Alloc(filesize, leafStub);
        int counter = 0;
        Leaf[] allocs = space.getAlloc();
        for (Leaf l: allocs){
            if (l!=null)
                counter++;
        }
        assertEquals(SIZE-space.countFreeSpace(),counter);

    }
}