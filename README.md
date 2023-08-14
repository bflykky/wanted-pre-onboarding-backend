- 교육생은 게시판을 관리하는 RESTful API를 개발하고 그 결과를 제출해야 합니다. (3. API 요구사항 참고)
- 데이터 저장소로는 MySQL 8.0 버전의 관계형데이터베이스를 사용해주세요.
- API의 정상 동작 여부, 작성된 코드의 품질, Git & Github의 사용 수준 등이 평가 기준이 됩니다.
- 요구사항에 맞게 API를 만든 후에 아래의 기능을 추가할 경우 가산점이 주어집니다.
  - 통합 테스트 또는 단위 테스트 코드를 추가한 경우
  - docker compose를 이용하여 애플리케이션 환경을 구성한 경우 (README.md 파일에 docker-compose 실행 방법 반드시 기입)
  - 클라우드 환경(AWS, GCP)에 배포 환경을 설계하고 애플리케이션을 배포한 경우 (README.md 파일에 배포된 API 주소와 설계한 AWS 환경 그림으로 첨부)

- 진행 중 발생하는 문의사항은 이 레포지토리의 Issue로 등록해주세요.


## 과제 제출 필수 사항
  - \[O\]과제의 소스코드는 반드시 본인의 GitHub 레포지토리에 Public으로 설정하여 업로드 해주세요.
  - \[O\]레파지토리의 이름은 wanted-pre-onboarding-backend로 지정해야 합니다.
  - \[ \]README.md 파일에는 다음과 같은 사항들이 포함되어야 합니다:
    - \[O\]지원자의 성명
    - \[ \]애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
    - \[O\]데이터베이스 테이블 구조
    - \[ \]구현한 API의 동작을 촬영한 데모 영상 링크
    - \[ \]구현 방법 및 이유에 대한 간략한 설명
    - \[O\]API 명세(request/response 포함)
    - \[ \]과제 제출은 참가 신청 시 수행한 과제의 레포지토리 주소를 제출하면 됩니다.
   

---

## 지원자의 성명: 김강연

## 어플리케이션의 실행 방법

### AWS에서 동작하는 서버로 접속
클라우드 환경 AWS 서버를 열어, 해당 서버에 어플리케이션 환경을 구축하였습니다.
배포한 서버의 주소는 다음과 같습니다(포트번호 포함).<br/>
http://ec2-43-201-120-175.ap-northeast-2.compute.amazonaws.com:8080
<br/>해당 도메인과 POSTMAN 프로그램을 이용해 어플리케이션을 실행할 수 있습니다.

## AWS 환경 구조도
![AWS 설계도](https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/14a905a5-265c-43e4-b015-43d727ab32bd)

## 데이터베이스 테이블 구조
![사전과제 테이블 다이어그램](https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/39bea3d3-6b0d-4a61-90e9-21d3f9cce5e7)


## 구현한 API의 동작을 촬영산 데모 영상 링크
맥북 녹화와 POSTMAN으로 영상 찍기

## 구현 방법 및 이유에 대한 간략한 설명

### 데이터 송수신 형태

전체적인 구현 방식은 다음과 같다.
먼저, RESTful API를 목적으로 API를 설계하였다. 이후, Request 시 전달해야 할 데이터가 있는 경우는 `RequestDto` 클래스를 생성하여 해당 객체를 이용해 Request Body의 데이터를 매핑하여 저장하였다.
동일한 방식으로, `ResponseDto` 클래스를 생성 후, 클라이언트가 요청한 데이터를 해당 ResponseDto 객체에 매핑하여 Response Body에 담아 전달한다.
현재 대표적인 데이터 송수신 형태인 JSON을 이용하기 위해 이와 같은 Dto 클래스를 이용하여 구현하였다.


### CRUD 방식
`JPA`와 `pring Data JPA`를 이용하여 CRUD를 구현하였다. 스프링(스프링 부트)을 사용할 경우 JPA는 굳이 안쓸 필요가 없다 생각하였고, <u>적은 코드로 CRUD를 구현할 수 있기 때문에 이용</u>하였다.


### 이메일과 비밀번호의 유효성 검증
RequestDto 종류의 클래스들을 구현 시, <strong>javax.validation 라이브러리</strong>의 `@NotNull`, `@Email` 등의 어노테이션을 이용하여, 
예외 발생 시 `ExceptionHandler` 또는 필터의 `handler`에서 예외 처리 후 에러 Response를 응답하도록 구현하였다. 


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


## API 명세(request 및 responses는 표 아래에 작성)
|URI|HTTP METHOD|기능|
|------|---|---|
|/members|POST|사용자 회원가입|
|/members/login|POST|사용자 로그인|
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

### /posts GET 게시글 목록 조회(10개 단위)
쿼리 스트링의 page 값을 입력하지 않아도 된다. 이때, 최근 작성된 10개의 게시글이 response로 전달된다.
page의 값은 0부터 시작한다.

Response
```json
{
  "code": "String", // ex) "M005"
  "message": "String", // ex) "모든 게시글을 성공적으로 조회하였습니다."
  "data": [
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

### /posts/:postId GET 특정 게시글 조회
Response
```json
{
  "code": "String", // ex) "M004"
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
    "code": "String", // ex) "M006"
    "message": "String", // ex) "입력한 postId의 게시글을 성공적으로 수정하였습니다."
    "data": {
        "postId": "number" // 수정한 게시글의 고유 id
    }
}
```

### /posts/:postId DELETE 특정 게시글 삭제
Response
```json
{
    "code": "String", // ex) "M007",
    "message": "String", // ex) "입력한 postId의 게시글을 성공적으로 삭제하였습니다",
    "data": null
}
```








