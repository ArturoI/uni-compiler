package test.uni.compiler.LexicAnalizer;

import java.io.IOException;

//import org.junit.Test;
//just a test
import com.uni.compiler.lexicAnalizer.LexicAnalizer;
import com.uni.compiler.lexicAnalizer.Token;

public class LexicAnallizerTest {
	private LexicAnalizer analizer;

//	@Test
	public void test() throws IOException {
		analizer = new LexicAnalizer(
				"/Users/emn/workspace/uni-compiler/testFiles/FirstTest.txt", null);
		Token lastToken;
		try {
			while ((lastToken = analizer.getToken()) != null) {
				System.out.println("Token processed:  " + lastToken.getType()
						+ " Lexema: " + lastToken.getToken());
			}
		} catch (Exception e) {
			System.out.println("An exception was trhown \n - Cause: " + e.getCause()
                                           + "\n Message: " + e.getMessage());
		}
	}

}
