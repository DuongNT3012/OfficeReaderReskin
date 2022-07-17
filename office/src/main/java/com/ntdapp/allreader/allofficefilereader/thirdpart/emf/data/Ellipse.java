

package com.ntdapp.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.ntdapp.allreader.allofficefilereader.java.awt.Rectangle;
import com.ntdapp.allreader.allofficefilereader.java.awt.geom.Ellipse2D;
import com.ntdapp.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.ntdapp.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.ntdapp.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * Ellipse TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: Ellipse.java 10367 2007-01-22 19:26:48Z duns $
 */
public class Ellipse extends EMFTag
{
    private Rectangle bounds;

    public Ellipse(Rectangle bounds)
    {
        this();
        this.bounds = bounds;
    }

    public Ellipse()
    {
        super(42, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new Ellipse(emf.readRECTL());
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
        // The Ellipse function draws an ellipse. The center of the ellipse
        // is the center of the specified bounding rectangle.
        // The ellipse is outlined by using the current pen and is filled by
        // using the current brush.
        // The current position is neither used nor updated by Ellipse.
        renderer.fillAndDrawOrAppend(new Ellipse2D.Double(bounds.x, bounds.y, bounds
            .getWidth(), bounds.getHeight()));
    }
}