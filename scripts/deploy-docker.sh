whoami

echo " "
echo "========================"
echo "Path move"
echo "========================"

cd /home/ubuntu/cicd/


echo " "
echo "========================"
echo "remove exist process"
echo "========================"

# 이미 실행 중인 Docker Compose 중지 및 컨테이너 삭제
docker-compose down

echo " "
echo "========================"
echo "Docker Compose Up"
echo "========================"

sudo docker-compose -f docker-compose-dev.yml up -d