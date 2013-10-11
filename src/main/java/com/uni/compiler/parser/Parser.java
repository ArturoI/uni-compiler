//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package com.uni.compiler.parser;

import java.util.*;

import com.uni.compiler.UI.UIMain;
import com.uni.compiler.UI.Style;
import com.uni.compiler.lexicAnalizer.*;

//#line 26 "Parser.java"




public class Parser
             implements ParserTokens
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    3,    4,    4,    6,    6,    5,
    7,    7,    7,    7,    8,    8,    9,    9,    9,   10,
   10,   10,   10,   10,   13,   13,   13,   13,   14,   14,
   14,   18,   18,   15,   15,    2,    2,    2,   19,   19,
   19,   19,   19,   11,   11,   11,   11,   16,   16,   16,
   16,   16,   16,   16,   22,   22,   22,   22,   22,   22,
   22,   23,   23,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   20,   20,   20,   17,   17,   17,   17,   17,
   25,   25,   25,   25,   25,   25,   24,   24,   21,   21,
};
final static short yylen[] = {                            2,
    2,    2,    1,    1,    1,    3,    3,    3,    1,    3,
    4,    2,    3,    4,    1,    3,    2,    1,    1,    2,
    1,    2,    1,    1,    4,    2,    3,    4,    4,    6,
    3,    3,    1,    4,    3,    2,    1,    1,    2,    1,
    1,    1,    1,    3,    2,    3,    2,    3,    3,    1,
    3,    2,    3,    2,    3,    3,    1,    3,    2,    3,
    2,    1,    1,    5,    5,    4,    3,    3,    2,    2,
    2,    1,    4,    6,    3,    5,    4,    5,    4,    4,
    1,    1,    1,    1,    1,    1,    3,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    4,    5,    9,    0,    0,
   43,    0,    0,    0,    0,   38,    0,    0,    2,    0,
   40,   37,   41,   42,    7,    6,    0,    0,    0,   15,
   10,    0,   62,   63,    0,    0,    0,    0,    0,    0,
    0,   57,    0,    0,    0,    0,    0,    0,    0,   71,
    0,   36,   39,    8,   12,    0,    0,    0,    0,    0,
   19,    0,   18,    0,   21,    0,   23,   24,    0,    0,
   59,   61,   46,    0,    0,    0,    0,    0,   82,   84,
   85,   86,   81,   83,    0,    0,    0,   75,    0,   90,
    0,   67,    0,   68,    0,   16,    0,    0,    0,   26,
    0,   13,   17,   20,   22,   51,    0,   53,    0,   58,
   55,   60,   56,    0,    0,    0,    0,   88,    0,   89,
   66,    0,   14,   11,    0,    0,   33,   31,   35,    0,
   27,    0,   80,    0,   79,   77,    0,    0,   65,   64,
    0,    0,   34,   28,   25,   78,   76,   87,   74,    0,
   32,   30,
};
final static short yydgoto[] = {                          3,
    4,   18,    5,    6,    7,    9,   31,   32,   62,   63,
   64,   65,   66,   67,   68,   44,   45,  128,   22,   23,
   24,   41,   42,  119,   86,
};
final static short yysindex[] = {                      -197,
 -240, -224,    0,  106,    0,    0,    0,    0,   53, -178,
    0,   12,    6,  136,  -25,    0,   29,  281,    0,  -23,
    0,    0,    0,    0,    0,    0, -201, -106, -240,    0,
    0,  117,    0,    0,   -4,   -4, -150, -150,   42,   75,
   -2,    0,   91,  -18, -163,    6,  257,   19,  -38,    0,
   75,    0,    0,    0,    0,  117,    8,    6,  111,  -32,
    0,  200,    0,    4,    0,   24,    0,    0,   -2,   -2,
    0,    0,    0,   75,  -12,   95,  -77,  -63,    0,    0,
    0,    0,    0,    0,  -18,   29,   29,    0,  275,    0,
    6,    0,  -35,    0,  179,    0,  185,    6,  206,    0,
   49,    0,    0,    0,    0,    0,   -2,    0,   -2,    0,
    0,    0,    0,   23,   98,  128,  139,    0, -173,    0,
    0,  -48,    0,    0,  226,  117,    0,    0,    0,    6,
    0,   34,    0,   60,    0,    0,  284,  275,    0,    0,
 -168,  246,    0,    0,    0,    0,    0,    0,    0,  226,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,  102,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
  -41,    0,    0,    0,    0,    0,    0,   20,   39,    0,
   67,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -36,  -31,
    0,    0,    0,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,    0,   70,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   58,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  157,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -1,  127,   22,    0,  118,    0,  121,  -22,   59,
  458,  477,    0,    0,    0,  105,   77, -103,   51,    0,
    0,  110,  125,   17,   -3,
};
final static int YYTABLESIZE=615;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   72,   50,   94,   50,   52,  122,   52,  101,   52,   54,
  140,   54,   47,   54,   49,   50,    8,   50,   50,   69,
   50,  141,   52,   52,   75,   52,   76,   54,   54,   37,
   54,   30,   10,   95,   38,   53,   99,   37,   70,   77,
   87,   83,   38,   84,   78,   43,  152,   37,   35,   30,
   36,   27,   38,   37,   35,   54,   36,   73,   38,   92,
    1,   72,  104,  133,   37,   35,   96,   36,   52,   38,
   37,   35,   39,   36,  145,   38,   75,    2,   76,    1,
   69,  114,  105,   37,   35,   28,   36,   29,   38,  138,
   37,   35,   88,   36,  150,   38,   27,   52,   89,   70,
  147,    1,   75,  142,   76,   48,   33,   48,   34,   48,
   49,   26,   49,   47,   49,  137,   40,   75,   73,   76,
  103,   51,   90,   48,   48,   45,   48,   44,   49,   49,
   19,   49,   37,   35,   97,   36,   37,   38,  135,  118,
   75,   38,   76,   74,   69,   70,   57,   85,   56,    0,
   83,    1,   84,  103,  149,  127,    0,  103,   55,   29,
    0,   71,   72,    0,   16,    0,   17,  120,  136,   61,
   75,   17,   76,    0,  129,   61,    0,   17,  110,   33,
    0,   34,    0,  127,  107,  109,    0,   52,  118,    0,
  115,  116,  112,   33,   16,   34,   17,   16,    0,   17,
  103,  111,  113,    0,    0,  132,  143,  139,  127,    0,
    0,    0,    0,    0,   50,    0,    0,   29,  134,   52,
  121,   93,    0,  100,   54,   50,   50,   50,   50,    0,
   52,   52,   52,   52,   48,   54,   54,   54,   54,   17,
    0,    0,    0,  106,   33,   17,   34,    0,   79,   80,
   81,   82,   33,    0,   34,    0,   72,   72,    0,    0,
   17,   72,   33,   72,   34,   72,   17,    0,   33,    0,
   34,   72,   72,   72,   72,   69,   69,    0,    0,   33,
   69,   34,   69,    0,   69,   33,   17,   34,    0,  144,
   69,   69,   69,   69,   70,   70,    0,   73,   33,   70,
   34,   70,    0,   70,  131,   33,   17,   34,   25,   70,
   70,   70,   70,   73,   73,  146,    0,   17,   73,    0,
   48,    0,   73,    0,    0,   49,    0,    0,   73,   73,
   73,   48,   48,   48,   48,   17,   49,   49,   49,   49,
    0,   17,    0,    0,   17,    0,    0,   33,    0,   34,
  108,   33,    0,   34,    0,    0,    0,   79,   80,   81,
   82,   11,   12,    1,    0,    0,   13,   12,    0,    0,
    0,   58,    0,   12,    0,    0,   14,   58,   15,    0,
    2,   59,   98,   15,   60,    0,    0,   59,    0,   15,
   60,   11,   12,    0,   11,   12,   13,    0,    0,   13,
    0,    0,    0,    0,    0,    0,   14,   46,   15,   14,
    0,   15,   29,   29,    0,    0,    0,   29,    0,    0,
    0,   29,    0,    0,    0,    0,    0,   29,   29,   29,
   29,    0,    0,    0,  123,   12,    0,    0,    0,   58,
    0,   12,    0,  124,    0,   58,  125,    0,  126,   59,
    0,   15,   60,    0,    0,   59,   12,   15,   60,    0,
   58,   20,   12,    0,  102,    0,   58,    0,    0,    0,
   59,   20,   15,   60,    0,   20,   59,  130,   15,   60,
   21,    0,   12,    0,    0,    0,   58,    0,    0,  126,
   21,    0,    0,    0,   21,    0,   59,    0,   15,   60,
    0,    0,   12,    0,   20,    0,   58,    0,    0,    0,
  151,    0,   11,   12,    0,    0,   59,   13,   15,   60,
    0,    0,    0,   21,    0,    0,    0,   14,   91,   15,
   11,   12,    0,    0,    0,   13,   11,   12,  117,   11,
   12,   13,    0,    0,   13,   14,   20,   15,  148,    0,
    0,   14,    0,   15,   14,    0,   15,    0,    0,    0,
    0,    0,    0,    0,    0,   21,    0,    0,    0,    0,
    0,    0,    0,    0,   20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,   20,   20,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,   21,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   43,   41,   45,   41,   41,   43,   40,   45,   41,
   59,   43,   14,   45,   40,   41,  257,   59,   60,    0,
   62,  125,   59,   60,   43,   62,   45,   59,   60,   42,
   62,   10,  257,   56,   47,   59,   59,   42,    0,   42,
   44,   60,   47,   62,   47,   40,  150,   42,   43,   28,
   45,   44,   47,   42,   43,  257,   45,    0,   47,   41,
  258,   61,   59,   41,   42,   43,   59,   45,   18,   47,
   42,   43,   61,   45,   41,   47,   43,  275,   45,  258,
   61,   85,   59,   42,   43,  264,   45,  266,   47,  263,
   42,   43,  256,   45,  263,   47,   44,   47,  262,   61,
   41,    0,   43,  126,   45,   41,  257,   43,  259,   45,
   41,   59,   43,   59,   45,  117,   12,   43,   61,   45,
   62,   17,   46,   59,   60,   59,   62,   59,   59,   60,
    4,   62,   42,   43,   58,   45,   42,   47,   41,   89,
   43,   47,   45,   39,   35,   36,   29,   43,   28,   -1,
   60,  258,   62,   95,  138,   97,   -1,   99,  265,  266,
   -1,   37,   38,   -1,   59,   -1,   61,   91,   41,   59,
   43,   61,   45,   -1,   98,   59,   -1,   61,  256,  257,
   -1,  259,   -1,  125,   75,   76,   -1,  137,  138,   -1,
   86,   87,  256,  257,   59,  259,   61,   59,   -1,   61,
  142,   77,   78,   -1,   -1,  101,  130,  256,  150,   -1,
   -1,   -1,   -1,   -1,  256,   -1,   -1,   61,  114,  256,
  256,  260,   -1,  256,  256,  267,  268,  269,  270,   -1,
  267,  268,  269,  270,  260,  267,  268,  269,  270,   61,
   -1,   -1,   -1,  256,  257,   61,  259,   -1,  267,  268,
  269,  270,  257,   -1,  259,   -1,  256,  257,   -1,   -1,
   61,  261,  257,  263,  259,  265,   61,   -1,  257,   -1,
  259,  271,  272,  273,  274,  256,  257,   -1,   -1,  257,
  261,  259,  263,   -1,  265,  257,   61,  259,   -1,  256,
  271,  272,  273,  274,  256,  257,   -1,  256,  257,  261,
  259,  263,   -1,  265,  256,  257,   61,  259,  256,  271,
  272,  273,  274,  256,  257,  256,   -1,   61,  261,   -1,
  256,   -1,  265,   -1,   -1,  256,   -1,   -1,  271,  272,
  273,  267,  268,  269,  270,   61,  267,  268,  269,  270,
   -1,   61,   -1,   -1,   61,   -1,   -1,  257,   -1,  259,
  256,  257,   -1,  259,   -1,   -1,   -1,  267,  268,  269,
  270,  256,  257,  258,   -1,   -1,  261,  257,   -1,   -1,
   -1,  261,   -1,  257,   -1,   -1,  271,  261,  273,   -1,
  275,  271,  272,  273,  274,   -1,   -1,  271,   -1,  273,
  274,  256,  257,   -1,  256,  257,  261,   -1,   -1,  261,
   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,  273,  271,
   -1,  273,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,
   -1,  265,   -1,   -1,   -1,   -1,   -1,  271,  272,  273,
  274,   -1,   -1,   -1,  256,  257,   -1,   -1,   -1,  261,
   -1,  257,   -1,  265,   -1,  261,  262,   -1,  264,  271,
   -1,  273,  274,   -1,   -1,  271,  257,  273,  274,   -1,
  261,    4,  257,   -1,  265,   -1,  261,   -1,   -1,   -1,
  271,   14,  273,  274,   -1,   18,  271,  272,  273,  274,
    4,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,  264,
   14,   -1,   -1,   -1,   18,   -1,  271,   -1,  273,  274,
   -1,   -1,  257,   -1,   47,   -1,  261,   -1,   -1,   -1,
  265,   -1,  256,  257,   -1,   -1,  271,  261,  273,  274,
   -1,   -1,   -1,   47,   -1,   -1,   -1,  271,  272,  273,
  256,  257,   -1,   -1,   -1,  261,  256,  257,  264,  256,
  257,  261,   -1,   -1,  261,  271,   89,  273,  265,   -1,
   -1,  271,   -1,  273,  271,   -1,  273,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   89,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  117,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  117,  137,  138,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  137,  138,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ID","INT","CTEINT","STRING","IF","THEN","ELSE",
"BEGIN","END","IMPORT","MENORIGUAL","MAYORIGUAL","IGUAL","DISTINTO","LOOP",
"UNTIL","PRINT","RETURN","FUNCTION",
};
final static String yyrule[] = {
"$accept : programa",
"programa : declaraciones sentencias",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : variables",
"declaracion : funcion",
"variables : INT conjVariables ';'",
"variables : INT conjVariables error",
"conjVariables : conjVariables ',' ID",
"conjVariables : ID",
"funcion : FUNCTION ID bloqueFuncion",
"bloqueFuncion : BEGIN declaracionFuncion sentenciasF END",
"bloqueFuncion : BEGIN END",
"bloqueFuncion : declaracionFuncion sentenciasF END",
"bloqueFuncion : BEGIN declaracionFuncion sentenciasF error",
"declaracionFuncion : variables",
"declaracionFuncion : IMPORT conjVariables ';'",
"sentenciasF : sentenciasF sentenciaF",
"sentenciasF : sentenciaF",
"sentenciasF : ';'",
"sentenciaF : asignacion ';'",
"sentenciaF : impresion",
"sentenciaF : returnF ';'",
"sentenciaF : seleccionF",
"sentenciaF : iteracionF",
"returnF : RETURN '(' exprAritmetica ')'",
"returnF : RETURN error",
"returnF : RETURN '(' error",
"returnF : RETURN '(' exprAritmetica error",
"seleccionF : IF condicion THEN sentenciaSeleccionF",
"seleccionF : IF condicion THEN sentenciaSeleccionF ELSE sentenciaSeleccionF",
"seleccionF : IF condicion sentenciaSeleccionF",
"sentenciaSeleccionF : BEGIN sentenciasF END",
"sentenciaSeleccionF : sentenciaF",
"iteracionF : LOOP sentenciasF UNTIL condicion",
"iteracionF : LOOP UNTIL condicion",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencias : ';'",
"sentencia : asignacion ';'",
"sentencia : impresion",
"sentencia : seleccion",
"sentencia : iteracion",
"sentencia : error",
"asignacion : ID '=' exprAritmetica",
"asignacion : '=' exprAritmetica",
"asignacion : ID '=' error",
"asignacion : ID exprAritmetica",
"exprAritmetica : exprAritmetica '+' termino",
"exprAritmetica : exprAritmetica '-' termino",
"exprAritmetica : termino",
"exprAritmetica : exprAritmetica '+' error",
"exprAritmetica : '+' termino",
"exprAritmetica : exprAritmetica '-' error",
"exprAritmetica : '-' termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : termino '*' error",
"termino : '*' factor",
"termino : termino '/' error",
"termino : '/' factor",
"factor : ID",
"factor : CTEINT",
"impresion : PRINT '(' STRING ')' ';'",
"impresion : PRINT '(' STRING ')' error",
"impresion : PRINT '(' STRING error",
"impresion : PRINT STRING ')'",
"impresion : PRINT '(' ')'",
"impresion : PRINT STRING",
"impresion : PRINT '('",
"impresion : PRINT ')'",
"impresion : PRINT",
"seleccion : IF condicion THEN sentenciaSeleccion",
"seleccion : IF condicion THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF condicion error",
"condicion : '(' exprAritmetica opLogico exprAritmetica ')'",
"condicion : exprAritmetica opLogico exprAritmetica ')'",
"condicion : '(' exprAritmetica opLogico exprAritmetica error",
"condicion : '(' opLogico exprAritmetica ')'",
"condicion : '(' exprAritmetica opLogico ')'",
"opLogico : '<'",
"opLogico : MENORIGUAL",
"opLogico : '>'",
"opLogico : MAYORIGUAL",
"opLogico : IGUAL",
"opLogico : DISTINTO",
"sentenciaSeleccion : BEGIN sentencias END",
"sentenciaSeleccion : sentencia",
"iteracion : LOOP sentencias UNTIL condicion",
"iteracion : LOOP UNTIL condicion",
};

//#line 199 "gramatica.y"

/*_________________________________________________________________________________________________________*/

private LexicAnalizer la;
private Enumeration elements;
//GUI
private UIMain uiMain;
//paneles para UI
private Style errorPanel;//Panel de Errores
private Style lexPanel;  //Panel de Lexema
private Style linePanel; //Panel para marcar errores
private String lastError = "";

public Stack pilaLoop;
public Stack pilaBegin;

private List<Token> tmpId; //vector de ids de delaraciones y asignaciones multiples

private List<Token> symbolsTable;

private String functionName;

 public Parser(LexicAnalizer la, UIMain v, boolean debugMe, List<Token> st)
	{
	    this.la = la;

	    yydebug=debugMe;
		
		//UIMain
	    this.uiMain = v;

	        
	    //Panel de Errores
	    errorPanel = new Style(uiMain.getjTextPane1());
	    //Panel de Lexema
	    lexPanel = new Style(uiMain.getjTextPane3());
	    //Panel para marcar errores
	    linePanel = new Style(uiMain.getjTextPane2());

            this.pilaLoop = new Stack();
            this.pilaBegin = new Stack();

            this.functionName = "";
            this.tmpId = new ArrayList<Token>();

            this.symbolsTable = st;
	}

    private int yylex() {

       Token tk = new Token();

       if(!la.hasMoreElements()){
           return 0;
       }else{

           tk = (Token)la.nextElement();
           
           yylval = new ParserVal(tk);

           while (la.hasMoreElements() && tk.hasError()){
                showErrorParser("Linea " + tk.getLine() + ": Error Lexico: " + tk.getError());
                tk = (Token)la.nextElement();
           }

           //System.out.println(tk.getType());
           
           if(tk.getType().equals("EOF"))
               return 0;
               
           if (tk.hasWarning()){
                showErrorParser("Linea " + tk.getLine() + ": Warning Lexico: " + tk.getWarning());
           } 

           if(this.la.getReservedWordsTable().containsKey(tk.getToken())){
               if(tk.getToken().equalsIgnoreCase("IF"))
                   return IF;
               if(tk.getToken().equalsIgnoreCase("THEN"))
                   return THEN;
               if(tk.getToken().equalsIgnoreCase("ELSE"))
                   return ELSE;
               if(tk.getToken().equalsIgnoreCase("PRINT"))
                   return PRINT;
               if(tk.getToken().equalsIgnoreCase("INT"))
                   return INT;
               if(tk.getToken().equalsIgnoreCase("LOOP")){
                    this.pilaLoop.push(tk);
                    return LOOP;
               }
               if(tk.getToken().equalsIgnoreCase("UNTIL")){
                    if (!this.pilaLoop.empty()){
                        this.pilaLoop.pop();
                    } else {
                        showErrorParser("Linea " + tk.getLine() + ": UNTIL sin su correspondiente LOOP");
                    }
                    return UNTIL;
               }
               if(tk.getToken().equalsIgnoreCase("BEGIN")){
                    this.pilaBegin.push(tk);
                    return BEGIN;
               }
               if(tk.getToken().equalsIgnoreCase("END")){
                    if (!this.pilaBegin.empty()){
                        this.pilaBegin.pop();
                    } else {
                        showErrorParser("Linea " + tk.getLine() + ": END sin su correspondiente BEGIN");

                    }
                    return END;
               }
               if(tk.getToken().equalsIgnoreCase("IMPORT"))
                   return IMPORT;
               if(tk.getToken().equalsIgnoreCase("RETURN"))
                   return RETURN;
               if(tk.getToken().equalsIgnoreCase("FUNCTION"))
               	   return FUNCTION;
           }else{
               if(tk.getType().equalsIgnoreCase("Identificador")){
                   return ID;
               }else{
                   if(tk.getType().equalsIgnoreCase("Constante Negativa") || tk.getType().equalsIgnoreCase("Constante Positiva")){
                        return CTEINT;
                   }else{
                        if(tk.getType().equalsIgnoreCase("Cadena")){
                                return STRING;
                        }else{
                        	if(tk.getToken().equalsIgnoreCase("+"))
                                    return '+';
                            if(tk.getToken().equalsIgnoreCase("-"))
                                    return '-';
                            if(tk.getToken().equalsIgnoreCase("*"))
                                    return '*';
                            if(tk.getToken().equalsIgnoreCase("/"))
                                    return '/';
                            if(tk.getToken().equalsIgnoreCase("="))
                                    return '=';
                            if(tk.getToken().equalsIgnoreCase(">"))
                                    return '>';
                            if(tk.getToken().equalsIgnoreCase("<"))
                                    return '<';
                            if(tk.getToken().equalsIgnoreCase(">="))
                                    return MAYORIGUAL;
                            if(tk.getToken().equalsIgnoreCase("!="))
                                    return DISTINTO;
                            if(tk.getToken().equalsIgnoreCase("<="))
                                    return MENORIGUAL;
                            if(tk.getToken().equalsIgnoreCase("=="))
                                    return IGUAL;
                            if(tk.getToken().equalsIgnoreCase(")"))
                                    return ')';
                            if(tk.getToken().equalsIgnoreCase("("))
                                    return '(';
                            if(tk.getToken().equalsIgnoreCase(";"))
                                    return ';';
                            if(tk.getToken().equalsIgnoreCase(","))
                                    return ',';
                        }
                   }
               }
           }
       }
       return 0;
    }

    private void yyerror(String string) {
        //showErrorParser("ERROR FROM PARSER : " + string);
    }
	
    private void showInfoParser(String s){
        //lexPanel.setCursiva("ShowInfoParser : ");
        lexPanel.setNegrita(s);
        lexPanel.newLine();
    }
	
    private void showErrorParser(String s){
        //errorPanel.setCursiva("ShowErrorParser : ");
        errorPanel.setNegrita(s);
        errorPanel.newLine();	
    }

    private void modifyVariableNames(){
        if (!this.functionName.equals("")){
          for (Token t : this.tmpId){
            t.setLexema(this.functionName + "&" + t.getToken());
          }
        }
    }

    private Token getTokenFromSymbolTable(String tokenId){
            for (Token t : this.symbolsTable){
                if (t.getToken().equals(tokenId)){
                    return t;
                }
            }
            return null;
        }
        
    private void saveVariableType(List<Token> l, String tipo){
        
        for (Token t: l){
            if (t.getType().equals("Identificador")){
                Token tokenInSymbolTable = getTokenFromSymbolTable(t.getToken());
                if (tokenInSymbolTable.getVariableType().equals("")){
                    t.setVariableType(tipo);
                }else{
                    showErrorParser("Linea "+ t.getLine() +": Error Semantico: " + t.getToken() + " ya fue declarado");
                }
            }
        }   
    }

    private void addFunctionNameToToken(String functionName, String variable){
            if (!functionName.equals("")){
                for (Token t : this.symbolsTable){
                    if (t.getToken().equals(variable)){
                        t.setLexema(functionName + "&" + variable);
                        t.functionName = functionName;
                    }
                }
            }
        }
//#line 677 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 6:
//#line 37 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Declaracion de variables."); 
                                      modifyVariableNames();
                                      saveVariableType(this.tmpId, "INT");
                                      this.tmpId = new ArrayList<Token>();
                                    }
break;
case 7:
//#line 43 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 8:
//#line 46 "gramatica.y"
{ this.tmpId.add((Token)val_peek(2).obj); addFunctionNameToToken(this.functionName, ((Token)val_peek(2).obj).getToken()); }
break;
case 9:
//#line 47 "gramatica.y"
{ this.tmpId.add((Token)val_peek(0).obj); addFunctionNameToToken(this.functionName, ((Token)val_peek(0).obj).getToken()); }
break;
case 10:
//#line 50 "gramatica.y"
{ this.functionName = ""; }
break;
case 11:
//#line 53 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion."); }
break;
case 12:
//#line 55 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
break;
case 13:
//#line 56 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
break;
case 14:
//#line 57 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
break;
case 15:
//#line 60 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Variables de la funcion."); this.functionName = ((Token)val_peek(0).obj).getToken(); }
break;
case 16:
//#line 61 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); this.functionName = ((Token)val_peek(2).obj).getToken();}
break;
case 26:
//#line 79 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 27:
//#line 80 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 28:
//#line 81 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 29:
//#line 86 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 30:
//#line 87 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 31:
//#line 88 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 34:
//#line 97 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 35:
//#line 98 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 43:
//#line 114 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Sentencia Invalida"); }
break;
case 44:
//#line 119 "gramatica.y"
{ /*showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Asignacion.");*/ }
break;
case 45:
//#line 121 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 46:
//#line 122 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 47:
//#line 123 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 51:
//#line 130 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 52:
//#line 131 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 53:
//#line 132 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 54:
//#line 133 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 58:
//#line 140 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 59:
//#line 141 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 60:
//#line 142 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 61:
//#line 143 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 64:
//#line 152 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");  }
break;
case 65:
//#line 154 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 66:
//#line 155 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 67:
//#line 156 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 68:
//#line 157 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 69:
//#line 158 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 70:
//#line 159 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 71:
//#line 160 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 72:
//#line 161 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 73:
//#line 166 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 74:
//#line 167 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 75:
//#line 169 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
break;
case 77:
//#line 174 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 78:
//#line 175 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 79:
//#line 176 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 80:
//#line 177 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 89:
//#line 194 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 90:
//#line 195 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
//#line 1030 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
