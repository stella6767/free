




# .gitignore
```angular2html

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/
secret.yml
secrets.tar.gz
secrets_base64.txt
```

# secret.yml

```angular2html
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            clientId: {}
            clientSecret: {} 
            redirect-uri: {} 
            scope:
              - user:email
              - read:user
cloud:
  aws:
    credentials:
      access-key: {}
      secret-key: {}
    region:
      static: {}
    s3:
      bucket: {}
      url: {}

github:
  accessToken: {}

---

spring:
  h2:
    console:
      enabled: true
      path: /h2-console
  config:
    activate:
      on-profile:
        - local
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:db;MODE=MYSQL
    username: sa
    password:
    hikari:
      maximum-pool-size: 5

---

spring:
  config:
    activate:
      on-profile:
        - prod
  datasource:
    driver-class-name: org.postgresql.Driver
    url: {}
    username: {}
    password: {}
    hikari:
      maximum-pool-size: 5

```

[참고](https://shanepark.tistory.com/465)

```angular2html

tar czvf secrets.tar.gz secret.yml
base64 -i secrets.tar.gz -o secrets_base64.txt

```


![secret_val.png](/img/secret_val.png)
        

# Github Action


```angular2html

name: Java CI with Gradle DEV

on:
  workflow_dispatch:
    inputs:
      BRANCH:
        description: 'Branch to use'
        required: true
        default: 'dev'
        type: choice
        options:
          - dev
          - prod

permissions:
  contents: read

jobs:

  build:
    runs-on: ubuntu-latest    
    steps:
        
      - uses: actions/checkout@v3
        with:
          #ref: ${{ github.event.inputs.target-branch }}  # 왜 안 먹히냐...
          ref: ${{ inputs.branch }}

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:         
          java-version: '21'
          distribution: 'temurin'

      -  name: Retrieve secrets
         env:
           MY_SECRETS_ARCHIVE: ${{ secrets.MY_SECRETS_ARCHIVE }}
         run: |
           echo "$MY_SECRETS_ARCHIVE" | base64 --decode > secrets.tar.gz
           tar xzvf secrets.tar.gz -C module-api/src/main/resources/     

          
      - name: Build with Gradle
        run: ./gradlew :module-api:build -x test
        shell: bash


      - name: Upload artifact
        uses: actions/upload-artifact@v2
        with:
          name: cicdsample
          retention-days: 1
          path: |
           module-api/build/libs/
           scripts/
           nginx/nginx.conf
           DockerFile
           docker-compose-dev.yml

  deploy:
    needs: build
    runs-on: ubuntu-latest
    steps:
      - name: Download artifact
        uses: actions/download-artifact@v2
        with:
           name: cicdsample
      - name: Setup SSH
        uses: webfactory/ssh-agent@v0.5.4
        with:
          ssh-private-key: ${{ secrets.SSH_PRIVATE_KEY }}
      - name: Add remote server to known hosts
        run: |
          mkdir -p ~/.ssh
          ssh-keyscan ${{ secrets.SERVER_IP }} >> ~/.ssh/known_hosts
      - name: check File List
        run: | 
         pwd
         ls -al    
         
      - name: SCP transfer
        run: |
         scp -r scripts module-api DockerFile docker-compose-dev.yml nginx ${{ secrets.SSH_USER }}@${{ secrets.SERVER_IP }}:~/cicd
         
      - name: Execute remote commands
        run: |
         if [[ "${{ inputs.branch }}" == "dev" ]]; then
             ssh -v ${{ secrets.SSH_USER }}@${{ secrets.SERVER_IP }} "sudo sh ~/cicd/scripts/deploy-docker.sh"            
          elif [[ "${{ inputs.branch }}" == "prod" ]]; then
             ssh -v ${{ secrets.SSH_USER }}@${{ secrets.SERVER_IP }} "sudo sh ~/cicd/scripts/deploy-docker.sh"             
          else
            echo "No specific script found for this"
          fi
     # delete-artifact
      - uses: geekyeggo/delete-artifact@v2
        with:
          name: cicdsample

```


```angular2html
docker compose build
./init-letsencrypt.sh
docker compose up
```
