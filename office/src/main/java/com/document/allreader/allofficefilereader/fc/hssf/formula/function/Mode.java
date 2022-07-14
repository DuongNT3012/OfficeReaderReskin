

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hssf.formula.TwoDEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.BlankEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.BoolEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.EvaluationException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NumberEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.RefEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.StringEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;



/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 *
 */
public final class Mode implements Function {

	/**
	 * if v is zero length or contains no duplicates, return value is
	 * Double.NaN. Else returns the value that occurs most times and if there is
	 * a tie, returns the first such value.
	 *
	 * @param v
	 */
	public static double evaluate(double[] v) throws EvaluationException {
		if (v.length < 2) {
			throw new EvaluationException(ErrorEval.NA);
		}

		// very naive impl, may need to be optimized
		int[] counts = new int[v.length];
		Arrays.fill(counts, 1);
		for (int i = 0, iSize = v.length; i < iSize; i++) {
			for (int j = i + 1, jSize = v.length; j < jSize; j++) {
				if (v[i] == v[j])
					counts[i]++;
			}
		}
		double maxv = 0;
		int maxc = 0;
		for (int i = 0, iSize = counts.length; i < iSize; i++) {
			if (counts[i] > maxc) {
				maxv = v[i];
				maxc = counts[i];
			}
		}
		if (maxc > 1) {
			return maxv;
		}
		throw new EvaluationException(ErrorEval.NA);

	}

	public ValueEval evaluate(ValueEval[] args, int srcCellRow, int srcCellCol) {
		double result;
		try {
			List<Double> temp = new ArrayList<Double>();
			for (int i = 0; i < args.length; i++) {
				collectValues(args[i], temp);
			}
			double[] values = new double[temp.size()];
			for (int i = 0; i < values.length; i++) {
				values[i] = temp.get(i).doubleValue();
			}
			result = evaluate(values);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		return new NumberEval(result);
	}

	private static void collectValues(ValueEval arg, List<Double> temp) throws EvaluationException {
		if (arg instanceof TwoDEval) {
			TwoDEval ae = (TwoDEval) arg;
			int width = ae.getWidth();
			int height = ae.getHeight();
			for (int rrIx = 0; rrIx < height; rrIx++) {
				for (int rcIx = 0; rcIx < width; rcIx++) {
					ValueEval ve1 = ae.getValue(rrIx, rcIx);
					collectValue(ve1, temp, false);
				}
			}
			return;
		}
		if (arg instanceof RefEval) {
			RefEval re = (RefEval) arg;
			collectValue(re.getInnerValueEval(), temp, true);
			return;
		}
		collectValue(arg, temp, true);

	}

	private static void collectValue(ValueEval arg, List<Double> temp, boolean mustBeNumber)
			throws EvaluationException {
		if (arg instanceof ErrorEval) {
			throw new EvaluationException((ErrorEval) arg);
		}
		if (arg == BlankEval.instance || arg instanceof BoolEval || arg instanceof StringEval) {
			if (mustBeNumber) {
				throw EvaluationException.invalidValue();
			}
			return;
		}
		if (arg instanceof NumberEval) {
			temp.add(new Double(((NumberEval) arg).getNumberValue()));
			return;
		}
		throw new RuntimeException("Unexpected value type (" + arg.getClass().getName() + ")");
	}
}