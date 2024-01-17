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

- Como filtrar consultas no find:
	- O primeiro jogo de chaves é onde colocamos as condições, exemplo: ```{campo: {$gt: 20}}```
  	- O segundo jogo de chaves é o de projeção (indica qual campo aparece ou não na consulta onde 1 é true e 0 é false - o id sempre aparece a menos que deixe explícito que não deve aparecer)
  		- exemplos:
      		```
      		{campo: 1}        -> aparece o campo e o id
      		{campo: 1, _id:0} -> aparece somente o campo
      		{campo: 0}        -> aparece todos menos o campo
      		```
      	
	- Para limitarmos a consulta usamos o método limit, exemplo (apenas os 5 primeiros registros): ```db.nomeCollection.find().limit(5)```
	- Para ordenarmos a consulta utilizamos o método sort (1 para ordem crescente e -1 para decrescente), exemplo: ```db.nomeCollection.find().sort({campo: 1})```
	- Para sabermos a quantidade de documentos da consulta, exemplo: ```db.nomeCollection.find().count()```
	- Para fazer uma consulta de um campo com distinct, exemplo:  ```db.nomeCollection.distinct("campo")```
	- Consultas em strings podemos fazer uma condição perguntando se contém determinada letra ou sequência de letras, exemplo: ```db.nomeCollection.find("campo": /a/)``` -> podemos acrescentar ao final a letra i se quisermos ignorar se é maiúscula ou minúscula ficando: ```db.nomeCollection.find("campo": /a/i)```

	- Operadores:
  	```
  	==     ->    $eq
  	!=     ->    $ne
  	>      ->    $gt
  	>=     ->    $gte
  	<      ->    $lt
  	<=     ->    $lte
  	in()   ->    $in[]
  	!in()  ->    $nin[]
 	 
  	$not[{}]
  	$and[{},{}]
  	$or[{},{}]
  	$nor[{},{}]

	campo existe? -> {"campo": {$exists: true}}
	
  	Para comparar arrays: $all: []

   	Para update
   	$set
   	$inc   -> incremento
  	```

- Atualizar um único documento: ```db.nomeCollection.updateOne({condição},{$set: {campo: novo valor}})``` (pode ser utilizado para atualizar um campo existente ou criar um novo campo dentro da coleção)

- Atualizar mais de um documento: ```db.nomeCollection.updateMany({"Campo":{$in:["xxxxxxxxxx", "yyyyyyy"]}},{$set: {"Campo": "Novo valor"}})```

- Excluir um documento: ```db.db.nomeCollection.deleteOne({condição})```

- Excluir mais de um documento: ```db.db.nomeCollection.deleteMany({condição})```
