/* Generated By:JJTree: Do not edit this line. ASTEqualityExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.sru.andgate.bitbot.parser;

public
class ASTEqualityExpression extends SimpleNode {
  public ASTEqualityExpression(int id) {
    super(id);
  }

  public ASTEqualityExpression(bc1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(bc1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9c4683339670f8494d922fffc1f7d728 (do not edit this line) */
