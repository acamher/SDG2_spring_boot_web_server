# SDG2_spring_boot_web_server
Spring boot web server for display Arduino data sensor saved on mysql.
The web server can be launched by command: `mvn run:spring-boot`
Also, the web server can be packaged by the command: `mvn package wagon:upload-single -X`. This way also send the `.java` to raspberry pi by ftp and needs extra configuration on directory: `%HOME%/.m2/settings.xml` where specify user and password of the raspbeery pi.

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
