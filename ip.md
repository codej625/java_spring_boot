# 아이피를 알아내자!

<br/>

```java
  package kr.co.mplanit.freemilkt.util;
  
  import java.net.InetAddress;
  import java.net.UnknownHostException;
  
  import javax.servlet.http.HttpServletRequest;
  
  public class SystemUtil {

    public static boolean isWindows() {
      String osType = System.getProperty("os.name");
      if (osType.startsWith("Windows"))
        return true;
      else
        return false;
    }
  
    public static String getClientIpAddr(HttpServletRequest request) {
      String ip = request.getHeader("X-Forwarded-For");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
      return ip;
    }
    
    public static String getClientAgent(HttpServletRequest request) {
      return request.getHeader("User-Agent");
    }
    
    public static String getServerIpAddr() {
      try {
        InetAddress inet = InetAddress.getLocalHost();
        return inet.getHostAddress();
      } catch (UnknownHostException e) {
        return "127.0.0.1";
      }
    }
  }
```
