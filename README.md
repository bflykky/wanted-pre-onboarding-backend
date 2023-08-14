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

## 성명

김강연

## 어플리케이션의 실행 방법

### AWS에서 동작하는 서버로 접속
서버의 도메인과 포트 번호인 'http://~~~:8080'을 루트 경로로 설정 후, POSTMAN 프로그램을 이용해 API를 호출할 수 있습니다.
단계는 다음과 같습니다.


## 데이터베이스 테이블 구조

![사전과제 테이블 다이어그램](https://github.com/bflykky/wanted-pre-onboarding-backend/assets/67828333/39bea3d3-6b0d-4a61-90e9-21d3f9cce5e7)


## 구현한 API의 동작을 촬영산 데모 영상 링크
맥북 녹화와 POSTMAN으로 영상 찍기

## 구현 방법 및 이유에 대한 간략한 설명


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

## 3. API 요구 사항
게시판을 관리하는 RESTful API를 개발해 주세요. 이때, 다음의 기능을 구현해야 합니다. 데이터베이스의 테이블 설계는 지원자분의 판단에 맡겨져 있습니다. 요구사항을 충족시키는 데 필요하다고 생각되는 구조로 자유롭게 설계해 주세요.

- **\[O\]과제 1. 사용자 회원가입 엔드포인트**
    - \[O\]이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
    - \[O\]이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
        - \[O\]이메일 조건: **@** 포함
        - \[O\]비밀번호 조건: 8자 이상
        - \[O\]비밀번호는 반드시 암호화하여 저장해 주세요.
        - \[O\]이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
- **\[O\]과제 2. 사용자 로그인 엔드포인트**
    - \[O\]사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
    - \[O\]과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
- **\[O\]과제 3. 새로운 게시글을 생성하는 엔드포인트**
- **\[O\]과제 4. 게시글 목록을 조회하는 엔드포인트**
    - \[O\]반드시 Pagination 기능을 구현해 주세요.
    => 쿼리스트링으로 페이지 값을 주지 않는 방법은 없을까? \[ \]
- **\[O\]과제 5. 특정 게시글을 조회하는 엔드포인트**
    - \[O\]게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
- **\[O\]과제 6. 특정 게시글을 수정하는 엔드포인트**
    - \[O\]게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    - \[O\]게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
- **\[O\]과제 7. 특정 게시글을 삭제하는 엔드포인트**
    - \[O\]게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    - \[O\]게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.








