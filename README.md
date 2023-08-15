<h1>원티드 프리온보딩 인턴십 백엔드 사전과제</h1>

<ul>
  <li><a href="#지원자의-성명-김강연">지원자의 성명: 김강연</a></li>
  <li><a href="#어플리케이션의-실행-방법">어플리케이션의 실행 방법</a></li>
  <li><a href="#AWS-환경-구조도">AWS 환경 구조도</a></li>
  <li><a href="#데이터베이스-테이블-구조">데이터베이스 테이블 구조</a></li>
  <li><a href="#구현한-API의-동작을-촬영한-데모-영상-링크">구현한 API의 동작을 촬영한 데모 영상 링크</a></li>
  <li><a href="#구현-방법-및-이유에-대한-간략한-설명">구현 방법 및 이유에 대한 간략한 설명</a></li>
  <li><a href="#API-명세request-및-responses는-표-아래에-작성">API 명세(request 및 responses는 표 아래에 작성)</a></li>
</ul>

---

## 지원자의 성명: 김강연


## 어플리케이션의 실행 방법
### git clone 후 로컬 실행 방법

[구글 드라이브에 저장된 application.yml 파일](https://drive.google.com/file/d/1MkCvnfkVN7k8mc8PY3trCW-KmD9uTsJU/view?usp=share_link)

```shell
cd {clone할 경로}
git clone https://github.com/bflykky/wanted-pre-onboarding-backend.git
cd wanted-pre-onboarding-backend
# 위 링크의 application.yml 파일 저장.
# mysql 접속 후 wanted_pre_assignment 데이터베이스 생성.
# 새로운 사용자 생성 및 해당 사용자에게 wanted_pre_assignment 관련 모든 권한 부여
# 저장한 application.yml 파일의 username 및 password를 새로 생성한 mysql의 유저 username과 password로 변경
# application.yml을 wanted-pre-onboarding-backend/src/main/resources 경로에 저장
./gradlew build
cd build/libs
java -jar pre-onboarding-0.0.1-SNAPSHOT.jar
```
이후, `POSTMAN` 등의 API 테스트 프로그램을 이용해 어플리케이션의 API를 호출할 수 있다.<br/>
1. POSTMAN의 워크스페이스 초대 링크이다. 아래 링크를 들어가 `사전과제 테스트(로컬)` 컬렉션의 만들어진 Request 및 새로 Request를 생성하여 API를 호출할 수 있다.<br/>
https://app.getpostman.com/join-team?invite_code=9a4d61ce8a3cc694c4d1ae4995448f31&target_code=dfce1f9a77c87c34404b6920165fe28a<br/>
2. POSTMAN의 Collection 링크이다. 만들어진 Requet로 API를 호출할 수 있다.<br/>
[POSTMAN 컬렉션 링크(로컬 서버)](https://www.postman.com/ssu-semicolon/workspace/wanted-pre-onboarding-pre-assignment/collection/28175596-b2fb3381-32c7-4c42-bb9c-73f9499fdc07?action=share&creator=28175596)

### AWS에 배포된 서버 접속 방법

AWS EC2 및 RDS를 이용해, 클라우드 환경 서버에 어플리케이션 환경을 구축하였다.
배포한 AWS 서버의 주소는 다음과 같다(포트번호 포함).<br/>
http://ec2-43-201-120-175.ap-northeast-2.compute.amazonaws.com:8080
<br/>해당 도메인 뒤에 API 별 경로를 붙여서, 아래와 같이 `POSTMAN` 등의 API 테스트 프로그램을 이용해 어플리케이션의 API를 호출할 수 있다.<br/>

1. POSTMAN의 워크스페이스 초대 링크이다. 아래 링크를 통해 만들어진 Request 및 새로 Request를 생성하여 API를 호출할 수 있다.<br/>
https://app.getpostman.com/join-team?invite_code=9a4d61ce8a3cc694c4d1ae4995448f31&target_code=dfce1f9a77c87c34404b6920165fe28a<br/>
2. POSTMAN의 Collection 링크이다. 만들어진 Requet로 API를 호출할 수 있다.<br/>
[POSTMAN 컬렉션 링크(AWS 서버)](https://www.postman.com/ssu-semicolon/workspace/wanted-pre-onboarding-pre-assignment/collection/28175596-6c49ec53-40f7-4aa6-9881-b9edf312f223?action=share&creator=28175596)

> ### 주의점
> 워크스페이스 내에 <strong>'사전 과제 테스트(AWS)'</strong>와 <strong>'사전 과제 테스트(로컬)'</strong> 2개가 있는데, 2개의 baseUrl이 각각 AWS서버 주소와, localhost:8080으로 다르게 설정되어 있기 때문에, 테스트할 때 확인해야 한다.

POSTMAN 사용 예시이다.
<img width="1770" alt="image" src="https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/0173583e-165e-4bc5-ad86-49811916bb3a">

> ## !!!주의점
> `이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.` 조건에 따라 이미 회원가입한 회원과 동일한 이메일을 이용해 회원가입하여도 정상 회원가입이 된다.
> <strong>하지만, 이후 로그인 과정에서 오류가 발생하므로 동일한 이메일로 회원가입해서는 안된다.</strong>
> 중복된 회원가입을 클라이언트가 직접 확인할 수 있게, `/members GET` 요청을 통해 회원가입된 회원들을 조회할 수 있다.(API 명세 참고)

## AWS 환경 구조도
![AWS 설계도](https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/14a905a5-265c-43e4-b015-43d727ab32bd)

---

## 데이터베이스 테이블 구조
!!! 사용한 MySQL 버전은 8.0.33입니다.<br/>
![사전과제 테이블 다이어그램](https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/39bea3d3-6b0d-4a61-90e9-21d3f9cce5e7)

---

## 구현한 API의 동작을 촬영한 데모 영상 링크
[구글 드라이브 API 동작 촬영 데모 영상 링크(MOV 파일)](https://drive.google.com/file/d/19DSba2G2Ncv5OCSgL6YUaGGeFOrhpkMb/view?usp=share_link)

---

## 구현 방법 및 이유에 대한 간략한 설명

### 데이터 송수신 형태

전체적인 구현 방식은 다음과 같다.
먼저, RESTful API를 목적으로 API를 설계하였다. 이후, Request 시 전달해야 할 데이터가 있는 경우는 `RequestDto` 클래스를 생성하여 해당 객체를 이용해 Request Body의 데이터를 매핑하여 저장하였다.
동일한 방식으로, `ResponseDto` 클래스를 생성 후, 클라이언트가 요청한 데이터를 해당 ResponseDto 객체에 매핑하여 Response Body에 담아 전달한다.
현재 대표적인 데이터 송수신 형태인 JSON을 이용하기 위해 이와 같은 Dto 클래스를 이용하여 구현하였다.


### CRUD 방식
`Spring MVC`, `JPA` 및 `pring Data JPA`를 이용하여 CRUD를 구현하였다. 스프링(스프링 부트)을 사용할 경우 JPA는 굳이 안쓸 필요가 없다 생각하였다.
'Controller', 'Service', 'Repository', 'Entity' 단을 분리하여 구현하였고, 회원은 Member로, 게시글은 Post로 도메인을 설정하여 구현하였다.

| Controller       | Service       | Repository       | Entity |
|------------------|---------------|------------------|--------|
| MemberController | MemberService | MemberRepository | Member |
| PostController   | PostService   | PostRepository   | Post   |


### 이메일과 비밀번호의 유효성 검증 및 비밀번호 암호화
RequestDto 종류의 클래스들을 구현 시, <strong>javax.validation 라이브러리</strong>의 `@NotNull`, `@Email` 등의 어노테이션을 이용하여, 
예외 발생 시 `@Valid` 어노테이션과 `GlobalExceptionHandler` 클래스에서 예외 처리를 하거나, 필터의 `handler`에서 예외 처리 후 에러 Response를 응답하도록 구현하였다.
비밀번호 암호화의 경우, 스프링 시큐리티의 BCryptEncoder 클래스를 이용해 클라이언트로부터 전달받은 비밀번호를 암호화하여 DB에 저장하도록 구현하였다.


### JWT 발급 및 JWT를 통한 작성자 여부 검증
`스프링 시큐리티(Spring Security)`를 이용하여 filter, AuthenticationProvider, handler, token 등을 구현하여 해당 기능을 구현하였다.
`로그인`과 `작성자 권한`은 `인증(Authentication)`과 `권한(Authorization)`이므로, 스프링에서 제공하는 스프링 시큐리티를 이용하는 게 적절하다고 생각하였다.
구현한 필터는 총 3가지이며 주로 검증하는 부분은 다음과 같다.
1. `JwtIssueFilter` 클래스에서 클라이언트가 로그인 요청을 보낼 경우 Request Body의 이메일과 비밀번호를 이용해, 이메일 및 비밀번호가 유효한지, 해당 이메일을 가진 회원이 존재하는지, 비밀번호는 일치하는지 검증하고, JWT를 발급한다.
2. `JwtAuthenticationFilter` 클래스에서 클라이언트가 게시글 생성 요청을 보낼 경우 Request 헤더의 JWT를 이용해, JWT가 만료되진 않았는지, JWT의 subject가 실제로 회원 DB에 존재하는지 등을 통해 로그인된 사용자인지 검증한다.
3. `WriterAuthorizationFilter` 클래스에서 클라이언트가 게시글 수정/삭제 요청을 보낼 경우 Request 헤더의 JWT를 이용해 수정/삭제 요청을 한 대상 게시글의 작성자와 일치하는지 검증한다.

처음 써보는 기술이었기 때문에 기술 적용에 많은 어려움이 있어 아래와 같이 포기한 점이 많았다.
- 중복된 설정이 있는 등, 코드가 깔끔하지 못하다.
- 원활하게 Controller 단으로 플로우를 이동시키지 못하고 불가피하게 response를 handler에서 전송하는 경우가 존재한다.
- 다른 필터에서 동일한 내용을 검증하는 비즈니스 로직이 있다.

그렇지만, 해당 기술을 공부하고 처음으로 적용해보았다는 점에서 스스로 의미가 있다고 생각하였다.

---

## API 명세(request 및 responses는 표 아래에 작성)
|URI|HTTP METHOD|기능|
|------|---|---|
|/members|POST|사용자 회원가입|
|/members/login|POST|사용자 로그인|
|/members|GET|전체 사용자 조회|
|/posts|POST|새로운 게시글 생성|
|/posts?page=number|GET|게시글 목록 조회(10개 단위)|
|/posts/:postId|GET|특정 게시글 조회|
|/posts/:postId|PATCH|특정 게시글 수정|
|/posts/:postId|DELETE|특정 게시글 삭제|

### /members POST 사용자 회원가입
Request
```json
{
	"email": "String", // '@'를 포함한 이메일 형식
	"password": "String" // 8자리 이상의 문자열
}
```
Response
```json
{
	"code": "String", // 성공 코드
	"message", "String", // 성공 메시지
	"data": {
		"memberId": "number" // 회원가입한 사용자의 고유 번호
	}
}
```
---
### /members/login POST 사용자 로그인
Request

```json
{
	"email": "String", // '@'를 포함한 이메일 형식
	"password": "String" // 8자리 이상의 문자열
}
```

Response

```json
{
	"code": "String", // 성공 코드
	"message", "String", // 성공 메시지
	"data": {
		"token": "String" // 로그인한 사용자의 JWT 형식의 Access Token
	}
}
```
---
### /members GET 전체 사용자 조회
해당 API는 위에서 명시한 주의점에 따라, 이미 등록된 회원들을 조회할 수 있어야 한다고 판단하여 구현하였다. 명시한 주의점은 다음과 같다.
> ## !!!주의점
> `이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.` 조건에 따라 이미 회원가입한 회원과 동일한 이메일을 이용해 회원가입하여도 정상 회원가입이 된다.
> <strong>하지만, 이후 로그인 과정에서 오류가 발생하므로 동일한 이메일로 회원가입해서는 안된다.</strong>

Respone
```json
{
  "code": "String", // ex) "M003"
  "message": "String", // ex) "모든 사용자들을 성공적으로 조회하였습니다."
  "data": [ // array 형식
    {
      "memberId": "number", // 사용자의 고유 id
      "email": "String", // 사용자의 email
    },
    ...
    {
      "memberId": "number", // 사용자의 고유 id
      "email": "String", // 사용자의 email
    }
  ]
}

```

---
### /posts POST 새로운 게시글 생성
Request

```json
{
	"title": "String",
	"content": "String"
}
```

Response

```json
{
	"code": "String",
	"message": "String",
	"data": {
		"postId": "number" // 새로 생성한 게시글의 고유 id
	}
}
```
---
### /posts GET 게시글 목록 조회(10개 단위)
쿼리 스트링의 page 값을 입력하지 않아도 된다. 이때, 최근 작성된 10개의 게시글이 response로 전달된다.
page의 값은 0부터 시작한다.

Response
```json
{
  "code": "String", // ex) "M006"
  "message": "String", // ex) "게시글 목록을 성공적으로 조회하였습니다."
  "data": [  // array 형식
    {
      "postId": "number", // 게시글의 고유 id
      "writer": "String", // 게시글 작성자의 이메일
      "writeDate": "LocalDateTime", 게시글이 작성된 시간 ex) 2023-08-06 20:05:55
      "title": "String", // 게시글의 제목
      "content": "String" // 게시글의 내용
    },
    ...
    {
      "postId": "number", // 게시글의 고유 id
      "writer": "String", // 게시글 작성자의 이메일
      "writeDate": "LocalDateTime", 게시글이 작성된 시간 ex) 2023-08-06 20:05:55
      "title": "String", // 게시글의 제목
      "content": "String" // 게시글의 내용
    }
  ]
}
```
---
### /posts/:postId GET 특정 게시글 조회
Response
```json
{
  "code": "String", // ex) "M005"
  "message": "String", // "입력한 postId의 게시글을 성공적으로 조회하였습니다."
  "data": {
    "postId": "number", // 게시글의 고유 id
    "writer": "String", // 게시글 작성자의 이메일
    "writeDate": "LocalDateTime", 게시글이 작성된 시간 ex) 2023-08-06 20:05:55
    "title": "String", // 게시글의 제목
    "content": "String" // 게시글의 내용
  }
}
```
---
### /posts/:postId PATCH 특정 게시글 수정
Requset

```json
{
	"title": "String", // 수정할 게시글 제목
	"content": "String" // 수정할 게시글 내용
}
```

Response

```json
{
    "code": "String", // ex) "M007"
    "message": "String", // ex) "입력한 postId의 게시글을 성공적으로 수정하였습니다."
    "data": {
        "postId": "number" // 수정한 게시글의 고유 id
    }
}
```
---
### /posts/:postId DELETE 특정 게시글 삭제
Response
```json
{
    "code": "String", // ex) "M008",
    "message": "String", // ex) "입력한 postId의 게시글을 성공적으로 삭제하였습니다",
    "data": null
}
```








