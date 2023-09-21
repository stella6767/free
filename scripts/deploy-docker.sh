whoami

echo " "
echo "========================"
echo "Path move"
echo "========================"

cd /home/ubuntu/cicd/


echo " "
echo "========================"
echo "Docker compose down"
echo "========================"

# 이미 실행 중인 Docker Compose 중지 및 컨테이너 삭제
sudo docker-compose -f docker-compose-dev.yml down

echo " "
echo "========================"
echo "Docker compose build"
echo "========================"

sudo docker-compose -f docker-compose-dev.yml build


echo " "
echo "========================"
echo "Docker Compose Up"
echo "========================"

sudo docker-compose -f docker-compose-dev.yml up -d




