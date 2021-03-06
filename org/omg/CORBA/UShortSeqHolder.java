package org.omg.CORBA;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UShortSeqHolder
  implements Streamable
{
  public short[] value = null;
  
  public UShortSeqHolder() {}
  
  public UShortSeqHolder(short[] paramArrayOfShort)
  {
    value = paramArrayOfShort;
  }
  
  public void _read(InputStream paramInputStream)
  {
    value = UShortSeqHelper.read(paramInputStream);
  }
  
  public void _write(OutputStream paramOutputStream)
  {
    UShortSeqHelper.write(paramOutputStream, value);
  }
  
  public TypeCode _type()
  {
    return UShortSeqHelper.type();
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\org\omg\CORBA\UShortSeqHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */