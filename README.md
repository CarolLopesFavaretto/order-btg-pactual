# order-btg-pactual

## Desafio
Processar pedidos e gerar relatório.

## Requisitos

Elabore e entregue um plano de trabalho.

Crie suas atividades em tasks e estime horas para cada uma delas.

Crie uma aplicação, na tecnologia de sua preferência, modele e implemente uma base de dados;

Crie um micro serviço que consuma dados de uma fila RabbitMQ e grave os dados para conseguir listar as informações:

- Valor total do pedido
- Quantidade de Pedidos por Cliente
- Lista de pedidos realizados por cliente

Exemplo da mensagem que deve ser consumida:

```json
{
  "codigoPedido": 1001,
  "codigoCliente":1,
  "itens": [
    {
      "produto": "lápis",
      "quantidade": 100,
      "preco": 1.10
    },
    {
      "produto": "caderno",
      "quantidade": 10,
      "preco": 1.00
    }
  ]
}
```

Crie uma API REST, em que permita o consultar as seguintes informações:

- Valor total do pedido
- Quantidade de Pedidos por Cliente
- Lista de pedidos realizados por cliente

## Como interagir com o banco de dados?
- Utilizamos o [MongoDB Compass](https://www.mongodb.com/products/tools/compass)

## Como interagir com a API?
- Utilizamos o [Postman](https://www.postman.com/)

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Data MongoDB
* RabbitMQ
* Docker