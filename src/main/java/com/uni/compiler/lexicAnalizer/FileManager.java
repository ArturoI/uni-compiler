package com.uni.compiler.lexicAnalizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

	private BufferedReader source;
        private int lineNumber = 1;
        
	public FileManager(String path) {
		try {
			source = new BufferedReader(new InputStreamReader(
					new FileInputStream(path)));
		} catch (IOException e) {
			System.out.println("Error File not found" + e);
		}
	}

        public int getLineNumber() { return this.lineNumber; }

	public Character readChar() throws IOException {
		int character;
		while ((character = source.read()) != -1) {
                    Character c = (char) character;
                    if (c.equals('\n')){
                        lineNumber++;    
                    }
                    return c;
		}
                source.close();
		return null;
	}

	public void write() {

	}

	public void open(String filePath) {

	}

}
