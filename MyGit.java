import java.util.*;
import java.io.*;

/**
* MyGit class, alternative git command interpreter
* @author Ronan M
* @version 1.0
*/
public class MyGit {
  ProcessBuilder builder = new ProcessBuilder();
  HashMap<String, Integer> files = new HashMap<String, Integer>();

  /**
  * Constructor for Builder class, initialised as empty string
  */
  public MyGit() {
    this.files = files;
  }

  /**
  * Runs command(s) given
  * @param command string which will interpreted
  */
  public void runCommand(String command) throws IOException {
    Process pr = Runtime.getRuntime().exec(command);

    BufferedReader stdInput = new BufferedReader(new
     InputStreamReader(pr.getInputStream()));

    String s = null;
    while ((s = stdInput.readLine()) != null) {
       System.out.println(s);
    }
  }

  /**
  * Initialises git repository
  */
  public void init() throws IOException {
    runCommand("git init");
  }

  /**
  * Adds file to index
  * @param name filename of file to be added
  */
  public void add(String name) throws IOException {
    String versionString = String.format("%s-Version%d", name, this.files.get(name));
    String addCommand = String.format("git add %s", versionString);
    runCommand(addCommand);
  }

  /**
  * Commits staged files to local repository
  */
  public void commit() throws IOException {
    runCommand("git commit -m new_commit");
  }

  /**
  * Creates file with a version of 1
  * @param name filename of file to be created
  */
  public void create(String name) throws IOException{
    this.files.put(name, 1);
    String versionString = String.format("%s-Version%d", name, this.files.get(name));
    String createCommand = String.format("touch %s", versionString);
    runCommand(createCommand);
  }

  /**
  * Update file and its version
  * @param name filename of file to be updated 
  */
  public void touch(String name) throws IOException{
    String oldVersionString = String.format("%s-Version%d", name, this.files.get(name));
    this.files.put(name, this.files.get(name) + 1);
    String newVersionString = String.format("%s-Version%d", name, this.files.get(name));
    String renameString = String.format("git mv %s %s", oldVersionString, newVersionString);
    runCommand(renameString);
  }

  public static void main(String[] args) throws IOException {
    MyGit mygit = new MyGit();
    mygit.runCommand("chmod -R 777 .git");
    mygit.runCommand("rm -r .git");
    for (int i = 0; i < args.length; ++i) {
      if (args[i].equals("init")) {
        mygit.init();
      } else if (args[i].equals("add")) {
        i++;
        mygit.add(args[i]);
      } else if (args[i].equals("commit")) {
        mygit.commit();
      } else if (args[i].equals("create")) {
        i++;
        mygit.create(args[i]);
      } else if (args[i].equals("touch")) {
        i++;
        mygit.touch(args[i]);
      } else {
        System.out.println("Incorrect argument.");
      }
    }

    System.out.println("Commits:\n");
    mygit.runCommand("git log");
    System.out.println("\n");
    System.out.println("Index:\n");
    mygit.runCommand("git ls-files --stage");
    System.out.println("\n");
    System.out.println("Working Tree:\n");
    mygit.runCommand("git status");
  }
}
