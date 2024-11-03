# 인천시 버스 개선 프로젝트
프론트엔드: https://github.com/GoldenMine0502/bus_improvement_front

크롤러: https://github.com/GoldenMine0502/bus_improvement_crawler

백엔드: https://github.com/GoldenMine0502/bus_improvement_backend

기술스택: Kotlin, Retrofit, Selenium, Hibernate, MySQL, Spring, JavaScript, React

버스 개선 프로젝트에 대한 개괄적인 정보는 프론트에서 확인해주세요.

서비스: http://web.goldenmine.kr:3000/ (언제 닫힐진 모름)

# 데이터 크롤러
대학교 프로젝트, 버스 노선 개선 프로젝트 데이터 크롤러

다양한 사용량 정보를 크롤링합니다.

아직 릴리즈는 따로 없어서 고칠 부분은 고쳐서 쓰셔야 됩니다.

# hibernate config 작성 (2022-11-22 업데이트)
```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/bus_improvement?useSSL=false&amp;characterEncoding=UTF-8&amp;allowPublicKeyRetrieval=true</property>
        <property name="connection.username">아이디</property>
        <property name="connection.password">비밀번호</property>

        <property name="log4j.appender.stdout.encoding">UTF-8</property>
        <property name="log4j.appender.logfile.Encoding">UTF-8</property>
        <!-- DB schema will be updated if needed -->
        <!-- <property name="hbm2ddl.auto">update</property> -->

        <property name="hibernate.connection.CharSet">UTF-8</property>
        <property name="hibernate.connection.characterEncoding">UTF-8</property>
        <property name="hibernate.connection.useUnicode">true</property>

        <property name="dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <!--        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>-->

        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_card.database.BusPastInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_traffic.database.TrafficInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusStopStationInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_stop.database.BusThroughInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.kakao_map.database.RoadNameInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.naver_map.database.BusPathInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.database.BusTrafficBusStopInfo"/>
        <mapping class="kr.goldenmine.bus_improvement_crawler.requests.bus_card_selenium.database.BusTrafficNodeInfo"/>
    </session-factory>
</hibernate-configuration>
```
connection.url, connection.username, connection.password 부분 맞게 수정하셔서 콘피그 불러오시면 됩니다.

DB 스키마는 sql 폴더 참고하시면 됩니다.

# crawlinfo.json 관련
```json
{
  "requestBusCardKey": "키 입력1",
  "requestBusStopKey": "키 입력2",
  "requestBusTrafficKey": "키 입력2",
  "requestNaverKeyId": "키 입력3",
  "requestNaverKey": "키 입력4",
  "requestKakaoKey": "KakaoAK 키 입력5",
  "year": 2022,
  "month": 9,
  "totalPage": 39
}
```
requestBusCardKey: https://stcis.go.kr/wps/openapi/devsvc/openApiDevList.do

requestBusStopKey, requestBusTrafficKey: https://www.data.go.kr/data/15058487/openapi.do

네이버 키: https://www.ncloud.com/product/applicationService/maps

카카오 키: https://apis.map.kakao.com/

year: 아직 지원 안함.

month: 크롤링할 월 입력. 1-12

totalPage: (인천시 총 버스노선 갯수 / 10) + 1 권장. 노선이 추가되지 않는 한 39 정도면 괜찮을 것 같음.
