package org.omg.CosNaming;

import org.omg.CORBA.portable.IDLEntity;

public final class NameComponent
  implements IDLEntity
{
  public String id = null;
  public String kind = null;
  
  public NameComponent() {}
  
  public NameComponent(String paramString1, String paramString2)
  {
    id = paramString1;
    kind = paramString2;
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\org\omg\CosNaming\NameComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */