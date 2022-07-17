package com.office.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.office.allreader.allofficefilereader.fc.p014ss.util.CellRangeAddressList;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataValidation */

public interface DataValidation {

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataValidation$ErrorStyle */
    
    public static final class ErrorStyle {
        public static final int INFO = 2;
        public static final int STOP = 0;
        public static final int WARNING = 1;
    }

    void createErrorBox(String str, String str2);

    void createPromptBox(String str, String str2);

    boolean getEmptyCellAllowed();

    String getErrorBoxText();

    String getErrorBoxTitle();

    int getErrorStyle();

    String getPromptBoxText();

    String getPromptBoxTitle();

    CellRangeAddressList getRegions();

    boolean getShowErrorBox();

    boolean getShowPromptBox();

    boolean getSuppressDropDownArrow();

    DataValidationConstraint getValidationConstraint();

    void setEmptyCellAllowed(boolean z);

    void setErrorStyle(int i);

    void setShowErrorBox(boolean z);

    void setShowPromptBox(boolean z);

    void setSuppressDropDownArrow(boolean z);
}
