mkdir mongo_storage

mkdir mongo_storage/storage_node_01
mkdir mongo_storage/storage_node_02
mkdir mongo_storage/storage_node_03

Start-Process -FilePath "mongod" -ArgumentList "--dbpath", "./mongo_storage/storage_node_01/", "--config", "./mongo/replica_set_01_config.yml"

Start-Process -FilePath "mongod" -ArgumentList "--dbpath", "./mongo_storage/storage_node_02/", "--config", "./mongo/replica_set_02_config.yml"

Start-Process -FilePath "mongod" -ArgumentList "--dbpath", "./mongo_storage/storage_node_03/", "--config", "./mongo/replica_set_03_config.yml"