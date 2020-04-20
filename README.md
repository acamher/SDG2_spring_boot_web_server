# SDG2_spring_boot_web_server
Spring boot web server for display Arduino data sensor saved on mysql. Needs **maven** and **java**.

## How to launch
The web server can be launched by command: `mvn run:spring-boot` on directory `/gs-spring-boot-master/complete`.

Otherwise, the server can be packaged by the command: `mvn package wagon:upload-single -X` in the same directory as before. This way also send the `.java` to raspberry pi by ftp and needs extra configuration on directory: `%USERPROFILE%/.m2/settings.xml` on windows (or `$HOME$/.m2/settings.xml` on linux) where user and password of the raspbeery pi are specified.
```
<!-------settings.xml----->
<settings>
    <servers>
        <server>
            <id>ftp-repository</id>
            <username>username</username>
            <password>password</password>
			<configuration>
				<StrictHostKeyChecking>no</StrictHostKeyChecking>
			</configuration>
        </server>
    </servers>
</settings>
```
