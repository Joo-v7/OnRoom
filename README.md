<p align="center">
<img width="1024" height="1024" alt="ChatGPT Image 2025년 12월 13일 오후 01_42_53" src="https://github.com/user-attachments/assets/5ced0815-6f6c-42be-a72e-a6108acc3513" />
</p>

***

# 💻 프로젝트 소개
- 분류: 개인 프로젝트
- 기간: 2025.11.02 ~ 2025.12.12
- 소개: 회의실을 실시간으로 예약/승인/관리하는 웹 기반 시스템
- 주소: http://onroom.site
- 관리자 계정
  - ID: admin1
  - PW: admin1!

***

# ⚙️ 개발 환경
| 구분 | 내용 |
| ------ | ------ |
| 시스템 운영환경 | Amazon Web Service(AWS) |
| OS | Rocky Linux 9.6 |
| WS | Apache 2.4.62 |
| WAS | Apache Tomcat 9.0.100 |
| DB | MariaDB 10.11.14 / Redis 6.2.20 |
| 프레임워크 | 전자정부프레임워크 4.3.0 |
| 프론트엔드 | BootStrap5, jQuery |
| IDE | IntelliJ |
| 버전관리 | Git |

***

# 📝 프로젝트 관리
## [WBS](https://docs.google.com/spreadsheets/d/1riN1BkslcHfxRbM3ZEnJF8fJ94Myii1HK28LHX7HIW4/edit?gid=0#gid=0)

<img width="1374" height="658" alt="image" src="https://github.com/user-attachments/assets/af27cf69-19cc-47fa-a33c-19dc9dd1a396" />

<br><br>

## 화면 설계서
<img width="1149" height="654" alt="image" src="https://github.com/user-attachments/assets/0ab153df-4bd7-4987-89cb-771481d6112a" />

<br><br>

## 시스템 아키텍처
<p align="center">
<img width="462" height="241" alt="image" src="https://github.com/user-attachments/assets/2eaf906e-a798-474a-a09b-19ec0be00293" />
</p>

<br><br>

## [ERD](https://www.erdcloud.com/d/nJEEZahupcYsMb5nz)

<img width="835" height="484" alt="image" src="https://github.com/user-attachments/assets/4cf3e6dc-3986-4606-9105-35d232294033" />

***

# 🎯 주요 기능 및 화면 소개

## 메인 페이지
> 사용자에게 회의실들을 한눈에 보여주고, 직관적인 탐색을 돕는 시작 화면입니다.

<img width="1214" height="722" alt="image" src="https://github.com/user-attachments/assets/9ec97467-075b-4b04-8c53-3c5dca7f7d68" />

<br><br>

## 사용자 인증(로그인/회원가입)
> 사용자(일반/관리자) 인증 기능을 구현했고, 소셜 로그인 연동도 제공합니다.

<img width="613" height="450" alt="image" src="https://github.com/user-attachments/assets/23761a67-312b-4bfa-b67c-5c480f5d69be" />

<br><br>

## 예약 및 조회 페이지
> 예약 현황을 달력으로 한 눈에 볼 수 있고, 예약을 등록할 수 있습니다.

<img width="1214" height="722" alt="image" src="https://github.com/user-attachments/assets/9ec97467-075b-4b04-8c53-3c5dca7f7d68" />

<br><br>

## 게시판 페이지
> 공지사항 및 자유로운 의견 교환을 위한 커뮤니케이션 공간을 제공합니다.

<img width="1199" height="770" alt="image" src="https://github.com/user-attachments/assets/67fdf7ae-4fad-46d3-b135-e769e3b020d2" />

<br><br>

## 마이 페이지
> 회원 정보 한 눈에 볼 수 있고, 수정/비밀번호 변경/회원탈퇴 기능을 제공합니다.  
> 나의 예약 목록을 볼 수 있고, 예약 수정/취소 기능을 제공합니다.  
> 나의 게시판 목록을 볼 수 있고, 게시판 등록/수정/삭제 기능을 제공합니다.  

<img width="1194" height="641" alt="image" src="https://github.com/user-attachments/assets/123e4e49-3b25-4dbe-b61d-eb8d7c2ea33f" />

<br><br>

## 관리자 - 예약 관리
> 예약 현황을 한 눈에 볼 수 있고, 예약 상태 변경을 통해 승인/반려/취소가 가능합니다.

<img width="1511" height="650" alt="image" src="https://github.com/user-attachments/assets/f63cebc2-ed0f-46f3-8e95-59073043165e" />

<br><br>

## 관리자 - 회의실 관리
> 회의실 목록을 볼 수 있고, 회의실 등록/수정/삭제(소프트 삭제)가 가능합니다.

<img width="1510" height="718" alt="image" src="https://github.com/user-attachments/assets/c92b9152-918d-49b7-8a2e-510026e187d4" />

<br><br>

## 관리자 - 회원 관리
> 회원 목록을 볼 수 있고, 회원 정보 수정/상태 변경/권한 부여가 가능합니다.  
> 로그인 제한 관리에서는 로그인이 일정 시간 동안 제한된 회원의 목록과 남은 제한 시간 등을 확인 가능합니다.  

<img width="1507" height="571" alt="image" src="https://github.com/user-attachments/assets/7d7148d2-0074-42f3-9e85-868842476e5b" />

***


# 🛠 기술 스택

### Language & Template
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![JSP](https://img.shields.io/badge/JSP-007396?style=for-the-badge&logo=java&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
![Bootstrap5](https://img.shields.io/badge/Bootstrap5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

### Framework & Library
![전자정부프레임워크](https://img.shields.io/badge/eGovFrame-0054A6?style=for-the-badge)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=for-the-badge)

### Database
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

### Infra & Server
![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)
![Red Hat](https://img.shields.io/badge/Red%20Hat-EE0000?style=for-the-badge&logo=redhat&logoColor=white)
![Apache](https://img.shields.io/badge/Apache-CA2136?style=for-the-badge&logo=apache&logoColor=white)
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)

### Build & Tools
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)

***

<br>

<p align="center">감사합니다</p>
