whoami

echo " "
echo "========================"
echo "Path move"
echo "========================"

cd /home/ubuntu/cicd/

pwd

echo " "
echo "========================"
echo "remove exist process"
echo "========================"


PORT=8081
PID=$(lsof -t -i:$PORT)

if [ -n "$PID" ]; then
    # 프로세스가 실행 중인 경우 종료합니다.
    echo "포트 $PORT를 사용하는 프로세스를 종료합니다 (PID: $PID)..."
    kill -9 "$PID"
else
    echo "포트 $PORT를 사용하는 프로세스가 실행 중이지 않습니다."
fi


echo " "
echo "========================"
echo "Jar execute"
echo "========================"

sudo nohup /usr/bin/java -Djasypt.encryptor.password=1234 -jar ~/cicd/*.jar --spring.profiles.active=prod > ~/cicd/nohup.log 2>&1 &
