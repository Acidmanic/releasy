
BASE=target/releasy

rm -rf $BASE

mvn clean install && \
mkdir $BASE && \
cp target/releasy-standalone.jar $BASE && \
echo 'DIR=$(dirname $BASH_SOURCE)' > $BASE/run.sh && \
echo 'DIR=$(realpath $DIR)' >> $BASE/run.sh && \
echo 'JAR=$DIR/releasy-standalone.jar' >> $BASE/run.sh && \
echo 'java -jar "$JAR" $@' >> $BASE/run.sh && \
chmod +x $BASE/run.sh 