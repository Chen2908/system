import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileSystemTest {

    public FileSystem fileSystem;

    @Before
    public void initialize(){
        fileSystem = new FileSystem(50);
    }

    @Test
    public void dir() {
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
    public void testDir() throws Exception{
        String [] path = new String[]{};

        fileSystem.dir(path);
    }

    @Test
    public void disk() {
    }

    @Test
    public void file() {
    }

    @Test
    public void lsdir() {
    }

    @Test
    public void rmfile() {
    }

    @Test
    public void rmdir() {
    }

    @Test
    public void fileExists() {
    }

    @Test
    public void dirExists() {
    }
}