/* Generated By:JJTree&JavaCC: Do not edit this line. bc1TokenManager.java */
package edu.sru.andgate.bitbot.parser;

/** Token Manager. */
public class bc1TokenManager implements bc1Constants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x1633beb000L) != 0L)
         {
            jjmatchedKind = 37;
            return 36;
         }
         if ((active0 & 0x40400000L) != 0L)
         {
            jjmatchedKind = 37;
            return 49;
         }
         if ((active0 & 0x80010000L) != 0L)
         {
            jjmatchedKind = 37;
            return 13;
         }
         if ((active0 & 0x80c000000L) != 0L)
         {
            jjmatchedKind = 37;
            return 33;
         }
         return -1;
      case 1:
         if ((active0 & 0x21250000L) != 0L)
            return 36;
         if ((active0 & 0x1ededab000L) != 0L)
         {
            if (jjmatchedPos != 1)
            {
               jjmatchedKind = 37;
               jjmatchedPos = 1;
            }
            return 36;
         }
         return -1;
      case 2:
         if ((active0 & 0x80810a000L) != 0L)
            return 36;
         if ((active0 & 0x16f6ca1000L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 37;
               jjmatchedPos = 2;
            }
            return 36;
         }
         return -1;
      case 3:
         if ((active0 & 0x10f8021000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 3;
            return 36;
         }
         if ((active0 & 0x606c80000L) != 0L)
            return 36;
         return -1;
      case 4:
         if ((active0 & 0x88021000L) != 0L)
            return 36;
         if ((active0 & 0x1070000000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 4;
            return 36;
         }
         return -1;
      case 5:
         if ((active0 & 0x1060000000L) != 0L)
            return 36;
         if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 37;
            jjmatchedPos = 5;
            return 36;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 38:
         return jjStopAtPos(0, 10);
      case 40:
         return jjStopAtPos(0, 41);
      case 41:
         return jjStopAtPos(0, 42);
      case 44:
         return jjStopAtPos(0, 43);
      case 65:
      case 97:
         return jjMoveStringLiteralDfa1_0(0x80010000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 68:
      case 100:
         return jjMoveStringLiteralDfa1_0(0x20048000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa1_0(0x80c000000L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa1_0(0x11000000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa1_0(0x82000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa1_0(0x40400000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa1_0(0x2200000L);
      case 87:
      case 119:
         return jjMoveStringLiteralDfa1_0(0x200020000L);
      default :
         return jjMoveNfa_0(14, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000802000L);
      case 70:
      case 102:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(1, 24, 36);
         break;
      case 72:
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x2020000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x200008000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x818000000L);
      case 79:
      case 111:
         if ((active0 & 0x40000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(1, 21, 36);
         return jjMoveStringLiteralDfa2_0(active0, 0x20180000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x80001000L);
      case 83:
      case 115:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(1, 16, 36);
         break;
      case 84:
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x40400000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 68:
      case 100:
         if ((active0 & 0x800000000L) != 0L)
         {
            jjmatchedKind = 35;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x2400000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x21000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000000L);
      case 77:
      case 109:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(2, 15, 36);
         break;
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 82:
      case 114:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(2, 20, 36);
         return jjMoveStringLiteralDfa3_0(active0, 0xc0000000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000L);
      case 84:
      case 116:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(2, 13, 36);
         return jjMoveStringLiteralDfa3_0(active0, 0x1210000000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000000L);
      case 88:
      case 120:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
      case 66:
      case 98:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000L);
      case 69:
      case 101:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(3, 26, 36);
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
      case 72:
      case 104:
         if ((active0 & 0x200000000L) != 0L)
            return jjStartNfaWithStates_0(3, 33, 36);
         break;
      case 73:
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x48000000L);
      case 76:
      case 108:
         if ((active0 & 0x400000000L) != 0L)
            return jjStartNfaWithStates_0(3, 34, 36);
         return jjMoveStringLiteralDfa4_0(active0, 0x20000L);
      case 78:
      case 110:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(3, 25, 36);
         return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
      case 80:
      case 112:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(3, 19, 36);
         else if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(3, 22, 36);
         break;
      case 84:
      case 116:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(3, 23, 36);
         break;
      case 85:
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(4, 17, 36);
         break;
      case 70:
      case 102:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(4, 27, 36);
         break;
      case 71:
      case 103:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x20000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x40000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000000L);
      case 84:
      case 116:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(4, 12, 36);
         break;
      case 89:
      case 121:
         if ((active0 & 0x80000000L) != 0L)
            return jjStartNfaWithStates_0(4, 31, 36);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(5, 29, 36);
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000L);
      case 71:
      case 103:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(5, 30, 36);
         break;
      case 78:
      case 110:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 36, 36);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 82:
      case 114:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(6, 28, 36);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 59;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 14:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 3)
                        kind = 3;
                     jjCheckNAddStates(0, 5);
                  }
                  else if ((0x842000000000L & l) != 0L)
                  {
                     if (kind > 9)
                        kind = 9;
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 8)
                        kind = 8;
                     jjCheckNAddTwoStates(0, 52);
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 11)
                        kind = 11;
                  }
                  else if ((0x5000000000000000L & l) != 0L)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(38, 39);
                  else if (curChar == 61)
                  {
                     if (kind > 14)
                        kind = 14;
                  }
                  else if (curChar == 33)
                     jjCheckNAdd(17);
                  else if (curChar == 46)
                     jjCheckNAdd(1);
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 28;
                  else if (curChar == 13)
                     jjCheckNAdd(26);
                  else if (curChar == 62)
                     jjCheckNAdd(21);
                  else if (curChar == 60)
                     jjCheckNAdd(21);
                  else if (curChar == 61)
                     jjCheckNAdd(17);
                  break;
               case 13:
               case 36:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(36);
                  break;
               case 33:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(36);
                  break;
               case 49:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(36);
                  break;
               case 0:
                  if (curChar == 46)
                     jjCheckNAdd(1);
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 3:
                  if ((0x280000000000L & l) != 0L)
                     jjCheckNAddTwoStates(4, 6);
                  break;
               case 4:
                  if (curChar == 46)
                     jjCheckNAdd(5);
                  break;
               case 5:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(5);
                  break;
               case 6:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddStates(6, 10);
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(7);
                  break;
               case 8:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(8, 4);
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(9, 10);
                  break;
               case 10:
                  if (curChar != 46)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(11);
                  break;
               case 11:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAdd(11);
                  break;
               case 17:
                  if (curChar == 61 && kind > 6)
                     kind = 6;
                  break;
               case 18:
                  if (curChar == 61)
                     jjCheckNAdd(17);
                  break;
               case 19:
                  if (curChar == 33)
                     jjCheckNAdd(17);
                  break;
               case 20:
                  if ((0x5000000000000000L & l) != 0L && kind > 7)
                     kind = 7;
                  break;
               case 21:
                  if (curChar == 61 && kind > 7)
                     kind = 7;
                  break;
               case 22:
                  if (curChar == 60)
                     jjCheckNAdd(21);
                  break;
               case 23:
                  if (curChar == 62)
                     jjCheckNAdd(21);
                  break;
               case 24:
                  if ((0x842000000000L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 25:
                  if ((0x2400L & l) != 0L && kind > 11)
                     kind = 11;
                  break;
               case 26:
                  if (curChar == 10 && kind > 11)
                     kind = 11;
                  break;
               case 27:
               case 30:
                  if (curChar == 13)
                     jjCheckNAdd(26);
                  break;
               case 28:
                  if (curChar == 47)
                     jjCheckNAddStates(11, 13);
                  break;
               case 29:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(11, 13);
                  break;
               case 31:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 32:
                  if (curChar == 61 && kind > 14)
                     kind = 14;
                  break;
               case 37:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(38, 39);
                  break;
               case 38:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(38, 39);
                  break;
               case 39:
                  if (curChar == 34 && kind > 40)
                     kind = 40;
                  break;
               case 52:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddStates(0, 5);
                  break;
               case 53:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddTwoStates(53, 2);
                  break;
               case 54:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(54, 0);
                  break;
               case 55:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(55, 56);
                  break;
               case 56:
                  if (curChar != 46)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddTwoStates(57, 2);
                  break;
               case 57:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 3)
                     kind = 3;
                  jjCheckNAddTwoStates(57, 2);
                  break;
               case 58:
                  if ((0x280000000000L & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjCheckNAddTwoStates(0, 52);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 14:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAdd(36);
                  }
                  if ((0x8000000080000L & l) != 0L)
                     jjAddStates(14, 15);
                  else if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  else if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 15;
                  else if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 13:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAdd(36);
                  }
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 33:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAdd(36);
                  }
                  if ((0x2000000020000L & l) != 0L)
                  {
                     if (kind > 14)
                        kind = 14;
                  }
                  break;
               case 49:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 37)
                        kind = 37;
                     jjCheckNAdd(36);
                  }
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 50;
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 2:
                  if ((0x2000000020L & l) != 0L)
                     jjAddStates(16, 18);
                  break;
               case 12:
                  if ((0x1000000010L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 15:
                  if ((0x4000000040000L & l) != 0L && kind > 5)
                     kind = 5;
                  break;
               case 16:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 29:
                  jjAddStates(11, 13);
                  break;
               case 34:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 35:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(36);
                  break;
               case 36:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 37)
                     kind = 37;
                  jjCheckNAdd(36);
                  break;
               case 38:
                  jjAddStates(19, 20);
                  break;
               case 40:
                  if ((0x8000000080000L & l) != 0L)
                     jjAddStates(14, 15);
                  break;
               case 41:
                  if ((0x2000000020L & l) != 0L && kind > 32)
                     kind = 32;
                  break;
               case 42:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 41;
                  break;
               case 43:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 42;
                  break;
               case 44:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 45:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 44;
                  break;
               case 46:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 45;
                  break;
               case 47:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 46;
                  break;
               case 48:
                  if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 50:
                  if ((0x400000004L & l) != 0L && kind > 32)
                     kind = 32;
                  break;
               case 51:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 29:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(11, 13);
                  break;
               case 38:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(19, 20);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 59 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   53, 54, 0, 55, 56, 2, 7, 8, 4, 9, 10, 29, 25, 30, 49, 51, 
   3, 4, 6, 38, 39, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, "\46", null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
"\50", "\51", "\54", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xf3fffffffe9L, 
};
static final long[] jjtoSkip = {
   0x6L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[59];
private final int[] jjstateSet = new int[118];
protected char curChar;
/** Constructor. */
public bc1TokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public bc1TokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 59; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100000200L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
