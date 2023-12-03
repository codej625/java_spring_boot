# 스프링부트에서 간단하게 디바이스를 체크해보자!

<br/>

1. 우선 build.gradle의 의존성을 추가해준다.
```gradle
dependencies {
  // 기존 의존성들...

  // Spring Mobile 의존성 추가
  implementation 'org.springframework.mobile:spring-mobile-device:1.1.3.RELEASE'
}
```

<br/>

2. device를 체크한다.
```java
@RestController
public class DeviceController {

  @GetMapping("/device/check")
  public String getDeviceInfo(Device device) {
    // Device 객체를 사용하여 디바이스 정보 반환
    // 예: device.isMobile(), device.isTablet(), device.isNormal()
    String deviceCheck = device.isMobile ? 1 : 2;
  }
}
