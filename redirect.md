# spring에서 redirect하는 방법을 알아보자!

1. query string이 없을때 redirect
```java
@RequestMapping(value = "/", method = RequestMethod.GET)
public String main() throws Exception {
  return "redirect:/test";
}
```
2. query string이 있을때 (HttpServletRequest객체를 활용한 redirect)
```java
@RequestMapping(value = "/", method = RequestMethod.GET)
public String main(HttpServletRequest request) throws Exception {
  if (null != request.getQueryString()) {
    return "redirect:/test?" + request.getQueryString();
  } else {
    return "redirect:/test";
  }
}
```