/* Generated By:JJTree: Do not edit this line. ASTListOfInstructions.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.sru.andgate.bitbot.parser;

public
class ASTListOfInstructions extends SimpleNode {
  public ASTListOfInstructions(int id) {
    super(id);
  }

  public ASTListOfInstructions(bc1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(bc1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d8e66c2d12af7048d0aef7094109af25 (do not edit this line) */
