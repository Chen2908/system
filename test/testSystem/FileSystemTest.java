package testSystem;

import org.junit.Before;
import org.junit.Test;
import system.BadFileNameException;
import system.FileSystem;
import system.OutOfSpaceException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FileSystemTest {

    private FileSystem fileSystem;

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


    @Test
    public void testDirGoodName() throws Exception{
        String [] path = new String[]{"root", "A", "B", "Bb"};
        fileSystem.dir(path);
        assertNotNull(fileSystem.DirExists(path));
    }

    @Test
    public void testDirDupName() throws Exception{
        String [] path = new String[]{"root", "A", "B"};
        fileSystem.dir(path);  //create dir, no exception
        String [] path2 = new String[]{"root", "A", "B"};
        fileSystem.dir(path2);   // duplicate. should do nothing
        assertNotNull(fileSystem.DirExists(path));
    }

    @Test
    public void disk() throws Exception{
        String [] path = new String[]{"root", "A", "Aa"};
        int k = 11;
        fileSystem.file(path, k); //create file, no exception

        String [] path2 = new String[]{"root", "A", "Ab"};
        int k2 = 5;
        fileSystem.file(path2, k2); //create file, no exception

        String [] path3 = new String[]{"root", "A", "Ac"};
        int k3 = 2;
        fileSystem.file(path3, k3); //create file, no exception

        fileSystem.rmfile(path2);  //remove Ab file

        String [] path4 = new String[]{"root", "A", "Ad"};
        int k4 = 4;
        fileSystem.file(path4, k4); //create file, no exception

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
    public void testFileRoot() throws Exception{
        String [] path = new String[]{"root", "a"};
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        int k = 20;
        fileSystem.file(path, k); //create file, no exception
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage-k);
        assertNotNull(fileSystem.FileExists(path));

    }

    @Test
    public void testFileTooLargeFileExistsNoDelete() throws Exception{
        Exception exception = null;
        String [] path = new String[]{"root", "A", "Aa"};
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        int k = 20;
        try {
            fileSystem.file(path, k); //create file, no exception
            k = 21;
            fileSystem.file(path, k); // try to create file, not big, will fall
        }
        catch (Exception e){
            exception=e;
        }
        assertNotNull(exception);
        assertEquals(OutOfSpaceException.class, exception.getClass());
    }

    @Test
    public void testFileTooLargeFileExists() throws Exception{
        String [] path = new String[]{"root", "A", "Aa"};
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        int k = 3;
        fileSystem.file(path, k); //create file, no exception
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage-k);
        Object leaf = fileSystem.FileExists(path);
        assertNotNull(leaf);
        k = 20;
        fileSystem.file(path, k); // try to create file, replace old file
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage-k);
        Object leaf2 = fileSystem.FileExists(path);
        assertNotNull(leaf2);
        assertNotEquals(leaf, leaf2);
        k = 15;
        fileSystem.file(path, k); // try to create file, replace old file
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage-k);
        Object leaf3 = fileSystem.FileExists(path);
        assertNotNull(leaf3);
        assertNotEquals(leaf2, leaf3);
    }


    @Test
    public void lsdirExist() throws Exception{
        String [] pathroot = new String[]{"root", "A", "Ab"};
        String[] resultroot = fileSystem.lsdir(pathroot);
        assertNull(resultroot);

        String [] files = {"file1", "b", "a file"};
        String [] pathfile1 = new String[]{"root", "A", "file1"};
        String [] pathfile2 = new String[]{"root", "A", "b"};
        String [] pathfile3 = new String[]{"root", "A", "a file"};
        String [] path = new String[]{"root", "A"};
        fileSystem.file(pathfile1, 3);
        fileSystem.file(pathfile2, 3);
        fileSystem.file(pathfile3, 3);
        String[] result = fileSystem.lsdir(path);
        Arrays.sort(files);
        assertNotNull(result);
        assertEquals(files.length, result.length);
        for(int i=0; i<files.length; i++){
            assertEquals(files[i],result[i]);
        }
    }

    @Test
    public void rmfileExists() throws Exception{
        String [] path = new String[]{"root", "A", "Ab"};
        int k = 2;
        int prevFileStorage = FileSystem.fileStorage.countFreeSpace();
        fileSystem.file(path, k); //create file, no exception
        fileSystem.rmfile(path); //remove file
        assertNull(fileSystem.FileExists(path));
        assertEquals(FileSystem.fileStorage.countFreeSpace(), prevFileStorage);
    }

    @Test
    public void rmfileNotExists() throws Exception{
        String [] path = new String[]{"root", "A", "Ab"};
        fileSystem.rmfile(path); //try to remove file
        assertNull(fileSystem.FileExists(path));
    }


    @Test
    public void rmdirExists() throws Exception{
        String [] dirpath = new String[]{"root", "A"};
        fileSystem.dir(dirpath); //create dir, no exception
        fileSystem.rmdir(dirpath); //remove dir, no exception
        assertNull(fileSystem.DirExists(dirpath));
    }

    @Test
    public void rmdirNotExists() throws Exception{
        String [] dirpath = new String[]{"root", "A"};
        fileSystem.rmdir(dirpath); //try to remove dir
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
    public void fileExistsFileOnlyRoot() {
        String [] filepath = {"root"};
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