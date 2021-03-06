

        

package com.document.allreader.allofficefilereader.fc.poifs.eventfilesystem;

/**
 * Interface POIFSReaderListener
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 * @version %I%, %G%
 */

public interface POIFSReaderListener
{

    /**
     * Process a POIFSReaderEvent that this listener had registered
     * for
     *
     * @param event the POIFSReaderEvent
     */

    public void processPOIFSReaderEvent(POIFSReaderEvent event);
}   // end public interface POIFSReaderListener

