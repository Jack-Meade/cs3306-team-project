import org.junit.Test;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import java.io.*;
// Conor McDonald 117445926

public class JUnit {
  MyGit gitTest = new MyGit();

  @Test(timeout = 1000)
  public void testInit() throws IOException {
    gitTest.init();
    File file = new File(".git");
    assertTrue(file.exists());
  }

  @Test(timeout = 1000)
  public void testRunCommand() throws IOException{
    gitTest.runCommand("touch Newfile.txt-Version1");
    File file = new File("Newfile.txt-Version1");
    assertTrue(file.exists());
  }

  @Test(timeout = 10000)
  public void testAdd() throws IOException, InterruptedException{
    gitTest.runCommand("git reset --hard HEAD");
    gitTest.runCommand("touch test.txt");
    gitTest.runCommand("git add test.txt");
    gitTest.runCommand("ls");
    gitTest.runCommand("git status");
    Process p = Runtime.getRuntime().exec("git diff --cached --exit-code");
    p.waitFor();
    // 1 means that there are changes that are not committed i.e successful add
    if (p.exitValue() == 1) {
      assertTrue(true);
    }
    else{
      assertFalse(true);
    }
  }

  @Test(timeout = 1000)
  public void testTouch() throws IOException{
    gitTest.create("Newfile.txt");
    gitTest.add("Newfile.txt");
    gitTest.touch("Newfile.txt");
    File file = new File("Newfile.txt-Version2");
    assertTrue(file.exists());
  }
  @Test(timeout = 1000)
  public void testTouch2() throws IOException{
    gitTest.create("Newfile.txt");
    gitTest.touch("Newfile.txt");
    File file = new File("Newfile.txt-Version1");
    assertFalse(file.exists());
  }

  @Test(timeout = 10000)
  public void testAdd2() throws IOException, InterruptedException{
    gitTest.create("/wdwdw/");
    gitTest.add("/wdwdw/");
    // If no file is created/added there should be no changes i.e exit 0
    gitTest.commit();
    Process f = Runtime.getRuntime().exec("git reset --hard HEAD");
    f.waitFor();

    //gitTest.runCommand("git reset --hard HEAD");
    Process p = Runtime.getRuntime().exec("git diff --exit-code");
    p.waitFor();

    System.out.println(p.exitValue());
    if (p.exitValue() == 0) {
      assertTrue(true);
    }
    else{
      assertFalse(true);
    }
  }

  @Test(timeout = 1000)
  public void testCommit() throws IOException, InterruptedException{
    gitTest.create("Newfile.txt");
    gitTest.add("Newfile.txt");
    gitTest.commit();
    Process p = Runtime.getRuntime().exec("git diff --cached --exit-code");
    p.waitFor();
    if (p.exitValue() == 0) {
      assertTrue(true);
    }
    else{
      assertFalse(true);
    }
  }
  @Test(timeout = 1000)
  public void testCreate() throws IOException{
    gitTest.create("Newfile.txt");
    File file = new File("Newfile.txt-Version1");
    assertTrue(file.exists());
  }

  @Test(timeout = 1000)
  public void testCreate2() throws IOException{
    gitTest.create("wfwfwf/efefe");
    File file = new File("wfwfwf/efefe");
    assertFalse(file.exists());
  }

  @Before
  public void prep() throws IOException{
    gitTest.runCommand("git reset --hard HEAD");
    gitTest.runCommand("git add .");
    gitTest.runCommand("rm Newfile.txt-Version1");
    gitTest.runCommand("rm Newfile.txt-Version2");
  }
  @After
  public void cleanup() throws IOException {
    gitTest.runCommand("rm Newfile.txt-Version1");
    gitTest.runCommand("rm Newfile.txt-Version2");
    gitTest.runCommand("git reset --hard HEAD");
  }
}
