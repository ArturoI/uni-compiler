package com.uni.compiler.lexicAnalizer;

import java.util.HashMap;

import com.uni.compiler.Actions.Action;
import com.uni.compiler.Actions.AssignAction;
import com.uni.compiler.Actions.CommentAction;
import com.uni.compiler.Actions.ConstAction;
import com.uni.compiler.Actions.ConsumeAction;
import com.uni.compiler.Actions.ComparatorAction;
import com.uni.compiler.Actions.DeleteLastCharAction;
import com.uni.compiler.Actions.EmptyAction;
import com.uni.compiler.Actions.ErrorAction;
import com.uni.compiler.Actions.IdAction;
import com.uni.compiler.Actions.InvalidCharacterAction;
import com.uni.compiler.Actions.NCComparatorAction;
import com.uni.compiler.Actions.NegativeConstAction;
import com.uni.compiler.Actions.NoConsumeAction;
import com.uni.compiler.Actions.OperationAction;
import com.uni.compiler.Actions.NCOperationAction;
import com.uni.compiler.Actions.StringAction;

// Analizer Factory     

public class AnalizerFactory {
	private LexicAnalizer lexicAnalizer;
	private HashMap<String, String> reservedWords = new HashMap<String, String>();

	private static AnalizerFactory analizerFactory;

	// States
	private State s1 = new State("BeginState");
	private State s2 = new State("SecondState");
	private State s3 = new State("ThirdState");
	private State s4 = new State("ForthState");
	private State s5 = new State("FifthState");
	private State s6 = new State("SisthState");
	private State s7 = new State("SeventhState");
	private State s8 = new State("EighthState");
	private State s9 = new State("NinthState");
	private State s10 = new State("TenthState");
        private State s11 = new State("EleventhState");
        private State s12 = new State("TwelvethState");
        
	private State se = new State("EndState");

	// actions
	private Action idAction = null;
	private Action constAction = null;
        private Action negativeConstAction = null;
	private Action stringAction = null;
	private Action emptyAction = null;
	private Action invalidCharacterAction = null;
	private Action operationAction = null;
        private Action ncOperationAction = null;
	private Action consumeAction = null;
        private Action noConsumeAction = null;
	private Action ncComparatorAction = null;
	private Action errorAction = null;
	private Action commentAction = null;
	private Action comparatorAction = null;
        private Action assignAction = null;
        private Action deleteLastCharAction = null;

	public AnalizerFactory(LexicAnalizer la) {
		lexicAnalizer = la;
                
		idAction = new IdAction(lexicAnalizer);
		constAction = new ConstAction(lexicAnalizer);
                negativeConstAction = new NegativeConstAction(lexicAnalizer);
		stringAction = new StringAction(lexicAnalizer);
		emptyAction = new EmptyAction();
		invalidCharacterAction = new InvalidCharacterAction(lexicAnalizer);
                assignAction = new AssignAction(lexicAnalizer);
		operationAction = new OperationAction(lexicAnalizer);
                ncOperationAction = new NCOperationAction(lexicAnalizer);
		consumeAction = new ConsumeAction(lexicAnalizer);
                noConsumeAction = new NoConsumeAction(lexicAnalizer);
		ncComparatorAction = new NCComparatorAction(lexicAnalizer);
		errorAction = new ErrorAction(lexicAnalizer);
		commentAction = new CommentAction(lexicAnalizer);
		comparatorAction = new ComparatorAction(lexicAnalizer);
                deleteLastCharAction = new DeleteLastCharAction(lexicAnalizer);
                

		createEndState();
                createState12();
                createState11();
                createState10();
		createState9();
		createState8();
		createState7();
		createState6();
		createState5();
		createState4();
		createState3();
		createState2();
		createBeginState();

	}

	/*public static void createInstance(LexicAnalizer la) {
		if (analizerFactory == null) {
			synchronized (AnalizerFactory.class) {
				if (analizerFactory == null) {
					analizerFactory = new AnalizerFactory(la);
				}
			}
		}
	}*/

	public HashMap<String, String> createReservedWords() {
		// commons reserved words
		reservedWords.put("if", "if");
		reservedWords.put("else", "else");
		reservedWords.put("then", "then");
		reservedWords.put("begin", "begin");
		reservedWords.put("end", "end");
		reservedWords.put("print", "print");
		reservedWords.put("function", "function");
		reservedWords.put("return", "return");

		// special reserved words
		reservedWords.put("import", "import");
		reservedWords.put("int", "int");
		reservedWords.put("loop", "loop");
		reservedWords.put("until", "until");

		return reservedWords;
	}

        /*
        
	public Action getEmptyAction() {
		return emptyAction;
	}

	public Action getConstAction() {
		return consAction;
	}

	public Action getIdAction() {
		return idAction;
	}

	public Action getStringAction() {
		return stringAction;
	}

	public Action createErrorAction() {
		return errorAction;
	} */

	public State stateMapping(int state) {
		if (state == 1) {
			return s1;
		}
		if (state == 2) {
			return s2;
		}
		if (state == 3) {
			return s3;
		}
		if (state == 4) {
			return s4;
		}
		if (state == 5) {
			return s5;
		}
		if (state == 6) {
			return s6;
		}
		if (state == 7) {
			return s7;
		}
		if (state == 8) {
			return s8;
		}
		if (state == 9) {
			return s9;
		}
                if (state == 10) {
			return s10;
		}
                if (state == 11){
                    return s11;
                }
		if (state == 12) {
			return se;
		}
		return null;
	}

	public void createBeginState() {
		s1.addNextState(new Integer(1), new Cell(s7, consumeAction));
		s1.addNextState(new Integer(2), new Cell(s8, consumeAction));
		s1.addNextState(new Integer(3), new Cell(se, operationAction));
		s1.addNextState(new Integer(4), new Cell(s11, consumeAction));
		s1.addNextState(new Integer(5), new Cell(se, operationAction));
		s1.addNextState(new Integer(6), new Cell(s2, consumeAction));
		s1.addNextState(new Integer(7), new Cell(s3, consumeAction));
		s1.addNextState(new Integer(8), new Cell(s4, consumeAction));
		s1.addNextState(new Integer(9), new Cell(s3, consumeAction));
		s1.addNextState(new Integer(10), new Cell(se, operationAction));
		s1.addNextState(new Integer(11), new Cell(s5, consumeAction));
		s1.addNextState(new Integer(12), new Cell(se, consumeAction));
		s1.addNextState(new Integer(13), new Cell(se, consumeAction));
		s1.addNextState(new Integer(14), new Cell(se, consumeAction));
		s1.addNextState(new Integer(15), new Cell(se, consumeAction));
		s1.addNextState(new Integer(16), new Cell(s9, consumeAction));
		s1.addNextState(new Integer(17), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(18), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(19), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(-1), new Cell(se, invalidCharacterAction));
	}

	public void createState2() {
		s2.addNextState(new Integer(1), new Cell(se, assignAction));
		s2.addNextState(new Integer(2), new Cell(se, assignAction));
		s2.addNextState(new Integer(3), new Cell(se, assignAction));
		s2.addNextState(new Integer(4), new Cell(se, assignAction));
		s2.addNextState(new Integer(5), new Cell(se, assignAction));
		s2.addNextState(new Integer(6), new Cell(se, comparatorAction));
		s2.addNextState(new Integer(7), new Cell(se, assignAction));
		s2.addNextState(new Integer(8), new Cell(se, assignAction));
		s2.addNextState(new Integer(9), new Cell(se, assignAction));
		s2.addNextState(new Integer(10), new Cell(se, assignAction));
		s2.addNextState(new Integer(11), new Cell(se, assignAction));
		s2.addNextState(new Integer(12), new Cell(se, assignAction));
		s2.addNextState(new Integer(13), new Cell(se, assignAction));
		s2.addNextState(new Integer(14), new Cell(se, assignAction));
		s2.addNextState(new Integer(15), new Cell(se, assignAction));
		s2.addNextState(new Integer(16), new Cell(se, assignAction));
		s2.addNextState(new Integer(17), new Cell(se, assignAction));
		s2.addNextState(new Integer(18), new Cell(se, assignAction));
		s2.addNextState(new Integer(19), new Cell(se, assignAction));
		s2.addNextState(new Integer(-1), new Cell(se, assignAction));
	}

	public void createState3() {
		s3.addNextState(new Integer(1), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(2), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(3), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(4), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(5), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(6), new Cell(se, comparatorAction));
		s3.addNextState(new Integer(7), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(8), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(9), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(10), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(11), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(12), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(13), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(14), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(15), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(16), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(17), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(18), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(19), new Cell(se, ncComparatorAction));
		s3.addNextState(new Integer(-1), new Cell(se, ncComparatorAction));
	}

	public void createState4() {
		s4.addNextState(new Integer(1), new Cell(se, errorAction));
		s4.addNextState(new Integer(2), new Cell(se, errorAction));
		s4.addNextState(new Integer(3), new Cell(se, errorAction));
		s4.addNextState(new Integer(4), new Cell(se, errorAction));
		s4.addNextState(new Integer(5), new Cell(se, errorAction));
		s4.addNextState(new Integer(6), new Cell(se, comparatorAction));
		s4.addNextState(new Integer(7), new Cell(se, errorAction));
		s4.addNextState(new Integer(8), new Cell(se, errorAction));
		s4.addNextState(new Integer(9), new Cell(se, errorAction));
		s4.addNextState(new Integer(10), new Cell(se, errorAction));
		s4.addNextState(new Integer(11), new Cell(se, errorAction));
		s4.addNextState(new Integer(12), new Cell(se, errorAction));
		s4.addNextState(new Integer(13), new Cell(se, errorAction));
		s4.addNextState(new Integer(14), new Cell(se, errorAction));
		s4.addNextState(new Integer(15), new Cell(se, errorAction));
		s4.addNextState(new Integer(16), new Cell(se, errorAction));
		s4.addNextState(new Integer(17), new Cell(se, errorAction));
		s4.addNextState(new Integer(18), new Cell(se, errorAction));
		s4.addNextState(new Integer(19), new Cell(se, errorAction));
		s4.addNextState(new Integer(-1), new Cell(se, errorAction));
	}

	public void createState5() {
		s5.addNextState(new Integer(1), new Cell(se, errorAction));
		s5.addNextState(new Integer(2), new Cell(se, errorAction));
		s5.addNextState(new Integer(3), new Cell(se, errorAction));
		s5.addNextState(new Integer(4), new Cell(se, errorAction));
		s5.addNextState(new Integer(5), new Cell(se, errorAction));
		s5.addNextState(new Integer(6), new Cell(se, errorAction));
		s5.addNextState(new Integer(7), new Cell(se, errorAction));
		s5.addNextState(new Integer(8), new Cell(se, errorAction));
		s5.addNextState(new Integer(9), new Cell(se, errorAction));
		s5.addNextState(new Integer(10), new Cell(se, errorAction));
		s5.addNextState(new Integer(11), new Cell(s6, consumeAction));
		s5.addNextState(new Integer(12), new Cell(se, errorAction));
		s5.addNextState(new Integer(13), new Cell(se, errorAction));
		s5.addNextState(new Integer(14), new Cell(se, errorAction));
		s5.addNextState(new Integer(15), new Cell(se, errorAction));
		s5.addNextState(new Integer(16), new Cell(se, errorAction));
		s5.addNextState(new Integer(17), new Cell(se, errorAction));
		s5.addNextState(new Integer(18), new Cell(se, errorAction));
		s5.addNextState(new Integer(19), new Cell(se, errorAction));
		s5.addNextState(new Integer(-1), new Cell(se, errorAction));
	}

	public void createState6() {
		s6.addNextState(new Integer(1), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(2), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(3), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(4), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(5), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(6), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(7), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(8), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(9), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(10), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(11), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(12), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(13), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(14), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(15), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(16), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(17), new Cell(se, commentAction));
		s6.addNextState(new Integer(18), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(19), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(-1), new Cell(s6, consumeAction));
	}

	public void createState7() {
		s7.addNextState(new Integer(1), new Cell(s7, consumeAction));
		s7.addNextState(new Integer(2), new Cell(s7, consumeAction));
		s7.addNextState(new Integer(3), new Cell(se, idAction));
		s7.addNextState(new Integer(4), new Cell(se, idAction));
		s7.addNextState(new Integer(5), new Cell(se, idAction));
		s7.addNextState(new Integer(6), new Cell(se, idAction));
		s7.addNextState(new Integer(7), new Cell(se, idAction));
		s7.addNextState(new Integer(8), new Cell(se, idAction));
		s7.addNextState(new Integer(9), new Cell(se, idAction));
		s7.addNextState(new Integer(10), new Cell(se, idAction));
		s7.addNextState(new Integer(11), new Cell(se, idAction));
		s7.addNextState(new Integer(12), new Cell(se, idAction));
		s7.addNextState(new Integer(13), new Cell(se, idAction));
		s7.addNextState(new Integer(14), new Cell(se, idAction));
		s7.addNextState(new Integer(15), new Cell(se, idAction));
		s7.addNextState(new Integer(16), new Cell(se, idAction));
		s7.addNextState(new Integer(17), new Cell(se, idAction));// deleteAction
		s7.addNextState(new Integer(18), new Cell(se, idAction));
		s7.addNextState(new Integer(19), new Cell(se, idAction));
		s7.addNextState(new Integer(-1), new Cell(se, idAction));
	}

	public void createState8() {
		s8.addNextState(new Integer(1), new Cell(se, constAction));
		s8.addNextState(new Integer(2), new Cell(s8, consumeAction));
		s8.addNextState(new Integer(3), new Cell(se, constAction));
		s8.addNextState(new Integer(4), new Cell(se, constAction));
		s8.addNextState(new Integer(5), new Cell(se, constAction));
		s8.addNextState(new Integer(6), new Cell(se, constAction));
		s8.addNextState(new Integer(7), new Cell(se, constAction));
		s8.addNextState(new Integer(8), new Cell(se, constAction));
		s8.addNextState(new Integer(9), new Cell(se, constAction));
		s8.addNextState(new Integer(10), new Cell(se, constAction));
		s8.addNextState(new Integer(11), new Cell(se, constAction));
		s8.addNextState(new Integer(12), new Cell(se, constAction));
		s8.addNextState(new Integer(13), new Cell(se, constAction));
		s8.addNextState(new Integer(14), new Cell(se, constAction));
		s8.addNextState(new Integer(15), new Cell(se, constAction));
		s8.addNextState(new Integer(16), new Cell(se, constAction));
		s8.addNextState(new Integer(17), new Cell(se, constAction));
		s8.addNextState(new Integer(18), new Cell(se, constAction));
		s8.addNextState(new Integer(19), new Cell(se, constAction));
		s8.addNextState(new Integer(-1), new Cell(se, constAction));
	}

	public void createState9() {
		s9.addNextState(new Integer(1), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(2), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(3), new Cell(s10, consumeAction));
		s9.addNextState(new Integer(4), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(5), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(6), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(7), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(8), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(9), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(10), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(11), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(12), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(13), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(14), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(15), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(16), new Cell(se, stringAction));
		s9.addNextState(new Integer(17), new Cell(se, errorAction));
		s9.addNextState(new Integer(18), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(19), new Cell(s9, consumeAction));
		s9.addNextState(new Integer(-1), new Cell(s9, consumeAction));
	}
        
        public void createState10() {
		s10.addNextState(new Integer(1), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(2), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(3), new Cell(s10, consumeAction));
		s10.addNextState(new Integer(4), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(5), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(6), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(7), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(8), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(9), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(10), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(11), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(12), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(13), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(14), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(15), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(16), new Cell(se, stringAction));
		s10.addNextState(new Integer(17), new Cell(s9, deleteLastCharAction));
		s10.addNextState(new Integer(18), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(19), new Cell(s9, consumeAction));
		s10.addNextState(new Integer(-1), new Cell(se, consumeAction));
	}
        
        public void createState11() {
		s11.addNextState(new Integer(1), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(2), new Cell(s12, negativeConstAction));
		s11.addNextState(new Integer(3), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(4), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(5), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(6), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(7), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(8), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(9), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(10), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(11), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(12), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(13), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(14), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(15), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(16), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(17), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(18), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(19), new Cell(se, ncOperationAction));
		s11.addNextState(new Integer(-1), new Cell(se, ncOperationAction));
        }
        
        public void createState12() {
		s12.addNextState(new Integer(1), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(2), new Cell(s12, negativeConstAction));
		s12.addNextState(new Integer(3), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(4), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(5), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(6), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(7), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(8), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(9), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(10), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(11), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(12), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(13), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(14), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(15), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(16), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(17), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(18), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(19), new Cell(se, noConsumeAction));
		s12.addNextState(new Integer(-1), new Cell(se, noConsumeAction));
        }

	public void createEndState() {
		/*se.addNextState(new Integer(1), new Cell(s1, emptyAction));
		se.addNextState(new Integer(2), new Cell(s1, emptyAction));
		se.addNextState(new Integer(3), new Cell(s1, emptyAction));
		se.addNextState(new Integer(4), new Cell(s1, emptyAction));
		se.addNextState(new Integer(5), new Cell(s1, emptyAction));
		se.addNextState(new Integer(6), new Cell(s1, emptyAction));
		se.addNextState(new Integer(7), new Cell(s1, emptyAction));
		se.addNextState(new Integer(8), new Cell(s1, emptyAction));
		se.addNextState(new Integer(9), new Cell(s1, emptyAction));
		se.addNextState(new Integer(10), new Cell(s1, emptyAction));
		se.addNextState(new Integer(11), new Cell(s1, emptyAction));
		se.addNextState(new Integer(12), new Cell(s1, emptyAction));
		se.addNextState(new Integer(13), new Cell(s1, emptyAction));
		se.addNextState(new Integer(14), new Cell(s1, emptyAction));
		se.addNextState(new Integer(15), new Cell(s1, emptyAction));
		se.addNextState(new Integer(16), new Cell(s1, emptyAction));
		se.addNextState(new Integer(17), new Cell(s1, emptyAction));
		se.addNextState(new Integer(18), new Cell(s1, emptyAction));
		se.addNextState(new Integer(19), new Cell(s1, emptyAction));
		se.addNextState(new Integer(-1), new Cell(s1, emptyAction));*/
	}

	public State getBeginState() {
		return s1;
	}

}
