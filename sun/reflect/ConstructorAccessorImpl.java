package sun.reflect;

import java.lang.reflect.InvocationTargetException;

abstract class ConstructorAccessorImpl
  extends MagicAccessorImpl
  implements ConstructorAccessor
{
  ConstructorAccessorImpl() {}
  
  public abstract Object newInstance(Object[] paramArrayOfObject)
    throws InstantiationException, IllegalArgumentException, InvocationTargetException;
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\sun\reflect\ConstructorAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */