/* Generated By:JJTree: Do not edit this line. ASTBooleanExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.sru.andgate.bitbot.parser;

public
class ASTBooleanExpression extends SimpleNode {
  public ASTBooleanExpression(int id) {
    super(id);
  }

  public ASTBooleanExpression(bc1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(bc1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6436def6f7a4fe74dafd78f44b5e3907 (do not edit this line) */
