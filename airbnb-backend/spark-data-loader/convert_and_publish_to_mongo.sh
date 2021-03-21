RESOURCE_FOLDER_RELATIVE='./src/main/resources'
RESOURCE_FOLDER='/src/main/resources'
BUILD_NAME='spark-transfer-mongo-1.0-SNAPSHOT-jar-with-dependencies.jar'
python $RESOURCE_FOLDER_RELATIVE/improve_csv.py $RESOURCE_FOLDER_RELATIVE/listings.csv
java -jar ./target/$BUILD_NAME $RESOURCE_FOLDER_RELATIVE/application.properties rooms