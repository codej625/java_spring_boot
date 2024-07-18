# SSL, TLS 인증에 관련된 에러를 해결해보자.

<br /><br />

* 여러가지 인증서 관련 오류
---

```
HTTP 프로토콜을 사용하여 통신을 하다 보면,
프로그램별 지원하는 인증서 버전 혹은 인증서 오류로 인해 여러 가지
문제가 발생한다.

관련 문제들을 모아 하나씩 해결해보자.
```

<br /><br /><br />

1. TLS10 문제

```
OpenJDK 는 security.properties 파일에서 기본적으로 TLS 1.0 및 1.1을 비활성화 한다.
(TLS 1.2 이상을 사용해야 한다.)
이는 ORACLE에서 게시한 OpenJDK 8u292 버전부터 모두 적용되어있다 한다.
```

<br />

```
* 해결방법

java.security 파일 안에 jdk.tls.disabledAlgorithms 에서 TLSv1, TLSv1.1을 제거하면 된다고 한다.
그럼 저 파일이 어디 있는가?

Mac 경우 다음 위치에 있다.
```

```
/Library/Java/JavaVirtualMachines/{jdk}.jdk/Contents/Home/conf/security
```

```
jdk.tls.disabledAlgorithms=SSLv3, TLSv1, TLSv1.1, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL, \
    include jdk.disabled.namedCurves
```

```
위에서 TLSv1, TLSv1.1 을 제거한다.

jdk.tls.disabledAlgorithms=SSLv3, RC4, DES, MD5withRSA, \
    DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL, \
    include jdk.disabled.namedCurves
```

<br /><br /><br />

2. 외부 API 호출 중 발생하는 unable to find valid certification path to requested target 에러

```
PKIX path building failed:
sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target;
nested exception is javax.net.ssl.SSLHandshakeException:
PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
```

```
Https를 사용하는 웹사이트에 연결을 시도할 때,
Java에서 신뢰하는 인증서 목록에 해당 웹사이트의 인증서가 존재하지 않아서 발생하는 문제이다.

즉, Java에서 신뢰하는 인증서 목록에 해당 웹사이트의 인증서를 추가해주면 문제가 해결된다.
```

<br />

```
* 해결 방법

1) 브라우저에서 인증서 뷰어를 통해서 인증서를 확인하고 내보내기 버튼을 눌러 내려받는다.
```

```
2) 인증서를 내려받았으면,
Java에서 신뢰하는 인증서 목록에 인증서 등록해야 한다.
```

```
* 윈도우 기준

keytool.exe -import -alias {인증서 별칭} -keystore "{JDK_HOME}\lib\security\cacerts" -storepass changeit -file "{인증서 파일 경로}"

명령어를 입력하면 추가를 할 것인지 물어보는데,
y 혹은 yes를 입력하면 성공적으로 인증서가 추가되었다는 메시지가 뜬다.
```

```
* MacOS 기준

sudo keytool -import -alias {인증서 별칭} -keystore "{JDK_HOME}/Contents/Home/lib/security/cacerts" -storepass changeit -file "{인증서 파일 경로}"

예시)
sudo keytool -import -alias {인증서 별칭} -keystore "/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/lib/security/cacerts" -storepass changeit -file "{인증서 파일 경로}"
```
