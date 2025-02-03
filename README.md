# API de Clima com Cache usando Spring Boot e Redis

Este projeto é uma API RESTful construída com Spring Boot que integra dados de clima e os armazena no Redis para otimizar a performance e reduzir o número de chamadas a serviços externos de clima. A API permite que os dados de clima sejam consultados por um único endpoint GET, garantindo eficiência no retorno das informações.


# Funcionalidades

- Armazenamento de dados de clima em cache com Redis.
- Recuperação de dados salvos por meio de um único endpoint GET.
- Integração simplificada com serviços de clima externos (API de clima).
- Atualização automática do cache conforme necessário.
- Redução do tempo de resposta através do uso de cache em memória.

# Requisitos 
[//]: # (- É necessario ter o [java]&#40;https://www.java.com/download/ie_manual.jsp&#41; instalado em sua maquina para rodar o programa e tambem o [maven]&#40;https://maven.apache.org/install.html&#41;.)
- Docker e Docker Compose: Se ainda não estiverem instalados, baixe-os nos links abaixo:
  - [docker](https://docs.docker.com/get-started/get-docker/) 
  - [docker compose](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-20-04). 
- Redis: O Redis precisa estar baixado e configurado corretamente. 
  - Instalação e documentação: [redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/) 


# Instalação

- Clone o repositório

```git
  git clone https://github.com/pedro-henrique1/Wather_Api.git
  cd Wather_Api
```

- Configure a key gerado por um serviço externo com [esse](https://www.visualcrossing.com/weather-api):

```
  cd src/main/resources
  
  cp example.application.properties  application.properties
```


-  Iniciando rodar o projeto:

```
 docker-compose up --build
 
```


# Como Fazer Requisições
A API fornece um único endpoint GET para retornar os dados de clima armazenados no Redis. Caso os dados não estejam em cache, eles serão buscados de um serviço de clima externo e armazenados no Redis para futuras consultas.

**GET /rest/{cidade}:** Retorna os dados de clima para a cidade informada. Se os dados da cidade já estiverem no cache, eles são retornados diretamente; caso contrário, a API faz a consulta externa, salva no Redis e retorna os dados.

### Exemplo de chamada de API:

```bash
   curl http://localhost:8080/rest/São-Paulo
```


### Exemplo de Resposta:

```json
{
    "resolvedAddress": "São Paulo, SP, Brasil",
    "address": "sao paulo",
    "timezone": "America/Sao_Paulo",
    "description": "Temperaturas semelhantes continuando com uma chance de chuva quarta-feira, quinta-feira & Sexta-feira.",
    "days": [
        {
            "datetime": "2024-10-11",
            "tempmax": 28.0,
            "tempmin": 18.1,
            "temp": 22.1,
            "feelslikemax": 28.8,
            "feelslikemin": 18.1,
            "feelslike": 22.3,
            "dew": 18.5,
            "humidity": 81.7,
            "precip": 3.3,
            "precipprob": 100.0,
            "precipcover": 25.0,
            "preciptype": [
                "rain"
            ],
            "snow": 0.0,
            "snowdepth": 0.0,
            "windgust": 56.5,
            "windspeed": 26.0,
            "winddir": 2.3,
            "cloudcover": 82.0,
            "visibility": 8.3,
            "uvindex": 4.0,
            "severerisk": 10.0,
            "conditions": "Chuva, Parcialmente nublado",
            "description": "Parcialmente nublado ao longo do dia com chuva.",
            "icon": "rain",
            "hours": [
                {
                    "datetime": "00:00:00",
                    "temp": 19.9,
                    "feelslike": 19.9,
                    "humidity": 94.39,
                    "dew": 19.0,
                    "precip": 2.5,
                    "precipprob": 100.0,
                    "snow": 0.0,
                    "snowdepth": 0.0,
                    "preciptype": [
                        "rain"
                    ],
                    "windgust": 9.4,
                    "windspeed": 11.3,
                    "winddir": 42.0,
                    "visibility": 6.9,
                    "cloudcover": 88.9,
                    "severerisk": 10,
                    "conditions": "Chuva, Parcialmente nublado",
                    "icon": "rain"
                },
                {
                    "datetime": "01:00:00",
                    "temp": 19.9,
                    "feelslike": 19.9,
                    "humidity": 94.39,
                    "dew": 19.0,
                    "precip": 0.3,
                    "precipprob": 100.0,
                    "snow": 0.0,
                    "snowdepth": 0.0,
                    "preciptype": [
                        "rain"
                    ],
                    "windgust": 12.2,
                    "windspeed": 5.8,
                    "winddir": 34.0,
                    "visibility": 8.8,
                    "cloudcover": 88.9,
                    "severerisk": 10,
                    "conditions": "Chuva, Parcialmente nublado",
                    "icon": "rain"
                },
                {
                    "datetime": "02:00:00",
                    "temp": 19.0,
                    "feelslike": 19.0,
                    "humidity": 100.0,
                    "dew": 19.0,
                    "precip": 0.1,
                    "precipprob": 100.0,
                    "snow": 0.0,
                    "snowdepth": 0.0,
                    "preciptype": [
                        "rain"
                    ],
                    "windgust": 15.1,
                    "windspeed": 9.7,
                    "winddir": 52.0,
                    "visibility": 7.0,
                    "cloudcover": 30.4,
                    "severerisk": 10,
                    "conditions": "Chuva, Parcialmente nublado",
                    "icon": "rain"
                },
          ]
}
```
# Atualização e Expiração de Cache
Os dados são armazenados no Redis com um tempo de expiração de 20 minutos, que pode ser mudado. 
