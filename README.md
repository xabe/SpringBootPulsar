## Spring boot with apache pulsar

En este ejemplo vemos como usar spring boot con apache pulsar, lo primero es arranca los containers de:

- apache pulsa
- zookeeper

Solo tenemos que lanzar el siguiente comando para arrancar todos los contenedores:

```shell script
docker-compose up -d
```

Lo siguiente es generar los binarios y arrancar el productor

```shell script
cd producer-pulsar/
mvn clean spring-boot:run
```

Una vez arrancado el productor podemos atacar el api del productor:

> ### Api productor
>
> - Crear un coche
>
>```shell script
>curl --request POST \
>  --url http://localhost:9080/api/producer/car \
>  --header 'content-type: application/json' \
>  --data '{
>	"id" : "mazda",
>	"name": "mazda"
>}'
>```

-------

Lo siguiente es generar los binarios y arrancar el consumidor

```shell script
cd consumer-pulsar/
mvn clean spring-boot:run
```

Una vez arrancado el productor podemos atacar el api del productor:

> ### Api consumidor
>
> - Obtener todos los coches
>
>```shell script
>curl --request GET --url http://localhost:9090/consumer
>```