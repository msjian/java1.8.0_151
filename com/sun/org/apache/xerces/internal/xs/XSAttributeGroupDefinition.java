package com.sun.org.apache.xerces.internal.xs;

public abstract interface XSAttributeGroupDefinition
  extends XSObject
{
  public abstract XSObjectList getAttributeUses();
  
  public abstract XSWildcard getAttributeWildcard();
  
  public abstract XSAnnotation getAnnotation();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\com\sun\org\apache\xerces\internal\xs\XSAttributeGroupDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */