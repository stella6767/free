whoami

echo " "
echo "========================"
echo "Path move"
echo "========================"

cd "$HOME/cicd/me"

echo $PWD

#openssl req -x509 -nodes -newkey rsa:2048 -keyout key.pem -out cert.pem -sha256 -days 365 \
#    -subj "/C=GB/ST=London/L=London/O=Alros/OU=IT Department/CN=localhost"

echo " "
echo "========================"
echo "Docker compose down"
echo "========================"

# 이미 실행 중인 Docker Compose 중지 및 컨테이너 삭제
docker compose -f "$HOME/cicd/me/docker-compose-dev.yml" down

# docker-compose -f /Users/stella6767/IdeaProjects/free/docker-compose-dev.yml down

#echo " "
#echo "========================"
#echo "Docker compose build"
#echo "========================"
#
#docker-compose -f /Users/stella6767/IdeaProjects/free/docker-compose-dev.yml build
docker compose -f "$HOME/cicd/me/docker-compose-dev.yml" build

echo " "
echo "========================"
echo "Docker Compose Up"
echo "========================"

#docker-compose -f /Users/stella6767/IdeaProjects/free/docker-compose-dev.yml up -d
docker compose -f "$HOME/cicd/me/docker-compose-dev.yml" up -d
