# 스프링(자바)에서 API를 구현해보자!

<br/>

1. controller
```java
@Slf4j
@RestController
@RequestMapping(value = "/{path}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiPublicController {

  @Autowired ThreadPoolTaskExecutor threadPoolTaskExecutor;
  @Autowired ApiUtil apiUtil;

  @CrossOrigin
  @PostMapping(value = "{url}")
  public Map<String, Object> apiTest(
    /* parameter */
    HttpServletRequest request, 
    @RequestParam(value = "name") String name,
    @RequestParam(value = "email") String email,
    @RequestParam(value = "test1", required = false) String param1,
    @RequestParam(value = "test2", required = false) String param2
  ) throws Exception {
    log.debug(">> apiTest start >>");
    
    /* Time setup (KST) */
    TimeZone koreaTimeZone = TimeZone.getTimeZone("Asia/Seoul");
    TimeZone.setDefault(koreaTimeZone);
    /* Current time, Date format */
    Date now = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String today = dateFormat.format(now);
    /* Device check */
    String userAgent = request.getHeader("User-Agent");
    String device = userAgent.contains("Mobile") ? "Mobile" : "PC";
    
    /* Database insert */
    TestDTO testDTO = new TestDTO();
    testDTO.setName(name);
    testDTO.setEmail(email);
    testDTO.setReqTime(today);
    testDTO.setIp(SystemUtil.getClientIpAddr(request));
    testDTO.setDevice(device);
    
    /* Thread */
    threadPoolTaskExecutor.execute(new ProxyApiHandler(testDTO));
    
    return RESULT_STATUS_TRUE; /* HashMap<String, Object> RESULT_STATUS_TRUE; */
  }

  private class ProxyApiHandler implements Runnable {

    private testDTO recordDO;

    public ProxyApiHandler(testDTO recordDO) {
      this.recordDO = recordDO;
    }
		
    @Override
    public void run() {
	    
      Map<String, Object> requestBody = new HashMap<>();
      requestBody.put("name", recordDO.getName());
      requestBody.put("email", recordDO.getEmail());
      requestBody.put("reqTime", recordDO.getReqTime());
      requestBody.put("device", recordDO.getDevice());

      try {
        apiUtil.send(requestBody);
      } catch (Exception e) {
        log.error("API error => {}", e.toString());
      }
    }
  }
}
```

<br/>

2. util
```java
public class SystemUtil {

  public static boolean isWindows() {
    String osType = System.getProperty("os.name");
    if (osType.startsWith("Windows")) {
      return true;
    } else {
      return false;
    }
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
      return "{ip}}";
    }
  }
}

@Slf4j
@Component
public class ApiUtil {

  private static final String PROXY_URL = "{url}" /* ex) https://www.naver.com */;

  @Autowired RestTemplate restTemplate;

    public ResponseEntity<String> send(Map<String, Object> requestBody) throws Exception {
    /* Request header setup */
    HttpHeaders headers = new HttpHeaders();
//  headers.set("Authorization", "token");
    headers.setContentType(MediaType.APPLICATION_JSON);
    /* Create an HttpEntity with headers and request body data */
    HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> ret = restTemplate.exchange(PROXY_URL, HttpMethod.GET, request, String.class);
//  ResponseEntity<String> ret = restTemplate.exchange(PROXY_URL, HttpMethod.POST, request, String.class);
    return ret;
  }
}
```
