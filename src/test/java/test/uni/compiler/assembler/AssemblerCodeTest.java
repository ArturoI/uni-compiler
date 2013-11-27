package test.uni.compiler.assembler;

import java.util.ArrayList;

//import org.junit.Test;

import com.uni.compiler.assembler.AssemblerCode;
import com.uni.compiler.parser.Terceto;

public class AssemblerCodeTest {

//	@Test
	public void test() {
		ArrayList<Terceto> tercetoList = new ArrayList<Terceto>();
		Terceto t1 = new Terceto("ADD", 10, 20, null);
		t1.setAsseblerResult(2);
		Terceto t2 = new Terceto("ADD", t1, 40, null);
		Terceto t3 = new Terceto("ADD", 50, t1, null);
		Terceto t5 = new Terceto("ADD", 50, 60, null);
		t5.setAsseblerResult(1);
		Terceto t4 = new Terceto("ADD", t1, t5, null);

		tercetoList.add(t5);
		//AssemblerCode myCode = new AssemblerCode(tercetoList);
		//myCode.getAssemblerCode(tercetoList);

	}

}
