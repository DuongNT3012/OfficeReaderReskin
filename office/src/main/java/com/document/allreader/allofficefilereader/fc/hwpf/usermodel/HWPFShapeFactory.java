
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.util.List;

import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherPropertyFactory;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;


public final class HWPFShapeFactory
{
    public static HWPFShape createShape(EscherContainerRecord spContainer, HWPFShape parent)
    {
        if (spContainer.getRecordId() == EscherContainerRecord.SPGR_CONTAINER)
        {
            return createShapeGroup(spContainer, parent);
        }
        return createSimpeShape(spContainer, parent);
    }
    
    public static HWPFShapeGroup createShapeGroup(EscherContainerRecord spContainer, HWPFShape parent)
    {
        HWPFShapeGroup group = null;
        EscherRecord opt = ShapeKit.getEscherChild((EscherContainerRecord)spContainer.getChild(0), (short)0xF122);
        if (opt != null)
        {
            try
            {
                EscherPropertyFactory f = new EscherPropertyFactory();
                List<EscherProperty> props = f.createProperties(opt.serialize(), 8, opt.getInstance());
                EscherSimpleProperty p = (EscherSimpleProperty)props.get(0);
                if (p.getPropertyNumber() == 0x39F && p.getPropertyValue() == 1)
                {
                    
                }
                else
                {
                    group = new HWPFShapeGroup(spContainer, parent);
                }
            }
            catch(Exception e)
            {
                group = new HWPFShapeGroup(spContainer, parent);
            }
        }
        else
        {
            group = new HWPFShapeGroup(spContainer, parent);
        }

        return group;
    }
    
    public static HWPFAutoShape createSimpeShape(EscherContainerRecord spContainer, HWPFShape parent)
    {
        EscherSpRecord spRecord = spContainer.getChildById(EscherSpRecord.RECORD_ID);
        if (spRecord != null)
        {
            return new HWPFAutoShape(spContainer, parent);
        }
        return null;
    }
}
