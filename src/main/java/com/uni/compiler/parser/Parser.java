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
public final static short ID=257;
public final static short INT=258;
public final static short CTEINT=259;
public final static short STRING=260;
public final static short IF=261;
public final static short THEN=262;
public final static short ELSE=263;
public final static short BEGIN=264;
public final static short END=265;
public final static short IMPORT=266;
public final static short MENORIGUAL=267;
public final static short MAYORIGUAL=268;
public final static short IGUAL=269;
public final static short DISTINTO=270;
public final static short LOOP=271;
public final static short UNTIL=272;
public final static short PRINT=273;
public final static short RETURN=274;
public final static short FUNCTION=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    3,    0,    1,    1,    4,    4,    5,    5,    7,    7,
    9,    6,    8,    8,    8,    8,   10,   10,   11,   11,
   11,   12,   12,   12,   12,   12,   15,   15,   15,   15,
   15,   16,   21,   16,   16,   20,   20,   22,   17,   17,
    2,    2,    2,   23,   23,   23,   23,   23,   23,   13,
   13,   13,   13,   18,   18,   18,   18,   18,   18,   18,
   27,   27,   27,   27,   27,   27,   27,   28,   28,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   24,   30,
   24,   24,   19,   19,   19,   19,   19,   31,   31,   31,
   31,   31,   31,   29,   29,   32,   25,   25,   26,
};
final static short yylen[] = {                            2,
    0,    3,    2,    1,    1,    1,    3,    3,    3,    1,
    0,    4,    4,    2,    3,    4,    1,    3,    2,    1,
    1,    2,    1,    2,    1,    1,    4,    3,    2,    3,
    4,    4,    0,    7,    3,    3,    1,    0,    5,    3,
    2,    1,    1,    2,    1,    1,    1,    1,    1,    3,
    2,    3,    2,    3,    3,    1,    3,    2,    3,    2,
    3,    3,    1,    3,    2,    3,    2,    1,    1,    5,
    5,    4,    3,    3,    2,    2,    2,    1,    4,    0,
    7,    3,    5,    4,    5,    4,    4,    1,    1,    1,
    1,    1,    1,    3,    1,    0,    5,    3,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    4,    5,    6,   10,    0,   11,
    0,    3,    8,    7,    0,    0,   49,    0,    0,    0,
    0,   43,    0,    0,    0,   45,   42,   46,   47,   48,
    9,    0,    0,   17,   12,    0,   68,   69,    0,    0,
    0,    0,    0,    0,    0,    0,   63,    0,    0,    0,
    0,    0,    0,    0,   77,    0,   41,   44,   14,    0,
    0,    0,    0,    0,    0,   21,    0,   20,    0,   23,
    0,   25,   26,    0,    0,   65,   67,   99,   52,    0,
    0,    0,    0,    0,   89,   91,   92,   93,   88,   90,
    0,    0,    0,   82,    0,   98,    0,   73,    0,   74,
    0,   18,    0,    0,    0,   29,    0,   15,   19,   22,
   24,   57,    0,   59,    0,   64,   61,   66,   62,    0,
    0,    0,    0,   95,    0,    0,   72,    0,   16,   13,
    0,    0,   37,   35,   40,    0,   30,   28,    0,   87,
    0,   86,   84,    0,   80,   97,   71,   70,    0,    0,
    0,   31,   27,   85,   83,   94,    0,   33,   36,   39,
   81,    0,   34,
};
final static short yydgoto[] = {                          3,
    4,   24,   11,    5,    6,    7,    9,   35,   16,   36,
   67,   68,   69,   70,   71,   72,   73,   49,   50,  134,
  162,  105,   27,   28,   29,   30,   46,   47,  125,  157,
   92,   52,
};
final static short yysindex[] = {                      -241,
 -244, -235,    0, -241,    0,    0,    0,    0,   72,    0,
  117,    0,    0,    0, -197, -178,    0,    6,   12, -203,
  -25,    0,   97,  266,   33,    0,    0,    0,    0,    0,
    0,  -83, -244,    0,    0,   96,    0,    0,   -4,   -4,
 -212, -212,   56,   42,   92,   81,    0,   91,  -18, -206,
   12,  117,   66,  -38,    0,   92,    0,    0,    0,   96,
   82,   48,   12, -152,  -32,    0,  195,    0,   84,    0,
   87,    0,    0,   81,   81,    0,    0,    0,    0,   92,
  -12,  107,  -88,  -72,    0,    0,    0,    0,    0,    0,
  -18,   97,   97,    0,  236,    0,  245,    0,  -35,    0,
  162,    0,  180,   12,   96,    0,   23,    0,    0,    0,
    0,    0,   81,    0,   81,    0,    0,    0,    0,   30,
  121,  122,  117,    0, -113,   12,    0,  -48,    0,    0,
  200,   96,    0,    0,    0,  206,    0,    0,   53,    0,
   60,    0,    0,  263,    0,    0,    0,    0, -111,  225,
   12,    0,    0,    0,    0,    0,  236,    0,    0,    0,
    0,  200,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  120,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  138,
    1,    0,    0,  177,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  129,  -41,    0,    0,    0,    0,
    0,    0,   20,   39,    0,  130,    0,    0,    0,    0,
    0,    0,    0,  111,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -36,  -31,    0,    0,    0,    0,  131,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   65,    0,   70,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -19,    0,  176,   67,    0,  159,    0,    0,  161,
   16,   55,   50,  435,    0,    0,    0,  503,  324,  -99,
    0,    0,   17,    0,    0,    0,   -3,   76,   38,    0,
   -9,    0,
};
final static int YYTABLESIZE=623;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         56,
   78,   56,  100,   56,   58,  128,   58,  107,   58,   60,
  148,   60,    8,   60,   54,   55,    1,   56,   56,   75,
   56,   10,   58,   58,   81,   58,   82,   60,   60,   41,
   60,  149,   97,    2,   42,   74,   75,   41,   76,   93,
   57,   89,   42,   90,   37,   43,   38,   41,   39,   94,
   40,   48,   42,   41,   39,   95,   40,   79,   42,   31,
   25,   78,  163,  138,   41,   39,   44,   40,   51,   42,
  140,   41,   39,   25,   40,  101,   42,  113,  115,    1,
   75,  120,   34,   41,   39,   32,   40,   33,   42,   41,
   39,   58,   40,  153,   42,   81,   78,   82,   34,   76,
  155,   25,   81,  144,   82,   54,   98,   54,   44,   54,
   55,  124,   55,   57,   55,   15,   76,   77,   79,  104,
  136,  109,   83,   54,   54,   15,   54,   84,   55,   55,
   14,   55,   41,   39,   81,   40,   82,   42,   41,   39,
  102,   40,  110,   42,   25,  111,   25,  150,   41,  145,
   89,  158,   90,   42,   66,  109,   23,  133,  117,  119,
   57,  142,  143,   81,   81,   82,   82,  116,   37,   38,
   38,   38,   25,  124,    1,   22,    2,   23,    1,   12,
    1,   59,   33,  118,   37,  133,   38,   53,   51,   50,
  109,   61,   60,   25,  161,    0,   96,    0,   96,    0,
    0,   32,    0,    0,  109,    0,   25,  147,    0,    0,
    0,    0,    0,    0,   56,    0,  133,    0,    0,   58,
  127,   99,   23,  106,   60,   56,   56,   56,   56,    0,
   58,   58,   58,   58,   53,   60,   60,   60,   60,    0,
   23,    0,    0,  112,   37,    0,   38,    0,   85,   86,
   87,   88,   37,    0,   38,   23,   78,   78,    0,    0,
   23,   78,   37,   78,   38,   78,   23,    0,   37,    0,
   38,   78,   78,   78,   78,   75,   75,    0,  137,   37,
   75,   38,   75,    0,   75,   23,   37,    0,   38,    0,
   75,   75,   75,   75,   76,   76,   23,   79,   37,   76,
   38,   76,    0,   76,   37,   23,   38,    0,  152,   76,
   76,   76,   76,   79,   79,  154,    0,    0,   79,    0,
   54,    0,   79,   23,    0,   55,   23,   13,   79,   79,
   79,   54,   54,   54,   54,    0,   55,   55,   55,   55,
    0,    0,    0,    0,    0,    0,    0,   37,    0,   38,
    0,    0,   62,   37,    0,   38,   63,   85,   86,   87,
   88,    0,  114,   37,    0,   38,   64,   38,   21,   65,
    0,   38,   17,   18,   96,    1,    1,   19,    0,    0,
    1,   38,    0,   38,   38,    0,  103,   20,    0,   21,
    1,    0,    1,   96,   96,    0,   32,   32,   96,    0,
    0,   32,    0,    0,    0,   32,    0,    0,   96,    0,
   96,   32,   32,   32,   32,    0,    0,  129,   62,    0,
    0,    0,   63,    0,    0,    0,  130,  135,    0,    0,
    0,    0,   64,    0,   21,   65,   62,    0,    0,    0,
   63,  131,    0,  132,    0,   26,    0,    0,    0,  146,
   64,   62,   21,   65,    0,   63,   62,    0,   26,  108,
   63,    0,   62,  132,    0,   64,   63,   21,   65,    0,
   64,    0,   21,   65,  160,    0,   64,  151,   21,   65,
    0,   62,    0,    0,    0,   63,   26,    0,    0,  159,
    0,   17,   18,    0,    0,   64,   19,   21,   65,  123,
   17,   18,    0,    0,    0,   19,   20,    0,   21,    0,
    0,    0,    0,    0,    0,   20,  126,   21,   17,   18,
   45,   17,   18,   19,    0,   56,   19,  156,    0,   26,
    0,   26,    0,   20,    0,   21,   20,    0,   21,    0,
    0,    0,    0,    0,    0,    0,   80,    0,    0,    0,
   91,    0,    0,    0,    0,    0,    0,   26,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   26,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   26,    0,    0,  121,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  139,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   43,   41,   45,   41,   41,   43,   40,   45,   41,
   59,   43,  257,   45,   40,   41,  258,   59,   60,    0,
   62,  257,   59,   60,   43,   62,   45,   59,   60,   42,
   62,  131,   52,  275,   47,   39,   40,   42,    0,   49,
   24,   60,   47,   62,  257,   40,  259,   42,   43,  256,
   45,   40,   47,   42,   43,  262,   45,    0,   47,  257,
   11,   61,  162,   41,   42,   43,   61,   45,  272,   47,
   41,   42,   43,   24,   45,   60,   47,   81,   82,  258,
   61,   91,   16,   42,   43,  264,   45,  266,   47,   42,
   43,   59,   45,   41,   47,   43,   41,   45,   32,   61,
   41,   52,   43,  123,   45,   41,   41,   43,   61,   45,
   41,   95,   43,   97,   45,   44,   41,   42,   61,  272,
  105,   67,   42,   59,   60,   44,   62,   47,   59,   60,
   59,   62,   42,   43,   43,   45,   45,   47,   42,   43,
   59,   45,   59,   47,   95,   59,   97,  132,   42,  263,
   60,  263,   62,   47,   59,  101,   61,  103,   83,   84,
  144,   41,   41,   43,   43,   45,   45,  256,  257,   59,
  259,   61,  123,  157,  258,   59,    0,   61,   59,    4,
   61,  265,  266,  256,  257,  131,  259,   59,   59,   59,
  136,   33,   32,  144,  157,   -1,   59,   -1,   61,   -1,
   -1,   61,   -1,   -1,  150,   -1,  157,  256,   -1,   -1,
   -1,   -1,   -1,   -1,  256,   -1,  162,   -1,   -1,  256,
  256,  260,   61,  256,  256,  267,  268,  269,  270,   -1,
  267,  268,  269,  270,  260,  267,  268,  269,  270,   -1,
   61,   -1,   -1,  256,  257,   -1,  259,   -1,  267,  268,
  269,  270,  257,   -1,  259,   61,  256,  257,   -1,   -1,
   61,  261,  257,  263,  259,  265,   61,   -1,  257,   -1,
  259,  271,  272,  273,  274,  256,  257,   -1,  256,  257,
  261,  259,  263,   -1,  265,   61,  257,   -1,  259,   -1,
  271,  272,  273,  274,  256,  257,   61,  256,  257,  261,
  259,  263,   -1,  265,  257,   61,  259,   -1,  256,  271,
  272,  273,  274,  256,  257,  256,   -1,   -1,  261,   -1,
  256,   -1,  265,   61,   -1,  256,   61,  256,  271,  272,
  273,  267,  268,  269,  270,   -1,  267,  268,  269,  270,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,
   -1,   -1,  257,  257,   -1,  259,  261,  267,  268,  269,
  270,   -1,  256,  257,   -1,  259,  271,  257,  273,  274,
   -1,  261,  256,  257,   51,  256,  257,  261,   -1,   -1,
  261,  271,   -1,  273,  274,   -1,   63,  271,   -1,  273,
  271,   -1,  273,  256,  257,   -1,  256,  257,  261,   -1,
   -1,  261,   -1,   -1,   -1,  265,   -1,   -1,  271,   -1,
  273,  271,  272,  273,  274,   -1,   -1,  256,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,  265,  104,   -1,   -1,
   -1,   -1,  271,   -1,  273,  274,  257,   -1,   -1,   -1,
  261,  262,   -1,  264,   -1,   11,   -1,   -1,   -1,  126,
  271,  257,  273,  274,   -1,  261,  257,   -1,   24,  265,
  261,   -1,  257,  264,   -1,  271,  261,  273,  274,   -1,
  271,   -1,  273,  274,  151,   -1,  271,  272,  273,  274,
   -1,  257,   -1,   -1,   -1,  261,   52,   -1,   -1,  265,
   -1,  256,  257,   -1,   -1,  271,  261,  273,  274,  264,
  256,  257,   -1,   -1,   -1,  261,  271,   -1,  273,   -1,
   -1,   -1,   -1,   -1,   -1,  271,  272,  273,  256,  257,
   18,  256,  257,  261,   -1,   23,  261,  265,   -1,   95,
   -1,   97,   -1,  271,   -1,  273,  271,   -1,  273,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   44,   -1,   -1,   -1,
   48,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   -1,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  144,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  157,   -1,   -1,   92,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  107,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  120,
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
"$$1 :",
"programa : declaraciones $$1 sentencias",
"declaraciones : declaraciones declaracion",
"declaraciones : declaracion",
"declaracion : variables",
"declaracion : funcion",
"variables : INT conjVariables ';'",
"variables : INT conjVariables error",
"conjVariables : conjVariables ',' ID",
"conjVariables : ID",
"$$2 :",
"funcion : FUNCTION ID $$2 bloqueFuncion",
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
"$$3 :",
"seleccionF : IF condicion THEN sentenciaSeleccionF ELSE $$3 sentenciaSeleccionF",
"seleccionF : IF condicion sentenciaSeleccionF",
"sentenciaSeleccionF : BEGIN sentenciasF END",
"sentenciaSeleccionF : sentenciaF",
"$$4 :",
"iteracionF : LOOP $$4 sentenciasF UNTIL condicion",
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
"$$5 :",
"seleccion : IF condicion THEN sentenciaSeleccion ELSE $$5 sentenciaSeleccion",
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
"$$6 :",
"iteracion : LOOP $$6 sentencias UNTIL condicion",
"iteracion : LOOP UNTIL condicion",
"llamadaAFuncion : ID '(' ')'",
};

//#line 259 "gramatica.y"

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

private int cantidadErrores=0;

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
private Stack pilaOperators;

private String functionName;
private String nombreFuncion;

private boolean functionNameNext;
private boolean executingFunctionCode;
private boolean errorSemantico;

public ArrayList<String> errorList = new ArrayList<String>();

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
      this.pilaOperators = new Stack();

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
	    //    errorPanel.setNegrita(s);
	    //    errorPanel.newLine();	
	    errorList.add(s);
	        cantidadErrores++;
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
      /*if (operand1 instanceof Integer){
        Object aux = operand1;
        operand1 = this.tercetoList.get(((Integer)aux).intValue() - 1);
      }
      if (operand2 instanceof Integer){
        Object aux2 = operand2;
        operand2 = this.tercetoList.get(((Integer)aux2).intValue() - 1);
      }*/
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
    
    public boolean compilar(){
    return (this.cantidadErrores > 1);
    }
//#line 822 "Parser.java"
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
case 1:
//#line 23 "gramatica.y"
{this.tercetoList.add(new Terceto ("LABELF","inicio",new Token(),null));}
break;
case 7:
//#line 37 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Declaracion de variables."); 
                                      saveVariableType("INT");
                                      addTmpIdToSymbolsTable();
                                      this.tmpId = new ArrayList<Token>();
                                    }
break;
case 8:
//#line 43 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": Error Sintactico : Se esperaba ';'");  }
break;
case 9:
//#line 46 "gramatica.y"
{ ((Token)val_peek(0).obj).setIsVariable(true); addFunctionNameToToken(this.functionName, ((Token)val_peek(0).obj)); this.tmpId.add((Token)val_peek(0).obj);  }
break;
case 10:
//#line 47 "gramatica.y"
{ ((Token)val_peek(0).obj).setIsVariable(true); addFunctionNameToToken(this.functionName, ((Token)val_peek(0).obj)); this.tmpId.add((Token)val_peek(0).obj);  }
break;
case 11:
//#line 50 "gramatica.y"
{ this.tercetoList.add(new Terceto("LABELF", "F" + ((Token)val_peek(0).obj).getToken(), new Token(), null)); }
break;
case 12:
//#line 50 "gramatica.y"
{ executingFunctionCode = false; }
break;
case 13:
//#line 53 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion.");
                                                           this.functionName = "";
                                                           this.tercetoList.add(new Terceto("RET", new Token("0"), new Token(), null)); 
                                                         }
break;
case 14:
//#line 58 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
break;
case 15:
//#line 59 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
break;
case 16:
//#line 60 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
break;
case 17:
//#line 63 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Variables de la funcion."); executingFunctionCode = true; }
break;
case 18:
//#line 64 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
break;
case 27:
//#line 81 "gramatica.y"
{ this.tercetoList.add(new Terceto("RET", new Token("EXP"), new Token(), null)); }
break;
case 28:
//#line 82 "gramatica.y"
{ this.tercetoList.add(new Terceto("RET", new Token("0"), new Token(), null)); }
break;
case 29:
//#line 83 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 30:
//#line 84 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 31:
//#line 85 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 32:
//#line 90 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                      setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                    }
break;
case 33:
//#line 93 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 34:
//#line 100 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel(); }
break;
case 35:
//#line 101 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 38:
//#line 110 "gramatica.y"
{ createLabelForLoop(); }
break;
case 39:
//#line 110 "gramatica.y"
{ 
                    showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                    setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 40:
//#line 114 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 49:
//#line 134 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": Error Sintactico : Sentencia Invalida"); }
break;
case 50:
//#line 139 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Asignacion.");
                                      checkContain((Token)val_peek(2).obj);
                                      checkNameMangling((Token)val_peek(2).obj);
                                      this.pilaTerceto.push((Token)val_peek(2).obj);
                                      crearTerceto("MOV", 1);
                                    }
break;
case 51:
//#line 146 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 52:
//#line 147 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 53:
//#line 148 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 54:
//#line 151 "gramatica.y"
{ crearTerceto("ADD", 2); }
break;
case 55:
//#line 152 "gramatica.y"
{ crearTerceto("SUB", 2); }
break;
case 57:
//#line 155 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 58:
//#line 156 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 59:
//#line 157 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 60:
//#line 158 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 61:
//#line 161 "gramatica.y"
{ crearTerceto("MUL", 2); }
break;
case 62:
//#line 162 "gramatica.y"
{ crearTerceto("DIV", 2); }
break;
case 64:
//#line 165 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 65:
//#line 166 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 66:
//#line 167 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 67:
//#line 168 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 68:
//#line 171 "gramatica.y"
{ checkContain((Token)val_peek(0).obj); checkNameMangling((Token)val_peek(0).obj); this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 69:
//#line 172 "gramatica.y"
{ this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 70:
//#line 177 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");
                                          this.pilaTerceto.push(new Token());
                                          this.pilaTerceto.push((Token)val_peek(2).obj);
                                          crearTerceto("PRINT", 1);
                                        }
break;
case 71:
//#line 183 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 72:
//#line 184 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 73:
//#line 185 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 74:
//#line 186 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 75:
//#line 187 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 76:
//#line 188 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 77:
//#line 189 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 78:
//#line 190 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 79:
//#line 195 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                  createLabel();
                                                 }
break;
case 80:
//#line 200 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 81:
//#line 207 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}
break;
case 82:
//#line 209 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
break;
case 83:
//#line 212 "gramatica.y"
{ crearTerceto("CMP", 1);
                                                            this.tercetoList.add(new Terceto((String) this.pilaOperators.pop(), new Token(), new Token(), null));
                                                            /*System.out.println("agregue un BF en la posicion " + this.tercetoList.size());*/
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            /*this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));*/
                                                            /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");*/
                                                          }
break;
case 84:
//#line 221 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 85:
//#line 222 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 86:
//#line 223 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 87:
//#line 224 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 88:
//#line 227 "gramatica.y"
{ pilaOperators.push("JAE"); }
break;
case 89:
//#line 228 "gramatica.y"
{ pilaOperators.push("JA"); }
break;
case 90:
//#line 229 "gramatica.y"
{ pilaOperators.push("JBE"); }
break;
case 91:
//#line 230 "gramatica.y"
{ pilaOperators.push("JB"); }
break;
case 92:
//#line 231 "gramatica.y"
{ pilaOperators.push("JNE"); }
break;
case 93:
//#line 232 "gramatica.y"
{ pilaOperators.push("JE"); }
break;
case 96:
//#line 241 "gramatica.y"
{ createLabelForLoop(); }
break;
case 97:
//#line 241 "gramatica.y"
{ 
                  showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                  setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 98:
//#line 245 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 99:
//#line 250 "gramatica.y"
{ nombreFuncion = ((Token)val_peek(2).obj).getToken();
                              showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Llamada a Funcion " + nombreFuncion);
                              /*((Token)$1.obj).getLine();*/
                              this.tercetoList.add(new Terceto("CALL", new Token("F" + ((Token)val_peek(2).obj).getToken()), new Token(), null));
                              /*Donde vuelve la funcion luego de ejecutarse.*/
                            }
break;
//#line 1311 "Parser.java"
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
