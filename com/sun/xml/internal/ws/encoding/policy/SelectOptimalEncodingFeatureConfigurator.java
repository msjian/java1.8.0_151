package com.sun.xml.internal.ws.encoding.policy;

import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;
import com.sun.xml.internal.ws.policy.AssertionSet;
import com.sun.xml.internal.ws.policy.Policy;
import com.sun.xml.internal.ws.policy.PolicyAssertion;
import com.sun.xml.internal.ws.policy.PolicyException;
import com.sun.xml.internal.ws.policy.PolicyMap;
import com.sun.xml.internal.ws.policy.PolicyMapKey;
import com.sun.xml.internal.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceFeature;

public class SelectOptimalEncodingFeatureConfigurator
  implements PolicyFeatureConfigurator
{
  public static final QName enabled = new QName("enabled");
  
  public SelectOptimalEncodingFeatureConfigurator() {}
  
  public Collection<WebServiceFeature> getFeatures(PolicyMapKey paramPolicyMapKey, PolicyMap paramPolicyMap)
    throws PolicyException
  {
    LinkedList localLinkedList = new LinkedList();
    if ((paramPolicyMapKey != null) && (paramPolicyMap != null))
    {
      Policy localPolicy = paramPolicyMap.getEndpointEffectivePolicy(paramPolicyMapKey);
      if ((null != localPolicy) && (localPolicy.contains(EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION)))
      {
        Iterator localIterator1 = localPolicy.iterator();
        while (localIterator1.hasNext())
        {
          AssertionSet localAssertionSet = (AssertionSet)localIterator1.next();
          Iterator localIterator2 = localAssertionSet.iterator();
          while (localIterator2.hasNext())
          {
            PolicyAssertion localPolicyAssertion = (PolicyAssertion)localIterator2.next();
            if (EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION.equals(localPolicyAssertion.getName()))
            {
              String str = localPolicyAssertion.getAttributeValue(enabled);
              boolean bool = (str == null) || (Boolean.valueOf(str.trim()).booleanValue());
              localLinkedList.add(new SelectOptimalEncodingFeature(bool));
            }
          }
        }
      }
    }
    return localLinkedList;
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\com\sun\xml\internal\ws\encoding\policy\SelectOptimalEncodingFeatureConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */