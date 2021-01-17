# file-config
A Java library that converts any file into a quarriable object. Specifically focused on getting and setting strings of a key/value pair.

**Currently Supported Types**:
- [X] YAML
- [ ] JSON
- [ ] TXT


## Get with Maven

Copy and paste the following into your **pom.xml** file.
```XML
<repositories>

    <!-- file-config Repository -->
    <repository>
        <id>file-config</id>
        <url>https://repo.coachluck.io/file-config/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>

</repositories>
```

### Choosing a project

Based off of your needs you can get any of the projects individually.
```XML
<dependencies>

    <!-- YAML Configuration -->
    <dependency>
        <groupId>io.coachluck</groupId>
        <artifactId>yaml-config</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
    <!-- JSON Configuration -->
    <dependency>
        <groupId>io.coachluck</groupId>
        <artifactId>json-config</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
</dependencies>
```

Or you can build your own extension of the main core file configuration system.
```XML
<dependencies>

    <!-- FileConfiguration Core -->
    <dependency>
        <groupId>io.coachluck</groupId>
        <artifactId>file-core</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
</dependencies>
```
