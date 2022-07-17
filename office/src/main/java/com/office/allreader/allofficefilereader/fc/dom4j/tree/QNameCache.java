

package com.office.allreader.allofficefilereader.fc.dom4j.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.office.allreader.allofficefilereader.fc.dom4j.DocumentFactory;
import com.office.allreader.allofficefilereader.fc.dom4j.Namespace;
import com.office.allreader.allofficefilereader.fc.dom4j.QName;


/**
 * <p>
 * <code>QNameCache</code> caches instances of <code>QName</code> for reuse
 * both across documents and within documents.
 * </p>< < < < < < < QNameCache.java
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.16 $ =======
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.16 $ >>>>>>> 1.15
 */
public class QNameCache
{
    /** Cache of {@link QName}instances with no namespace */
    protected Map noNamespaceCache = Collections.synchronizedMap(new WeakHashMap());

    /**
     * Cache of {@link Map}instances indexed by namespace which contain caches
     * of {@link QName}for each name
     */
    protected Map namespaceCache = Collections.synchronizedMap(new WeakHashMap());

    /**
     * The document factory associated with new QNames instances in this cache
     * or null if no instances should be associated by default
     */
    private DocumentFactory documentFactory;

    public QNameCache()
    {
    }

    public QNameCache(DocumentFactory documentFactory)
    {
        this.documentFactory = documentFactory;
    }

    /**
     * Returns a list of all the QName instances currently used
     * 
     * @return DOCUMENT ME!
     */
    public List getQNames()
    {
        List answer = new ArrayList();
        answer.addAll(noNamespaceCache.values());

        for (Iterator it = namespaceCache.values().iterator(); it.hasNext();)
        {
            Map map = (Map)it.next();
            answer.addAll(map.values());
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * 
     * @return the QName for the given name and no namepsace
     */
    public QName get(String name)
    {
        QName answer = null;

        if (name != null)
        {
            answer = (QName)noNamespaceCache.get(name);
        }
        else
        {
            name = "";
        }

        if (answer == null)
        {
            answer = createQName(name);
            answer.setDocumentFactory(documentFactory);
            noNamespaceCache.put(name, answer);
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param name
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * 
     * @return the QName for the given local name and namepsace
     */
    public QName get(String name, Namespace namespace)
    {
        Map cache = getNamespaceCache(namespace);
        QName answer = null;

        if (name != null)
        {
            answer = (QName)cache.get(name);
        }
        else
        {
            name = "";
        }

        if (answer == null)
        {
            answer = createQName(name, namespace);
            answer.setDocumentFactory(documentFactory);
            cache.put(name, answer);
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param localName
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * @param qName
     *            DOCUMENT ME!
     * 
     * @return the QName for the given local name, qualified name and namepsace
     */
    public QName get(String localName, Namespace namespace, String qName)
    {
        Map cache = getNamespaceCache(namespace);
        QName answer = null;

        if (localName != null)
        {
            answer = (QName)cache.get(localName);
        }
        else
        {
            localName = "";
        }

        if (answer == null)
        {
            answer = createQName(localName, namespace, qName);
            answer.setDocumentFactory(documentFactory);
            cache.put(localName, answer);
        }

        return answer;
    }

    public QName get(String qualifiedName, String uri)
    {
        int index = qualifiedName.indexOf(':');

        if (index < 0)
        {
            return get(qualifiedName, Namespace.get(uri));
        }
        else
        {
            String name = qualifiedName.substring(index + 1);
            String prefix = qualifiedName.substring(0, index);

            return get(name, Namespace.get(prefix, uri));
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param qname
     *            DOCUMENT ME!
     * 
     * @return the cached QName instance if there is one or adds the given qname
     *         to the cache if not
     */
    public QName intern(QName qname)
    {
        return get(qname.getName(), qname.getNamespace(), qname.getQualifiedName());
    }

    /**
     * DOCUMENT ME!
     * 
     * @param namespace
     *            DOCUMENT ME!
     * 
     * @return the cache for the given namespace. If one does not currently
     *         exist it is created.
     */
    protected Map getNamespaceCache(Namespace namespace)
    {
        if (namespace == Namespace.NO_NAMESPACE)
        {
            return noNamespaceCache;
        }

        Map answer = null;

        if (namespace != null)
        {
            answer = (Map)namespaceCache.get(namespace);
        }

        if (answer == null)
        {
            answer = createMap();
            namespaceCache.put(namespace, answer);
        }

        return answer;
    }

    /**
     * A factory method
     * 
     * @return a newly created {@link Map}instance.
     */
    protected Map createMap()
    {
        return Collections.synchronizedMap(new HashMap());
    }

    /**
     * Factory method to create a new QName object which can be overloaded to
     * create derived QName instances
     * 
     * @param name
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected QName createQName(String name)
    {
        return new QName(name);
    }

    /**
     * Factory method to create a new QName object which can be overloaded to
     * create derived QName instances
     * 
     * @param name
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected QName createQName(String name, Namespace namespace)
    {
        return new QName(name, namespace);
    }

    /**
     * Factory method to create a new QName object which can be overloaded to
     * create derived QName instances
     * 
     * @param name
     *            DOCUMENT ME!
     * @param namespace
     *            DOCUMENT ME!
     * @param qualifiedName
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    protected QName createQName(String name, Namespace namespace, String qualifiedName)
    {
        return new QName(name, namespace, qualifiedName);
    }
}
