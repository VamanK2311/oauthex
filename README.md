**Steps-**

1. Google Console
2. API & Services
3. Credentials
4. Create OAuth client ID
5. Select as Web Application
6. Give Application Name
7. URI's- [http://localhost:9199](http://localhost:9199/)
8. Redirect URI's- http://localhost:9199/login/oauth2/code/google
9. Click on Create
10. Copy the credentials ID and Secrete
11. Create Spring Boot Project and add Oauth Client Dependency

Please use below content in appliaction.yml file-

spring:
security:
oauth2:
client:
registration:
google :
clientId: your_client_id
clientSecret: your_client_secret