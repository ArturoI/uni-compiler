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

//#line 27 "Parser.java"




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
    0,    1,    1,    3,    3,    4,    6,    6,    5,    5,
    7,    8,    8,    9,    9,    9,    9,   11,   11,   11,
   11,    2,    2,    2,   10,   10,   10,   10,   10,   13,
   13,   13,   13,   12,   12,   12,   12,   12,   12,   12,
   17,   17,   17,   17,   17,   17,   17,   18,   18,   14,
   14,   14,   14,   14,   14,   14,   14,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   19,   19,   21,
   21,   21,   21,   21,   21,   20,   20,   16,   16,
};
final static short yylen[] = {                            2,
    2,    3,    2,    1,    1,    2,    3,    1,    3,    2,
    4,    2,    3,    2,    2,    1,    1,    4,    3,    3,
    3,    3,    1,    1,    1,    1,    1,    1,    1,    3,
    2,    3,    2,    3,    3,    1,    3,    2,    3,    2,
    3,    3,    1,    3,    2,    3,    2,    1,    1,    4,
    3,    3,    3,    2,    2,    2,    1,    6,    8,    5,
    5,    5,    5,    7,    7,    7,    7,    3,    2,    1,
    1,    1,    1,    1,    1,    3,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    4,    5,    8,    0,    0,
    0,   10,   29,    0,    0,    0,    0,   24,    0,    0,
    0,   23,   25,   26,   27,   28,    3,    0,    9,    0,
    0,    0,   48,   49,    0,    0,    0,    0,    0,    0,
    0,   43,   71,   73,   74,   75,    0,   70,   72,    0,
    0,    0,    0,    0,    0,    0,   56,    0,    0,    2,
    7,    0,   12,    0,    0,   16,   17,   32,    0,    0,
    0,   45,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   79,    0,   52,    0,   53,   22,   13,    0,
    0,   11,   14,   15,   37,    0,   39,    0,   44,   41,
   46,   42,    0,    0,    0,    0,    0,   78,   50,   20,
    0,   19,    0,    0,    0,    0,    0,    0,    0,   18,
    0,   77,    0,    0,    0,    0,    0,   76,   66,   65,
    0,   67,   64,   59,
};
final static short yydgoto[] = {                          3,
    4,   20,    5,    6,    7,    9,   12,   32,   65,  114,
   67,   50,   23,   24,   25,   26,   41,   42,   51,  115,
   52,
};
final static short yysindex[] = {                      -238,
 -250, -210,    0,  402,  -30,    0,    0,    0,  -18, -221,
 -231,    0,    0,   52,  -32,  422,  -38,    0,   96,  431,
   -7,    0,    0,    0,    0,    0,    0, -199,    0, -250,
    6,  455,    0,    0,   74,  -33,  -33, -178, -178,   48,
   -9,    0,    0,    0,    0,    0,  544,    0,    0,  -21,
   39,   96,  -26,  371,   46,  -37,    0,   48,   42,    0,
    0,  -19,    0,    8,  -55,    0,    0,    0,   48,   -9,
   -9,    0,    0,  121,  143, -200, -112, -170,  -41,   96,
 -159,   48,    0,  -26,    0,   68,    0,    0,    0,   30,
   33,    0,    0,    0,    0,   -9,    0,   -9,    0,    0,
    0,    0,  476,  476,  463,   48,  476,    0,    0,    0,
   55,    0,  425,   56, -149, -145,  476, -143, -139,    0,
  485,    0,  476,  476, -138,  476,  476,    0,    0,    0,
  476,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   72,    0,
    0,    0,    0,    0,    0,    0,  153,    0,    0,  135,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  173,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  193,  217,    0,  239,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  261,   23,
   45,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  111,    0,    0,    0,  283,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   67,    0,   89,    0,    0,
    0,    0,    0,    0,    0,  133,    0,    0,    0,    0,
  444,    0,    0,    0,  305,  327,    0,  352,  374,    0,
    0,    0,    0,    0,  396,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -11,  132,  129,    0,  116,  144,    0,    0,  499,
   90,  123,    0,    0,    0,    0,   -5,   84,  -35,  647,
  106,
};
final static int YYTABLESIZE=814;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
   36,   56,   57,   87,   54,   19,    8,   47,   38,   38,
   36,   79,   37,   39,   39,   38,   36,   83,   37,    1,
   39,   74,   38,   75,   28,   28,    1,   48,   27,   49,
   70,   71,   76,   48,   30,   49,    2,   77,   48,   89,
   49,   36,   11,   36,   40,   36,   10,   90,  108,   38,
   36,   60,   37,   11,   39,   99,   33,   61,   34,   36,
   36,   36,   36,   38,   63,   38,   34,   38,   96,   98,
  110,   38,   36,  112,   37,   74,   39,   75,   33,   81,
   34,   38,   38,   38,   38,   40,   85,   40,   35,   40,
   74,  103,   75,   38,   36,  120,   37,   74,   39,   75,
   88,  121,  107,   40,   40,   40,   40,   34,  109,   34,
   69,   34,   35,  123,  122,   38,   36,  124,   37,  126,
   39,   72,   73,  127,  131,   34,   34,   34,   34,   35,
    6,   35,   68,   35,    1,   21,   40,   38,   36,   31,
   37,   58,   39,  101,   33,   62,   34,   35,   35,   35,
   35,   69,   57,   29,   94,   80,    0,   69,    0,  100,
  102,    0,   38,    0,    0,    0,    0,   39,    0,   69,
    0,   69,   33,   68,   82,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   38,    0,   91,    0,    0,   39,
    0,   68,   54,   68,    0,    0,    0,    0,    0,    0,
   13,   14,  106,    0,    0,   15,    0,    0,    0,   92,
    0,   57,  111,   57,    0,   16,   55,   17,   64,    0,
  104,   55,   86,   33,   33,   34,   34,    0,    0,    0,
   33,   33,   34,   33,   43,   44,   45,   46,   31,    0,
   43,   44,   45,   46,    0,   43,   44,   45,   46,    0,
    0,   54,    0,   54,    0,    0,   36,   36,    0,    0,
   30,   36,   36,    0,   33,   36,   34,   36,   36,   36,
   36,   36,   36,   36,   36,   55,    0,   55,   38,   38,
    0,    0,   51,   38,   38,    0,   33,   38,   34,   38,
   38,   38,   38,   38,   38,   38,   38,   31,    0,   31,
   40,   40,    0,    0,   62,   40,   40,    0,   33,   40,
   34,   40,   40,   40,   40,   40,   40,   40,   40,   30,
    0,   30,   34,   34,    0,    0,   61,   34,   34,   68,
   33,   34,   34,   34,   34,   34,   34,   34,   34,   34,
   34,   51,    0,   51,   35,   35,    0,    0,    0,   35,
   35,   63,   33,   35,   34,   35,   35,   35,   35,   35,
   35,   35,   35,   62,    0,   62,   69,   69,    0,    0,
    0,   69,   69,   60,    0,   69,   95,   33,    0,   34,
    0,   69,   69,   69,   69,   61,    0,   61,   68,   68,
    0,    0,    0,   68,   68,   58,    0,   68,   97,   33,
    0,   34,    0,   68,   68,   68,   68,    0,   57,   57,
   63,    0,   63,   57,    0,    0,    0,   57,    0,    0,
    0,    0,    0,   57,   57,   57,   57,    0,   33,   33,
    0,   19,   60,   33,   60,    0,    0,   33,    0,    0,
    0,    0,    0,   33,   33,   33,   33,    0,   54,   54,
    0,    0,    0,   54,   58,    0,   58,   54,    0,    0,
   18,    0,   19,   54,   54,   54,   54,    0,    0,    0,
    0,    0,   55,   55,    0,    0,    0,   55,    0,    0,
   18,   55,   19,   18,    0,   19,    0,   55,   55,   55,
   55,   19,    0,    0,   31,   31,    0,    0,    0,   31,
    0,    0,   22,   31,   21,    0,    0,    0,    0,   31,
   31,   31,   31,    0,   22,   19,   30,   30,   59,    0,
    0,   30,    0,   19,    0,   30,    0,    0,    0,    0,
   66,   30,   30,   30,   30,    0,   19,    0,   51,   51,
    0,    0,    0,   51,    0,   19,    0,   51,    0,    0,
    0,    0,   59,   51,   51,   51,   51,    0,    0,    0,
   62,   62,    0,   93,    0,   62,    0,    0,    0,   62,
    0,    0,    0,    0,    0,   62,   62,   62,   62,    0,
    0,    0,   61,   61,   78,   38,   36,   61,   37,    0,
   39,   61,    0,    0,    0,    0,    0,   61,   61,   61,
   61,    0,    0,   48,    0,   49,    0,   63,   63,    0,
    0,   22,   63,    0,    0,    0,   63,    0,    0,   59,
    0,    0,   63,   63,   63,   63,   13,   14,    0,   60,
   60,   15,    0,    0,   60,    0,    0,    0,   60,    0,
    0,   16,   84,   17,   60,   60,   60,   60,    0,    0,
    0,   58,   58,    0,    0,    0,   58,   13,   14,    1,
   58,    0,   15,    0,    0,    0,   58,   58,   58,   58,
    0,    0,   16,    0,   17,    0,    2,   13,   14,    0,
   13,   14,   15,    0,    0,   15,   13,   14,    0,    0,
    0,   15,   16,   53,   17,   16,    0,   17,    0,   21,
   21,   16,    0,   17,   21,    0,    0,    0,   21,    0,
   13,   14,    0,    0,   21,   15,   21,   21,   13,   14,
    0,    0,    0,   15,  117,   16,  113,   17,   64,    0,
    0,   13,   14,   16,    0,   17,   15,    0,    0,  113,
   13,   14,    0,    0,    0,   15,   16,    0,   17,  128,
  116,  118,    0,  119,    0,   16,    0,   17,    0,    0,
    0,    0,    0,  125,    0,    0,    0,    0,    0,  129,
  130,    0,  132,  133,    0,    0,    0,  134,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   33,    0,   34,    0,    0,    0,    0,    0,    0,    0,
   43,   44,   45,   46,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   41,   16,   61,  257,   40,   42,   42,
   43,   47,   45,   47,   47,   42,   43,   53,   45,  258,
   47,   43,    0,   45,   44,   44,  258,   60,   59,   62,
   36,   37,   42,   60,  266,   62,  275,   47,   60,   59,
   62,   41,  264,   43,    0,   45,  257,   40,   84,   42,
   43,   59,   45,  264,   47,  256,  257,  257,  259,   59,
   60,   61,   62,   41,   59,   43,    0,   45,   74,   75,
   41,   42,   43,   41,   45,   43,   47,   45,  257,   41,
  259,   59,   60,   61,   62,   41,   41,   43,    0,   45,
   43,  262,   45,   42,   43,   41,   45,   43,   47,   45,
   59,  113,  262,   59,   60,   61,   62,   41,   41,   43,
    0,   45,   61,  263,   59,   42,   43,  263,   45,  263,
   47,   38,   39,  263,  263,   59,   60,   61,   62,   41,
   59,   43,    0,   45,    0,    4,   14,   42,   43,   11,
   45,   19,   47,  256,  257,   30,  259,   59,   60,   61,
   62,   41,    0,   10,   65,   50,   -1,   35,   -1,   76,
   77,   -1,   42,   -1,   -1,   -1,   -1,   47,   -1,   59,
   -1,   61,    0,   41,   52,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   42,   -1,   64,   -1,   -1,   47,
   -1,   59,    0,   61,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,   80,   -1,   -1,  261,   -1,   -1,   -1,  265,
   -1,   59,   90,   61,   -1,  271,    0,  273,  274,   -1,
  262,  260,  260,  257,  257,  259,  259,   -1,   -1,   -1,
  257,   59,  259,   61,  267,  268,  269,  270,    0,   -1,
  267,  268,  269,  270,   -1,  267,  268,  269,  270,   -1,
   -1,   59,   -1,   61,   -1,   -1,  256,  257,   -1,   -1,
    0,  261,  262,   -1,  257,  265,  259,  267,  268,  269,
  270,  271,  272,  273,  274,   59,   -1,   61,  256,  257,
   -1,   -1,    0,  261,  262,   -1,  257,  265,  259,  267,
  268,  269,  270,  271,  272,  273,  274,   59,   -1,   61,
  256,  257,   -1,   -1,    0,  261,  262,   -1,  257,  265,
  259,  267,  268,  269,  270,  271,  272,  273,  274,   59,
   -1,   61,  256,  257,   -1,   -1,    0,  261,  262,  256,
  257,  265,  259,  267,  268,  269,  270,  271,  272,  273,
  274,   59,   -1,   61,  256,  257,   -1,   -1,   -1,  261,
  262,    0,  257,  265,  259,  267,  268,  269,  270,  271,
  272,  273,  274,   59,   -1,   61,  256,  257,   -1,   -1,
   -1,  261,  262,    0,   -1,  265,  256,  257,   -1,  259,
   -1,  271,  272,  273,  274,   59,   -1,   61,  256,  257,
   -1,   -1,   -1,  261,  262,    0,   -1,  265,  256,  257,
   -1,  259,   -1,  271,  272,  273,  274,   -1,  256,  257,
   59,   -1,   61,  261,   -1,   -1,   -1,  265,   -1,   -1,
   -1,   -1,   -1,  271,  272,  273,  274,   -1,  256,  257,
   -1,   61,   59,  261,   61,   -1,   -1,  265,   -1,   -1,
   -1,   -1,   -1,  271,  272,  273,  274,   -1,  256,  257,
   -1,   -1,   -1,  261,   59,   -1,   61,  265,   -1,   -1,
   59,   -1,   61,  271,  272,  273,  274,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,
   59,  265,   61,   59,   -1,   61,   -1,  271,  272,  273,
  274,   61,   -1,   -1,  256,  257,   -1,   -1,   -1,  261,
   -1,   -1,    4,  265,   61,   -1,   -1,   -1,   -1,  271,
  272,  273,  274,   -1,   16,   61,  256,  257,   20,   -1,
   -1,  261,   -1,   61,   -1,  265,   -1,   -1,   -1,   -1,
   32,  271,  272,  273,  274,   -1,   61,   -1,  256,  257,
   -1,   -1,   -1,  261,   -1,   61,   -1,  265,   -1,   -1,
   -1,   -1,   54,  271,  272,  273,  274,   -1,   -1,   -1,
  256,  257,   -1,   65,   -1,  261,   -1,   -1,   -1,  265,
   -1,   -1,   -1,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,   -1,  256,  257,   41,   42,   43,  261,   45,   -1,
   47,  265,   -1,   -1,   -1,   -1,   -1,  271,  272,  273,
  274,   -1,   -1,   60,   -1,   62,   -1,  256,  257,   -1,
   -1,  113,  261,   -1,   -1,   -1,  265,   -1,   -1,  121,
   -1,   -1,  271,  272,  273,  274,  256,  257,   -1,  256,
  257,  261,   -1,   -1,  261,   -1,   -1,   -1,  265,   -1,
   -1,  271,  272,  273,  271,  272,  273,  274,   -1,   -1,
   -1,  256,  257,   -1,   -1,   -1,  261,  256,  257,  258,
  265,   -1,  261,   -1,   -1,   -1,  271,  272,  273,  274,
   -1,   -1,  271,   -1,  273,   -1,  275,  256,  257,   -1,
  256,  257,  261,   -1,   -1,  261,  256,  257,   -1,   -1,
   -1,  261,  271,  272,  273,  271,   -1,  273,   -1,  256,
  257,  271,   -1,  273,  261,   -1,   -1,   -1,  265,   -1,
  256,  257,   -1,   -1,  271,  261,  273,  274,  256,  257,
   -1,   -1,   -1,  261,  262,  271,  264,  273,  274,   -1,
   -1,  256,  257,  271,   -1,  273,  261,   -1,   -1,  264,
  256,  257,   -1,   -1,   -1,  261,  271,   -1,  273,  265,
  104,  105,   -1,  107,   -1,  271,   -1,  273,   -1,   -1,
   -1,   -1,   -1,  117,   -1,   -1,   -1,   -1,   -1,  123,
  124,   -1,  126,  127,   -1,   -1,   -1,  131,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   -1,  259,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  267,  268,  269,  270,
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
"declaraciones : declaraciones declaracion ';'",
"declaraciones : declaracion ';'",
"declaracion : variables",
"declaracion : funcion",
"variables : INT conjVariables",
"conjVariables : conjVariables ',' ID",
"conjVariables : ID",
"funcion : FUNCTION ID bloqueFuncion",
"funcion : FUNCTION bloqueFuncion",
"bloqueFuncion : BEGIN declaracionFuncion sentenciasFuncion END",
"declaracionFuncion : variables ';'",
"declaracionFuncion : IMPORT conjVariables ';'",
"sentenciasFuncion : sentenciasFuncion sentencia",
"sentenciasFuncion : sentenciasFuncion return",
"sentenciasFuncion : sentencia",
"sentenciasFuncion : return",
"return : RETURN '(' exprAritmetica ')'",
"return : RETURN exprAritmetica ')'",
"return : RETURN '(' ')'",
"return : RETURN '(' exprAritmetica",
"sentencias : sentencias sentencia ';'",
"sentencias : sentencia",
"sentencias : ';'",
"sentencia : asignacion",
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
"impresion : PRINT '(' STRING ')'",
"impresion : PRINT '(' STRING",
"impresion : PRINT STRING ')'",
"impresion : PRINT '(' ')'",
"impresion : PRINT STRING",
"impresion : PRINT '('",
"impresion : PRINT ')'",
"impresion : PRINT",
"seleccion : IF '(' condicion ')' THEN sentenciaSeleccion",
"seleccion : IF '(' condicion ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF condicion ')' THEN sentenciaSeleccion",
"seleccion : IF '(' condicion THEN sentenciaSeleccion",
"seleccion : IF '(' ')' THEN sentenciaSeleccion",
"seleccion : IF '(' condicion ')' sentenciaSeleccion",
"seleccion : IF condicion ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF '(' condicion THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF '(' ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion",
"seleccion : IF '(' condicion ')' sentenciaSeleccion ELSE sentenciaSeleccion",
"condicion : exprAritmetica opLogico exprAritmetica",
"condicion : opLogico exprAritmetica",
"opLogico : '<'",
"opLogico : MENORIGUAL",
"opLogico : '>'",
"opLogico : MAYORIGUAL",
"opLogico : IGUAL",
"opLogico : DISTINTO",
"sentenciaSeleccion : BEGIN sentencias END",
"sentenciaSeleccion : sentencia ';'",
"iteracion : LOOP sentencias UNTIL condicion",
"iteracion : LOOP UNTIL condicion",
};

//#line 159 "gramatica.y"

/*_________________________________________________________________________________________________________*/

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

           //System.out.println(tk.getType());
           
           if(tk.getType().equals("EOF"))
               return 0;

           if(this.la.getReservedWordsTable().containsKey(tk.getToken())){
               if(tk.getToken().equalsIgnoreCase("IF"))
                   return IF;
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
                   if(tk.getType().equalsIgnoreCase("Constante")){
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

    private void yyerror(String string) {}
	
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
//#line 612 "Parser.java"
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
//#line 21 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": Programa."); }
break;
case 6:
//#line 33 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": Declaracion"); }
break;
case 10:
//#line 41 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba Nombre de la funcion"); }
break;
case 11:
//#line 44 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Cuerpo de la funcion."); }
break;
case 12:
//#line 47 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Variables de la funcion."); }
break;
case 13:
//#line 48 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
break;
case 19:
//#line 58 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 20:
//#line 59 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
break;
case 21:
//#line 60 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 29:
//#line 76 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Sentencia no valida ERROR"); }
break;
case 31:
//#line 82 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 32:
//#line 83 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 33:
//#line 84 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 37:
//#line 90 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 38:
//#line 91 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 39:
//#line 92 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 40:
//#line 93 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 44:
//#line 99 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 45:
//#line 100 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 46:
//#line 101 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 47:
//#line 102 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 50:
//#line 111 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Impresion");  }
break;
case 51:
//#line 112 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 52:
//#line 113 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 53:
//#line 114 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 54:
//#line 115 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 55:
//#line 116 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 56:
//#line 117 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 57:
//#line 118 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 58:
//#line 123 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(5).obj).getLine() + ": " + "Sentencia IF-ELSE"); }
break;
case 59:
//#line 124 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(7).obj).getLine() + ": " + "Sentencia IF-ELSE");  }
break;
case 60:
//#line 126 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 61:
//#line 127 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 62:
//#line 128 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba condicion "); }
break;
case 63:
//#line 129 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 64:
//#line 131 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(6).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 65:
//#line 132 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(6).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 66:
//#line 133 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(6).obj).getLine() + ": " + "Error Sintactico : Se esperaba condicion "); }
break;
case 67:
//#line 134 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(6).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 69:
//#line 138 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 78:
//#line 155 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
break;
case 79:
//#line 156 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
//#line 929 "Parser.java"
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
