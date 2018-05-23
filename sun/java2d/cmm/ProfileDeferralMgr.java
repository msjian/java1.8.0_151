package sun.java2d.cmm;

import java.awt.color.ProfileDataException;
import java.util.Iterator;
import java.util.Vector;

public class ProfileDeferralMgr
{
  public static boolean deferring = true;
  private static Vector<ProfileActivator> aVector;
  
  public ProfileDeferralMgr() {}
  
  public static void registerDeferral(ProfileActivator paramProfileActivator)
  {
    if (!deferring) {
      return;
    }
    if (aVector == null) {
      aVector = new Vector(3, 3);
    }
    aVector.addElement(paramProfileActivator);
  }
  
  public static void unregisterDeferral(ProfileActivator paramProfileActivator)
  {
    if (!deferring) {
      return;
    }
    if (aVector == null) {
      return;
    }
    aVector.removeElement(paramProfileActivator);
  }
  
  public static void activateProfiles()
  {
    deferring = false;
    if (aVector == null) {
      return;
    }
    int i = aVector.size();
    Iterator localIterator = aVector.iterator();
    while (localIterator.hasNext())
    {
      ProfileActivator localProfileActivator = (ProfileActivator)localIterator.next();
      try
      {
        localProfileActivator.activate();
      }
      catch (ProfileDataException localProfileDataException) {}
    }
    aVector.removeAllElements();
    aVector = null;
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\sun\java2d\cmm\ProfileDeferralMgr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */