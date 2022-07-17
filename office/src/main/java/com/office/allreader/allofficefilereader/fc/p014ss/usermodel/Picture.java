package com.office.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.office.allreader.allofficefilereader.fc.hssf.usermodel.IClientAnchor;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.Picture */

public interface Picture {
    PictureData getPictureData();

    IClientAnchor getPreferredSize();

    void resize();

    void resize(double d);
}