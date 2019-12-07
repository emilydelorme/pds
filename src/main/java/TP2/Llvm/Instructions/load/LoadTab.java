package TP2.Llvm.Instructions.load;

import TP2.Llvm.Instruction;

public class LoadTab implements Instruction
{
    String ident;
    String tabIdent;
    String tabIndex;
    String tabSize;

    public LoadTab(String ident, String tabIdent, String tabIndex, String tabSize) {
        this.ident = ident;
        this.tabIdent = tabIdent;
        this.tabIndex = tabIndex;
        this.tabSize = tabSize;
    }

    public String toString() {
        return ident + " = getelementptr [" + tabSize + " x i32], [" + tabSize + " x i32]* " + tabIdent
                + ", i64 0, i32 " + tabIndex + "\n";
    }
}