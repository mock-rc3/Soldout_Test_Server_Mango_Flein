# Soldout_Test_Server_Mango_Flein
라이징테스트_서버_솔드아웃_a_망고_플라인


<img width="200" alt="soldout" src="https://user-images.githubusercontent.com/95139402/150359990-03af50ef-52ac-46f6-bb4b-37f0575530d0.png">

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
- 플라인
[FULINE](https://github.com/mock-rc3/Soldout_Test_Server_Mango_Flein/files/7906393/README_FULINE.md)
- 망고
[MANGO](https://github.com/mock-rc3/Soldout_Test_Server_Mango_Flein/files/7906402/README_MANGO.md)  



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




