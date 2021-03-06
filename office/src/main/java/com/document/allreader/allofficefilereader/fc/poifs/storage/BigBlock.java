

        

package com.document.allreader.allofficefilereader.fc.poifs.storage;

/**
 * Abstract base class of all POIFS block storage classes. All
 * extensions of BigBlock should write 512 or 4096 bytes of data when
 * requested to write their data (as per their BigBlockSize).
 *
 * This class has package scope, as there is no reason at this time to
 * make the class public.
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;


abstract class BigBlock
    implements BlockWritable
{
    /** 
     * Either 512 bytes ({@link POIFSConstants#SMALLER_BIG_BLOCK_SIZE}) 
     *  or 4096 bytes ({@link POIFSConstants#LARGER_BIG_BLOCK_SIZE})
     */
    protected POIFSBigBlockSize bigBlockSize;
    
    protected BigBlock(POIFSBigBlockSize bigBlockSize) {
       this.bigBlockSize = bigBlockSize;
    }

    /**
     * Default implementation of write for extending classes that
     * contain their data in a simple array of bytes.
     *
     * @param stream the OutputStream to which the data should be
     *               written.
     * @param data the byte array of to be written.
     *
     * @exception IOException on problems writing to the specified
     *            stream.
     */

    protected void doWriteData(final OutputStream stream, final byte [] data)
        throws IOException
    {
        stream.write(data);
    }

    /**
     * Write the block's data to an OutputStream
     *
     * @param stream the OutputStream to which the stored data should
     *               be written
     *
     * @exception IOException on problems writing to the specified
     *            stream
     */

    abstract void writeData(final OutputStream stream)
        throws IOException;

    /* ********** START implementation of BlockWritable ********** */

    /**
     * Write the storage to an OutputStream
     *
     * @param stream the OutputStream to which the stored data should
     *               be written
     *
     * @exception IOException on problems writing to the specified
     *            stream
     */

    public void writeBlocks(final OutputStream stream)
        throws IOException
    {
        writeData(stream);
    }

    /* **********  END  implementation of BlockWritable ********** */
}   // end abstract class BigBlock

