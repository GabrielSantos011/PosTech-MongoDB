# Comandos mongodb

- Mostrar databases: ```show databases```

- Usar/Criar database: ```use nomeDatabase``` (só vai aparecer no show databases após inserir uma coleção)

- Criar uma coleção: ```db.createCollection("NomeColeção")``` (comando db lembra o this no java representando o banco atual)

- Mostrar coleções: ```show collections```

- Deletar uma coleção: ```db.nomeCollection.drop()```

- Deletar um banco (deve estar conectado no mesmo): ```db.dropDatabase()```

- Inserir documento: ```db.nomeCollection.insertOne({"chave": "campo","chave": campo})```
	obs: mongodb cria automaticamente um id (como se fosse uma chave primaria)

- Inserir mais de um documento: ```db.nomeCollection.insertMany([{"chave": "campo","chave": campo}, {"chave": "campo","chave": campo}])```

- Quantos documentos tem a coleção: ```db.nomeCollection.countDocuments()```

- Listar todos os documentos da coleção: ```db.nomeCollection.find()```

- listagem mais detalhada:
```
db.nomeCollection.find({condição (pode ser vazia),{"_id": 0 (0 n mostra id e 1 mostra só o id)}})
```
