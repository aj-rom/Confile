# Confile ( CURRENTLY IN ALPHA )
A Java library that converts any .yml file into a quarriable (FileConfiguration) object. Specifically focused on getting and setting strings of a key/value pair.

Doumenation can be found at my website [here](https://confile.coachluck.io).

**Currently Supported Types**:
- [X] YAML
- [ ] TXT

## Motivation
I wanted to make this publically accessible because I have found it very useful to be able to easily convert files into objects within Java. This also is the core for my project TransFile, that is underdevelopment focusing on translating any nested structured file while maintaing key, value structure.

## Code Style & Status
![Codacy Badge](https://img.shields.io/codacy/grade/46a1abe29c9548909ad142206973f31d?style=for-the-badge) ![Issues Badge](https://img.shields.io/github/issues/CoachLuck/Confile?style=for-the-badge) ![License](https://img.shields.io/github/license/CoachLuck/Confile?style=for-the-badge)

Conform to the code style by Google found [here](https://google.github.io/styleguide/javaguide.html)

## Frameworks used
Lombok for simpilized getter/setter methods, and SnakeYaml for reading and writing from Yaml files.

- [Lombok](https://projectlombok.org/)
- [SnakeYaml](https://mvnrepository.com/artifact/org.yaml/snakeyaml)

## Features
Under construction...

## API Usage
Documentation can be found [here](confile.coachlucl.io)

```Java
// Easily create a new YamlConfiguration from any file
File yamlFile = new File("some-yaml.yml")

// Then its as easy as loading it into a YamlConfiguration as this
YamlConfiguration yamlConfig = YamlConfiguration.load(yamlFile)

// Now you can view/change keys and their values
yamlConfig.getString("Hello") // should get the string value at the 'Hello' key of the yaml file if non existang it will be null

// You can also set new keys and values or replace existing ones
yamlConfig.set("Hello", "Hello World!)

// To then save the changes to our file we could call or not if you would like to maintain file integrity.
yamlConfig.save(yamlFile)

```

### Get with Maven

#### Setting up the repository
Copy and paste the following into your **pom.xml** file.
```XML
<repositories>

    <!-- Confile Repository -->
    <repository>
        <id>Confile</id>
        <url>https://repo.coachluck.io/Confile/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>

</repositories>
```

#### Choosing a project

Based off of your needs you can get any of the projects individually.
```XML
<dependencies>

    <!-- YAML Configuration -->
    <dependency>
        <groupId>io.coachluck</groupId>
        <artifactId>yaml-config</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
    <!-- OPTIONAL: FileConfiguration Core -->
    <dependency>
        <groupId>io.coachluck</groupId>
        <artifactId>file-config</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    
</dependencies>
```

## Tests
Under construction

## Contribute
Under construction

## License
This project is licensed under the Apache License v2.0. Check out the [LICENSE](https://github.com/CoachLuck/Confile/blob/main/LICENSE) for more information.

Apache License v2.0 © [A.J. Romaniello](https://github.com/CoachLuck)
