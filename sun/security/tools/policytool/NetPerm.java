package sun.security.tools.policytool;

class NetPerm
  extends Perm
{
  public NetPerm()
  {
    super("NetPermission", "java.net.NetPermission", new String[] { "setDefaultAuthenticator", "requestPasswordAuthentication", "specifyStreamHandler", "setProxySelector", "getProxySelector", "setCookieHandler", "getCookieHandler", "setResponseCache", "getResponseCache" }, null);
  }
}


/* Location:              C:\Program Files (x86)\Java\jre1.8.0_151\lib\rt.jar!\sun\security\tools\policytool\NetPerm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */