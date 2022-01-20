# Soldout_Test_Server_Mango_Flein
라이징테스트_서버_솔드아웃_a_망고_플라인


<img width="500" alt="s" src="https://user-images.githubusercontent.com/95139402/150372706-2a8224a8-49fc-4d52-bbfa-bd6da88684fe.png">


## 프로젝트 소개
- 한정판 중고거래 마켓 무신사 솔드아웃 어플을 클론한 프로젝트 입니다
- 서버 구축, DB 설계, Restful API 명세서 작성/개발을 맡았으며 명세서를 프론트엔드 개발자에게 제공했습니다.

## 기간
- 22.01.08~ 22.01.21

## 기술 스택
- Backend : **Spring Boot 2.4, Java 11**

- Infra : **AWS(EC2, RDS)**

- RDBMS : **MySQL 8**

- Tool : **IntelliJ IDEA, DataGrip,Erdcloud, Google 스프레드시트,Mysqlworkbench**

- **Restful API**

## ERD

<img width="1375" alt="soldout erd" src="https://user-images.githubusercontent.com/95139402/150359359-093cec43-b208-41b2-a395-1d68d16a78b1.png">

## 개발 일지
<details>
<summary>플라인</summary>
<div markdown="1">       

## 2021-01-08

#### 작업내용

1. ec2 서버구축
2. RDS DB 서버구축
3. SSL 인증 및 연결

#### 이슈사항

1. 테이블명이 잘못되게 구축되어서 테이블 수정하여 RDS 재구축 

---

### 2021-01-09

#### 작업내용

1. 1차 ERD 설계 

#### 이슈사항

1.  ERD툴에서 auto increament 기능을 지원하지 않아 테이블 생성 수기로 넣어서 생성
2. 기존 ERD 툴말고 새로운 ERD 툴사용을 위해서 ERD툴에 관련하여 공부

---

### 2021-01-10

#### 작업내용

1. 1차 API 명세서 작성

---

### 2021-01-11

#### 작업내용

1. ERD , API명세서 수정 
2. product ,  image, magazine  데이터 크롤링 코드 작성 

#### 1차 피드백

​	API 명세서

- url 주소 통일하기(ex) 카멜케이스등) 

  ERD 설계

- 상태 컬럼 char에서 tinyint 로 변경

- 분류할수 컬럼을 숫자 말고 문자형태로 직접적으로 적어서 변경 

- 테이블 이름 변경 (컬럼하고 스타일 통일)

 기타

- index 추가를 해주어서 조회속도 빠르게 하기
- github 파일 올리고 서버 구성하는 내용 확인 

#### 이슈사항

1. 기존에 솔드아웃 사이트가 어플이라서 내용 크롤링이 안되어서 비슷한 한정판 중고거래사이트인 크림 사이트를 내용을 가져워서 크롤링 작업

---

### 2021-01-12

#### 작업내용

1.  product, image, magazine ,purchase 테이블 데이터 삽입
2.  테이블명 대문자로 변경 
3. API 개발
   - 신규 발매 상품 조회
   - 상품 랭킹 조회
   - 알림 등록
   - 발매 예정 조회

---

### 2021-01-13

#### 작업내용

1. order 테이블 데이터 삽입 

  2. 상품 랭킹 조회 api 수정

---

### 2021-01-14

#### 작업내용

1. order 테이블 주문내역 컬럼추가 , 데이터 삽입
2. API 개발
   - 검색 페이지 조회
   - 핫이슈 검색 페이지 조회

---

### 2021-01-15

#### 작업내용

 1. API 개발

    - 연관 추천 상품 조회
- 상세페이지 상품 이미지 조회
    - 상품 상단 정보 조회
- 최근 거래 조회
    - 입찰 현황 조회
- 상품정보, 구매처 조회

---

### 2021-01-16

#### 작업내용

1. API 개발	

   사이즈별 가격 조회 

​      구매내역,판매내역 갯수 조회

---

### 2021-01-17

#### 작업내용

 1. API 개발 및 수정 

    - 구매,판매 입찰 내역 상세 조회
    - 구매,판매 완료 내역 상세 조회
    - 입찰 구매,판매
    - 입찰 내역 삭제
    - 날짜별 발매 상품 조회
    - 메인페이지 productid 값 추가
    

---

### 2021-01-18

#### 작업내용

 1. API  개발 및 수정 

    - 메인페이지 배너, 특정 검색어 제품 조회
    - 새로운 즉시 판매가 상품 조회
    - 사이즈별 거래가 조회 수정 

#### 2차 피드백

1.  에러 처리 추가 하기
2.  외부 api 기능 추가하기 
3.  branch 이름 변경

---

### 2021-01-19

#### 작업내용

1. userid 값 들어가는 api들 jwt 값 일치 여부 확인 예외처리 추가 
2. 각 api 주석 추가 
3. md파일 수정 

---

## 2021-01-20

#### 작업내용

	1. validation 처리



## 2021-01-21

#### 작업내용

1. 카카오페이 테스트모드 추가
</div>
</details>
<details>
<summary>망고</summary>
<div markdown="1">       

## 2021-01-08

#### 작업내용
1. RDS 데이터베이스 구축
2. EC2 인스턴스 구축
3. SSL 인증
---
## 2021-01-09

#### 작업내용
1. ERD 설계 1차
---
## 2021-01-10

#### 작업내용
1. ERD 설계 2차
2. API 명세서 작성 1차
3. 회원가입 API 추가
4. 로그인 API 추가
5. 회원조회 API 추가

#### 이슈사항
1. JWT_SECRET_KEY 다른 user row 수정
---
## 2021-01-11

#### 작업내용

1. API 명세서 수정
2. ERD 수정
3. 회원탈퇴 API 추가
4. 닉네임 변경 API 추가
5. 이메일 변경 API 추가
6. 핸드폰번호 변경 API 추가
7. 아이디 찾기 API 추가
8. 1차 피드백
<details>
<summary>1차 피드백 내용</summary>
<div markdown="1">       
- api url 주소 형식 통일(ex) 카멜케이스등) <br /> 
- type 컬럼에 숫자 대신 명시적으로 문자열 자체로 작성 <br /> 
- 이분법으로 구분되는 경우 (status 컬럼) char에서 tinyint 로 변경 <br /> 
- erd 테이블과 컬럼명 형식 통일 <br /> 
- index : 조회 속도 향상 <br /> 
- 유니크키 : 유일성만 가짐 <br /> 
- auto increament dbms 마다 다름<br /> 
- githup : branch, pr ...
</div>
</details>

#### 이슈사항
1. erd 수정에 따라 rds 수정을 용이하게 하기 위해 임시로 fk 설정없이 테스트
---
## 2021-01-12

#### 작업내용
1. 서버 반영

#### 이슈사항
1. ec2 내에서 build를 진행할 경우 메모리 문제로 멈추기 때문에 로컬에서 build 후에 ec2에서는 실행만 하는 방식으로 진행
2. 프론트엔드 측에서 회원가입시 비밀번호 확인란에 대한 의문 - 회원가입에서 확인용 비밀번호까지 저장되는 구조는 이상한 것 같아 별도의 확인 api가 필요하다고 생각했으나 프론트엔드 측에서 해결 가능한 이슈였음
---
## 2021-01-13

#### 작업내용
1. 배송지 추가 API
2. 배송지 삭제 API
3. 배송지 조회 API
4. 찜 추가 API 
5. 찜 삭제 API
6. 찜 조회 API
7. 서버 반영 

#### 이슈사항
1. 회원용 api인 경우 별도의 입력없이 jwt에서 받아온 user_id 사용하는 것이 더 효율적이라고 생각 -> 차후 피드백 결과 Path Variable로 받아와 검증하는 방식이 더 알맞다고 하여 수정
---
## 2021-01-14

#### 작업내용
1. 매거진 전체 조회 API
2. 매거진 타입별 조회 API
3. 매거진 상세 조회 API
4. 서버 반영 

#### 이슈사항
1. 잘못된 ip 주소 전달로 인한 프론트엔드 서버 연결 오류
2. 매거진 타입별 조회에서 Path Variable과 Query String 방식 중 고민 - 필터링에 가까운 것이라 생각해 Query String으로 결정 
3. 입찰 내역, 거래 내역으로 테이블을 나누었던 것이 불필요하다 판단되어 orderHistory라는 하나의 테이블로 합친 후 거래 완료 여부 확인을 위한 컬럼만 추가하는 방식으로 수정
---
## 2021-01-15~16

#### 작업내용
1. 타입별 상품 판매/구매 입찰 주문 조회 API 
2. 특정 사이즈별 타입별 상품 판매/구매 입찰 주문 조회 API
3. 즉시 구매가/판매가 조회 API
4. 특정 사이즈별 즉시 구매가/판매가 조회 API
5. 즉시 구매 API
6. 즉시 판매 API
7. 서버 반영

#### 이슈사항
1. 최댓값, 최솟값에 대한 쿼리를 위해 서브쿼리, group by, limit 고민
2. 로직에 대한 고민
---
## 2021-01-17~18

#### 작업내용
1. point 조회 API
2. 포인트 내역 조회 API
3. 타입 별 포인트 내역 조회 찜 조회 API
4. 사이즈 조회 API 추가
5. 구매/판매 입찰 조회 API 수정
6. 서버 반영
7. 2차 피드백
<details>
<summary>2차 피드백 내용</summary>
<div markdown="1">       
- 좀 더 세세한 validation 처리 : 당장의 클라이언트 요구가 없어도 잘 처리해두어야함 <br /> 
- 외부 api 추가 <br /> 
- github : branch, commit, pr 이름만 보고도 어떤 변화있는지 알 수 있도록 작성, 코드 리뷰 <br /> 
- readme 단순히 작업 내용만이 아닌 이슈사항들도  <br /> 
- jwt토큰에서 가져오는 값 추가적으로 비교 통해서 검증하는 것이 더욱 좋음 단, 유저에 따라 달라지는 값이 아닌 경우는 불필요<br /> 
- tinyint Bit 차이
</div>
</details>

---

## 2021-01-19

#### 작업내용
1. 핸드폰번호 인증 메세지 전송 API
2. 인증번호 확인 API
3. 서버 반영

#### 이슈사항
1. 핸드폰번호 전달 방식 주의
---
## 2021-01-20

#### 작업내용
1. 카카오 로그인 토큰 받아오기 API
2. 카카오 로그인 유저 정보 조회 API
3. validation 추가
4. 주석 추가
5. 서버 반영
---

## 2021-01-21

#### 작업내용
1. README 정리 및 시연영상 제작

---
</div>
</details>


## Directory Structure 
``` 
  📦src
 ┣ 📂address - 배송지 추가, 조회, 삭제 API 
 ┃ ┣ 📂model 
 ┃ ┣ 📜AddressController.java
 ┃ ┣ 📜AddressDao.java
 ┃ ┣ 📜AddressProvider.java
 ┃ ┗ 📜AddressService.java
 ┣ 📂kakao - 카카오 로그인, 카카오 페이API
 ┃ ┣ 📂model
 ┃ ┣ 📜KakaoController.java
 ┃ ┗ 📜KakaoService.java
 ┣ 📂magazine - 매거진 전체/타입별/상세 조회 API
 ┃ ┣ 📂model
 ┃ ┣ 📜MagazineController.java
 ┃ ┣ 📜MagazineDao.java
 ┃ ┗ 📜MagazineProvider.java
 ┣ 📂message - 핸드폰 번호 인증 메세지 전송, 확인 API
 ┃ ┣ 📂model
 ┃ ┣ 📜MessageController.java
 ┃ ┗ 📜MessageService.java
 ┣ 📂notices - 알림 등록 API
 ┃ ┣ 📂model
 ┃ ┣ 📜NoticeController.java
 ┃ ┣ 📜NoticeDao.java
 ┃ ┣ 📜NoticeProvider.java
 ┃ ┗ 📜NoticeService.java
 ┣ 📂orderHistory - 입찰 주문, 즉시 구매/판매, 최근 거래 및 입찰 내역 관련 API
 ┃ ┣ 📂model
 ┃ ┣ 📜OrderHistoryController.java
 ┃ ┣ 📜OrderHistoryDao.java
 ┃ ┣ 📜OrderHistoryProvider.java
 ┃ ┗ 📜OrderHistoryService.java
 ┣ 📂point - 포인트, 포인트 내역 조회 API
 ┃ ┣ 📂model
 ┃ ┣ 📜PointController.java
 ┃ ┣ 📜PointDao.java
 ┃ ┗ 📜PointProvider.java
 ┣ 📂products - 조건별 상품 검색/조회, 상품 상세(정보, 거래 내역 등) 조회 API 
 ┃ ┣ 📂model
 ┃ ┣ 📜ProductController.java
 ┃ ┣ 📜ProductDao.java
 ┃ ┣ 📜ProductProvider.java
 ┃ ┗ 📜ProductService.java
 ┣ 📂test - 연결 테스트
 ┃ ┗ 📜TestController.java
 ┣ 📂user - 회원가입, 로그인, 회원탈퇴, 회원정보 조회 및 관리 API
 ┃ ┣ 📂model
 ┃ ┣ 📜UserController.java
 ┃ ┣ 📜UserDao.java
 ┃ ┣ 📜UserProvider.java
 ┃ ┗ 📜UserService.java
 ┣ 📂wish - 찜 추가, 삭제, 조회 API
 ┃ ┣ 📂model
 ┃ ┣ 📜WishController.java
 ┃ ┣ 📜WishDao.java
 ┃ ┣ 📜WishProvider.java
 ┃ ┗ 📜WishService.java
 ┣ 📜.DS_Store
 ┗ 📜WebSecurityConfig.java 
 ``` 
 
## 프로젝트 인원

| 역할       | 닉네임 |
| ---------- | ------ |
| 프론트엔드 | 민     |
| 백엔드     | 망고   |
| 백엔드     | 플라인 |


## URL 주소
### ERD 설계

https://www.erdcloud.com/d/4KNf8zAXxQkPkP5QX

### API 명세서 

https://docs.google.com/spreadsheets/d/144STz79GQvjYdrKegNv-M4BHlryrBKqya2KfQg9yFck/edit#gid=381549751




