import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShellCommand{
	final String cmd = "ls -lrt";
	int pid = -1;

	public printOutput getStreamWrapper(InputStream is, String type) {
		return new printOutput(is, type);
	}

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		ShellCommand rte = new ShellCommand();
		printOutput errorReported, outputMessage;
	
		try {
		    // Run ls command
		    Process proc = rt.exec("ls -ltra /Library");
			errorReported = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
			outputMessage = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
			errorReported.start();
			outputMessage.start();
		} catch(Exception e) {
		    e.printStackTrace(System.err);
		}
	}

	private class printOutput extends Thread {
		InputStream is = null;
 
		printOutput(InputStream is, String type) {
			this.is = is;
		}
 
		public void run() {
			String s = null;
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				while ((s = br.readLine()) != null) {
					System.out.println(s);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
}