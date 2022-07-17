
package com.office.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.office.allreader.allofficefilereader.java.awt.Stroke;
import com.office.allreader.allofficefilereader.java.awt.geom.GeneralPath;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.office.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * WidenPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: WidenPath.java 10367 2007-01-22 19:26:48Z duns $
 */
public class WidenPath extends EMFTag {

    public WidenPath() {
        super(66, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return this;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        GeneralPath currentPath = renderer.getPath();
        Stroke currentPenStroke = renderer.getPenStroke();
        if (currentPath != null && currentPenStroke != null) {
            GeneralPath newPath = new GeneralPath(
                renderer.getWindingRule());
            newPath.append(currentPenStroke.createStrokedShape(currentPath), false);
            renderer.setPath(newPath);
        }
    }
}
