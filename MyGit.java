import java.util.*;
import java.io.*;

public class MyGit {
  ProcessBuilder builder = new ProcessBuilder();
  HashMap<String, Integer> files = new HashMap<String, Integer>();

  public MyGit() {
    this.files = files;
  }

  public void runCommand(String command) throws IOException {
    Process pr = Runtime.getRuntime().exec(command);

    BufferedReader stdInput = new BufferedReader(new
     InputStreamReader(pr.getInputStream()));

    String s = null;
    while ((s = stdInput.readLine()) != null) {
       System.out.println(s);
    }
  }

  public void init() throws IOException {
    runCommand("git init");
  }

  public void add(String name) throws IOException {
    String addCommand = String.format("git add %s", name);
    runCommand(addCommand);
  }

  public void commit() throws IOException {
    runCommand("git commit -m new_commit");
  }

  public void create(String name) throws IOException{
    String createCommand = String.format("touch %s", name);
    runCommand(createCommand);
    this.files.put(name, 0);
  }

  public void touch(String name) {
    this.files.put(name, this.files.get(name) + 1);
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
