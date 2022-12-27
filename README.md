# avata

## maven 接入

```xml

<repositories>
    <repository>
        <id>pinheng-release</id>
        <name>pinheng-release</name>
        <url>http://47.99.219.2:8081/repository/maven-public/</url>
    </repository>
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupId>ai.avata.spring</groupId>
        <artifactId>avata-starter</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

完事儿就可以在应用中可见 `AvataService`。
