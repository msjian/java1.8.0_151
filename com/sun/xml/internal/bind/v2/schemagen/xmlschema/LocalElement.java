package com.sun.xml.internal.bind.v2.schemagen.xmlschema;

import com.sun.xml.internal.txw2.TypedXmlWriter;
import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("element")
public abstract interface LocalElement
  extends Element, Occurs, TypedXmlWriter
{
  @XmlAttribute
  public abstract LocalElement form(String paramString);
  
  @XmlAttribute
  public abstract LocalElement name(String paramString);
  
  @XmlAttribute
  public abstract LocalElement ref(QName paramQName);
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\com\sun\xml\internal\bind\v2\schemagen\xmlschema\LocalElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */