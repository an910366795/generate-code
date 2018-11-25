server:
  port: 8080

spring:
  datasource:
    name: mydb
    type: com.alibaba.druid.pool.DruidDataSource
    url: ${dbUrl}
    username: ${dbUsername}
    password: ${dbPassword}
    driver-class-name: com.mysql.cj.jdbc.Driver
    minIdle: 5
    maxActive: 20
    initialSize: 5
    timeBetweenEvictionRunsMillis: 3000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 'ZTM' FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    maxWait: 60000
    # open PSCache
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    filters: stat,wall,log4j2