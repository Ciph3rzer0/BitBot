/* Generated By:JJTree: Do not edit this line. ASTRoot.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.sru.andgate.bitbot.parser;

public
class ASTRoot extends SimpleNode {
  public ASTRoot(int id) {
    super(id);
  }

  public ASTRoot(bc1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(bc1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=332b55a7ee34af3c9c5614d162a8249c (do not edit this line) */
