package TP2.ASD;

import TP2.ASD.Ret.GenericRet;
import TP2.exceptions.TypeException;

public interface StatementInterface
{
    String pp();
    GenericRet toIR() throws TypeException;
}