<?xml version="1.0" encoding="utf-8" ?>

<configuration>
    <!--日志路径-->
    <property name="logPath" value="${basePath}"/>
    <!--每种日志的文件最大数-->
    <property name="maxFile" value="30"/>

    <!--控制台输出-->
    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--debug级别日志-->
    <appender name="bg_case_debug" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--文件名-->
        <File>${r"${logPath}"}/debug.log</File>
        <!--滚动记录文件，先将日志记录到指定目录下，当符合某个条件时，将日志记录到其他文件中-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${r"${logPath}"}/debug_%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--保存的文件数最多为5个，超过将会被删除-->
            <MaxHistory>${r"${maxFile}"}</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!--级别过滤-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>DEBUG</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
    <!--info级别日志-->
    <appender name="bg_case_info" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--文件名-->
        <File>${r"${logPath}"}/info.log</File>
        <!--滚动记录文件，先将日志记录到指定目录下，当符合某个条件时，将日志记录到其他文件中-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${r"${logPath}"}/info_%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--保存的文件数最多为5个，超过将会被删除-->
            <MaxHistory>${r"${maxFile}"}</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!--级别过滤-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>
    <!--error级别日志-->
    <appender name="bg_case_error" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--文件名-->
        <File>${r"${logPath}"}/error.log</File>
        <!--滚动记录文件，先将日志记录到指定目录下，当符合某个条件时，将日志记录到其他文件中-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${r"${logPath}"}/error_%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--保存的文件数最多为5个，超过将会被删除-->
            <MaxHistory>${r"${maxFile}"}</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!--级别过滤-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--根据包和类来进行日志存放-->
    <!--<logger name ="com.dubbo.rolling.web.controller" level="DEBUG" additivity="false">
        <appender-ref ref="rolling-info"/>
    </logger>-->

    <root level="INFO">
        <appender-ref ref="stdout"/>
        <appender-ref ref="bg_case_debug"/>
        <appender-ref ref="bg_case_info"/>
        <appender-ref ref="bg_case_error"/>
    </root>

    <!--dao包下的日志-->
    <logger name="web.mapper" level="DEBUG">
        <appender-ref ref="stdout"/>
    </logger>

</configuration>