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

import java.util.Enumeration;

import com.uni.compiler.UI.UIMain;
import com.uni.compiler.UI.Style;
import com.uni.compiler.lexicAnalizer.*;
//#line 25 "Parser.java"




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
    7,    7,    7,    7,    7,    8,    8,    9,    9,    9,
   10,   10,   10,   10,   10,   13,   13,   13,   13,   14,
   14,   14,   18,   18,   15,   15,    2,    2,    2,   19,
   19,   19,   19,   19,   11,   11,   11,   11,   16,   16,
   16,   16,   16,   16,   16,   22,   22,   22,   22,   22,
   22,   22,   23,   23,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   20,   20,   20,   17,   17,   17,   17,
   17,   25,   25,   25,   25,   25,   25,   24,   24,   21,
   21,
};
final static short yylen[] = {                            2,
    2,    2,    1,    1,    1,    3,    3,    3,    1,    3,
    4,    2,    3,    4,    1,    1,    3,    2,    1,    1,
    2,    1,    2,    1,    1,    4,    2,    3,    4,    4,
    6,    3,    3,    1,    4,    3,    2,    1,    1,    2,
    1,    1,    1,    1,    3,    2,    3,    2,    3,    3,
    1,    3,    2,    3,    2,    3,    3,    1,    3,    2,
    3,    2,    1,    1,    5,    5,    4,    3,    3,    2,
    2,    2,    1,    4,    6,    3,    5,    4,    5,    4,
    4,    1,    1,    1,    1,    1,    1,    3,    1,    4,
    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    4,    5,    9,    0,    0,
   44,    0,    0,    0,    0,   39,    0,    0,    2,    0,
   41,   38,   42,   43,    7,    6,    0,    0,    0,   15,
   16,   10,    0,   63,   64,    0,    0,    0,    0,    0,
    0,    0,   58,    0,    0,    0,    0,    0,    0,    0,
   72,    0,   37,   40,    8,   12,    0,    0,    0,    0,
    0,   20,    0,   19,    0,   22,    0,   24,   25,    0,
    0,   60,   62,   47,    0,    0,    0,    0,    0,   83,
   85,   86,   87,   82,   84,    0,    0,    0,   76,    0,
   91,    0,   68,    0,   69,    0,   17,    0,    0,    0,
   27,    0,   13,   18,   21,   23,   52,    0,   54,    0,
   59,   56,   61,   57,    0,    0,    0,    0,   89,    0,
   90,   67,    0,   14,   11,    0,    0,   34,   32,   36,
    0,   28,    0,   81,    0,   80,   78,    0,    0,   66,
   65,    0,    0,   35,   29,   26,   79,   77,   88,   75,
    0,   33,   31,
};
final static short yydgoto[] = {                          3,
    4,   18,    5,    6,    7,    9,   32,   33,   63,   64,
   65,   66,   67,   68,   69,   45,   46,  129,   22,   23,
   24,   42,   43,  120,   87,
};
final static short yysindex[] = {                      -241,
 -224, -210,    0,  106,    0,    0,    0,    0,   53, -178,
    0,   12,    6,  136,  -25,    0,   29,  281,    0,   -7,
    0,    0,    0,    0,    0,    0, -197, -104, -224,    0,
    0,    0,  117,    0,    0,   -4,   -4, -155, -155,   42,
   83,   -2,    0,   91,  -18, -142,    6,  257,   28,  -38,
    0,   83,    0,    0,    0,    0,  117,   -3,    6,  111,
  -32,    0,  200,    0,   40,    0,   59,    0,    0,   -2,
   -2,    0,    0,    0,   83,  -12,   95, -107,  -82,    0,
    0,    0,    0,    0,    0,  -18,   29,   29,    0,  275,
    0,    6,    0,  -35,    0,  179,    0,  185,    6,  206,
    0,   49,    0,    0,    0,    0,    0,   -2,    0,   -2,
    0,    0,    0,    0,   23,   98,  123,  139,    0, -185,
    0,    0,  -48,    0,    0,  226,  117,    0,    0,    0,
    6,    0,   34,    0,   60,    0,    0,  284,  275,    0,
    0, -132,  246,    0,    0,    0,    0,    0,    0,    0,
  226,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,  135,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   96,  -41,    0,    0,    0,    0,    0,    0,   20,   39,
    0,   97,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -36,
  -31,    0,    0,    0,  100,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,    0,   70,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  157,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -1,  159,   22,    0,  140,    0,  143,   33,  470,
  468,  477,    0,    0,    0,  104,   48,  -90,   19,    0,
    0,   46,  107,   41,  -23,
};
final static int YYTABLESIZE=621;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   73,   51,   95,   51,   53,  123,   53,  102,   53,   55,
  141,   55,   48,   55,   50,   51,    1,   51,   51,   70,
   51,   88,   53,   53,   76,   53,   77,   55,   55,   38,
   55,   31,    8,    2,   39,  142,   53,   38,   71,   78,
   27,   84,   39,   85,   79,   44,   10,   38,   36,   31,
   37,   54,   39,   38,   36,   97,   37,   74,   39,   55,
  153,   73,  115,  134,   38,   36,   53,   37,   93,   39,
   38,   36,   40,   37,  146,   39,   76,  139,   77,    1,
   70,   70,   71,   38,   36,   28,   37,   29,   39,   96,
   38,   36,  100,   37,   91,   39,   27,   30,  105,   71,
  148,   34,   76,   35,   77,   49,   98,   49,  119,   49,
   50,   26,   50,   89,   50,   41,  138,  106,   74,   90,
   52,  108,  110,   49,   49,   76,   49,   77,   50,   50,
  151,   50,   38,   36,    1,   37,   38,   39,  136,  121,
   76,   39,   77,   75,   72,   73,  130,   86,  111,   34,
   84,   35,   85,    1,   48,   46,   53,  119,   45,  143,
   56,   29,   19,  137,   16,   76,   17,   77,   58,   62,
   57,   17,    0,  113,   34,   62,   35,   17,  144,  150,
    0,    0,    0,    0,  112,  114,    0,    0,    0,    0,
  116,  117,    0,    0,   16,    0,   17,   16,    0,   17,
    0,    0,    0,    0,    0,  133,    0,  140,    0,    0,
    0,    0,    0,    0,   51,    0,    0,   30,  135,   53,
  122,   94,    0,  101,   55,   51,   51,   51,   51,    0,
   53,   53,   53,   53,   49,   55,   55,   55,   55,   17,
    0,    0,    0,  107,   34,   17,   35,    0,   80,   81,
   82,   83,   34,    0,   35,    0,   73,   73,    0,    0,
   17,   73,   34,   73,   35,   73,   17,    0,   34,    0,
   35,   73,   73,   73,   73,   70,   70,    0,    0,   34,
   70,   35,   70,    0,   70,   34,   17,   35,    0,  145,
   70,   70,   70,   70,   71,   71,    0,   74,   34,   71,
   35,   71,    0,   71,  132,   34,   17,   35,   25,   71,
   71,   71,   71,   74,   74,  147,    0,   17,   74,    0,
   49,    0,   74,    0,    0,   50,    0,    0,   74,   74,
   74,   49,   49,   49,   49,   17,   50,   50,   50,   50,
    0,   17,    0,    0,   17,    0,    0,   34,    0,   35,
  109,   34,    0,   35,    0,    0,    0,   80,   81,   82,
   83,   11,   12,    1,    0,    0,   13,   12,    0,    0,
    0,   59,    0,   12,    0,    0,   14,   59,   15,    0,
    2,   60,   99,   15,   61,    0,    0,   60,    0,   15,
   61,   11,   12,    0,   11,   12,   13,    0,    0,   13,
    0,    0,    0,    0,    0,    0,   14,   47,   15,   14,
    0,   15,   30,   30,    0,    0,    0,   30,    0,    0,
    0,   30,    0,    0,    0,    0,    0,   30,   30,   30,
   30,    0,    0,    0,  124,   12,    0,    0,    0,   59,
    0,   12,    0,  125,    0,   59,  126,    0,  127,   60,
    0,   15,   61,    0,    0,   60,   12,   15,   61,    0,
   59,    0,   12,    0,  103,    0,   59,    0,    0,    0,
   60,   20,   15,   61,    0,    0,   60,  131,   15,   61,
   21,   20,   12,    0,    0,   20,   59,    0,    0,  127,
   21,    0,    0,    0,   21,    0,   60,    0,   15,   61,
    0,    0,   12,    0,    0,    0,   59,    0,    0,    0,
  152,    0,   11,   12,    0,   20,   60,   13,   15,   61,
    0,    0,    0,    0,   21,    0,    0,   14,   92,   15,
   11,   12,  104,    0,    0,   13,   11,   12,  118,   11,
   12,   13,    0,    0,   13,   14,    0,   15,  149,    0,
    0,   14,    0,   15,   14,    0,   15,   20,    0,    0,
    0,    0,    0,    0,    0,  104,   21,  128,    0,  104,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,    0,    0,    0,    0,
    0,    0,    0,    0,   21,  128,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,   20,    0,    0,    0,
    0,    0,  104,    0,   21,   21,    0,    0,    0,    0,
  128,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   43,   41,   45,   41,   41,   43,   40,   45,   41,
   59,   43,   14,   45,   40,   41,  258,   59,   60,    0,
   62,   45,   59,   60,   43,   62,   45,   59,   60,   42,
   62,   10,  257,  275,   47,  126,   18,   42,    0,   42,
   44,   60,   47,   62,   47,   40,  257,   42,   43,   28,
   45,   59,   47,   42,   43,   59,   45,    0,   47,  257,
  151,   61,   86,   41,   42,   43,   48,   45,   41,   47,
   42,   43,   61,   45,   41,   47,   43,  263,   45,  258,
   61,   36,   37,   42,   43,  264,   45,  266,   47,   57,
   42,   43,   60,   45,   47,   47,   44,  276,   59,   61,
   41,  257,   43,  259,   45,   41,   59,   43,   90,   45,
   41,   59,   43,  256,   45,   12,  118,   59,   61,  262,
   17,   76,   77,   59,   60,   43,   62,   45,   59,   60,
  263,   62,   42,   43,    0,   45,   42,   47,   41,   92,
   43,   47,   45,   40,   38,   39,   99,   44,  256,  257,
   60,  259,   62,  258,   59,   59,  138,  139,   59,  127,
  265,  266,    4,   41,   59,   43,   61,   45,   29,   59,
   28,   61,   -1,  256,  257,   59,  259,   61,  131,  139,
   -1,   -1,   -1,   -1,   78,   79,   -1,   -1,   -1,   -1,
   87,   88,   -1,   -1,   59,   -1,   61,   59,   -1,   61,
   -1,   -1,   -1,   -1,   -1,  102,   -1,  256,   -1,   -1,
   -1,   -1,   -1,   -1,  256,   -1,   -1,   61,  115,  256,
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
  261,   -1,  257,   -1,  265,   -1,  261,   -1,   -1,   -1,
  271,    4,  273,  274,   -1,   -1,  271,  272,  273,  274,
    4,   14,  257,   -1,   -1,   18,  261,   -1,   -1,  264,
   14,   -1,   -1,   -1,   18,   -1,  271,   -1,  273,  274,
   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,
  265,   -1,  256,  257,   -1,   48,  271,  261,  273,  274,
   -1,   -1,   -1,   -1,   48,   -1,   -1,  271,  272,  273,
  256,  257,   63,   -1,   -1,  261,  256,  257,  264,  256,
  257,  261,   -1,   -1,  261,  271,   -1,  273,  265,   -1,
   -1,  271,   -1,  273,  271,   -1,  273,   90,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   96,   90,   98,   -1,  100,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  118,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  118,  126,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  138,  139,   -1,   -1,   -1,
   -1,   -1,  143,   -1,  138,  139,   -1,   -1,   -1,   -1,
  151,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=276;
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
"UNTIL","PRINT","RETURN","FUNCTION","\"\"",
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
"bloqueFuncion : \"\"",
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

//#line 187 "gramatica.y"

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

 public Parser(LexicAnalizer la, UIMain v, boolean debugMe)
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
                //tk = (Token)la.nextElement();
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
               if(tk.getToken().equalsIgnoreCase("LOOP"))
                   return LOOP;
               if(tk.getToken().equalsIgnoreCase("UNTIL"))
                   return UNTIL;
               if(tk.getToken().equalsIgnoreCase("BEGIN"))
                   return BEGIN;
               if(tk.getToken().equalsIgnoreCase("END"))
                   return END;
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
//#line 605 "Parser.java"
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
//#line 36 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Declaracion de variables."); }
break;
case 7:
//#line 37 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 11:
//#line 47 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion."); }
break;
case 12:
//#line 48 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
break;
case 13:
//#line 49 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
break;
case 14:
//#line 50 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
break;
case 15:
//#line 51 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
break;
case 16:
//#line 54 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Variables de la funcion."); }
break;
case 17:
//#line 55 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
break;
case 27:
//#line 73 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 28:
//#line 74 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 29:
//#line 75 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 30:
//#line 80 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 31:
//#line 81 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 32:
//#line 82 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 35:
//#line 91 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 36:
//#line 92 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 44:
//#line 108 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Sentencia Invalida"); }
break;
case 45:
//#line 113 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Asignacion."); }
break;
case 46:
//#line 114 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 47:
//#line 115 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 48:
//#line 116 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 52:
//#line 122 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 53:
//#line 123 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 54:
//#line 124 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 55:
//#line 125 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 59:
//#line 131 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 60:
//#line 132 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 61:
//#line 133 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 62:
//#line 134 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 65:
//#line 143 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");  }
break;
case 66:
//#line 144 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 67:
//#line 145 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 68:
//#line 146 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 69:
//#line 147 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 70:
//#line 148 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 71:
//#line 149 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 72:
//#line 150 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 73:
//#line 151 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 74:
//#line 156 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 75:
//#line 157 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 76:
//#line 158 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
break;
case 78:
//#line 162 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 79:
//#line 163 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 80:
//#line 164 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 81:
//#line 165 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 90:
//#line 182 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 91:
//#line 183 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
//#line 946 "Parser.java"
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
