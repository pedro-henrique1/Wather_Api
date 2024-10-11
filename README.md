# API de Clima com Cache usando Spring Boot e Redis

Este projeto é uma API RESTful construída com Spring Boot que integra dados de clima e os armazena no Redis para otimizar a performance e reduzir o número de chamadas a serviços externos de clima. A API permite que os dados de clima sejam consultados por um único endpoint GET, garantindo eficiência no retorno das informações.


# Funcionalidades

- Armazenamento de dados de clima em cache com Redis.
- Recuperação de dados salvos por meio de um único endpoint GET.
- Integração simplificada com serviços de clima externos (API de clima).
- Atualização automática do cache conforme necessário.
- Redução do tempo de resposta através do uso de cache em memória.

# Requisitos 
- É necessario ter o [java](https://www.java.com/download/ie_manual.jsp) instalado em sua maquina para rodar o programa e tambem o [maven](https://maven.apache.org/install.html).
- Támbem e necessario ter o [redis](https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/) baixado e configurado. 

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


- Instalar Dependência:

```
  mvn clean install
  mvn spring-boot:run

```


# Como Fazer Requisições
A API fornece um único endpoint GET para retornar os dados de clima armazenados no Redis. Caso os dados não estejam em cache, eles serão buscados de um serviço de clima externo e armazenados no Redis para futuras consultas.

**GET /rest/{cidade}:** Retorna os dados de clima para a cidade informada. Se os dados da cidade já estiverem no cache, eles são retornados diretamente; caso contrário, a API faz a consulta externa, salva no Redis e retorna os dados.

### Exemplo de chamada de API:

```bash
   curl http://localhost:8080/api/clima/São-Paulo
```


### Exemplo de Resposta:

```json
    {
  "resolvedAddress": "São Paulo, SP, Brasil",
  "address": "São paulo",
  "timezone": "America/Sao_Paulo",
  "description": "Esfriando com uma chance de chuva Vários dias.",
  "days": [
    {
      "datetime": "2024-10-08",
      "tempmax": 36.5,
      "tempmin": 16.0,
      "temp": 24.1,
      "feelslikemax": 34.1,
      "feelslikemin": 16.0,
      "feelslike": 23.5,
      "dew": 11.2,
      "humidity": 52.7,
      "precip": 0.0,
      "precipprob": 0.0,
      "precipcover": 0.0,
      "preciptype": null,
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 31.0,
      "windspeed": 22.7,
      "winddir": 161.9,
      "cloudcover": 51.4,
      "visibility": 22.9,
      "uvindex": 10.0,
      "severerisk": 10.0,
      "conditions": "Parcialmente nublado",
      "description": "Parcialmente nublado ao longo do dia.",
      "icon": "partly-cloudy-day"
    },
    {
      "datetime": "2024-10-09",
      "tempmax": 26.1,
      "tempmin": 17.6,
      "temp": 20.4,
      "feelslikemax": 26.1,
      "feelslikemin": 17.6,
      "feelslike": 20.4,
      "dew": 14.9,
      "humidity": 71.9,
      "precip": 0.4,
      "precipprob": 87.1,
      "precipcover": 16.67,
      "preciptype": [
        "rain"
      ],
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 48.6,
      "windspeed": 28.1,
      "winddir": 134.3,
      "cloudcover": 58.0,
      "visibility": 23.3,
      "uvindex": 10.0,
      "severerisk": 10.0,
      "conditions": "Chuva, Parcialmente nublado",
      "description": "Tornando-se nublado à tarde com uma chance de chuva.",
      "icon": "rain"
    },
    {
      "datetime": "2024-10-10",
      "tempmax": 24.5,
      "tempmin": 18.1,
      "temp": 20.3,
      "feelslikemax": 24.5,
      "feelslikemin": 18.1,
      "feelslike": 20.3,
      "dew": 16.5,
      "humidity": 79.2,
      "precip": 4.1,
      "precipprob": 87.1,
      "precipcover": 45.83,
      "preciptype": [
        "rain"
      ],
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 45.0,
      "windspeed": 22.3,
      "winddir": 106.7,
      "cloudcover": 98.0,
      "visibility": 21.7,
      "uvindex": 7.0,
      "severerisk": 10.0,
      "conditions": "Chuva, Nublado",
      "description": "Céu nublado ao longo do dia com uma chance de chuva ao longo do dia.",
      "icon": "rain"
    },
    {
      "datetime": "2024-10-11",
      "tempmax": 28.9,
      "tempmin": 18.9,
      "temp": 21.7,
      "feelslikemax": 28.8,
      "feelslikemin": 18.9,
      "feelslike": 21.7,
      "dew": 17.5,
      "humidity": 79.1,
      "precip": 13.4,
      "precipprob": 83.9,
      "precipcover": 62.5,
      "preciptype": [
        "rain"
      ],
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 52.9,
      "windspeed": 22.0,
      "winddir": 1.2,
      "cloudcover": 95.6,
      "visibility": 20.8,
      "uvindex": 10.0,
      "severerisk": 10.0,
      "conditions": "Chuva, Nublado",
      "description": "Céu nublado ao longo do dia com uma chance de chuva ao longo do dia.",
      "icon": "rain"
    },
    {
      "datetime": "2024-10-12",
      "tempmax": 27.2,
      "tempmin": 17.7,
      "temp": 21.3,
      "feelslikemax": 28.0,
      "feelslikemin": 17.7,
      "feelslike": 21.3,
      "dew": 17.2,
      "humidity": 78.6,
      "precip": 1.6,
      "precipprob": 54.8,
      "precipcover": 41.67,
      "preciptype": [
        "rain"
      ],
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 39.2,
      "windspeed": 20.5,
      "winddir": 172.0,
      "cloudcover": 99.8,
      "visibility": 22.9,
      "uvindex": 8.0,
      "severerisk": 10.0,
      "conditions": "Chuva, Nublado",
      "description": "Céu nublado ao longo do dia com chuva de manhã cedo.",
      "icon": "rain"
    },
    {
      "datetime": "2024-10-13",
      "tempmax": 21.2,
      "tempmin": 17.1,
      "temp": 18.5,
      "feelslikemax": 21.2,
      "feelslikemin": 17.1,
      "feelslike": 18.5,
      "dew": 15.3,
      "humidity": 81.6,
      "precip": 0.3,
      "precipprob": 51.6,
      "precipcover": 12.5,
      "preciptype": [
        "rain"
      ],
      "snow": 0.0,
      "snowdepth": 0.0,
      "windgust": 34.6,
      "windspeed": 22.0,
      "winddir": 135.3,
      "cloudcover": 99.9,
      "visibility": 20.5,
      "uvindex": 4.0,
      "severerisk": 10.0,
      "conditions": "Chuva, Nublado",
      "description": "Céu nublado ao longo do dia com Chuva do final da tarde.",
      "icon": "rain"
    }
  ],
  "currentConditions": {
    "datetime": "01:28:00",
    "temp": 16.0,
    "feelslike": 16.0,
    "humidity": 87.9,
    "dew": 14.0,
    "precip": 0.0,
    "precipprob": 0.0,
    "snow": 0.0,
    "snowdepth": 0.0,
    "preciptype": null,
    "windgust": 9.7,
    "windspeed": 8.8,
    "winddir": 106.0,
    "visibility": 10.0,
    "cloudcover": 88.0,
    "solarradiation": 0.0,
    "uvindex": 0.0,
    "severerisk": 0.0,
    "conditions": "Parcialmente nublado",
    "icon": "partly-cloudy-night"
  }
}

```
# Atualização e Expiração de Cache
Os dados são armazenados no Redis com um tempo de expiração de 20 minutos, que pode ser mudado. 
