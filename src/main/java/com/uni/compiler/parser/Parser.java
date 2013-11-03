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
    0,    1,    1,    3,    3,    4,    4,    6,    6,    8,
    5,    7,    7,    7,    7,    9,    9,   10,   10,   10,
   11,   11,   11,   11,   11,   14,   14,   14,   14,   14,
   15,   20,   15,   15,   19,   19,   21,   16,   16,    2,
    2,    2,   22,   22,   22,   22,   22,   22,   12,   12,
   12,   12,   17,   17,   17,   17,   17,   17,   17,   26,
   26,   26,   26,   26,   26,   26,   27,   27,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   23,   29,   23,
   23,   18,   18,   18,   18,   18,   30,   30,   30,   30,
   30,   30,   28,   28,   31,   24,   24,   25,
};
final static short yylen[] = {                            2,
    2,    2,    1,    1,    1,    3,    3,    3,    1,    0,
    4,    4,    2,    3,    4,    1,    3,    2,    1,    1,
    2,    1,    2,    1,    1,    4,    3,    2,    3,    4,
    4,    0,    7,    3,    3,    1,    0,    5,    3,    2,
    1,    1,    2,    1,    1,    1,    1,    1,    3,    2,
    3,    2,    3,    3,    1,    3,    2,    3,    2,    3,
    3,    1,    3,    2,    3,    2,    1,    1,    5,    5,
    4,    3,    3,    2,    2,    2,    1,    4,    0,    7,
    3,    5,    4,    5,    4,    4,    1,    1,    1,    1,
    1,    1,    3,    1,    0,    5,    3,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    4,    5,    9,    0,   10,
   48,    0,    0,    0,    0,   42,    0,    0,    2,    0,
   44,   41,   45,   46,   47,    7,    6,    0,    0,   67,
   68,    0,    0,    0,    0,    0,    0,    0,    0,   62,
    0,    0,    0,    0,    0,    0,    0,   76,    0,   40,
   43,    8,    0,    0,   16,   11,    0,    0,    0,   64,
   66,   98,   51,    0,    0,    0,    0,    0,   88,   90,
   91,   92,   87,   89,    0,    0,    0,   81,    0,   97,
    0,   72,    0,   73,   13,    0,    0,    0,    0,    0,
    0,   20,    0,   19,    0,   22,    0,   24,   25,   56,
    0,   58,    0,   63,   60,   65,   61,    0,    0,    0,
    0,   94,    0,    0,   71,    0,    0,   17,    0,    0,
    0,   28,    0,   14,   18,   21,   23,   86,    0,   85,
   83,    0,   79,   96,   70,   69,   15,   12,    0,    0,
   36,   34,   39,    0,   29,   27,    0,   84,   82,   93,
    0,    0,    0,    0,   30,   26,   80,   32,   35,   38,
    0,   33,
};
final static short yydgoto[] = {                          3,
    4,   18,    5,    6,    7,    9,   56,   29,   57,   93,
   94,   95,   96,   97,   98,   99,   42,   43,  142,  161,
  121,   22,   23,   24,   25,   39,   40,  113,  151,   76,
   45,
};
final static short yysindex[] = {                      -225,
 -235, -197,    0,  111,    0,    0,    0,    0,   72,    0,
    0,    6,   12, -240,  -25,    0,   97,  264,    0,    4,
    0,    0,    0,    0,    0,    0,    0, -178, -146,    0,
    0,   -4,   -4, -155, -155,   41,   42,   83,   -2,    0,
   91,  -18, -176,   12,  138,   66,  -38,    0,   83,    0,
    0,    0, -144, -235,    0,    0,  114,   -2,   -2,    0,
    0,    0,    0,   83,  -12,  107,  -80,  -75,    0,    0,
    0,    0,    0,    0,  -18,   97,   97,    0,  144,    0,
  256,    0,  -35,    0,    0,  114,   17,   48,   12, -137,
  -32,    0,  206,    0,   78,    0,   87,    0,    0,    0,
   -2,    0,   -2,    0,    0,    0,    0,   30,  100,  120,
  138,    0, -116,   12,    0,  -48,  181,    0,  187,   12,
  114,    0,   23,    0,    0,    0,    0,    0,   53,    0,
    0,  275,    0,    0,    0,    0,    0,    0,  217,  114,
    0,    0,    0,  225,    0,    0,   60,    0,    0,    0,
  144, -115,  245,   12,    0,    0,    0,    0,    0,    0,
  217,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  141,    1,    0,    0,  152,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  101,  -41,    0,
    0,    0,    0,    0,    0,   20,   39,    0,  103,    0,
    0,    0,    0,    0,    0,    0,    0,  -36,  -31,    0,
    0,    0,    0,  105,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  119,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,    0,   70,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  162,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -28,  163,  -16,    0,  112,    0,    0,  115,  -52,
  422,  466,  489,    0,    0,    0,  526,    3,  -83,    0,
    0,   18,    0,    0,    0,  123,  124,   32,    0,   -1,
    0,
};
final static int YYTABLESIZE=649;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         55,
   77,   55,   84,   55,   57,  116,   57,  123,   57,   59,
  136,   59,   55,   59,   47,   48,   81,   55,   55,   74,
   55,    8,   57,   57,   65,   57,   66,   59,   59,   34,
   59,   44,    1,  117,   35,   50,   55,   34,   75,   67,
   77,   73,   35,   74,   68,   36,   80,   34,   32,    2,
   33,   41,   35,   34,   32,  152,   33,   78,   35,   10,
   28,   77,   51,  146,   34,   32,   37,   33,  144,   35,
  128,   34,   32,  108,   33,  118,   35,  162,   52,   78,
   74,   62,  132,   34,   32,   79,   33,  153,   35,   34,
   32,  119,   33,  149,   35,   65,  112,   66,   50,   75,
  156,   30,   65,   31,   66,   53,   82,   53,   37,   53,
   54,    1,   54,    1,   54,   28,  134,   53,   78,   54,
   85,   54,  143,   53,   53,   65,   53,   66,   54,   54,
   27,   54,   34,   32,  120,   33,  126,   35,   34,   32,
  130,   33,   65,   35,   66,  127,  133,  158,   34,   50,
   73,    1,   74,   35,   58,   59,  160,   60,   61,   52,
  131,   50,   65,   49,   66,   87,   19,   86,  112,   16,
    0,   17,   92,    0,   17,  104,   30,   37,   31,   37,
  106,   30,  157,   31,    0,    0,    0,  101,  103,    0,
  105,  107,    0,    0,    0,    0,   16,    0,   17,   95,
    0,   95,    0,    0,   17,    0,    0,  135,    0,    0,
    0,    0,    0,    0,   55,    0,    0,    0,    0,   57,
  115,   83,   31,  122,   59,   55,   55,   55,   55,    0,
   57,   57,   57,   57,   46,   59,   59,   59,   59,    0,
    0,   17,    0,  100,   30,    0,   31,   17,   69,   70,
   71,   72,   30,    0,   31,    0,   77,   77,    0,    0,
    0,   77,   30,   77,   31,   77,   17,    0,   30,    0,
   31,   77,   77,   77,   77,   74,   74,   17,  145,   30,
   74,   31,   74,    0,   74,   17,   30,    0,   31,    0,
   74,   74,   74,   74,   75,   75,    0,   63,   30,   75,
   31,   75,    0,   75,   30,   17,   31,    0,  148,   75,
   75,   75,   75,   78,   78,  155,   17,    0,   78,    0,
   53,    0,   78,    0,   17,   54,    0,   26,   78,   78,
   78,   53,   53,   53,   53,   17,   54,   54,   54,   54,
    0,    0,    0,    0,    0,    0,    0,   30,    0,   31,
    0,    0,    0,   30,    0,   31,    0,   69,   70,   71,
   72,    0,  102,   30,    0,   31,   11,   12,    1,    0,
   88,   13,    0,    0,   89,   37,    0,    0,    0,   37,
    0,   14,    0,   15,   90,    2,   15,   91,    0,   37,
    0,   37,   37,   11,   12,    0,   95,   95,   13,   11,
   12,   95,    0,    0,   13,    0,    0,  111,   14,    0,
   15,   95,    0,   95,   14,    0,   15,   31,   31,    0,
    0,    0,   31,    0,    0,    0,   31,    0,    0,    0,
    0,    0,   31,   31,   31,   31,  137,   88,    0,    0,
    0,   89,    0,   88,    0,  138,    0,   89,  139,    0,
  140,   90,    0,   15,   91,    0,    0,   90,    0,   15,
   91,    0,   88,    0,    0,    0,   89,    0,    0,   20,
  124,    0,    0,   88,    0,    0,   90,   89,   15,   91,
  140,   88,    0,   20,    0,   89,    0,   90,    0,   15,
   91,    0,   21,    0,    0,   90,  154,   15,   91,    0,
    0,   88,    0,    0,    0,   89,   21,    0,    0,  159,
   20,   11,   12,    0,  125,   90,   13,   15,   91,   11,
   12,    0,    0,    0,   13,    0,   14,  114,   15,    0,
   11,   12,    0,   21,   14,   13,   15,   38,  125,  150,
  141,    0,   49,    0,   20,   14,   20,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  141,    0,   64,    0,    0,  125,   75,   21,    0,   21,
    0,    0,    0,    0,  125,    0,   20,    0,    0,    0,
    0,    0,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,   21,
    0,  109,  110,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   38,    0,    0,   20,    0,    0,    0,
   21,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  129,    0,    0,    0,    0,    0,   21,
    0,    0,    0,    0,    0,    0,    0,    0,  147,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   43,   41,   45,   41,   41,   43,   40,   45,   41,
   59,   43,   29,   45,   40,   41,   45,   59,   60,    0,
   62,  257,   59,   60,   43,   62,   45,   59,   60,   42,
   62,  272,  258,   86,   47,   18,   53,   42,    0,   42,
   42,   60,   47,   62,   47,   40,   44,   42,   43,  275,
   45,   40,   47,   42,   43,  139,   45,    0,   47,  257,
   44,   61,   59,   41,   42,   43,   61,   45,  121,   47,
   41,   42,   43,   75,   45,   59,   47,  161,  257,  256,
   61,   41,  111,   42,   43,  262,   45,  140,   47,   42,
   43,   89,   45,   41,   47,   43,   79,   45,   81,   61,
   41,  257,   43,  259,   45,   41,   41,   43,   61,   45,
   41,  258,   43,  258,   45,   44,  114,  264,   61,  266,
  265,  266,  120,   59,   60,   43,   62,   45,   59,   60,
   59,   62,   42,   43,  272,   45,   59,   47,   42,   43,
   41,   45,   43,   47,   45,   59,  263,  263,   42,  132,
   60,    0,   62,   47,   32,   33,  154,   34,   35,   59,
   41,   59,   43,   59,   45,   54,    4,   53,  151,   59,
   -1,   61,   59,   -1,   61,  256,  257,   59,  259,   61,
  256,  257,  151,  259,   -1,   -1,   -1,   65,   66,   -1,
   67,   68,   -1,   -1,   -1,   -1,   59,   -1,   61,   59,
   -1,   61,   -1,   -1,   61,   -1,   -1,  256,   -1,   -1,
   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,   -1,  256,
  256,  260,   61,  256,  256,  267,  268,  269,  270,   -1,
  267,  268,  269,  270,  260,  267,  268,  269,  270,   -1,
   -1,   61,   -1,  256,  257,   -1,  259,   61,  267,  268,
  269,  270,  257,   -1,  259,   -1,  256,  257,   -1,   -1,
   -1,  261,  257,  263,  259,  265,   61,   -1,  257,   -1,
  259,  271,  272,  273,  274,  256,  257,   61,  256,  257,
  261,  259,  263,   -1,  265,   61,  257,   -1,  259,   -1,
  271,  272,  273,  274,  256,  257,   -1,  256,  257,  261,
  259,  263,   -1,  265,  257,   61,  259,   -1,  256,  271,
  272,  273,  274,  256,  257,  256,   61,   -1,  261,   -1,
  256,   -1,  265,   -1,   61,  256,   -1,  256,  271,  272,
  273,  267,  268,  269,  270,   61,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,   -1,  257,   -1,  259,   -1,  267,  268,  269,
  270,   -1,  256,  257,   -1,  259,  256,  257,  258,   -1,
  257,  261,   -1,   -1,  261,  257,   -1,   -1,   -1,  261,
   -1,  271,   -1,  273,  271,  275,  273,  274,   -1,  271,
   -1,  273,  274,  256,  257,   -1,  256,  257,  261,  256,
  257,  261,   -1,   -1,  261,   -1,   -1,  264,  271,   -1,
  273,  271,   -1,  273,  271,   -1,  273,  256,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,  265,   -1,   -1,   -1,
   -1,   -1,  271,  272,  273,  274,  256,  257,   -1,   -1,
   -1,  261,   -1,  257,   -1,  265,   -1,  261,  262,   -1,
  264,  271,   -1,  273,  274,   -1,   -1,  271,   -1,  273,
  274,   -1,  257,   -1,   -1,   -1,  261,   -1,   -1,    4,
  265,   -1,   -1,  257,   -1,   -1,  271,  261,  273,  274,
  264,  257,   -1,   18,   -1,  261,   -1,  271,   -1,  273,
  274,   -1,    4,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,  257,   -1,   -1,   -1,  261,   18,   -1,   -1,  265,
   45,  256,  257,   -1,   93,  271,  261,  273,  274,  256,
  257,   -1,   -1,   -1,  261,   -1,  271,  272,  273,   -1,
  256,  257,   -1,   45,  271,  261,  273,   12,  117,  265,
  119,   -1,   17,   -1,   79,  271,   81,  273,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  139,   -1,   37,   -1,   -1,  144,   41,   79,   -1,   81,
   -1,   -1,   -1,   -1,  153,   -1,  111,   -1,   -1,   -1,
   -1,   -1,  161,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  132,   -1,  111,
   -1,   76,   77,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   88,   -1,   -1,  151,   -1,   -1,   -1,
  132,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  108,   -1,   -1,   -1,   -1,   -1,  151,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
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
"$$1 :",
"funcion : FUNCTION ID $$1 bloqueFuncion",
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
"returnF : RETURN '(' ')'",
"returnF : RETURN error",
"returnF : RETURN '(' error",
"returnF : RETURN '(' exprAritmetica error",
"seleccionF : IF condicion THEN sentenciaSeleccionF",
"$$2 :",
"seleccionF : IF condicion THEN sentenciaSeleccionF ELSE $$2 sentenciaSeleccionF",
"seleccionF : IF condicion sentenciaSeleccionF",
"sentenciaSeleccionF : BEGIN sentenciasF END",
"sentenciaSeleccionF : sentenciaF",
"$$3 :",
"iteracionF : LOOP $$3 sentenciasF UNTIL condicion",
"iteracionF : LOOP UNTIL condicion",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencias : ';'",
"sentencia : asignacion ';'",
"sentencia : impresion",
"sentencia : seleccion",
"sentencia : iteracion",
"sentencia : llamadaAFuncion",
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
"$$4 :",
"seleccion : IF condicion THEN sentenciaSeleccion ELSE $$4 sentenciaSeleccion",
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
"$$5 :",
"iteracion : LOOP $$5 sentencias UNTIL condicion",
"iteracion : LOOP UNTIL condicion",
"llamadaAFuncion : ID '(' ')'",
};

//#line 254 "gramatica.y"

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

public List<Terceto> tercetoList;
private Stack pilaBranches;	  //para el seguimiento de terceto.
private Stack pilaTerceto;
private Stack pilaLoopLabel;

private Stack pilaBILoop;

private Stack pilaReturn;

private String functionName;
private String nombreFuncion;
private boolean functionNameNext;
private boolean executingFunctionCode;
private boolean errorSemantico;

private int loopLabelNumber;


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
      this.nombreFuncion = "";
      this.tmpId = new ArrayList<Token>();

      this.tercetoList = new ArrayList<Terceto>();
      this.pilaTerceto = new Stack();
      this.pilaBranches = new Stack();
      this.pilaLoopLabel = new Stack();
      this.pilaBILoop = new Stack();
      this.pilaReturn = new Stack();

      this.symbolsTable = st;
      this.functionNameNext = false;
      this.errorSemantico = false; //Para indicarle al generador de Assembler si generar o hubo un error.

      loopLabelNumber = 1;
      executingFunctionCode = false;
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

           if (this.functionNameNext){
              this.functionName = tk.getToken();
              addFunctionToSymbolsTable(tk);
              this.functionNameNext = false;
          }

           //System.out.println(tk.getType());
           
           if(tk.getType().equals("EOF"))
               return ';';
               
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
                        this.errorSemantico = true;
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
                        this.errorSemantico = true;
                    }
                    return END;
               }
               if(tk.getToken().equalsIgnoreCase("IMPORT"))
                   return IMPORT;
               if(tk.getToken().equalsIgnoreCase("RETURN"))
                   return RETURN;
               if(tk.getToken().equalsIgnoreCase("FUNCTION")){
                   this.functionNameNext = true;
               	   return FUNCTION;
               }
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

    private Token getTokenFromSymbolTable(String tokenId){
        for (Token t : this.symbolsTable){
            if (t.getToken().equals(tokenId)){
                return t;
            }
        }
        return null;
    }
        
    private void saveVariableType(String tipo){
        for (Token t: this.tmpId){
            t.setVariableType(tipo);
        }   
    }

    private void addFunctionNameToToken(String functionName, Token t){
      if (!functionName.equals("")){
        t.setLexema(functionName + "&" + t.getToken());
        t.functionName = functionName;
      }
    }

    private void addFunctionToSymbolsTable(Token t){
      if (t.getType().equals("Identificador")){
          Token tokenInSymbolTable = getTokenFromSymbolTable(t.getToken());
          if (tokenInSymbolTable == null){
            this.symbolsTable.add(t);
          }else{
            showErrorParser("Linea "+ t.getLine() +": Error Semantico: La funcion " + t.getToken() + " ya fue declarada");
            this.errorSemantico = true;
          }
        }
    }

    private void addTmpIdToSymbolsTable(){
      for (Token t: this.tmpId){
        if (t.getType().equals("Identificador")){
          Token tokenInSymbolTable = getTokenFromSymbolTable(t.getToken());
          if (tokenInSymbolTable == null){
            this.symbolsTable.add(t);
          }else{
            if (!t.getToken().contains("&")){
              showErrorParser("Linea "+ t.getLine() +": Error Semantico: " + t.getToken() + " ya fue declarado");
              this.errorSemantico = true;
            }
          }
        }
      }
    }

    // si presedencia 1 => operacion, primer Operando, segundo Operando
    // si presedencia 2 => operacion, segundo Operando, primer Operando
    private void crearTerceto(String operador, int presedencia){
      Object operand1 = this.pilaTerceto.pop();
      Object operand2 = this.pilaTerceto.pop();
      Terceto t;
      if (presedencia == 1){
        t = new Terceto(operador, operand1, operand2, null);
      } else {
        t = new Terceto(operador, operand2, operand1, null);
      }
      this.tercetoList.add(t);
      this.pilaTerceto.push(this.tercetoList.size());
    }

    public List<Terceto> getTercetoList(){ return this.tercetoList; }

    private void setDireccionDeSaltoEnTerceto(Integer terceto, int dirDeSalto){
        ((Terceto) this.tercetoList.get(terceto - 1)).setFirstOperand("LABEL" + dirDeSalto);
        //System.out.println("Agregar Dir Salto en Terceto #" + terceto.toString() + " el valor " + dirDeSalto);
        //System.out.println("Terceto: " + ((Terceto) this.tercetoList.get(terceto - 1)).toString());
    }

    private void setDireccionDeSaltoEnTercetoLabelLoop(Integer terceto, int dirDeSalto){
        ((Terceto) this.tercetoList.get(terceto - 1)).setFirstOperand("LABLOOP" + dirDeSalto);
        //System.out.println("Agregar Dir Salto en Terceto #" + terceto.toString() + " el valor " + dirDeSalto);
        //System.out.println("Terceto: " + ((Terceto) this.tercetoList.get(terceto - 1)).toString());
    }

    private void createLabel(){
      Token label = new Token(); label.setLexema("LABEL" + Integer.toString(this.tercetoList.size() + 1));
      this.tercetoList.add(new Terceto("LABEL", label, new Token(), null));
    }

    private void createLabelForLoop(){
      this.pilaLoopLabel.push(this.loopLabelNumber);
      Token label = new Token(); label.setLexema("LABLOOP" + Integer.toString(this.loopLabelNumber));
      this.tercetoList.add(new Terceto("LABEL", label, new Token(), null));
      this.loopLabelNumber++;
    }

    private void checkContain(Token t) {
      String token = t.getToken();
      if (executingFunctionCode){
        token = functionName + "&" + t.getToken();
      }
      if (getTokenFromSymbolTable(token) == null){
        showErrorParser("Linea " + t.getLine() + ": Error Semantico: Variable "+t.getToken()+" no declarada.");
        this.errorSemantico = true;
      }
    }

    private void checkNameMangling(Token t){
      if (executingFunctionCode){
        String s = functionName + "&" + t.getToken();
        t.setLexema(s);
      }
    }
//#line 804 "Parser.java"
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
                                      saveVariableType("INT");
                                      addTmpIdToSymbolsTable();
                                      this.tmpId = new ArrayList<Token>();
                                    }
break;
case 7:
//#line 43 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Error Sintactico : Se esperaba ';'");  }
break;
case 8:
//#line 46 "gramatica.y"
{ addFunctionNameToToken(this.functionName, ((Token)val_peek(0).obj)); this.tmpId.add((Token)val_peek(0).obj);  }
break;
case 9:
//#line 47 "gramatica.y"
{ addFunctionNameToToken(this.functionName, ((Token)val_peek(0).obj)); this.tmpId.add((Token)val_peek(0).obj);  }
break;
case 10:
//#line 50 "gramatica.y"
{ this.tercetoList.add(new Terceto("LABEL", "F" + ((Token)val_peek(0).obj).getToken(), new Token(), null)); }
break;
case 11:
//#line 50 "gramatica.y"
{ executingFunctionCode = false; }
break;
case 12:
//#line 53 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion."); this.functionName = ""; }
break;
case 13:
//#line 55 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
break;
case 14:
//#line 56 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
break;
case 15:
//#line 57 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
break;
case 16:
//#line 60 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Variables de la funcion."); executingFunctionCode = true; }
break;
case 17:
//#line 61 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
break;
case 26:
//#line 78 "gramatica.y"
{ this.tercetoList.add(new Terceto("RET", new Token("EXP"), new Token(), null)); }
break;
case 27:
//#line 79 "gramatica.y"
{ this.tercetoList.add(new Terceto("RET", new Token("0"), new Token(), null)); }
break;
case 28:
//#line 80 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 29:
//#line 81 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 30:
//#line 82 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 31:
//#line 87 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                      setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                    }
break;
case 32:
//#line 90 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("BI", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 33:
//#line 97 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel(); }
break;
case 34:
//#line 98 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 37:
//#line 107 "gramatica.y"
{ createLabelForLoop(); }
break;
case 38:
//#line 107 "gramatica.y"
{ 
                    showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                    setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 39:
//#line 111 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 48:
//#line 131 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": Error Sintactico : Sentencia Invalida"); }
break;
case 49:
//#line 136 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Asignacion.");
                                      checkContain((Token)val_peek(2).obj);
                                      checkNameMangling((Token)val_peek(2).obj);
                                      this.pilaTerceto.push((Token)val_peek(2).obj);
                                      crearTerceto("MOV", 1);
                                    }
break;
case 50:
//#line 143 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 51:
//#line 144 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 52:
//#line 145 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 53:
//#line 148 "gramatica.y"
{ crearTerceto("ADD", 2); }
break;
case 54:
//#line 149 "gramatica.y"
{ crearTerceto("SUB", 2); }
break;
case 56:
//#line 152 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 57:
//#line 153 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 58:
//#line 154 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 59:
//#line 155 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 60:
//#line 158 "gramatica.y"
{ crearTerceto("MUL", 2); }
break;
case 61:
//#line 159 "gramatica.y"
{ crearTerceto("DIV", 2); }
break;
case 63:
//#line 162 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 64:
//#line 163 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 65:
//#line 164 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 66:
//#line 165 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 67:
//#line 168 "gramatica.y"
{ checkContain((Token)val_peek(0).obj); checkNameMangling((Token)val_peek(0).obj); this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 68:
//#line 169 "gramatica.y"
{ this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 69:
//#line 174 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");
                                          this.pilaTerceto.push(new Token());
                                          this.pilaTerceto.push((Token)val_peek(2).obj);
                                          crearTerceto("PRINT", 1);
                                        }
break;
case 70:
//#line 180 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 71:
//#line 181 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 72:
//#line 182 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 73:
//#line 183 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 74:
//#line 184 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 75:
//#line 185 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 76:
//#line 186 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 77:
//#line 187 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 78:
//#line 192 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                 }
break;
case 79:
//#line 196 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("BI", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 80:
//#line 203 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}
break;
case 81:
//#line 205 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
break;
case 82:
//#line 208 "gramatica.y"
{ crearTerceto(((Token)val_peek(2).obj).getToken(), 2);
                                                            this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));
                                                            /*System.out.println("agregue un BF en la posicion " + this.tercetoList.size());*/
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");*/
                                                          }
break;
case 83:
//#line 216 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 84:
//#line 217 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 85:
//#line 218 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 86:
//#line 219 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 95:
//#line 236 "gramatica.y"
{ createLabelForLoop(); }
break;
case 96:
//#line 236 "gramatica.y"
{ 
                    showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                    setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 97:
//#line 240 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 98:
//#line 245 "gramatica.y"
{ nombreFuncion = ((Token)val_peek(2).obj).getToken();
                              showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Llamada a Funcion " + nombreFuncion);
                              /*((Token)$1.obj).getLine();*/
                              this.tercetoList.add(new Terceto("CALL", new Token("F" + ((Token)val_peek(2).obj).getToken()), new Token(), null));
                              /*Donde vuelve la funcion luego de ejecutarse.*/
                            }
break;
//#line 1260 "Parser.java"
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
