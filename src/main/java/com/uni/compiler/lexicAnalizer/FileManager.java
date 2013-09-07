package com.uni.compiler.lexicAnalizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

	private BufferedReader source;

	public FileManager(String path) {
		try {
			source = new BufferedReader(new InputStreamReader(
					new FileInputStream(path)));
		} catch (IOException e) {
			System.out.println("Error File not found" + e);
		}
	}

	public String readLine() {
		String line = "";
		try {
			line = source.readLine();
		} catch (IOException e) {
			System.out.println("Error reading the line" + e);
		}
		return line;
	}

	public Character readChar() throws IOException {
		int character;
		while ((character = source.read()) != -1) {
			return (char) character;
		}
		return null;
	}

	public void write() {

	}

	public void open(String filePath) {

	}

}
