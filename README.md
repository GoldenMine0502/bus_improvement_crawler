# 인천시 버스 노선 개선 프로젝트
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
