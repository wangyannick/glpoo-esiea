package musichub.logger;

import java.util.*;
import java.io.*;
import java.sql.Timestamp;

public class SingletonFileLogger implements ILogger {
	private static SingletonFileLogger logger = null;

	private SingletonFileLogger() {
	}

	public static synchronized SingletonFileLogger getInstance() {
		if (logger == null)
			logger = new SingletonFileLogger();
		return logger;
	}

	private static final String DIR = System.getProperty("user.dir");
	private static final String LOGS_FILE_PATH = DIR + "\\log.txt";

	public void write(Level l, String error) {
		writeToFile(l, error, LOGS_FILE_PATH);
	}

	private void writeToFile(Level l, String error, String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write("[" + new Timestamp(new Date().getTime()).toString() + "] - " + l + " - " + error);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}