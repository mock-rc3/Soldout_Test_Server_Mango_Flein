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




