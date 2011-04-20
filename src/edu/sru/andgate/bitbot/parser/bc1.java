/* Generated By:JJTree&JavaCC: Do not edit this line. bc1.java */
package edu.sru.andgate.bitbot.parser;

public class bc1/*@bgen(jjtree)*/implements bc1TreeConstants, bc1Constants {/*@bgen(jjtree)*/
  protected JJTbc1State jjtree = new JJTbc1State();

  final public SimpleNode Start() throws ParseException {
 /*@bgen(jjtree) Root */
  ASTRoot jjtn000 = new ASTRoot(JJTROOT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Program();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public void Program() throws ParseException {
 /*@bgen(jjtree) Program */
  ASTProgram jjtn000 = new ASTProgram(JJTPROGRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROGRAM:
        jj_consume_token(PROGRAM);
        Identifier();
        label_1:
        while (true) {
          jj_consume_token(NL);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case NL:
            ;
            break;
          default:
            jj_la1[0] = jj_gen;
            break label_1;
          }
        }
        break;
      default:
        jj_la1[1] = jj_gen;
        ;
      }
      ListOfInstructions();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case END:
        jj_consume_token(END);
        label_2:
        while (true) {
          jj_consume_token(NL);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case NL:
            ;
            break;
          default:
            jj_la1[2] = jj_gen;
            break label_2;
          }
        }
        label_3:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case SUBROUTINE:
            ;
            break;
          default:
            jj_la1[3] = jj_gen;
            break label_3;
          }
          SubDef();
          label_4:
          while (true) {
            jj_consume_token(NL);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case NL:
              ;
              break;
            default:
              jj_la1[4] = jj_gen;
              break label_4;
            }
          }
        }
        break;
      default:
        jj_la1[5] = jj_gen;
        ;
      }
      jj_consume_token(0);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void ListOfInstructions() throws ParseException {
 /*@bgen(jjtree) ListOfInstructions */
  ASTListOfInstructions jjtn000 = new ASTListOfInstructions(JJTLISTOFINSTRUCTIONS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER_LITERAL:
        case ADD_OP:
        case PRINT:
        case LET:
        case DIM:
        case WHILE:
        case FOR:
        case IF:
        case CALL:
        case IDENTIFIER:
        case STRING_LITERAL:
        case 45:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_5;
        }
        Instruction();
        label_6:
        while (true) {
          jj_consume_token(NL);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case NL:
            ;
            break;
          default:
            jj_la1[7] = jj_gen;
            break label_6;
          }
        }
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void Instruction() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case ADD_OP:
    case IDENTIFIER:
    case STRING_LITERAL:
    case 45:
      Expression();
      break;
    case LET:
      Assignment();
      break;
    case PRINT:
      Print();
      break;
    case DIM:
      Declaration();
      break;
    case CALL:
      SubCall();
      break;
    case WHILE:
      WhileLoop();
      break;
    case IF:
      IfStatement();
      break;
    case FOR:
      ForLoop();
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void WhileLoop() throws ParseException {
 /*@bgen(jjtree) WhileLoop */
  ASTWhileLoop jjtn000 = new ASTWhileLoop(JJTWHILELOOP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(WHILE);
      Expression();
      jj_consume_token(DO);
      jj_consume_token(NL);
      ListOfInstructions();
      jj_consume_token(LOOP);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void IfStatement() throws ParseException {
 /*@bgen(jjtree) IfStatement */
  ASTIfStatement jjtn000 = new ASTIfStatement(JJTIFSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(IF);
      Expression();
      jj_consume_token(THEN);
      jj_consume_token(NL);
      ListOfInstructions();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ELSE:
        jj_consume_token(ELSE);
        jj_consume_token(NL);
        ListOfInstructions();
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }
      jj_consume_token(ENDIF);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void ForLoop() throws ParseException {
 /*@bgen(jjtree) ForLoop */
  ASTForLoop jjtn000 = new ASTForLoop(JJTFORLOOP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(FOR);
      Identifier();
      jj_consume_token(EQUAL);
      Expression();
      jj_consume_token(TO);
      Expression();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STEP:
        jj_consume_token(STEP);
        Expression();
        break;
      default:
        jj_la1[10] = jj_gen;
        ;
      }
      jj_consume_token(NL);
      ListOfInstructions();
      jj_consume_token(NEXT);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void SubCall() throws ParseException {
 /*@bgen(jjtree) SubCall */
  ASTSubCall jjtn000 = new ASTSubCall(JJTSUBCALL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(CALL);
      Identifier();
      jj_consume_token(45);
      ParameterList();
      jj_consume_token(46);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void ParameterList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case ADD_OP:
    case IDENTIFIER:
    case STRING_LITERAL:
    case 45:
      Expression();
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 47:
          ;
          break;
        default:
          jj_la1[11] = jj_gen;
          break label_7;
        }
        jj_consume_token(47);
        Expression();
      }
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
  }

  final public void SubDef() throws ParseException {
 /*@bgen(jjtree) SubDef */
  ASTSubDef jjtn000 = new ASTSubDef(JJTSUBDEF);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(SUBROUTINE);
      Identifier();
      jj_consume_token(NL);
      ListOfInstructions();
      jj_consume_token(RETURN);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void Print() throws ParseException {
 /*@bgen(jjtree) Print */
  ASTPrint jjtn000 = new ASTPrint(JJTPRINT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PRINT);
      Expression();
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void Declaration() throws ParseException {
 /*@bgen(jjtree) Declaration */
  ASTDeclaration jjtn000 = new ASTDeclaration(JJTDECLARATION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(DIM);
      Identifier();
      jj_consume_token(AS);
      jj_consume_token(INTEGER);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void Assignment() throws ParseException {
 /*@bgen(jjtree) Assignment */
  ASTAssignment jjtn000 = new ASTAssignment(JJTASSIGNMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LET);
      Identifier();
      jj_consume_token(EQUAL);
      Expression();
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

/* ----------------------- */
  final public void Expression() throws ParseException {
    ConcatExpression();

  }

  final public void ConcatExpression() throws ParseException {
 /*@bgen(jjtree) #ConcatExpression(> 1) */
ASTConcatExpression jjtn000 = new ASTConcatExpression(JJTCONCATEXPRESSION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
    try {
      StringExpression();
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CONCAT:
          ;
          break;
        default:
          jj_la1[13] = jj_gen;
          break label_8;
        }
        t = jj_consume_token(CONCAT);
                     jjtn000.value = t.image;
        StringExpression();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void StringExpression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case ADD_OP:
    case IDENTIFIER:
    case 45:
      BooleanExpression();
      break;
    case STRING_LITERAL:
      StringLiteral();
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void StringLiteral() throws ParseException {
 /*@bgen(jjtree) StringLiteral */
  ASTStringLiteral jjtn000 = new ASTStringLiteral(JJTSTRINGLITERAL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(STRING_LITERAL);
                           jjtree.closeNodeScope(jjtn000, true);
                           jjtc000 = false;
                           jjtn000.value = t.image;
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void BooleanExpression() throws ParseException {
 /*@bgen(jjtree) #BooleanExpression(> 1) */
  ASTBooleanExpression jjtn000 = new ASTBooleanExpression(JJTBOOLEANEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      EqualityExpression();
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOL_OP:
          ;
          break;
        default:
          jj_la1[15] = jj_gen;
          break label_9;
        }
        t = jj_consume_token(BOOL_OP);
                      jjtn000.value = t.image;
        EqualityExpression();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void EqualityExpression() throws ParseException {
 /*@bgen(jjtree) #EqualityExpression(> 1) */
  ASTEqualityExpression jjtn000 = new ASTEqualityExpression(JJTEQUALITYEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      RelationalExpression();
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case EQ_OP:
          ;
          break;
        default:
          jj_la1[16] = jj_gen;
          break label_10;
        }
        t = jj_consume_token(EQ_OP);
                    jjtn000.value = t.image;
        RelationalExpression();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void RelationalExpression() throws ParseException {
 /*@bgen(jjtree) #RelationalExpression(> 1) */
  ASTRelationalExpression jjtn000 = new ASTRelationalExpression(JJTRELATIONALEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      AdditiveExpression();
      label_11:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case REL_OP:
          ;
          break;
        default:
          jj_la1[17] = jj_gen;
          break label_11;
        }
        t = jj_consume_token(REL_OP);
                     jjtn000.value = t.image;
        AdditiveExpression();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void AdditiveExpression() throws ParseException {
 /*@bgen(jjtree) #AdditiveExpression(> 1) */
  ASTAdditiveExpression jjtn000 = new ASTAdditiveExpression(JJTADDITIVEEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Node n;
  Token t;
  String op = "err";
    try {
      MultiplicativeExpression();
      label_12:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ADD_OP:
          ;
          break;
        default:
          jj_la1[18] = jj_gen;
          break label_12;
        }
        t = jj_consume_token(ADD_OP);
                     op = t.image;
        MultiplicativeExpression();
      /* This code is key to imitating the left recursiveness that expressions
         exibit, but JavaCC disallows us from using.
       */
      ASTAdditiveExpression newN = new ASTAdditiveExpression(bc1TreeConstants.JJTADDITIVEEXPRESSION);
      newN.value = op;

      // Pop and add the first operand from the stack
      n = jjtree.popNode();
      newN.jjtAddChild(n, 1);
      n.jjtSetParent(newN);

      // Pop and add the second operand from the stack
      n = jjtree.popNode();
      newN.jjtAddChild(n, 0);
      n.jjtSetParent(newN);

      // Push the new node back on the stack
      jjtree.pushNode(newN);
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void MultiplicativeExpression() throws ParseException {
 /*@bgen(jjtree) #MultiplicativeExpression(> 1) */
  ASTMultiplicativeExpression jjtn000 = new ASTMultiplicativeExpression(JJTMULTIPLICATIVEEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Node n;
  Token t;
  String op = "err";
    try {
      UnaryExpression();
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MUL_OP:
          ;
          break;
        default:
          jj_la1[19] = jj_gen;
          break label_13;
        }
        t = jj_consume_token(MUL_OP);
                     op = t.image;
        UnaryExpression();
      /* This code is key to imitating the left recursiveness that expressions
         exibit, but JavaCC disallows us from using.
       */
      ASTMultiplicativeExpression newN = new ASTMultiplicativeExpression(bc1TreeConstants.JJTMULTIPLICATIVEEXPRESSION);
      newN.value = op;

      // Pop and add the first operand from the stack
      n = jjtree.popNode();
      newN.jjtAddChild(n, 1);
      n.jjtSetParent(newN);

      // Pop and add the second operand from the stack
      n = jjtree.popNode();
      newN.jjtAddChild(n, 0);
      n.jjtSetParent(newN);

      // Push the new node back on the stack
      jjtree.pushNode(newN);
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
    }
    }
  }

  final public void UnaryExpression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 45:
      jj_consume_token(45);
      Expression();
      jj_consume_token(46);
      break;
    case ADD_OP:
      UnaryPlusOrMinus();
      break;
    case IDENTIFIER:
      Identifier();
      break;
    case INTEGER_LITERAL:
      Integer();
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void UnaryPlusOrMinus() throws ParseException {
 /*@bgen(jjtree) UnaryExpression */
ASTUnaryExpression jjtn000 = new ASTUnaryExpression(JJTUNARYEXPRESSION);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(ADD_OP);
                    jjtn000.value = t.image;
      UnaryExpression();
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  final public void Identifier() throws ParseException {
 /*@bgen(jjtree) Identifier */
  ASTIdentifier jjtn000 = new ASTIdentifier(JJTIDENTIFIER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(IDENTIFIER);
                       jjtree.closeNodeScope(jjtn000, true);
                       jjtc000 = false;
                       jjtn000.value = t.image.toLowerCase();
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void Integer() throws ParseException {
 /*@bgen(jjtree) IntegerLiteral */
  ASTIntegerLiteral jjtn000 = new ASTIntegerLiteral(JJTINTEGERLITERAL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(INTEGER_LITERAL);
                            jjtree.closeNodeScope(jjtn000, true);
                            jjtc000 = false;
                            jjtn000.value = t.image;
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  /** Generated Token Manager. */
  public bc1TokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[21];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x8000,0x0,0x8000,0x0,0x8000,0x0,0x112b1020,0x8000,0x112b1020,0x40000000,0x4000000,0x0,0x1020,0x4000,0x1020,0x200,0x400,0x800,0x1000,0x2000,0x1020,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x10,0x0,0x20,0x0,0x80,0x3240,0x0,0x3240,0x0,0x0,0x8000,0x3200,0x0,0x3200,0x0,0x0,0x0,0x0,0x0,0x2200,};
   }

  /** Constructor with InputStream. */
  public bc1(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public bc1(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new bc1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public bc1(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new bc1TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public bc1(bc1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(bc1TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[48];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 21; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 48; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

                  }
