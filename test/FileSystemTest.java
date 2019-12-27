import org.junit.Before;
import org.junit.Test;
import java.nio.file.DirectoryNotEmptyException;
import static org.junit.Assert.*;

public class FileSystemTest {

    public FileSystem fileSystem;

    @Before
    public void initialize()
    {
        fileSystem = new FileSystem(20);
    }

    @Test
    public void dirNoRoot() {
        Exception exception = null;
        String [] path = new String[]{"A", "Aa"};
        try{
            fileSystem.dir(path);
        }
        catch(Exception e){
            exception=e;
        }
        assertNotNull(exception);
        assertEquals(BadFileNameException.class, exception.getClass());
    }

    @Test(expected = BadFileNameException.class)
    public void testDirBadName() throws Exception{
        String [] path = new String[]{};
        fileSystem.dir(path);
    }

    @Test
    public void disk() {
        String [][] d = fileSystem.disk();
        assertEquals(d.length, FileSystem.fileStorage.getAlloc().length);
    }

    @Test
    public void fileNull() {
        Exception exception = null;
        String [] fileName = null;
        try{
            fileSystem.file(fileName, 5);
        }
        catch(Exception e){
            exception=e;
        }
        assertNotNull(exception);
        assertEquals(BadFileNameException.class, exception.getClass());
    }

    @Test(expected = OutOfSpaceException.class)
    public void testFileTooLarge() throws Exception{
        String [] path = new String[]{"root", "A", "Aa"};
        fileSystem.file(path, 25);
    }

    @Test
    public void testFileTooLargeFileExists() throws Exception{
        String [] path = new String[]{"root", "A", "Aa"};
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        int k = 20;
        fileSystem.file(path, k); //create file, no exception
        k = 19;
        fileSystem.file(path, k); // try to create file, replace old file
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage-k);
    }

    @Test
    public void lsdir() {
        String [] path = new String[]{"root", "A", "Ab"};
        String[] result = fileSystem.lsdir(path);
        assertNull(result);
    }

    @Test
    public void rmfile() throws Exception{
        String [] path = new String[]{"root", "A", "Ab"};
        int k = 2;
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        fileSystem.file(path, k); //create file, no exception
        fileSystem.rmfile(path); //remove file
        assertNull(fileSystem.FileExists(path));
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage);
    }


    @Test
    public void rmdir() throws Exception{
        String [] dirpath = new String[]{"root", "A"};
        fileSystem.dir(dirpath); //create dir, no exception
        fileSystem.rmdir(dirpath); //remove dir, no exception
        assertNull(fileSystem.DirExists(dirpath));
    }


    @Test (expected = DirectoryNotEmptyException.class)
    public void rmdirFileInDir() throws Exception{
        String [] filepath = new String[]{"root", "A", "Ab"};
        int k = 2;
        fileSystem.file(filepath, k); //create file, no exception
        String [] dirpath = new String[]{"root", "A"};
        fileSystem.rmdir(dirpath);
    }

    @Test
    public void fileExistsFileIsNull() {
        String [] filepath = null;
        assertNull(fileSystem.FileExists(filepath));
    }

    @Test
    public void fileExists() throws Exception{
        String [] filepath = new String[]{"root", "A", "Ab"};
        int k = 2;
        fileSystem.file(filepath, k); //create file, no exception
        assertNotNull(fileSystem.FileExists(filepath));
    }

    @Test
    public void dirExists() throws  Exception{
        String [] dirpath = new String[]{"root", "A", "B", "C"};
        fileSystem.dir(dirpath); //create dir
        assertNotNull(fileSystem.DirExists(dirpath));

    }
}