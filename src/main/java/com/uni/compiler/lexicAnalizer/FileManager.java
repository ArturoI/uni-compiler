package com.uni.compiler.lexicAnalizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

	private BufferedReader source;
        private int lineNumber = 1;
        public boolean hasMore = true;
        private boolean hasLastChar = true;
        
	public FileManager(String path) {
            try {
                hasMore = true;
                hasLastChar = true;
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
            if (hasLastChar){
                hasLastChar = false;
                return '\n';
            }
            hasMore = false;
            return null;
	}
        
        public boolean hasMoreElement(){
            return this.hasMore;
        }

	public void write() {

	}
        
        public void close() throws IOException{ source.close(); }

	public void open(String filePath) {

	}

}
