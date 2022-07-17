

package com.ntdapp.allreader.allofficefilereader.fc.hssf.record;

import com.ntdapp.allreader.allofficefilereader.fc.util.HexDump;
import com.ntdapp.allreader.allofficefilereader.fc.util.LittleEndianOutput;

/**
 * Title: Recalc Id Record (0x01C1)<p/>
 * Description:  This record contains an ID that marks when a worksheet was last
 *               recalculated. It's an optimization Excel uses to determine if it
 *               needs to  recalculate the spreadsheet when it's opened. So far, only
 *               the two engine ids <code>0x80 0x38 0x01 0x00</code>
 *               and <code>0x60 0x69 0x01 0x00</code> have been seen.
 *               A value of <code>0x00</code> will cause Excel to recalculate
 *               all formulas on the next load.<p/>
 * REFERENCE:  http://chicago.sourceforge.net/devel/docs/excel/biff8.html<p/>
 * @author Luc Girardin (luc dot girardin at macrofocus dot com)
 */
public final class RecalcIdRecord extends StandardRecord {
    public final static short sid = 0x01C1;
    private final int _reserved0;

    /**
     * An unsigned integer that specifies the recalculation engine identifier
     * of the recalculation engine that performed the last recalculation.
     * If the value is less than the recalculation engine identifier associated with the application,
     * the application will recalculate the results of all formulas on
     * this workbook immediately after loading the file
     */
    private int _engineId;

    public RecalcIdRecord() {
        _reserved0 = 0;
        _engineId = 0;
    }

    public RecalcIdRecord(RecordInputStream in) {
    	in.readUShort(); // field 'rt' should have value 0x01C1, but Excel doesn't care during reading
    	_reserved0 = in.readUShort();
    	_engineId = in.readInt();
    }

    public boolean isNeeded() {
        return true;
    }

    public void setEngineId(int val) {
        _engineId = val;
    }

    public int getEngineId() {
        return _engineId;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[RECALCID]\n");
        buffer.append("    .reserved = ").append(HexDump.shortToHex(_reserved0)).append("\n");
        buffer.append("    .engineId = ").append(HexDump.intToHex(_engineId)).append("\n");
        buffer.append("[/RECALCID]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(sid); // always write 'rt' field as 0x01C1
        out.writeShort(_reserved0);
        out.writeInt(_engineId);
    }

    protected int getDataSize() {
        return 8;
    }

    public short getSid() {
        return sid;
    }
}