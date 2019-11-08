package TP2.ASD.Expression;

import TP2.ASD.ExpressionInterface;
import TP2.ASD.Ret;
import TP2.exceptions.TypeException;

// Concrete class for Expression: mul case
  public class MulExpressionInterface implements ExpressionInterface {
    private ExpressionInterface left;
    private ExpressionInterface right;

    public MulExpressionInterface(ExpressionInterface left, ExpressionInterface right) {
      this.left = left;
      this.right = right;
    }

    // Pretty-printer
    public String pp() {
      return "(" + left.pp() + " * " + right.pp() + ")";
    }

    // IR generation (IR = Représentation intermédiaire)
    public Ret toIR() throws TypeException {
      return ExpressionHelper.retExpression(left.toIR(), right.toIR());
    }
  }