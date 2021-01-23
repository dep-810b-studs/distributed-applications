RESOURCE_FOLDER='./src/main/resources'
python $RESOURCE_FOLDER/improve_csv.py $RESOURCE_FOLDER/listings.csv
java -jar ./target/spark-transfer-mongo-1.0-SNAPSHOT.jar $RESOURCE_FOLDER/updated.csv rooms