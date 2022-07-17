package com.office.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FontUnderline */

public enum FontUnderline {
    SINGLE(1),
    DOUBLE(2),
    SINGLE_ACCOUNTING(3),
    DOUBLE_ACCOUNTING(4),
    NONE(5);
    
    private static FontUnderline[] _table = new FontUnderline[6];
    private int value;

    static {
        FontUnderline[] values = values();
        for (FontUnderline fontUnderline : values) {
            _table[fontUnderline.getValue()] = fontUnderline;
        }
    }

    private FontUnderline(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FontUnderline$1 */

    static  class C09731 {
        static final  int[] $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline;

        static {
            int[] iArr = new int[FontUnderline.values().length];
            $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline = iArr;
            try {
                iArr[FontUnderline.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline[FontUnderline.DOUBLE_ACCOUNTING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline[FontUnderline.SINGLE_ACCOUNTING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline[FontUnderline.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline[FontUnderline.SINGLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public byte getByteValue() {
        int i = C09731.$SwitchMap$com$furestic$office$ppt$lxs$docx$pdf$viwer$reader$free$fc$ss$usermodel$FontUnderline[ordinal()];
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 34;
        }
        if (i != 3) {
            return i != 4 ? (byte) 1 : 0;
        }
        return 33;
    }

    public static FontUnderline valueOf(int i) {
        return _table[i];
    }

    public static FontUnderline valueOf(byte b) {
        if (b == 1) {
            return SINGLE;
        }
        if (b == 2) {
            return DOUBLE;
        }
        if (b == 33) {
            return SINGLE_ACCOUNTING;
        }
        if (b != 34) {
            return NONE;
        }
        return DOUBLE_ACCOUNTING;
    }
}
