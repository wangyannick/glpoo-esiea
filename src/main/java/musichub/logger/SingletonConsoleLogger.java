package main.java.musichub.logger;

import java.util.*;
import java.sql.Timestamp;

public class SingletonConsoleLogger implements ILogger {
	private static SingletonConsoleLogger logger = null;

	private SingletonConsoleLogger() {
	}

	public static synchronized SingletonConsoleLogger getInstance() {
		if (logger == null)
			logger = new SingletonConsoleLogger();
		return logger;
	}

	public void write(Level l, String error) {
		writeToConsole(l, error);
	}

	private void writeToConsole(Level l, String error) {
		System.out.println("[" + new Timestamp(new Date().getTime()).toString() + "] - " + l + " - " + error);
	}

}