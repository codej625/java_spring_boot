# jar 파일 만들기

<br/>

1. jar 파일 만들기
```
command line -> .\gradlew build
```

2. jar 이름 바꾸기
```
jar {
  // JAR 파일의 기본 이름 설정
  archiveBaseName = 'myCustomName'
  // 버전 설정을 유지하거나 변경
  archiveVersion = '0.0.1'
  // 확장자 설정 (일반적으로 '.jar'을 유지)
  archiveExtension = 'jar'
}
```
```
ex)
jar {
  archiveBaseName = 'myApp'
  archiveVersion = '1.0'
}
```
