# 자바 프로젝트 명명 규칙.

<br/><br/>

* 명명 규칙

<br/>

1. 프로젝트 생성 시
---

```
Group

-> 일반적으로 도메인의 역순으로 ex) com.codej625
```

```
Artifact

-> 프로젝트의 이름 ex) web-service, myproject
```

```
Package name

-> 패키지 이름은 Java 클래스들의 논리적 그룹을 형성한다.
-> 일반적으로 Group ID와 Artifact ID를 조합하여 패키지 이름을 만든다.
-> ex) com.codej625.myproject
```

<br /><br /><br />

2. 프로젝트 명명 규칙
---

```
1) 프로젝트 이름은 영문 알파벳으로 시작해야 한다.

2) 이름은 일반적으로 소문자 영문, 숫자, 하이픈(-)으로 구성할 수 있다.

3) 공백은 사용할 수 없다.

4) 이름은 의미 있고 명확해야 한다.

5) 예약어를 피해야 한다.

6) 대문자로 시작을 권장 ex) TestApplication

7) 스프링부트 공식 깃에서는 스네이크 케이스를 사용하고 있다.
```

<br /><br /><br />

3. 관례적 명명 규칙
---

```
1) 패키지(Package)명

-> 패키지명은 모두 소문자로 작성하며, 일반적으로 역도메인 이름을 역으로 사용 한다.
-> com.company.folder ex) com.naver.myapp
-> kr.co.company.project ex) kr.co.naver.firstproject
-> kr.co.company.platform.project ex) kr.co.naver.eclipse.firstproject
```

```
2) 클래스(Class)명

-> 대문자로 시작하고 CamelCase(파스칼 표기법)를 사용
-> ex) MyClass, BankAccount.
```

```
3) 메서드(Method)명

-> 소문자로 시작하고 CamelCase를 사용
-> ex) getBalance(), withdrawFunds().
```

```
4) 변수(Variable)명

-> 소문자로 시작하고 CamelCase를 사용
-> ex) accountBalance, userName.
```

```
5) 상수(Constant)명

-> 대문자로 작성하고 단어 사이를 밑줄(_)로 구분 한다.
-> ex) MAX_VALUE
```

<br /><br /><br />

4. 클래스 파일과 디렉토리 구조
---

```
클래스명은 클래스 파일의 이름과 일치해야 한다.
패키지 구조와 디렉토리 구조는 일치해야 한다.
```

<br /><br /><br />

5. 의미 있는 이름 사용
---

```
명명은 가독성을 고려하며, 이름 자체가 변수 또는 메서드의 용도를 나타내도록 작성해야 한다.
```

<br /><br /><br />

* Folder, CSS, Javascript, Img 명명 규칙.

<br />

1. 폴더 명명 규칙

```
src/main/resources/static/ 디렉토리는 정적 웹 자산(이미지, CSS, JavaScript 등)을 저장하는 곳이다.
폴더 및 디렉토리 명은 소문자를 사용하고, 스페이스나 특수 문자를 피해야 한다.
ex) static/css/, static/js/

여러 단어를 사용할 때 각 단어 사이에 언더바를 사용하여 구분 한다.
ex) my_css_folder, javascript_files
```

<br />

2. CSS 파일 명명 규칙

```
CSS 파일명은 소문자로 작성하며, 단어 사이를 언더바로 구분 한다.
ex) styles.css, main_style.css
```

<br />

3. JavaScript 파일 명명 규칙

```
JavaScript 파일명은 소문자로 작성하며, 단어 사이를 카멜 케이스(CamelCase)나 밑줄(_)로 구분할 수 있다.
JavaScript 파일 확장자는 .js를 사용한다.
ex) script.js, utility_functions.js
```

<br />

4. Image 파일 명명 규칙

```
image 파일명은 소문자로 작성하며, 네이밍의 조합은 '형태_의미_(순서)_상태'을 기본 순서로 사용한다.
ex) btn_cancle_01_off.gif
```

<br />

5. HTML 파일 명명 규칙

```
HTML 파일의 이름은 소문자로 작성하며, 여러 단어를 포함하는 경우, 단어 사이에 언더바를 사용하여 가독성을 높일 수 있다.
ex) my_html_file.html
```

<br />

6. 리소스 버전 관리

```
파일 업데이트 시 브라우저 캐시 문제를 피하기 위해 파일 이름에 버전 번호를 포함할 수 있다. ex) styles-v1.0.css
```
