
package com.document.allreader.allofficefilereader.fc.hssf.formula;

import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.AreaPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.AreaPtgBase;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.OperandPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.RefPtg;
import com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.RefPtgBase;
import com.document.allreader.allofficefilereader.fc.ss.SpreadsheetVersion;


/**
 *   Encapsulates logic to convert shared formulaa into non shared equivalent
 */
public class SharedFormula {

    private final int _columnWrappingMask;
    private final int _rowWrappingMask;

    public SharedFormula(SpreadsheetVersion ssVersion){
        _columnWrappingMask = ssVersion.getLastColumnIndex(); //"IV" for .xls and  "XFD" for .xlsx
        _rowWrappingMask = ssVersion.getLastRowIndex();
    }

    /**
     * Creates a non shared formula from the shared formula counterpart, i.e.
     * Converts the shared formula into the equivalent {@link com.document.allreader.allofficefilereader.fc.hssf.formula.ptg.Ptg} array that it would have,
     * were it not shared.
     *
     * @param ptgs parsed tokens of the shared formula
     * @param formulaRow
     * @param formulaColumn
     */
    public Ptg[] convertSharedFormulas(Ptg[] ptgs, int formulaRow, int formulaColumn) {

        Ptg[] newPtgStack = new Ptg[ptgs.length];

        for (int k = 0; k < ptgs.length; k++) {
            Ptg ptg = ptgs[k];
            byte originalOperandClass = -1;
            if (!ptg.isBaseToken()) {
                originalOperandClass = ptg.getPtgClass();
            }
            if (ptg instanceof RefPtgBase) {
                RefPtgBase refNPtg = (RefPtgBase)ptg;
                ptg = new RefPtg(fixupRelativeRow(formulaRow,refNPtg.getRow(),refNPtg.isRowRelative()),
                                     fixupRelativeColumn(formulaColumn,refNPtg.getColumn(),refNPtg.isColRelative()),
                                     refNPtg.isRowRelative(),
                                     refNPtg.isColRelative());
                ptg.setClass(originalOperandClass);
            } else if (ptg instanceof AreaPtgBase) {
                AreaPtgBase areaNPtg = (AreaPtgBase)ptg;
                ptg = new AreaPtg(fixupRelativeRow(formulaRow,areaNPtg.getFirstRow(),areaNPtg.isFirstRowRelative()),
                                fixupRelativeRow(formulaRow,areaNPtg.getLastRow(),areaNPtg.isLastRowRelative()),
                                fixupRelativeColumn(formulaColumn,areaNPtg.getFirstColumn(),areaNPtg.isFirstColRelative()),
                                fixupRelativeColumn(formulaColumn,areaNPtg.getLastColumn(),areaNPtg.isLastColRelative()),
                                areaNPtg.isFirstRowRelative(),
                                areaNPtg.isLastRowRelative(),
                                areaNPtg.isFirstColRelative(),
                                areaNPtg.isLastColRelative());
                ptg.setClass(originalOperandClass);
            } else if (ptg instanceof OperandPtg) {
                // Any subclass of OperandPtg is mutable, so it's safest to not share these instances.
                ptg = ((OperandPtg) ptg).copy();
            } else {
            	// all other Ptgs are immutable and can be shared
            }
            newPtgStack[k] = ptg;
        }
        return newPtgStack;
    }

    private int fixupRelativeColumn(int currentcolumn, int column, boolean relative) {
        if(relative) {
            // mask out upper bits to produce 'wrapping' at the maximum column ("IV" for .xls and  "XFD" for .xlsx)
            return (column + currentcolumn) & _columnWrappingMask;
        }
        return column;
    }

    private int fixupRelativeRow(int currentrow, int row, boolean relative) {
        if(relative) {
            return (row+currentrow) & _rowWrappingMask;
        }
        return row;
    }

}