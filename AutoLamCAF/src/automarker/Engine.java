package automarker;

/*
 * Decompiled with CFR 0_101.
 */


import automarker.Context;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

//Classes Referenced Gui, Expr, ExprWrapper
class Engine {

    private Gui gui;
  
    //private Runner runner = null;

    Engine(Gui gui) {
        this.gui = gui;
    }

    
    void addDefinition( String string) {
      process(string); 
      
    }

    private Expr process(String string) {
        Expr expr;
        JTextArea jTextArea = this.gui.getOutputArea();
        Context context = this.gui.getContext();
        Context context2 = null;
        if (Options.getSubstituteSymbolsOption().getValue()) {
            context2 = context;
        }
        boolean bl = Options.getVaryParenthesesOption().getValue();
        boolean bl2 = Options.getShowIntermediateOption().getValue();
        int n = Options.getMaxLengthOption().getValue();
        try {
            expr = Parser.parse(string);
        }
        catch (Parser.ParseException var9_9) {
        	System.out.println(var9_9.getMessage());
            jTextArea.setText(var9_9.getMessage());
            return null;
        }
        
        expr = context.substitute(expr);
        jTextArea.setText(expr.toStringSubstituteBelow(context2, n, bl));
        Expr expr3 = expr;
        int n2 = expr.size();
        Expr expr4 = Simplify.simplify(expr);
        HashSet<ExprWrapper> hashSet = new HashSet<ExprWrapper>();
        Expr[] arrexpr = new Expr[100];
        int n3 = -1;
        int n4 = 0;
        while (expr4 != expr) {
            expr = expr4;
            if (bl2) {
                jTextArea.append("\n   = ");
                jTextArea.append(expr.toString(context2, n, bl));
            }
            int n5 = expr.size();
            ExprWrapper exprWrapper = new ExprWrapper(expr);
            if ( ++n4 > Options.getMaxReductionsOption().getValue() || hashSet.contains(exprWrapper)) {
                jTextArea.append("\n   = ... ");
                expr = expr3;
                jTextArea.append(expr.toString(context2, n, bl));
                break;
            }
            if (++n3 == arrexpr.length) {
                n3 = 0;
            }
            if (arrexpr[n3] != null) {
                hashSet.remove(arrexpr[n3]);
            }
            arrexpr[n3] = expr;
            hashSet.add(exprWrapper);
            if (n5 < n2) {
                expr3 = expr;
                n2 = n5;
            }
            expr4 = Simplify.simplify(expr);
        }
        if (!bl2) {
            jTextArea.append("\n   = ");
            jTextArea.append(expr.toString(context2, n, bl));
        }
        return expr;
    }

  

  

}
