package com.uni.compiler.lexicAnalizer;

import java.util.HashMap;

import com.uni.compiler.Actions.Action;
import com.uni.compiler.Actions.CommentAction;
import com.uni.compiler.Actions.ConstAction;
import com.uni.compiler.Actions.ConsumeAction;
import com.uni.compiler.Actions.DeleteCharacterAction;
import com.uni.compiler.Actions.EmptyAction;
import com.uni.compiler.Actions.ErrorAction;
import com.uni.compiler.Actions.IdAction;
import com.uni.compiler.Actions.InvalidCharacterAction;
import com.uni.compiler.Actions.NotConsumeAction;
import com.uni.compiler.Actions.OperationAction;
import com.uni.compiler.Actions.StringAction;

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
	private State se = new State("EndState");

	// actions
	private Action idAction = null;
	private Action consAction = null;
	private Action stringAction = null;
	private Action emptyAction = null;
	private Action invalidCharacterAction = null;
	private Action operationAction = null;
	private Action consumeAction = null;
	private Action notConsumeAction = null;
	private Action errorAction = null;
	private Action commentAction = null;
	private Action deleteCharAction = null;

	private AnalizerFactory(LexicAnalizer la) {
		lexicAnalizer = la;
		idAction = new IdAction(lexicAnalizer);
		consAction = new ConstAction(lexicAnalizer);
		stringAction = new StringAction(lexicAnalizer);
		emptyAction = new EmptyAction();
		invalidCharacterAction = new InvalidCharacterAction(lexicAnalizer);
		operationAction = new OperationAction(lexicAnalizer);
		consumeAction = new ConsumeAction(lexicAnalizer);
		notConsumeAction = new NotConsumeAction(lexicAnalizer);
		errorAction = new ErrorAction(lexicAnalizer);
		commentAction = new CommentAction(lexicAnalizer);
		deleteCharAction = new DeleteCharacterAction(lexicAnalizer);

		createEndState();
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

	public static void createInstance(LexicAnalizer la) {
		if (analizerFactory == null) {
			synchronized (AnalizerFactory.class) {
				if (analizerFactory == null) {
					analizerFactory = new AnalizerFactory(la);
				}
			}
		}
	}

	public static AnalizerFactory getInstance(LexicAnalizer la) {
		createInstance(la);
		return analizerFactory;
	}

	public HashMap<String, String> createReservedWords() {
		// commons reserved words
		reservedWords.put("if", "if");
		reservedWords.put("else", "else");
		reservedWords.put("then", "then");
		reservedWords.put("begin", "begin");
		reservedWords.put("end", "end");
		reservedWords.put("end", "ulong");
		reservedWords.put("print", "print");
		reservedWords.put("fuction", "fuction");
		reservedWords.put("return", "return");

		// special reserved words
		reservedWords.put("import", "import");
		reservedWords.put("int", "int");
		reservedWords.put("loop", "loop");
		reservedWords.put("until", "until");

		return reservedWords;
	}

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
	}

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
			return se;
		}
		return null;
	}

	public void createBeginState() {
		s1.addNextState(new Integer(1), new Cell(s4, idAction));
		s1.addNextState(new Integer(2), new Cell(s5, consAction));
		s1.addNextState(new Integer(3), new Cell(se, operationAction));
		s1.addNextState(new Integer(4), new Cell(se, operationAction));
		s1.addNextState(new Integer(5), new Cell(se, operationAction));
		s1.addNextState(new Integer(6), new Cell(s2, operationAction));
		s1.addNextState(new Integer(7), new Cell(s2, operationAction));
		s1.addNextState(new Integer(8), new Cell(s3, operationAction));
		s1.addNextState(new Integer(9), new Cell(s2, operationAction));
		s1.addNextState(new Integer(10), new Cell(se, operationAction));
		s1.addNextState(new Integer(11), new Cell(s8, commentAction));
		s1.addNextState(new Integer(12), new Cell(se, operationAction));
		s1.addNextState(new Integer(13), new Cell(se, operationAction));
		s1.addNextState(new Integer(14), new Cell(se, operationAction));
		s1.addNextState(new Integer(15), new Cell(se, operationAction));
		s1.addNextState(new Integer(16), new Cell(s6, stringAction));
		s1.addNextState(new Integer(17), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(18), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(19), new Cell(s1, emptyAction));
		s1.addNextState(new Integer(-1), new Cell(se, invalidCharacterAction));
	}

	public void createState2() {
		s2.addNextState(new Integer(1), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(2), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(3), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(4), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(5), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(6), new Cell(se, consumeAction));
		s2.addNextState(new Integer(7), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(8), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(9), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(10), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(11), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(12), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(13), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(14), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(15), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(16), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(17), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(18), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(19), new Cell(se, notConsumeAction));
		s2.addNextState(new Integer(-1), new Cell(se, notConsumeAction));
	}

	public void createState3() {
		s3.addNextState(new Integer(1), new Cell(se, errorAction));
		s3.addNextState(new Integer(2), new Cell(se, errorAction));
		s3.addNextState(new Integer(3), new Cell(se, errorAction));
		s3.addNextState(new Integer(4), new Cell(se, errorAction));
		s3.addNextState(new Integer(5), new Cell(se, errorAction));
		s3.addNextState(new Integer(6), new Cell(se, consumeAction));
		s3.addNextState(new Integer(7), new Cell(se, errorAction));
		s3.addNextState(new Integer(8), new Cell(se, errorAction));
		s3.addNextState(new Integer(9), new Cell(se, errorAction));
		s3.addNextState(new Integer(10), new Cell(se, errorAction));
		s3.addNextState(new Integer(11), new Cell(se, errorAction));
		s3.addNextState(new Integer(12), new Cell(se, errorAction));
		s3.addNextState(new Integer(13), new Cell(se, errorAction));
		s3.addNextState(new Integer(14), new Cell(se, errorAction));
		s3.addNextState(new Integer(15), new Cell(se, errorAction));
		s3.addNextState(new Integer(16), new Cell(se, errorAction));
		s3.addNextState(new Integer(17), new Cell(se, errorAction));
		s3.addNextState(new Integer(18), new Cell(se, errorAction));
		s3.addNextState(new Integer(19), new Cell(se, errorAction));
		s3.addNextState(new Integer(-1), new Cell(se, errorAction));
	}

	public void createState4() {
		s4.addNextState(new Integer(1), new Cell(s4, consumeAction));
		s4.addNextState(new Integer(2), new Cell(s4, consumeAction));
		s4.addNextState(new Integer(3), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(4), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(5), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(6), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(7), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(8), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(9), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(10), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(11), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(12), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(13), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(14), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(15), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(16), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(17), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(18), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(19), new Cell(se, notConsumeAction));
		s4.addNextState(new Integer(-1), new Cell(se, notConsumeAction));
	}

	public void createState5() {
		s5.addNextState(new Integer(1), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(2), new Cell(s5, consumeAction));
		s5.addNextState(new Integer(3), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(4), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(5), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(6), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(7), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(8), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(9), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(10), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(11), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(12), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(13), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(14), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(15), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(16), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(17), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(18), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(19), new Cell(se, notConsumeAction));
		s5.addNextState(new Integer(-1), new Cell(se, notConsumeAction));
	}

	public void createState6() {
		s6.addNextState(new Integer(1), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(2), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(3), new Cell(s7, consumeAction));
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
		s6.addNextState(new Integer(16), new Cell(se, consumeAction));
		s6.addNextState(new Integer(17), new Cell(se, errorAction));
		s6.addNextState(new Integer(18), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(19), new Cell(s6, consumeAction));
		s6.addNextState(new Integer(-1), new Cell(se, consumeAction));
	}

	public void createState7() {
		s7.addNextState(new Integer(1), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(2), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(3), new Cell(s7, consumeAction));
		s7.addNextState(new Integer(4), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(5), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(6), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(7), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(8), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(9), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(10), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(11), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(12), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(13), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(14), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(15), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(16), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(17), new Cell(s6, deleteCharAction));// deleteAction
		s7.addNextState(new Integer(18), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(19), new Cell(s6, consumeAction));
		s7.addNextState(new Integer(-1), new Cell(se, consumeAction));
	}

	public void createState8() {
		s8.addNextState(new Integer(1), new Cell(se, errorAction));
		s8.addNextState(new Integer(2), new Cell(se, errorAction));
		s8.addNextState(new Integer(3), new Cell(se, errorAction));
		s8.addNextState(new Integer(4), new Cell(se, errorAction));
		s8.addNextState(new Integer(5), new Cell(se, errorAction));
		s8.addNextState(new Integer(6), new Cell(se, errorAction));
		s8.addNextState(new Integer(7), new Cell(se, errorAction));
		s8.addNextState(new Integer(8), new Cell(se, errorAction));
		s8.addNextState(new Integer(9), new Cell(se, errorAction));
		s8.addNextState(new Integer(10), new Cell(se, errorAction));
		s8.addNextState(new Integer(11), new Cell(s9, emptyAction));
		s8.addNextState(new Integer(12), new Cell(se, errorAction));
		s8.addNextState(new Integer(13), new Cell(se, errorAction));
		s8.addNextState(new Integer(14), new Cell(se, errorAction));
		s8.addNextState(new Integer(15), new Cell(se, errorAction));
		s8.addNextState(new Integer(16), new Cell(se, errorAction));
		s8.addNextState(new Integer(17), new Cell(se, errorAction));
		s8.addNextState(new Integer(18), new Cell(se, errorAction));
		s8.addNextState(new Integer(19), new Cell(se, errorAction));
		s8.addNextState(new Integer(-1), new Cell(se, errorAction));
	}

	public void createState9() {
		s9.addNextState(new Integer(1), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(2), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(3), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(4), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(5), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(6), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(7), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(8), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(9), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(10), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(11), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(12), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(13), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(14), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(15), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(16), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(17), new Cell(se, emptyAction));
		s9.addNextState(new Integer(18), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(19), new Cell(s9, emptyAction));
		s9.addNextState(new Integer(-1), new Cell(se, emptyAction));
	}

	public void createEndState() {
		se.addNextState(new Integer(1), new Cell(s1, emptyAction));
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
		se.addNextState(new Integer(-1), new Cell(s1, emptyAction));
	}

	public State getBeginState() {
		return s1;
	}

}
