package TP2.ASD.Statement;

import java.util.List;

import TP2.ErrorHandlerInterface;
import TP2.ASD.ExpressionInterface;
import TP2.ASD.Ret.TypeRet;
import TP2.SymbolTable.PrototypeSymbol;
import TP2.SymbolTable.Symbol;
import TP2.SymbolTable.SymbolTable;
import TP2.SymbolTable.VariableSymbol;
import TP2.ASD.StatementInterface;
import TP2.ASD.Expression.VariableExpression;
import TP2.exceptions.TypeException;

public class FunctionCall implements StatementInterface, ExpressionInterface, ErrorHandlerInterface
{
    private String funcIdent;
    private List<ExpressionInterface> expressions;
    private SymbolTable symbolTable;

    public FunctionCall(String funcIdent, List<ExpressionInterface> expressions, SymbolTable symbolTable)
    {
        this.funcIdent = funcIdent;
        this.expressions = expressions;
        this.symbolTable = symbolTable;
    }

    @Override
    public void checkError()
    {
        Symbol symbol = this.symbolTable.lookup(this.funcIdent);

        if (symbol instanceof PrototypeSymbol)
        {
            PrototypeSymbol prototypeSymbol = (PrototypeSymbol)symbol;

            if (!prototypeSymbol.isDefined())
            {
                exitWithMessage(String.format("[Function call] (%s) function not defined", this.funcIdent));
            }
            
            if (prototypeSymbol.getArguments().size() != this.expressions.size())
            {
                exitWithMessage("[Function call] mismatch parameters number");
            }
            
            int argumentsSize = prototypeSymbol.getArguments().size();
            
            for (int i = 0; i < argumentsSize; ++i)
            {
                VariableSymbol prototypeVariableSymbol = prototypeSymbol.getArguments().get(i);
                ExpressionInterface currentCallParameter = this.expressions.get(i);

                if (currentCallParameter instanceof VariableExpression)
                {
                    VariableSymbol functionCallParameterVariableSymbol = (VariableSymbol)this.symbolTable.lookup(((VariableExpression)this.expressions.get(i)).getVariableForm().getIdent());

                    if (prototypeVariableSymbol.isArray() && !functionCallParameterVariableSymbol.isArray())
                    {
                        exitWithMessage(String.format("[Function call] (%s) needs to be an array", functionCallParameterVariableSymbol.getIdent()));
                    }
                    
                    if (!prototypeVariableSymbol.isArray() && functionCallParameterVariableSymbol.isArray())
                    {
                        exitWithMessage(String.format("[Function call] (%s) needs to be a normal variable", functionCallParameterVariableSymbol.getIdent()));
                    }
                }
                else if (!(currentCallParameter instanceof VariableExpression) && prototypeVariableSymbol.isArray())
                {
                    exitWithMessage(String.format("[Function call] (%s) needs to be an array", currentCallParameter.pp()));
                }
            }
        }
        else
        {
            exitWithMessage(String.format("[Function call] (%s) unknown function name", this.funcIdent));
        }
    }

    @Override
    public String pp()
    {
        checkError();
        
        StringBuilder str = new StringBuilder(this.funcIdent);
        
        str.append("(");
        
        int variablesSize = this.expressions.size();
        
        for(int i = 0; i < variablesSize; ++i)
        {
            str.append(this.expressions.get(i).pp());

            if (i < variablesSize - 1)
            {
                str.append(", ");
            }
        }
        
        str.append(")");
        
        return str.toString();
    }

    @Override
    public TypeRet toIR() throws TypeException
    {
        checkError();
        
        return null;
    }
}