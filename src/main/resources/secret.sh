tar czvf secrets.tar.gz secret.yml
base64 -i secrets.tar.gz -o secrets_base64.txt

#위에서 생성된 텍스트를 Github의 Secrets 에 MY_SECRETS_ARCHIVE 라는 이름으로 추가해줍니다.