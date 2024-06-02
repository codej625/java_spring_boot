# 자바 프로젝트 명명 규칙.

<br/><br/>

* 명명 규칙

<br/>

1. 식별자
---

```
식별자(변수, 메서드, 클래스, 패키지 등의 이름)는 문자로 시작해야 한다.
문자로 시작한 뒤에는 영문 대소문자, 숫자, 밑줄(_), 달러 기호($)를 포함할 수 있다.
예약어(Java 예약어, 키워드)를 식별자로 사용해서는 안 된다.
```

<br />

2. 프로젝트 명명 규칙
---

```
1) 프로젝트 이름은 영문 알파벳으로 시작해야 한다.

2) 이름은 영문 대소문자, 숫자, 밑줄 (_)로 구성할 수 있습니다.

3) 공백은 사용할 수 없습니다.

4) 이름은 의미 있고 명확해야 합니다.

5) 예약어(예: Java의 키워드)를 피해야 합니다.

6) 이름은 관례에 따라 CamelCase(카멜 표기법) 또는 스네이크 케이스(Snake_case)를 사용할 수 있습니다.
```

<br />

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

<br />

4. 클래스 파일과 디렉토리 구조
---

```
클래스명은 클래스 파일의 이름과 일치해야 한다.
패키지 구조와 디렉토리 구조는 일치해야 한다.
```

<br />

5. 의미 있는 이름 사용
---

```
명명은 가독성을 고려하며, 이름 자체가 변수 또는 메서드의 용도를 나타내도록 작성해야 한다.
```

<br /><br /><br />

* Folder, CSS, Javascript, Img 명명 규칙.

<br />

1. 폴더 명명 규칙
---

```
src/main/resources/static/ 디렉토리는 정적 웹 자산(이미지, CSS, JavaScript 등)을 저장하는 곳이다.
폴더 및 디렉토리 명은 소문자를 사용하고, 스페이스나 특수 문자를 피해야 한다.
ex) static/css/, static/js/

여러 단어를 사용할 때 각 단어 사이에 하이픈(-)을 사용하여 구분 한다.
ex) my-css-folder, javascript-files
```

<br />

2. CSS 파일 명명 규칙
---

```
CSS 파일명은 소문자로 작성하며, 단어 사이를 하이픈(-)으로 구분 한다.
ex) styles.css, main-style.css
```

<br />

3. JavaScript 파일 명명 규칙
---

```
JavaScript 파일명은 소문자로 작성하며, 단어 사이를 카멜 케이스(CamelCase)나 밑줄(_)로 구분할 수 있다.
JavaScript 파일 확장자는 .js를 사용합니다.
ex) script.js, utility-functions.js
```

<br />

4. Image 파일 명명 규칙
---

```
image 파일명은 소문자로 작성하며, 네이밍의 조합은 '형태_의미_(순서)_상태'을 기본 순서로 사용한다.
ex) btn_cancle_01_off.gif
```

<br />

5. 리소스 버전 관리
---

```
파일 업데이트 시 브라우저 캐시 문제를 피하기 위해 파일 이름에 버전 번호를 포함할 수 있다. ex) styles-v1.0.css
```
