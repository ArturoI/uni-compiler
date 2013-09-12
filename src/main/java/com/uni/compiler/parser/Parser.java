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
    0,    1,    1,    1,    3,    3,    4,    4,    6,    6,
    5,    7,    8,    8,    9,    9,    9,   10,   10,   10,
   10,   10,   13,   13,   13,   13,   14,   14,   14,   18,
   18,   15,   15,    2,    2,    2,   19,   19,   19,   19,
   19,   11,   11,   11,   11,   16,   16,   16,   16,   16,
   16,   16,   22,   22,   22,   22,   22,   22,   22,   23,
   23,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   20,   20,   20,   17,   17,   17,   17,   17,   25,   25,
   25,   25,   25,   25,   24,   24,   21,   21,
};
final static short yylen[] = {                            2,
    2,    2,    1,    1,    1,    1,    3,    3,    3,    1,
    3,    4,    1,    3,    2,    1,    1,    2,    1,    2,
    1,    1,    4,    2,    3,    4,    4,    6,    3,    3,
    1,    4,    3,    2,    1,    1,    2,    1,    1,    1,
    1,    3,    2,    3,    2,    3,    3,    1,    3,    2,
    3,    2,    3,    3,    1,    3,    2,    3,    2,    1,
    1,    5,    5,    3,    3,    3,    2,    2,    2,    1,
    4,    6,    3,    5,    4,    5,    4,    4,    1,    1,
    1,    1,    1,    1,    3,    1,    4,    3,
};
final static short yydefred[] = {                         0,
    4,    0,    0,    0,    0,    3,    5,    6,   10,    0,
    0,   41,    0,    0,    0,    0,   36,    0,    0,    2,
    0,   38,   35,   39,   40,    8,    7,    0,    0,   11,
   60,   61,    0,    0,    0,    0,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    0,    0,   69,    0,   34,
   37,    9,    0,   13,    0,    0,    0,   57,   59,   44,
    0,    0,    0,    0,    0,   80,   82,   83,   84,   79,
   81,    0,    0,    0,    0,    0,   86,   73,   88,    0,
   65,    0,   66,    0,    0,    0,    0,   17,    0,   16,
    0,   19,    0,   21,   22,   49,    0,   51,    0,   56,
   53,   58,   54,    0,    0,    0,    0,    0,   87,    0,
   14,    0,    0,    0,   24,    0,   12,   15,   18,   20,
   78,    0,   77,   75,    0,   85,   63,   62,    0,    0,
   31,   29,   33,    0,   25,    0,   76,   74,   72,    0,
    0,   32,   26,   23,    0,   30,   28,
};
final static short yydgoto[] = {                          4,
    5,   19,    6,    7,    8,   10,   30,   55,   89,   90,
   21,   22,   93,   94,   95,   42,   43,  132,   23,   24,
   25,   39,   40,   78,   73,
};
final static short yysindex[] = {                      -189,
    0, -224, -220,    0,  114,    0,    0,    0,    0,   31,
 -219,    0,   12,    6,  150,  -25,    0,   29,  228,    0,
   45,    0,    0,    0,    0,    0,    0, -197, -226,    0,
    0,    0,   -4,   -4, -164, -164,   42,   56,   55,    0,
  110,  -18,  236,    6,  255,   38,  -38,    0,   56,    0,
    0,    0, -224,    0,  131,   55,   55,    0,    0,    0,
   56,  -12,   61, -136,  -88,    0,    0,    0,    0,    0,
    0,  -18,   29,   29,  274,  153,    0,    0,    0,    6,
    0,   41,    0,   -3,    6,  125,  -32,    0,  -55,    0,
   47,    0,   59,    0,    0,    0,   55,    0,   55,    0,
    0,    0,    0,   23,   72,   90, -202,  283,    0,  -48,
    0,  156,    6,  185,    0,   49,    0,    0,    0,    0,
    0,   66,    0,    0,  274,    0,    0,    0,  206,  131,
    0,    0,    0,    6,    0,   69,    0,    0,    0, -137,
  217,    0,    0,    0,  206,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,  128,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   81,  -41,    0,
    0,    0,    0,    0,    0,   20,   39,    0,   82,    0,
    0,    0,    0,    0,    0,  -36,  -31,    0,    0,    0,
   86,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   58,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   84,    0,   89,    0,
    0,    0,    0,    0,    0,    0,   77,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  179,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,  142,  121,    0,  103,    0,    0,  -50,   53,
   50,  448,    0,    0,    0,  492,    3,  -95,   79,    0,
    0,  126,  301,  -62,  -20,
};
final static int YYTABLESIZE=608;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   70,   48,   83,   48,   50,   18,   50,  116,   50,   52,
  128,   52,  107,   52,   47,   48,   45,   48,   48,   67,
   48,   74,   50,   50,   62,   50,   63,   52,   52,   35,
   52,    2,    9,  140,   36,  114,   11,   35,   68,   53,
   28,   70,   36,   71,   29,   41,   79,   35,   33,  147,
   34,  104,   36,   35,   33,  111,   34,   64,   36,   52,
  125,   70,  139,  121,   35,   33,    1,   34,    2,   36,
   35,   33,   37,   34,   28,   36,   71,  108,   81,  141,
   67,  110,  109,   35,   33,    3,   34,  112,   36,   27,
   35,   33,   31,   34,   32,   36,   64,   50,   62,   68,
   63,   65,   35,   51,   91,  119,  138,   36,   62,  144,
   63,   62,  123,   63,   62,  133,   63,  120,   64,  100,
   31,   77,   32,   50,   46,  145,   46,    1,   46,   47,
  124,   47,   62,   47,   63,   91,  142,   71,   91,   45,
   43,  118,   46,   46,   42,   46,   20,   47,   47,   54,
   47,   35,   33,   77,   34,   84,   36,    0,   56,   57,
    0,   91,    0,   91,  131,    0,  118,  102,   31,   70,
   32,   71,   17,    0,   18,    0,    0,    0,   91,   91,
    0,  131,    0,   88,    0,   18,   50,   97,   99,   88,
   91,   18,    0,  118,   91,    0,    0,  131,    0,    0,
    0,   13,    0,   77,    0,   85,    0,  127,   17,  117,
   18,   17,    0,   18,   48,   86,   18,   16,   87,   50,
    0,   82,    0,  115,   52,   48,   48,   48,   48,    0,
   50,   50,   50,   50,   46,   52,   52,   52,   52,   27,
    0,    0,    0,   96,   31,   18,   32,    0,   66,   67,
   68,   69,   31,    0,   32,    0,   70,   70,    0,    0,
    0,   70,   31,   70,   32,   70,   18,    0,   31,    0,
   32,   70,   70,   70,   70,   67,   67,   18,    0,   31,
   67,   32,   67,    0,   67,   31,   26,   32,   18,    0,
   67,   67,   67,   67,   68,   68,   18,   60,   31,   68,
   32,   68,    0,   68,  135,   31,    0,   32,    0,   68,
   68,   68,   68,   64,   64,   18,   98,   31,   64,   32,
   64,  137,   64,    0,  143,    0,    0,    0,   64,   64,
   64,   64,   71,   71,   18,   58,   59,   71,    0,   46,
    0,   71,    0,   18,   47,    0,    0,   71,   71,   71,
   46,   46,   46,   46,    0,   47,   47,   47,   47,    0,
    0,    0,    0,    0,  101,  103,   31,    0,   32,   12,
   13,    2,    0,    0,   14,    0,   66,   67,   68,   69,
    0,   13,    0,    0,   15,   85,   16,   13,    3,    0,
    0,   85,    0,    0,    0,   86,  113,   16,   87,    0,
    0,   86,    0,   16,   87,   12,   13,    0,   12,   13,
   14,    0,   13,   14,    0,    0,   85,  129,    0,  130,
   15,   44,   16,   15,    0,   16,   86,    0,   16,   87,
    0,    0,    0,    0,    0,   27,    0,    0,    0,   27,
    0,   13,    0,   27,    0,   85,    0,    0,    0,   27,
   27,   27,   27,    0,    0,   86,  134,   16,   87,    0,
    0,    0,   13,    0,    0,    0,   85,    0,    0,  130,
    0,    0,    0,   13,    0,    0,   86,   85,   16,   87,
    0,  146,    0,   12,   13,    0,    0,   86,   14,   16,
   87,   12,   13,    0,    0,    0,   14,   75,   15,   76,
   16,    0,   92,    0,   38,    0,   15,    0,   16,   49,
   12,   13,    0,    0,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   15,   80,   16,   61,   12,
   13,    0,   72,   92,   14,    0,   92,   76,   12,   13,
    0,    0,    0,   14,   15,    0,   16,  126,    0,    0,
    0,    0,    0,   15,    0,   16,    0,    0,    0,   92,
    0,   92,    0,    0,  105,  106,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,   92,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   92,    0,
    0,    0,   92,    0,    0,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  136,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   43,   41,   45,   41,   61,   43,   40,   45,   41,
   59,   43,   75,   45,   40,   41,   15,   59,   60,    0,
   62,   42,   59,   60,   43,   62,   45,   59,   60,   42,
   62,  258,  257,  129,   47,   86,  257,   42,    0,  266,
   44,   60,   47,   62,  264,   40,   44,   42,   43,  145,
   45,   72,   47,   42,   43,   59,   45,    0,   47,  257,
  263,   61,  125,   41,   42,   43,  256,   45,  258,   47,
   42,   43,   61,   45,   44,   47,    0,   76,   41,  130,
   61,   41,   80,   42,   43,  275,   45,   85,   47,   59,
   42,   43,  257,   45,  259,   47,   42,   19,   43,   61,
   45,   47,   42,   59,   55,   59,   41,   47,   43,   41,
   45,   43,   41,   45,   43,  113,   45,   59,   61,  256,
  257,   43,  259,   45,   41,  263,   43,    0,   45,   41,
   41,   43,   43,   45,   45,   86,  134,   61,   89,   59,
   59,   89,   59,   60,   59,   62,    5,   59,   60,   29,
   62,   42,   43,   75,   45,   53,   47,   -1,   33,   34,
   -1,  112,   -1,  114,  112,   -1,  114,  256,  257,   60,
  259,   62,   59,   -1,   61,   -1,   -1,   -1,  129,  130,
   -1,  129,   -1,   59,   -1,   61,  108,   62,   63,   59,
  141,   61,   -1,  141,  145,   -1,   -1,  145,   -1,   -1,
   -1,  257,   -1,  125,   -1,  261,   -1,  256,   59,  265,
   61,   59,   -1,   61,  256,  271,   61,  273,  274,  256,
   -1,  260,   -1,  256,  256,  267,  268,  269,  270,   -1,
  267,  268,  269,  270,  260,  267,  268,  269,  270,   61,
   -1,   -1,   -1,  256,  257,   61,  259,   -1,  267,  268,
  269,  270,  257,   -1,  259,   -1,  256,  257,   -1,   -1,
   -1,  261,  257,  263,  259,  265,   61,   -1,  257,   -1,
  259,  271,  272,  273,  274,  256,  257,   61,   -1,  257,
  261,  259,  263,   -1,  265,  257,  256,  259,   61,   -1,
  271,  272,  273,  274,  256,  257,   61,  256,  257,  261,
  259,  263,   -1,  265,  256,  257,   -1,  259,   -1,  271,
  272,  273,  274,  256,  257,   61,  256,  257,  261,  259,
  263,  256,  265,   -1,  256,   -1,   -1,   -1,  271,  272,
  273,  274,  256,  257,   61,   35,   36,  261,   -1,  256,
   -1,  265,   -1,   61,  256,   -1,   -1,  271,  272,  273,
  267,  268,  269,  270,   -1,  267,  268,  269,  270,   -1,
   -1,   -1,   -1,   -1,   64,   65,  257,   -1,  259,  256,
  257,  258,   -1,   -1,  261,   -1,  267,  268,  269,  270,
   -1,  257,   -1,   -1,  271,  261,  273,  257,  275,   -1,
   -1,  261,   -1,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,  271,   -1,  273,  274,  256,  257,   -1,  256,  257,
  261,   -1,  257,  261,   -1,   -1,  261,  262,   -1,  264,
  271,  272,  273,  271,   -1,  273,  271,   -1,  273,  274,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,  261,
   -1,  257,   -1,  265,   -1,  261,   -1,   -1,   -1,  271,
  272,  273,  274,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,  264,
   -1,   -1,   -1,  257,   -1,   -1,  271,  261,  273,  274,
   -1,  265,   -1,  256,  257,   -1,   -1,  271,  261,  273,
  274,  256,  257,   -1,   -1,   -1,  261,  262,  271,  264,
  273,   -1,   55,   -1,   13,   -1,  271,   -1,  273,   18,
  256,  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  271,  272,  273,   37,  256,
  257,   -1,   41,   86,  261,   -1,   89,  264,  256,  257,
   -1,   -1,   -1,  261,  271,   -1,  273,  265,   -1,   -1,
   -1,   -1,   -1,  271,   -1,  273,   -1,   -1,   -1,  112,
   -1,  114,   -1,   -1,   73,   74,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  129,  130,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  141,   -1,
   -1,   -1,  145,   -1,   -1,  104,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  116,
};
}
final static short YYFINAL=4;
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
"declaraciones : error",
"declaracion : variables",
"declaracion : funcion",
"variables : INT conjVariables ';'",
"variables : INT conjVariables error",
"conjVariables : conjVariables ',' ID",
"conjVariables : ID",
"funcion : FUNCTION ID bloqueFuncion",
"bloqueFuncion : BEGIN declaracionFuncion sentenciasF END",
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
"impresion : PRINT '(' STRING",
"impresion : PRINT STRING ')'",
"impresion : PRINT '(' ')'",
"impresion : PRINT STRING",
"impresion : PRINT '('",
"impresion : PRINT ')'",
"impresion : PRINT",
"seleccion : IF condicion THEN sentenciaSeleccion",
"seleccion : IF condicion THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF condicion sentenciaSeleccion",
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

//#line 184 "gramatica.y"

/*_________________________________________________________________________________________________________*/

private LexicAnalizer la;
private Enumeration elements;
//GUI
private UIMain uiMain;
//paneles para UI
private Style errorPanel;//Panel de Errores
private Style lexPanel;  //Panel de Lexema
private Style linePanel; //Panel para marcar errores

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
//#line 593 "Parser.java"
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
case 4:
//#line 29 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Declaracion invalida"); }
break;
case 7:
//#line 36 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Declaracion de variables."); }
break;
case 8:
//#line 37 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 12:
//#line 47 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion."); }
break;
case 13:
//#line 50 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Variables de la funcion."); }
break;
case 14:
//#line 51 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
break;
case 24:
//#line 69 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 25:
//#line 70 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 26:
//#line 71 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 27:
//#line 76 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 28:
//#line 77 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 29:
//#line 78 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 32:
//#line 87 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 33:
//#line 88 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 41:
//#line 104 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Sentencia invalida"); }
break;
case 42:
//#line 109 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Asignacion."); }
break;
case 43:
//#line 110 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 44:
//#line 111 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 45:
//#line 112 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 49:
//#line 118 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 50:
//#line 119 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 51:
//#line 120 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 52:
//#line 121 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 56:
//#line 127 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 57:
//#line 128 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 58:
//#line 129 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 59:
//#line 130 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 62:
//#line 139 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");  }
break;
case 63:
//#line 140 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 64:
//#line 141 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 65:
//#line 142 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 66:
//#line 143 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 67:
//#line 144 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 68:
//#line 145 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 69:
//#line 146 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 70:
//#line 147 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 71:
//#line 152 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF"); }
break;
case 72:
//#line 153 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 73:
//#line 154 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 75:
//#line 159 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 76:
//#line 160 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 77:
//#line 161 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 78:
//#line 162 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 87:
//#line 179 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 88:
//#line 180 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
//#line 922 "Parser.java"
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
