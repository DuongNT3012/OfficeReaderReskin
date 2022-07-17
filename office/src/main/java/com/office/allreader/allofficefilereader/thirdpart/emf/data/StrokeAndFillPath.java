

package com.office.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.office.allreader.allofficefilereader.java.awt.Rectangle;
import com.office.allreader.allofficefilereader.java.awt.geom.GeneralPath;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * StrokeAndFillPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: StrokeAndFillPath.java 10367 2007-01-22 19:26:48Z duns $
 */
public class StrokeAndFillPath extends EMFTag
{

    private Rectangle bounds;

    public StrokeAndFillPath()
    {
        super(63, 1);
    }

    public StrokeAndFillPath(Rectangle bounds)
    {
        this();
        this.bounds = bounds;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new StrokeAndFillPath(emf.readRECTL());
    }


    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        GeneralPath currentPath = renderer.getPath();
        // fills the current path
        if (currentPath != null)
        {
            renderer.fillShape(currentPath);
            renderer.drawShape(currentPath);
            renderer.setPath(null);
        }
    }
}