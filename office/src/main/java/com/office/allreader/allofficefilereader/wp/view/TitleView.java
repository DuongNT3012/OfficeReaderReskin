/*
 * 文件名称:          TitleView.java
 *  
 * 编译器:            android2.2
 * 时间:              下午5:18:40
 */
package com.office.allreader.allofficefilereader.wp.view;

import com.office.allreader.allofficefilereader.constant.wp.WPViewConstant;
import com.office.allreader.allofficefilereader.simpletext.control.IWord;
import com.office.allreader.allofficefilereader.simpletext.model.IDocument;
import com.office.allreader.allofficefilereader.simpletext.model.IElement;
import com.office.allreader.allofficefilereader.simpletext.view.AbstractView;
import com.office.allreader.allofficefilereader.simpletext.view.IView;
import com.office.allreader.allofficefilereader.system.IControl;

/**
 * header、footer view
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-7-4
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public class TitleView extends AbstractView
{

    /**
     * 
     * @param elem
     */
    public TitleView(IElement elem)
    {
        super();
        this.elem = elem;
    }
    

    public short getType()
    {
        return WPViewConstant.TITLE_VIEW;
    }
    
    /**
     * 得到组件
     */
    public IWord getContainer()
    {
        if (pageRoot != null)
        {
            return pageRoot.getContainer();
        }
        return null;
    }
    
    /**
     * 得到组件
     */
    public IControl getControl()
    {
        if (pageRoot != null)
        {
            return pageRoot.getControl();
        }
        return null;
    }

    /**
     * 得到model
     */
    public IDocument getDocument()
    {
        if (pageRoot != null)
        {
            return pageRoot.getDocument();
        }
        return null;
    }

    public void setPageRoot(IView root)
    {
        pageRoot = root;
    }
    
    public void dispose()
    {
        super.dispose();
        pageRoot = null;
    }
    

    private IView pageRoot;
}
