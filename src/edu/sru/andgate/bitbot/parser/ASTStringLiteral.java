/* Generated By:JJTree: Do not edit this line. ASTStringLiteral.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.sru.andgate.bitbot.parser;

public
class ASTStringLiteral extends SimpleNode {
  public ASTStringLiteral(int id) {
    super(id);
  }

  public ASTStringLiteral(bc1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(bc1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ad493533735d60a88ff4355f4670321e (do not edit this line) */
