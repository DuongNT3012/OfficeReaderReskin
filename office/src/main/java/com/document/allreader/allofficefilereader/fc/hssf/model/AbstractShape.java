

package com.document.allreader.allofficefilereader.fc.hssf.model;

import com.document.allreader.allofficefilereader.constant.AutoShapeConstant;
import com.document.allreader.allofficefilereader.fc.ddf.EscherBoolProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRGBProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hssf.record.ObjRecord;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFAnchor;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFComment;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFPolygon;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFShape;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFSimpleShape;
import com.document.allreader.allofficefilereader.fc.hssf.usermodel.HSSFTextbox;


/**
 * An abstract shape is the lowlevel model for a shape.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public abstract class AbstractShape
{
    /**
     * Create a new shape object used to create the escher records.
     *
     * @param hssfShape     The simple shape this is based on.
     */
    public static AbstractShape createShape( HSSFShape hssfShape, int shapeId )
    {
        AbstractShape shape;
        if (hssfShape instanceof HSSFComment)
        {
            shape = new CommentShape( (HSSFComment)hssfShape, shapeId );
        }
        else if (hssfShape instanceof HSSFTextbox)
        {
            shape = new TextboxShape( (HSSFTextbox)hssfShape, shapeId );
        }
        else if (hssfShape instanceof HSSFPolygon)
        {
            shape = new PolygonShape( (HSSFPolygon) hssfShape, shapeId );
        }
        else if (hssfShape instanceof HSSFSimpleShape)
        {
            HSSFSimpleShape simpleShape = (HSSFSimpleShape) hssfShape;
            switch ( simpleShape.getShapeType() )
            {
                case HSSFSimpleShape.OBJECT_TYPE_PICTURE:
                    shape = new PictureShape( simpleShape, shapeId );
                    break;
                case HSSFSimpleShape.OBJECT_TYPE_LINE:
                    shape = new LineShape( simpleShape, shapeId );
                    break;
                case HSSFSimpleShape.OBJECT_TYPE_OVAL:
                case HSSFSimpleShape.OBJECT_TYPE_RECTANGLE:
                    shape = new SimpleFilledShape( simpleShape, shapeId );
                    break;
                case HSSFSimpleShape.OBJECT_TYPE_COMBO_BOX:
                    shape = new ComboboxShape( simpleShape, shapeId );
                    break;
                default:
                    throw new IllegalArgumentException("Do not know how to handle this type of shape");
            }
        }
        else
        {
            throw new IllegalArgumentException("Unknown shape type");
        }
        EscherSpRecord sp = shape.getSpContainer().getChildById(EscherSpRecord.RECORD_ID);
        if (hssfShape.getParent() != null)
            sp.setFlags(sp.getFlags() | EscherSpRecord.FLAG_CHILD);
        return shape;
    }

    protected AbstractShape()
    {
    }

    /**
     * @return  The shape container and it's children that can represent this
     *          shape.
     */
    public abstract EscherContainerRecord getSpContainer();

    /**
     * @return  The object record that is associated with this shape.
     */
    public abstract ObjRecord getObjRecord();

    /**
     * Creates an escher anchor record from a HSSFAnchor.
     *
     * @param userAnchor    The high level anchor to convert.
     * @return  An escher anchor record.
     */
    protected EscherRecord createAnchor( HSSFAnchor userAnchor )
    {
        return ConvertAnchor.createAnchor(userAnchor);
    }

    /**
     * Add standard properties to the opt record.  These properties effect
     * all records.
     *
     * @param shape     The user model shape.
     * @param opt       The opt record to add the properties to.
     * @return          The number of options added.
     */
    protected int addStandardOptions( HSSFShape shape, EscherOptRecord opt )
    {
        opt.addEscherProperty( new EscherBoolProperty( EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x080000 ) );
//        opt.addEscherProperty( new EscherBoolProperty( EscherProperties.TEXT__SIZE_TEXT_TO_FIT_SHAPE, 0x080008 ) );
        if ( shape.isNoFill() )
        {
            // Wonderful... none of the spec's give any clue as to what these constants mean.
            opt.addEscherProperty( new EscherBoolProperty( EscherProperties.FILL__NOFILLHITTEST, 0x00110000 ) );
        }
        else
        {
            opt.addEscherProperty( new EscherBoolProperty( EscherProperties.FILL__NOFILLHITTEST, 0x00010000 ) );
        }
        opt.addEscherProperty( new EscherRGBProperty( EscherProperties.FILL__FILLCOLOR, shape.getFillColor() ) );
        opt.addEscherProperty( new EscherBoolProperty( EscherProperties.GROUPSHAPE__PRINT, 0x080000 ) );
        opt.addEscherProperty( new EscherRGBProperty( EscherProperties.LINESTYLE__COLOR, shape.getLineStyleColor() ) );
        int options = 5;
        if (shape.getLineWidth() != AutoShapeConstant.LINEWIDTH_DEFAULT)
        {
            opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.LINESTYLE__LINEWIDTH, shape.getLineWidth()));
            options++;
        }
        if (shape.getLineStyle() != AutoShapeConstant.LINESTYLE_SOLID)
        {
            opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.LINESTYLE__LINEDASHING, shape.getLineStyle()));
            opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.LINESTYLE__LINEENDCAPSTYLE, 0));
            if (shape.getLineStyle() == AutoShapeConstant.LINESTYLE_NONE)
                opt.addEscherProperty( new EscherBoolProperty( EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x00080000));
            else
                opt.addEscherProperty( new EscherBoolProperty( EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x00080008));
            options += 3;
        }
        opt.sortProperties();
        return options;   // # options added
    }

    /**
     * Generate id for the CommonObjectDataSubRecord that stands behind this shape
     *
     * <p>
     *     Typically objectId starts with 1, is unique among all Obj record within the worksheet stream
     *     and increments by 1 for every new shape.
     *     For most shapes there is a straight relationship between shapeId (generated by DDF) and objectId:
     * </p>
     * <p>
     *     shapeId  is unique and starts with 1024, hence objectId can be derived as <code>shapeId-1024</code>.
     * </p>
     * <p>
     *     An exception from this rule is the CellComment shape whose objectId start with 1024.
     *      See {@link CommentShape#getCmoObjectId(int)}
     * </p>
     *
     *
     *
     * @param  shapeId   shape id as generated by drawing manager
     * @return objectId  object id that will be assigned to the Obj record
     */
    int getCmoObjectId(int shapeId){
        return shapeId - 1024;
    }
}
