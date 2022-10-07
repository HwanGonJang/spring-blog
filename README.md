# 블로그 만들기(유어슈 백엔드 팀 과제)
## 개요
스프링 부트, 코틀린, MySQL을 이용하여 간단한 블로그 API를 작성합니다.
## 사용한 스택
### 백엔드
* 스프링 부트 2.7.4
* 언어: 코틀린
* JAVA 11, Gradle
* MySQL
* Docker
* Spring JPA
* Spring Security
* Swagger
* Kotlin Logging
* Validation
* 그 외 스타터 라이브러리
### 테스트 코드
* Kotest
* Mockk
* JUnit5
* H2 Database(in-memory DB)
## DB 설정하기
프로젝트 경로에서 다음 명령어를 입력하여 DB 컨테이너를 실행합니다.(도커 설치 필수)  
`docker-compose up -d`  
테이블 DDL은 table.sql에서 user, password, db name 등은 docker-compose.yml 에서 수정할 수 있습니다.
![image](https://user-images.githubusercontent.com/33739448/194445880-38499283-b914-44a7-b953-c5cae390d5ba.png)

위와 같이 도커 컨테이너가 정상적으로 실행되면 Datasource를 지정해 DB를 연결합니다.(IntelliJ 기준)
![image](https://user-images.githubusercontent.com/33739448/194445803-69da21e6-71d2-4a99-879e-b2e946fa089c.png)

JPA 사용을 위해 application.yml에서 datasource 설정을 해줍니다.
```
datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: "jdbc:mysql://localhost:3306/blog"
    username: myggona
    password: ggona12
```
## 구현 내용
### Validation
#### Email
* **Email 형식이 아닐 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446092-66b93fa6-8b4f-4662-8115-d5f1def5f5ca.png)
#### Password
* **6 ~ 13자 사이의 비밀번호가 아닐 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446211-83f61ef9-40e3-4af4-a5ce-e437aa272fd4.png)
#### Blank
* **"", " ", null 값이 들어왔을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446344-0ad3b89e-dfe0-46ed-8f01-d2cfce0bac12.png)
  ![image](https://user-images.githubusercontent.com/33739448/194446385-a4b7c380-1bd0-4c3c-a5ae-e4916361bbc0.png)
#### Validation Error Response
![image](https://user-images.githubusercontent.com/33739448/194446121-9912a9c7-b980-402b-8a87-a4236a0bbbf7.png)
### User
#### User 회원가입
* **정상 동작**  
  ![image](https://user-images.githubusercontent.com/33739448/194446457-d844d757-1bb9-4eec-b046-5a0e5e6ce4d1.png)
  ![image](https://user-images.githubusercontent.com/33739448/194446489-d028d3c2-9b59-401f-a1c1-f12953c9d654.png)
  ![image](https://user-images.githubusercontent.com/33739448/194446616-74f737da-1d94-4878-8849-76a36ffaf6ea.png)
* **이미 존재하는 회원일 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446523-c84e4ad4-5f42-4cec-8a41-4111190e635c.png)
#### User 회원탈퇴
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194446816-9a4c363e-b580-46fe-88a2-28290e7deead.png)
* **고객이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446669-23ec661a-98fe-4621-b879-75380b3c627b.png)
* **비밀번호 불일치**
  ![image](https://user-images.githubusercontent.com/33739448/194446700-c868771a-4008-4f0b-925b-d53a7a4d0326.png)
* **고객을 삭제하면 ON DELETE CASCADE 옵션으로 해당 고객의 게시글 및 댓글이 함께 삭제됩니다.**
### Article
#### 게시글 등록
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194446907-d80f9e08-821a-43b0-b184-1b04f9f8190c.png)
  ![image](https://user-images.githubusercontent.com/33739448/194446929-d8ac11cc-9087-4879-8d50-64ad53c10770.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447094-47a7bd37-9fee-4fad-9bac-25429678070e.png)
* **고객이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194446967-5e03d1da-d16a-4d98-8730-df50aed60db5.png)
* **비밀번호 불일치**
  ![image](https://user-images.githubusercontent.com/33739448/194446998-1fa82bd2-2142-42c1-98a0-eea397eafca5.png)
#### 게시글 수정
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194447184-86eac214-dea8-49b1-9a2b-67a4bdd9e0fe.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447155-54712d34-eb70-4992-a03c-662ed241ec19.png)
* **고객이 존재하지 않을 때 or 해당하는 게시글이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194447245-12bd8a83-2487-41be-ad37-fec187e4cda3.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447209-e7189242-e0ba-4221-b85b-d102ee6a8c05.png)
* **비밀번호 불일치 or 수정할 권한이 없을 때(작성자가 아닐 때)**
  ![image](https://user-images.githubusercontent.com/33739448/194447326-e6512120-d959-41d6-bbca-89492c393b96.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447383-93083d7b-9343-4bf3-919e-d12ff4bd0489.png)
#### 게시글 삭제
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194447542-9ace7d9b-97b6-4f85-be4a-cba8e72186ac.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447553-4e787613-e499-4559-bd31-797571c24ed5.png)
* **고객이 존재하지 않을 때 or 해당하는 게시글이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194447245-12bd8a83-2487-41be-ad37-fec187e4cda3.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447209-e7189242-e0ba-4221-b85b-d102ee6a8c05.png)
* **비밀번호 불일치 or 삭제할 권한이 없을 때(작성자가 아닐 때)**
  ![image](https://user-images.githubusercontent.com/33739448/194447326-e6512120-d959-41d6-bbca-89492c393b96.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447485-b58bae6b-1ff2-40eb-98b8-26397e861037.png)
* **게시글을 삭제하면 ON DELETE CASCADE 옵션으로 해당 게시글의 댓글이 함께 삭제됩니다.**
### Comment
#### 댓글 등록
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194447719-693594eb-5665-4c8b-96ac-11f51eea36cb.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447737-c859ccdb-0472-4612-bcdf-87000233628e.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447888-ce76e493-7a3f-41e9-8488-33fd6e5b62e4.png)
* **고객이 존재하지 않을 때 or 해당하는 게시글이 없을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194447245-12bd8a83-2487-41be-ad37-fec187e4cda3.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447786-adc9dd41-e781-43c4-b02a-64f601b342f0.png)
* **비밀번호 불일치**
  ![image](https://user-images.githubusercontent.com/33739448/194447326-e6512120-d959-41d6-bbca-89492c393b96.png)
#### 댓글 수정
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194448054-e3b26bc5-7278-48b7-9abe-bfaa9fe4beb2.png)
  ![image](https://user-images.githubusercontent.com/33739448/194448077-0b0d5300-d26f-419e-a1df-fbbbe4e5e802.png)
* **고객이 존재하지 않을 때 or 해당하는 댓글이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194447245-12bd8a83-2487-41be-ad37-fec187e4cda3.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447981-d1e87549-d704-4f89-9554-88a0cfe31664.png)
* **비밀번호 불일치 or 수정할 권한이 없을 때(작성자가 아닐 때)**
  ![image](https://user-images.githubusercontent.com/33739448/194447326-e6512120-d959-41d6-bbca-89492c393b96.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447485-b58bae6b-1ff2-40eb-98b8-26397e861037.png)
#### 댓글 삭제
* **정상 동작**
  ![image](https://user-images.githubusercontent.com/33739448/194448302-43f932bd-e9e8-4bcd-8320-c03162cf1f55.png)
* **고객이 존재하지 않을 때 or 해당하는 댓글이 존재하지 않을 때**
  ![image](https://user-images.githubusercontent.com/33739448/194447245-12bd8a83-2487-41be-ad37-fec187e4cda3.png)
  ![image](https://user-images.githubusercontent.com/33739448/194448241-766831cc-05cd-4d72-9522-07ede8d9790c.png)
* **비밀번호 불일치 or 삭제할 권한이 없을 때(작성자가 아닐 때)**
  ![image](https://user-images.githubusercontent.com/33739448/194447326-e6512120-d959-41d6-bbca-89492c393b96.png)
  ![image](https://user-images.githubusercontent.com/33739448/194447485-b58bae6b-1ff2-40eb-98b8-26397e861037.png)
  
### 테스트 코드
비즈니스 로직이 없는 메소드 혹은 기본으로 제공되는 메소드에 대해서는 테스트 코드를 작성하지 않았습니다.
#### 테스트 방법
* Gradel Test
* Kotest
* Mockk
* **BDD 방식**
  * BehaviorSpec()
* Integration Test
  * SpringBootTest
* Unit Test
* JPA Test
  * DataJpaTest
#### 테스트 결과
![image](https://user-images.githubusercontent.com/33739448/194448933-e3987288-432d-4331-89fc-d9529bd4e3df.png)
![image](https://user-images.githubusercontent.com/33739448/194448980-cc45a2f6-7d4f-4de4-8726-f325ae5b1eb9.png)


### 감사합니다!
