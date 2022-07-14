

package com.document.allreader.allofficefilereader.fc.hssf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;

/**
 * Common header/footer base class
 *
 * @author Josh Micich
 */
public abstract class HeaderFooterBase extends StandardRecord {
	private boolean field_2_hasMultibyte;
	private String field_3_text;

	protected HeaderFooterBase(String text) {
		setText(text);
	}

	protected HeaderFooterBase(RecordInputStream in) {
		if (in.remaining() > 0) {
			int field_1_footer_len = in.readShort();
			field_2_hasMultibyte = in.readByte() != 0x00;

			if (field_2_hasMultibyte) {
				field_3_text = in.readUnicodeLEString(field_1_footer_len);
			} else {
				field_3_text = in.readCompressedUnicode(field_1_footer_len);
			}
		} else {
			// Note - this is unusual for BIFF records in general, but normal for header / footer records:
			// when the text is empty string, the whole record is empty (just the 4 byte BIFF header)
			field_3_text = "";
		}
	}

	/**
	 * set the footer string
	 *
	 * @param text string to display
	 */
	public final void setText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("text must not be null");
		}
		field_2_hasMultibyte = StringUtil.hasMultibyte(text);
		field_3_text = text;

		// Check it'll fit into the space in the record
		if (getDataSize() > RecordInputStream.MAX_RECORD_DATA_SIZE) {
			throw new IllegalArgumentException("Header/Footer string too long (limit is "
					+ RecordInputStream.MAX_RECORD_DATA_SIZE + " bytes)");
		}
	}

	/**
	 * get the length of the footer string
	 *
	 * @return length of the footer string
	 */
	private int getTextLength() {
		return field_3_text.length();
	}

	public final String getText() {
		return field_3_text;
	}

	public final void serialize(LittleEndianOutput out) {
		if (getTextLength() > 0) {
			out.writeShort(getTextLength());
			out.writeByte(field_2_hasMultibyte ? 0x01 : 0x00);
			if (field_2_hasMultibyte) {
				StringUtil.putUnicodeLE(field_3_text, out);
			} else {
				StringUtil.putCompressedUnicode(field_3_text, out);
			}
		}
	}

	protected final int getDataSize() {
		if (getTextLength() < 1) {
			return 0;
		}
		return 3 + getTextLength() * (field_2_hasMultibyte ? 2 : 1);
	}
}