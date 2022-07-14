

package com.document.allreader.allofficefilereader.thirdpart.emf.data;

import java.io.IOException;

import com.document.allreader.allofficefilereader.java.awt.Dimension;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFInputStream;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFRenderer;
import com.document.allreader.allofficefilereader.thirdpart.emf.EMFTag;

/**
 * SetWindowExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: SetWindowExtEx.java 10367 2007-01-22 19:26:48Z duns $
 */
public class SetWindowExtEx extends EMFTag
{

    private Dimension size;

    public SetWindowExtEx()
    {
        super(9, 1);
    }

    public SetWindowExtEx(Dimension size)
    {
        this();
        this.size = size;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new SetWindowExtEx(emf.readSIZEL());
    }

    public String toString()
    {
        return super.toString() + "\n  size: " + size;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        renderer.setWindowSize(size);
    }
}