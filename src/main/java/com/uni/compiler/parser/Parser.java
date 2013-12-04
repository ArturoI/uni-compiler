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
    3,    0,    1,    1,    4,    4,    5,    5,    7,    7,
    9,    6,    8,    8,    8,    8,   10,   10,   11,   11,
   11,   12,   12,   12,   12,   12,   15,   15,   15,   15,
   15,   19,   19,   19,   19,   19,   20,   20,   20,   20,
   20,   20,   20,   16,   24,   16,   16,   23,   23,   25,
   17,   17,    2,    2,    2,   26,   26,   26,   26,   26,
   26,   13,   13,   13,   13,   18,   18,   18,   18,   18,
   18,   18,   18,   22,   22,   22,   22,   22,   22,   22,
   30,   30,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   27,   33,   27,   27,   31,   31,   31,   31,   31,
   21,   21,   21,   21,   21,   21,   32,   32,   34,   28,
   28,   29,
};
final static short yylen[] = {                            2,
    0,    3,    2,    1,    1,    1,    3,    3,    3,    1,
    0,    4,    4,    2,    3,    4,    1,    3,    2,    1,
    1,    2,    1,    2,    1,    1,    4,    3,    2,    3,
    4,    5,    4,    5,    4,    4,    3,    3,    1,    3,
    2,    3,    2,    4,    0,    7,    3,    3,    1,    0,
    5,    3,    2,    1,    1,    2,    1,    1,    1,    1,
    1,    3,    2,    3,    2,    3,    3,    1,    1,    3,
    2,    3,    2,    3,    3,    1,    3,    2,    3,    2,
    1,    1,    5,    5,    4,    3,    3,    2,    2,    2,
    1,    4,    0,    7,    3,    5,    4,    5,    4,    4,
    1,    1,    1,    1,    1,    1,    3,    1,    0,    5,
    3,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    4,    5,    6,   10,    0,   11,
    0,    3,    8,    7,    0,    0,   61,    0,    0,    0,
    0,   55,    0,    0,    0,   57,   54,   58,   59,   60,
    9,    0,    0,   17,   12,    0,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,   69,   76,    0,    0,
    0,    0,    0,    0,    0,   90,    0,   53,   56,   14,
    0,    0,    0,    0,    0,    0,   21,    0,   20,    0,
   23,    0,   25,   26,   81,    0,    0,   78,   80,  112,
   64,    0,    0,    0,    0,    0,  102,  104,  105,  106,
  101,  103,    0,    0,    0,   95,    0,  111,    0,   86,
    0,   87,    0,   18,    0,    0,    0,    0,    0,    0,
    0,    0,   29,    0,   15,   19,   22,   24,   70,    0,
   72,    0,   77,   74,   79,   75,    0,    0,    0,    0,
  108,    0,    0,   85,    0,   16,   13,    0,    0,    0,
    0,    0,    0,   49,   47,    0,    0,    0,   52,    0,
   30,   28,    0,  100,    0,   99,   97,    0,   93,  110,
   84,   83,    0,    0,    0,    0,   40,    0,   42,    0,
    0,    0,   31,   27,   98,   96,  107,    0,   36,    0,
   35,   45,   48,   33,   51,   94,   34,   32,    0,   46,
};
final static short yydgoto[] = {                          3,
    4,   24,   11,    5,    6,    7,    9,   35,   16,   36,
   68,   69,   70,   71,   72,   73,   74,   50,  108,  109,
   94,   46,  145,  189,  112,   27,   28,   29,   47,   48,
   51,  132,  178,   53,
};
final static short yysindex[] = {                      -215,
 -251, -243,    0, -215,    0,    0,    0,    0,  223,    0,
  248,    0,    0,    0, -207, -110,    0,    6,   12, -216,
  -22,    0,  179,  391,   10,    0,    0,    0,    0,    0,
    0, -119, -251,    0,    0,  184,   55,    0,   46,   46,
 -183, -183,   41,  166,   64,   23,    0,    0,  144,  -20,
 -218,   12,  248,   61,  -41,    0,   64,    0,    0,    0,
  184,  135,  275,   21, -173,  -29,    0,  207,    0,   62,
    0,   67,    0,    0,    0,   23,   23,    0,    0,    0,
    0,   64,  173,  204,  -75,  149,    0,    0,    0,    0,
    0,    0,  -20,  179,  179,    0,  183,    0,  370,    0,
  -39,    0,  338,    0,   46,   46,  150,  267,  -15,   23,
   21,  184,    0,   30,    0,    0,    0,    0,    0,   23,
    0,   23,    0,    0,    0,    0,   42,  129,  155,  248,
    0, -135,   12,    0,  -23,    0,    0,   23,   23,  -15,
  300,  292,  184,    0,    0,  214,  237,  300,    0,  343,
    0,    0,   60,    0,   71,    0,    0,  388,    0,    0,
    0,    0,   49,  337, -129,  363,    0,   23,    0,   23,
  405,   21,    0,    0,    0,    0,    0,  183,    0,  160,
    0,    0,    0,    0,    0,    0,    0,    0,  292,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  289,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  312,
    1,    0,    0,  137,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -38,    0,    0,    0,
    0,    0,    0,    0,   83,  -33,    0,    0,    0,    0,
    0,    0,    0,   20,   39,    0,   85,    0,    0,    0,
    0,    0,    0,    0,  229,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -28,   65,    0,    0,    0,
    0,   92,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   70,
    0,   90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   58,    0,    0,    0,    0,    0,  100,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  315,    0,    0,  123,    0,  128,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -12,    0,  154,   88,    0,  120,    0,    0,  133,
  -26,  557,  499,  524,    0,    0,    0,  640,  -74,  326,
  344,  611, -109,    0,    0,  386,    0,    0,  582,   37,
  -36,  -11,    0,    0,
};
final static int YYTABLESIZE=783;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        102,
   91,  135,   81,   81,   81,    8,   81,   68,   81,   68,
  114,   68,   71,   10,   71,   98,   71,   55,   56,   88,
   81,   81,   83,   81,   84,   68,   68,  146,   68,  147,
   71,   71,  165,   71,  103,  162,  149,   96,   89,   91,
   99,   92,    1,   97,   91,   43,   92,   41,   39,   31,
   40,   49,   42,   41,   39,   52,   40,   92,   42,    2,
  107,   91,   41,  105,   85,  106,   44,   42,   59,   86,
  152,   41,   39,   75,   40,   38,   42,   78,   79,  190,
   88,   80,  154,   41,   39,  150,   40,   41,   42,  179,
   41,  105,   42,  106,   43,   42,  160,  185,  111,   89,
  174,  100,   83,   34,   84,   73,   83,   73,   84,   73,
   66,  176,   66,   83,   66,   84,  166,  158,   92,   34,
  117,  124,  126,   73,   73,  118,   73,  159,   66,   66,
   67,   66,   67,  182,   67,   39,    2,   39,    1,   39,
   41,   65,   41,   63,   41,   60,   33,    1,   67,   67,
   62,   67,   62,   32,   39,   33,   39,   12,   43,   41,
   43,   41,   43,   37,   61,   37,  186,   37,   38,  156,
   38,   83,   38,   84,    0,    0,    0,   43,   15,   43,
  123,   75,   37,   38,   37,   41,   39,   38,   40,   38,
   42,   41,  105,  104,  106,  157,   42,   83,    0,   84,
  188,    0,  146,   91,  147,   92,    0,   41,   39,   91,
   40,   92,   42,    0,   41,    0,  134,   81,  101,   42,
   41,   39,   68,   40,    0,   42,  113,   71,   81,   81,
   81,   81,  161,   68,   68,   68,   68,   54,   71,   71,
   71,   71,   67,   23,   23,   41,   87,   88,   89,   90,
   42,   87,   88,   89,   90,   41,   91,   91,    0,    0,
   42,   91,   37,   91,   38,   91,   15,   23,   37,    0,
   38,   91,   91,   91,   91,   88,   88,   75,   41,   38,
   88,   14,   88,   42,   88,  151,   37,   50,   38,   50,
   88,   88,   88,   88,   89,   89,    0,    0,   37,   89,
   38,   89,   75,   89,   38,   75,   22,   38,   23,   89,
   89,   89,   89,   92,   92,  173,   41,   39,   92,   40,
   73,   42,   92,    0,    0,   66,  175,   23,   92,   92,
   92,   73,   73,   73,   73,   44,   66,   66,   66,   66,
    0,   41,  105,    0,  106,   67,   42,    1,    0,    1,
   39,    0,   23,    0,    0,   41,   67,   67,   67,   67,
    0,   39,   39,   39,   39,    0,   41,   41,   41,   41,
  109,    0,  109,   43,    0,   44,    0,  181,   37,  146,
    0,  147,    0,   38,   43,   43,   43,   43,    0,   37,
   37,   37,   37,   95,   38,   38,   38,   38,   23,    0,
   37,    0,   38,   23,  125,   75,   75,   38,   38,   58,
   87,   88,   89,   90,    0,  187,   87,   88,   89,   90,
    0,   81,   37,   23,   38,    0,    0,    0,  119,   75,
   23,   38,  140,    0,    0,   37,  127,   38,   17,   18,
   63,    0,    0,   19,   64,  184,  130,  146,   23,  147,
  141,   23,  148,   20,   65,   21,   21,   66,    0,  121,
   75,    0,   38,   63,    0,    0,  164,   64,    0,  167,
   75,  115,   38,  171,    0,    0,    0,   65,   13,   21,
   66,    0,  131,  163,   58,   50,    0,    0,  180,   50,
    0,    0,  169,   75,    0,   38,    0,    0,    0,   50,
    0,   50,   50,   17,   18,    0,    0,    0,   19,   25,
    0,    0,    0,    0,    0,    0,    0,    0,   20,    0,
   21,    0,   25,   63,    0,    0,    0,   64,  142,    0,
  143,   37,    0,   38,   26,    0,    0,   65,    0,   21,
   66,    0,    0,   58,    1,    1,    0,   26,   63,    1,
    0,   25,   64,    0,    0,  143,   75,    0,   38,    1,
    0,    1,   65,  131,   21,   66,    0,  109,  109,    0,
   44,   44,  109,    0,    0,   44,   26,    0,    0,   44,
    0,    0,  109,    0,  109,   44,   44,   44,   44,    0,
    0,    0,   30,  136,   63,   25,    0,   25,   64,   63,
    0,    0,  137,   64,    0,   30,    0,    0,   65,    0,
   21,   66,    0,   65,  172,   21,   66,    0,    0,   63,
   26,    0,   26,   64,  116,   17,   18,  183,   25,    0,
   19,    0,    0,   65,   30,   21,   66,    0,    0,    0,
   20,  133,   21,   17,   18,    0,   17,   18,   19,   76,
   77,   19,  177,   26,    0,    0,   25,   45,   20,  116,
   21,   20,   57,   21,  144,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  110,    0,   25,    0,   30,    0,
   30,   26,    0,   82,    0,    0,    0,    0,   93,    0,
    0,    0,    0,  120,  122,    0,    0,    0,  144,    0,
    0,   26,   45,    0,    0,    0,  116,    0,    0,    0,
    0,   30,    0,    0,    0,  138,  139,  110,    0,    0,
    0,  110,  116,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  128,  129,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,  144,    0,    0,    0,    0,
    0,  110,    0,  153,    0,    0,  168,  170,  110,   30,
    0,    0,    0,    0,    0,    0,  155,    0,    0,    0,
    0,    0,    0,  110,    0,    0,    0,    0,    0,    0,
    0,    0,  110,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   41,   42,   43,  257,   45,   41,   47,   43,
   40,   45,   41,  257,   43,   52,   45,   40,   41,    0,
   59,   60,   43,   62,   45,   59,   60,   43,   62,   45,
   59,   60,  142,   62,   61,   59,  111,  256,    0,   60,
   53,   62,  258,  262,   60,   40,   62,   42,   43,  257,
   45,   40,   47,   42,   43,  272,   45,    0,   47,  275,
   40,   61,   42,   43,   42,   45,   61,   47,   59,   47,
   41,   42,   43,  257,   45,  259,   47,   41,   42,  189,
   61,   41,   41,   42,   43,  112,   45,   42,   47,   41,
   42,   43,   47,   45,   40,   47,  133,  172,  272,   61,
   41,   41,   43,   16,   45,   41,   43,   43,   45,   45,
   41,   41,   43,   43,   45,   45,  143,  130,   61,   32,
   59,   85,   86,   59,   60,   59,   62,  263,   59,   60,
   41,   62,   43,  263,   45,   41,    0,   43,  258,   45,
   41,   59,   43,   59,   45,  265,  266,  258,   59,   60,
   59,   62,   33,  264,   60,  266,   62,    4,   41,   60,
   43,   62,   45,   41,   32,   43,  178,   45,   41,   41,
   43,   43,   45,   45,   -1,   -1,   -1,   60,   44,   62,
  256,  257,   60,  259,   62,   42,   43,   60,   45,   62,
   47,   42,   43,   59,   45,   41,   47,   43,   -1,   45,
   41,   -1,   43,   60,   45,   62,   -1,   42,   43,   60,
   45,   62,   47,   -1,   42,   -1,  256,  256,  260,   47,
   42,   43,  256,   45,   -1,   47,  256,  256,  267,  268,
  269,  270,  256,  267,  268,  269,  270,  260,  267,  268,
  269,  270,   59,   61,   61,   42,  267,  268,  269,  270,
   47,  267,  268,  269,  270,   42,  256,  257,   -1,   -1,
   47,  261,  257,  263,  259,  265,   44,   61,  257,   -1,
  259,  271,  272,  273,  274,  256,  257,  257,   42,  259,
  261,   59,  263,   47,  265,  256,  257,   59,  259,   61,
  271,  272,  273,  274,  256,  257,   -1,   -1,  257,  261,
  259,  263,  257,  265,  259,  257,   59,  259,   61,  271,
  272,  273,  274,  256,  257,  256,   42,   43,  261,   45,
  256,   47,  265,   -1,   -1,  256,  256,   61,  271,  272,
  273,  267,  268,  269,  270,   61,  267,  268,  269,  270,
   -1,   42,   43,   -1,   45,  256,   47,   59,   -1,   61,
  256,   -1,   61,   -1,   -1,  256,  267,  268,  269,  270,
   -1,  267,  268,  269,  270,   -1,  267,  268,  269,  270,
   59,   -1,   61,  256,   -1,   61,   -1,   41,  256,   43,
   -1,   45,   -1,  256,  267,  268,  269,  270,   -1,  267,
  268,  269,  270,   50,  267,  268,  269,  270,   61,   -1,
  257,   -1,  259,   61,  256,  257,  257,  259,  259,   24,
  267,  268,  269,  270,   -1,  256,  267,  268,  269,  270,
   -1,  256,  257,   61,  259,   -1,   -1,   -1,  256,  257,
   61,  259,  107,   -1,   -1,  257,   93,  259,  256,  257,
  257,   -1,   -1,  261,  261,   41,  264,   43,   61,   45,
  107,   61,  109,  271,  271,  273,  273,  274,   -1,  256,
  257,   -1,  259,  257,   -1,   -1,  141,  261,   -1,  256,
  257,  265,  259,  148,   -1,   -1,   -1,  271,  256,  273,
  274,   -1,   97,  140,   99,  257,   -1,   -1,  163,  261,
   -1,   -1,  256,  257,   -1,  259,   -1,   -1,   -1,  271,
   -1,  273,  274,  256,  257,   -1,   -1,   -1,  261,   11,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,
  273,   -1,   24,  257,   -1,   -1,   -1,  261,  262,   -1,
  264,  257,   -1,  259,   11,   -1,   -1,  271,   -1,  273,
  274,   -1,   -1,  158,  256,  257,   -1,   24,  257,  261,
   -1,   53,  261,   -1,   -1,  264,  257,   -1,  259,  271,
   -1,  273,  271,  178,  273,  274,   -1,  256,  257,   -1,
  256,  257,  261,   -1,   -1,  261,   53,   -1,   -1,  265,
   -1,   -1,  271,   -1,  273,  271,  272,  273,  274,   -1,
   -1,   -1,   11,  256,  257,   97,   -1,   99,  261,  257,
   -1,   -1,  265,  261,   -1,   24,   -1,   -1,  271,   -1,
  273,  274,   -1,  271,  272,  273,  274,   -1,   -1,  257,
   97,   -1,   99,  261,   68,  256,  257,  265,  130,   -1,
  261,   -1,   -1,  271,   53,  273,  274,   -1,   -1,   -1,
  271,  272,  273,  256,  257,   -1,  256,  257,  261,   39,
   40,  261,  265,  130,   -1,   -1,  158,   18,  271,  103,
  273,  271,   23,  273,  108,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   64,   -1,  178,   -1,   97,   -1,
   99,  158,   -1,   44,   -1,   -1,   -1,   -1,   49,   -1,
   -1,   -1,   -1,   83,   84,   -1,   -1,   -1,  142,   -1,
   -1,  178,   63,   -1,   -1,   -1,  150,   -1,   -1,   -1,
   -1,  130,   -1,   -1,   -1,  105,  106,  107,   -1,   -1,
   -1,  111,  166,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   94,   95,   -1,   -1,   -1,   -1,  158,
   -1,   -1,   -1,   -1,   -1,  189,   -1,   -1,   -1,   -1,
   -1,  141,   -1,  114,   -1,   -1,  146,  147,  148,  178,
   -1,   -1,   -1,   -1,   -1,   -1,  127,   -1,   -1,   -1,
   -1,   -1,   -1,  163,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  172,
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
"condicionF : '(' exprAritmeticaF opLogico exprAritmeticaF ')'",
"condicionF : exprAritmeticaF opLogico exprAritmeticaF ')'",
"condicionF : '(' exprAritmeticaF opLogico exprAritmeticaF error",
"condicionF : '(' opLogico exprAritmeticaF ')'",
"condicionF : '(' exprAritmeticaF opLogico ')'",
"exprAritmeticaF : exprAritmeticaF '+' termino",
"exprAritmeticaF : exprAritmeticaF '-' termino",
"exprAritmeticaF : termino",
"exprAritmeticaF : exprAritmeticaF '+' error",
"exprAritmeticaF : '+' termino",
"exprAritmeticaF : exprAritmeticaF '-' error",
"exprAritmeticaF : '-' termino",
"seleccionF : IF condicionF THEN sentenciaSeleccionF",
"$$3 :",
"seleccionF : IF condicionF THEN sentenciaSeleccionF ELSE $$3 sentenciaSeleccionF",
"seleccionF : IF condicionF sentenciaSeleccionF",
"sentenciaSeleccionF : BEGIN sentenciasF END",
"sentenciaSeleccionF : sentenciaF",
"$$4 :",
"iteracionF : LOOP $$4 sentenciasF UNTIL condicionF",
"iteracionF : LOOP UNTIL condicionF",
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
"exprAritmetica : llamadaAFuncion",
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

//#line 287 "gramatica.y"

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
//#line 877 "Parser.java"
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
//#line 88 "gramatica.y"
{ crearTerceto("CMP", 1);
                                                            this.tercetoList.add(new Terceto((String) this.pilaOperators.pop(), new Token(), new Token(), null));
                                                            /*System.out.println("agregue un BF en la posicion " + this.tercetoList.size());*/
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            /*this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));*/
                                                            /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");*/
                                                          }
break;
case 33:
//#line 97 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 34:
//#line 98 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 35:
//#line 99 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 36:
//#line 100 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 37:
//#line 103 "gramatica.y"
{ crearTerceto("ADD", 2); }
break;
case 38:
//#line 104 "gramatica.y"
{ crearTerceto("SUB", 2); }
break;
case 40:
//#line 108 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 41:
//#line 109 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 42:
//#line 110 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 43:
//#line 111 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 44:
//#line 116 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                      setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                      createLabel();
                                                    }
break;
case 45:
//#line 120 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 46:
//#line 127 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}
break;
case 47:
//#line 128 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
break;
case 50:
//#line 137 "gramatica.y"
{ createLabelForLoop(); }
break;
case 51:
//#line 137 "gramatica.y"
{ 
                    showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                    setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 52:
//#line 141 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 61:
//#line 161 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": Error Sintactico : Sentencia Invalida"); }
break;
case 62:
//#line 166 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Asignacion.");
                                      checkContain((Token)val_peek(2).obj);
                                      checkNameMangling((Token)val_peek(2).obj);
                                      this.pilaTerceto.push((Token)val_peek(2).obj);
                                      crearTerceto("MOV", 1);
                                    }
break;
case 63:
//#line 173 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
break;
case 64:
//#line 174 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
break;
case 65:
//#line 175 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
break;
case 66:
//#line 178 "gramatica.y"
{ crearTerceto("ADD", 2); }
break;
case 67:
//#line 179 "gramatica.y"
{ crearTerceto("SUB", 2); }
break;
case 70:
//#line 183 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 71:
//#line 184 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 72:
//#line 185 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 73:
//#line 186 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 74:
//#line 189 "gramatica.y"
{ crearTerceto("MUL", 2); }
break;
case 75:
//#line 190 "gramatica.y"
{ crearTerceto("DIV", 2); }
break;
case 77:
//#line 193 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 78:
//#line 194 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 79:
//#line 195 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 80:
//#line 196 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }
break;
case 81:
//#line 199 "gramatica.y"
{ checkContain((Token)val_peek(0).obj); checkNameMangling((Token)val_peek(0).obj); this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 82:
//#line 200 "gramatica.y"
{ this.pilaTerceto.push((Token)val_peek(0).obj); }
break;
case 83:
//#line 205 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Impresion");
                                          this.pilaTerceto.push(new Token());
                                          this.pilaTerceto.push((Token)val_peek(2).obj);
                                          crearTerceto("PRINT", 1);
                                        }
break;
case 84:
//#line 211 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
break;
case 85:
//#line 212 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
break;
case 86:
//#line 213 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
break;
case 87:
//#line 214 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
break;
case 88:
//#line 215 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
break;
case 89:
//#line 216 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
break;
case 90:
//#line 217 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(1).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
break;
case 91:
//#line 218 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(0).obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
break;
case 92:
//#line 223 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Sentencia IF");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                  createLabel();
                                                 }
break;
case 93:
//#line 228 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  /*System.out.println("agregue un BI en la posicion " + this.tercetoList.size());*/
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");*/
                                                  createLabel();
                                                }
break;
case 94:
//#line 235 "gramatica.y"
{ setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}
break;
case 95:
//#line 237 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
break;
case 96:
//#line 240 "gramatica.y"
{ crearTerceto("CMP", 1);
                                                            this.tercetoList.add(new Terceto((String) this.pilaOperators.pop(), new Token(), new Token(), null));
                                                            /*System.out.println("agregue un BF en la posicion " + this.tercetoList.size());*/
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            /*this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));*/
                                                            /*System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");*/
                                                          }
break;
case 97:
//#line 249 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
break;
case 98:
//#line 250 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
break;
case 99:
//#line 251 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 100:
//#line 252 "gramatica.y"
{ showErrorParser("Linea " + ((Token)val_peek(3).obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
break;
case 101:
//#line 255 "gramatica.y"
{ pilaOperators.push("JAE"); }
break;
case 102:
//#line 256 "gramatica.y"
{ pilaOperators.push("JA"); }
break;
case 103:
//#line 257 "gramatica.y"
{ pilaOperators.push("JBE"); }
break;
case 104:
//#line 258 "gramatica.y"
{ pilaOperators.push("JB"); }
break;
case 105:
//#line 259 "gramatica.y"
{ pilaOperators.push("JNE"); }
break;
case 106:
//#line 260 "gramatica.y"
{ pilaOperators.push("JE"); }
break;
case 109:
//#line 269 "gramatica.y"
{ createLabelForLoop(); }
break;
case 110:
//#line 269 "gramatica.y"
{ 
                  showInfoParser("Linea " + ((Token)val_peek(4).obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                  setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
break;
case 111:
//#line 273 "gramatica.y"
{ showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
break;
case 112:
//#line 278 "gramatica.y"
{ nombreFuncion = ((Token)val_peek(2).obj).getToken();
                              showInfoParser("Linea " + ((Token)val_peek(2).obj).getLine() + ": " + "Llamada a Funcion " + nombreFuncion);
                              /*((Token)$1.obj).getLine();*/
                              this.tercetoList.add(new Terceto("CALL", new Token("F" + ((Token)val_peek(2).obj).getToken()), new Token(), null));
                              /*Donde vuelve la funcion luego de ejecutarse.*/
                            }
break;
//#line 1417 "Parser.java"
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
