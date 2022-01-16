# WeatherProject
L'applicativo sviluppato consente di ottenere informazioni meteorologiche sulle città statunitensi.</br>
Consente inoltre la visualizzazione di statistiche rigurdardanti particolari dati meteo, descritti in seguito, con possiblità di filtraggio per data ed ora.

### Indice
- [Installazione](#Installazione)
- [Configurazione](#Configurazione)
- [Descrizione](#Descrizione)
  - [Funzionalità](#Funzionalità)
- [Rotte](#Rotte)
  - [Add](#Add)
  - [Remove](#Remove)
  - [Current](#Current)
  - [Hourly](#Hourly)
  - [Daily](#Daily)
  - [Dayslot](#Dayslot)
- [Statistiche](#Statistiche)
  - [Esempio di chiamata](#Esempio-di-chiamata-alle-statistiche)
- [Eccezioni](#Eccezioni)
- [Documentazione e Test](#Documentazione-e-Test)

## Installazione
WeatherProject è installabile al seguente indirizzo:
```
https://github.com/EdoardoSampaolesi/WeatherProject.git
```

## Configurazione
L'applicativo per funzionare necessita di dati che vengono ottenuti mediante chiamate a [OpenWeather](https:///openweathermap.org/ "OpenWeather"), per cui durante lo sviluppo è stata utilizzata una una Api Key gratuita, la quale però ha una durata limitata che non si protrae oltre il *1/2/2022*, da quella data in poi è necessario generarne un'altra e sostituirla a quella già presente.

## Descrizione
Il servizio è disponibile al seguente indirizzo:
```
localhost:8080
```
Il servizio è stato pensato come la parte server di un applicativo più grande che avrebbe poi integrato un'interfaccia per dispositivi mobili.</br>
Il progetto generale era basato su molteplici utenti i quali richedevano le informazioni meteo per alcune città dal proprio dispositivo, proprio come i normali servizi meteo che troviamo ad oggi su ogni smartphone o simili, in tal senso è stato modellato il problema.</br>
Attualmente lo sviluppo non fornisce funzionalità di gestione utenti, è come se esistesse un solo utente che fa uso del servizio.

### Funzionalità
Il centro del programma è un oggetto di tipo ``CitiesManager`` il quale gestisce le città e si preoccupa di lavorare con le classi necessarie alla gestione delle informazioni meteo.
In esso è inserita una lista di città chiamata ``cityList`` la quale contiene tutte le città per il quale l'utente ha richiesto le informazioni meteo nel corso del tempo. 
Sono poi disponibili metodi per aggiungere e rimuovere elementi dalla lista.</br>
Ogni qual volta un utente richiede informazioni meteo per una o più città, esse vengono inserite all'interno della lista e rimangono disponibili fino a quanto non vengono rimosse volontriamente dall'utente.</br>
La lista è un elemento molto importante perchè viene utilizzata nella generazione delle statistiche. Abbiamo ritenuto che fosse interesse dell'utente generare statistiche per le città di suo interesse e risulta ovvio che queste sono quelle per le quali sono state richieste, in passato, le previsioni meteorologiche. Qualora un utente sia interessato a conoscere le statistiche solo per una parte delle città presenti nella lista, è stato messo a disposizione un campo ``exclude`` nella [rotta delle statistiche](#Statistiche "rotta delle statistiche") che consente di escludere tali città nella generazione delle statistiche.

## Rotte
Si evidenziano di seguito le rotte disponibili e le loro funzionalità:</br>
| Tipo  | Indirizzo  | Campi  | Descrizione |
| ------------ | ------------ | ------------ | ------------ |
| ``GET`` | ``\weather\add`` | ``cities`` | Permette di aggiungere una o più città alla lista delle città |
| ``GET`` | ``\weather\remove`` | ``cities`` | Permette di rimuovere una o più città alla lista delle città |
| ``GET`` | ``\weather\current`` | ``cities`` | Fornisce le principali informazioni meteo al momento della richiesta per le città definite nel campo ``cities``, si ricorda che le città devono essere necessariamente statunitensi |
| ``GET`` | ``\weather\hourly`` | ``cities`` | Fornisce le principali informazioni meteo ogni ora per 24 ore dal momento della richiesta per le città definite nel campo ``cities``, si ricorda che le città devono essere necessariamente statunitensi |
| ``GET`` | ``\weather\daily`` | ``cities`` | Fornisce le principali informazioni meteo giornaliere per 7 giorni da oggi per le città definite nel campo ``cities``, si ricorda che le città devono essere necessariamente statunitensi |
| ``GET`` | ``\weather\dayslot`` | ``cities`` | Fornisce le principali informazioni meteo odierne raggruppate per momenti della giornata per le città definite nel campo ``cities``, si ricorda che le città devono essere necessariamente statunitensi |

> **I momenti della giornata sono così definiti:**
> - Notte, dalle 00:00 alle 05:59
> - Mattino, dalle 06:00 alle 11:59 
> - Pomeriggio dalle 12:00 alle 17:59
> - Sera dalle 18:00 alle 23:59

Ogni altra rotta è gestita mediante ``@GetMapping("/error")``, ad esso è associato un metodo che ritorna un semplice messaggio di errore **non in formato JSON**

### Add
Come già definito sulla tabella, questa rotta permette di aggiungere una o più città alla lista personale delle città.</br>
Una volta inserito il nome, o i nomi separati da virgola, la rotta restituirà la lista completa in **formato JSON** delle città ed eventuali messaggi di errore, per questi si rimanda alle [eccezioni](#Eccezioni).</br>
Andiamo ora a fare un esempio di chiamata, aggiungendo le seguenti città: New York, Chicago e Los Angeles, alla lista personale:
```
localhost:8080/weather/add?cities=New%20York,Chicago,Los%20Angeles
```
 > Come si evince da sopra, i caratteri speciali necessitano di essere codificati, esistono strumenti appositi all'interno di servizi quali Postman, che consentono di farlo in automatico. ( Le virgole **non** devono essere codificate, esse servono per definire che gli elementi separati dalla virgola stessa sono due o più elementi diversi )

Il programma produrrà il seguente output:
```
[
    [
        {
            "Time zone":America/New_York,
            "Latitude and longitude": "lat=40.7127281&lon=-74.0060152",
            "Name": "New York"
        },
        {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        },
        {
            "Time zone":America/Los_Angeles,
            "Latitude and longitude": "lat=34.0536909&lon=-118.242766",
            "Name": "Los Angeles"
        }
    ]
]
```

### Remove
Come già definito sulla tabella, questa rotta permette di rimuovere una o più città dalla lista personale delle città.</br>
Una volta inserito il nome, o i nomi separati da virgola, la rotta restituirà la lista completa in **formato JSON** delle città ed eventuali messaggi di errore, per questi si rimanda alle [eccezioni](#Eccezioni).</br>
Andiamo ora a fare un esempio di chiamata, rimuovendo le seguenti città: Chicago e New York, dalla lista personale:
```
localhost:8080/weather/remove?cities=Chicago,New%20York
```
 > Come si evince da sopra, i caratteri speciali necessitano di essere codificati, esistono strumenti appositi all'interno di servizi quali Postman, che consentono di farlo in automatico. ( Le virgole **non** devono essere codificate, esse servono per definire che gli elementi separati dalla virgola stessa sono due o più elementi diversi )

Il programma produrrà il seguente output:
```
[
    [
        {
            "Time zone":America/Los_Angeles,
            "Latitude and longitude": "lat=34.0536909&lon=-118.242766",
            "Name": "Los Angeles"
        }
    ]
]
```

### Current
La seguente rotta permette di ottene in output il meteo corrente per le città specificate nel campo ``cities``.
Andiamo ora a fare un esempio di chiamata, richiediamo il meteo corrente per le città di Chicago e Phoenix:
```
localhost:8080/weather/current?cities=Chicago,Phoenix
```
Il *JSON* resituito sarà il seguente:
```
[
    {
        "Current weather": [
            {
                "date": "2022-01-09 06:28:15",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "8.99 mi/h",
                "wind direction": "324°",
                "probability percipitation": "0.0 %",
                "current temperature": "30.67 F",
                "cloudiness": "100 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "22.62 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "84 %"
            }
        ],
        "City": {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        }
    },
    {
        "Current weather": [
            {
                "date": "2022-01-09 05:28:16",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "3.44 mi/h",
                "wind direction": "90°",
                "probability percipitation": "0.0 %",
                "current temperature": "50.49 F",
                "cloudiness": "40 %",
                "weather descritpion": "scattered clouds",
                "feels like temperature": "48.0 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "59 %"
            }
        ],
        "City": {
            "Time zone":America/Phoenix,
            "Latitude and longitude": "lat=33.4484367&lon=-112.0741417",
            "Name": "Phoenix"
        }
    }
]
```
### Huorly
La seguente rotta permette di ottene in output il meteo ora per ora per 24 ore per le città specificate nel campo ``cities``.
Andiamo ora a fare un esempio di chiamata, richiediamo il meteo per la città di Chicago (per richiedere più città basta scrivere i nomi separati da virgola, come negli esempi sopra):
```
localhost:8080/weather/hourly?cities=Chicago
```
Il *JSON* resituito sarà il seguente:
```
[
    {
        "City": {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        },
        "Hourly weather": [
            {
                "date": "2022-01-09 06:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "16.62 mi/h",
                "wind direction": "301°",
                "probability percipitation": "0.39 %",
                "current temperature": "30.49 F",
                "cloudiness": "100 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "19.08 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "85 %"
            },
            {
                "date": "2022-01-09 07:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "18.05 mi/h",
                "wind direction": "306°",
                "probability percipitation": "0.0 %",
                "current temperature": "30.6 F",
                "cloudiness": "100 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "18.73 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "84 %"
            },
            {
                "date": "2022-01-09 08:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "19.08 mi/h",
                "wind direction": "308°",
                "probability percipitation": "0.0 %",
                "current temperature": "29.37 F",
                "cloudiness": "100 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "16.83 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "81 %"
            },
            {
                "date": "2022-01-09 09:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "18.81 mi/h",
                "wind direction": "308°",
                "probability percipitation": "0.0 %",
                "current temperature": "26.83 F",
                "cloudiness": "98 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "14.23 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "77 %"
            },
            {
                "date": "2022-01-09 10:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "18.45 mi/h",
                "wind direction": "310°",
                "probability percipitation": "0.0 %",
                "current temperature": "23.81 F",
                "cloudiness": "91 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "11.21 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "73 %"
            },
            {
                "date": "2022-01-09 11:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "18.45 mi/h",
                "wind direction": "313°",
                "probability percipitation": "0.0 %",
                "current temperature": "20.84 F",
                "cloudiness": "87 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "8.24 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "68 %"
            },
            {
                "date": "2022-01-09 12:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "18.19 mi/h",
                "wind direction": "313°",
                "probability percipitation": "0.0 %",
                "current temperature": "17.98 F",
                "cloudiness": "72 %",
                "weather descritpion": "broken clouds",
                "feels like temperature": "5.38 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "64 %"
            },
            {
                "date": "2022-01-09 13:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "17.81 mi/h",
                "wind direction": "312°",
                "probability percipitation": "0.0 %",
                "current temperature": "17.92 F",
                "cloudiness": "6 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "5.32 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "65 %"
            },
            {
                "date": "2022-01-09 14:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "17.13 mi/h",
                "wind direction": "311°",
                "probability percipitation": "0.0 %",
                "current temperature": "17.71 F",
                "cloudiness": "3 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "5.11 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "66 %"
            },
            {
                "date": "2022-01-09 15:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "14.99 mi/h",
                "wind direction": "310°",
                "probability percipitation": "0.0 %",
                "current temperature": "17.35 F",
                "cloudiness": "2 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "4.75 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "66 %"
            },
            {
                "date": "2022-01-09 16:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "13.62 mi/h",
                "wind direction": "306°",
                "probability percipitation": "0.0 %",
                "current temperature": "16.72 F",
                "cloudiness": "2 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "4.12 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "69 %"
            },
            {
                "date": "2022-01-09 17:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "13.71 mi/h",
                "wind direction": "300°",
                "probability percipitation": "0.0 %",
                "current temperature": "15.66 F",
                "cloudiness": "1 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "3.06 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "71 %"
            },
            {
                "date": "2022-01-09 18:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "13.24 mi/h",
                "wind direction": "298°",
                "probability percipitation": "0.0 %",
                "current temperature": "14.97 F",
                "cloudiness": "1 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "2.37 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "72 %"
            },
            {
                "date": "2022-01-09 19:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "12.53 mi/h",
                "wind direction": "293°",
                "probability percipitation": "0.0 %",
                "current temperature": "14.56 F",
                "cloudiness": "0 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "1.96 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "73 %"
            },
            {
                "date": "2022-01-09 20:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "11.79 mi/h",
                "wind direction": "284°",
                "probability percipitation": "0.0 %",
                "current temperature": "14.04 F",
                "cloudiness": "0 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "1.44 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "75 %"
            },
            {
                "date": "2022-01-09 21:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "11.99 mi/h",
                "wind direction": "281°",
                "probability percipitation": "0.0 %",
                "current temperature": "13.55 F",
                "cloudiness": "3 %",
                "weather descritpion": "clear sky",
                "feels like temperature": "0.95 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clear",
                "humidity": "77 %"
            },
            {
                "date": "2022-01-09 22:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "12.26 mi/h",
                "wind direction": "281°",
                "probability percipitation": "0.0 %",
                "current temperature": "13.14 F",
                "cloudiness": "12 %",
                "weather descritpion": "few clouds",
                "feels like temperature": "0.54 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "77 %"
            },
            {
                "date": "2022-01-09 23:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "12.06 mi/h",
                "wind direction": "280°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.76 F",
                "cloudiness": "19 %",
                "weather descritpion": "few clouds",
                "feels like temperature": "0.16 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "77 %"
            },
            {
                "date": "2022-01-10 24:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "11.54 mi/h",
                "wind direction": "280°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.47 F",
                "cloudiness": "27 %",
                "weather descritpion": "scattered clouds",
                "feels like temperature": "-0.13 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "76 %"
            },
            {
                "date": "2022-01-10 01:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "11.81 mi/h",
                "wind direction": "279°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.18 F",
                "cloudiness": "41 %",
                "weather descritpion": "scattered clouds",
                "feels like temperature": "-0.42 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "76 %"
            },
            {
                "date": "2022-01-10 02:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "11.72 mi/h",
                "wind direction": "280°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.2 F",
                "cloudiness": "65 %",
                "weather descritpion": "broken clouds",
                "feels like temperature": "-0.4 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "74 %"
            },
            {
                "date": "2022-01-10 03:00:00",
                "snow volume": "0.0 mm",
                "visibility": "10000 ft",
                "wind speed": "12.1 mi/h",
                "wind direction": "280°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.61 F",
                "cloudiness": "77 %",
                "weather descritpion": "broken clouds",
                "feels like temperature": "0.01 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "73 %"
            },
            {
                "date": "2022-01-10 04:00:00",
                "snow volume": "0.0 mm",
                "visibility": "9900 ft",
                "wind speed": "12.15 mi/h",
                "wind direction": "283°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.76 F",
                "cloudiness": "82 %",
                "weather descritpion": "broken clouds",
                "feels like temperature": "0.16 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "82 %"
            },
            {
                "date": "2022-01-10 05:00:00",
                "snow volume": "0.0 mm",
                "visibility": "545 ft",
                "wind speed": "11.9 mi/h",
                "wind direction": "286°",
                "probability percipitation": "0.0 %",
                "current temperature": "12.99 F",
                "cloudiness": "86 %",
                "weather descritpion": "overcast clouds",
                "feels like temperature": "0.39 F",
                "precipitation volume": "0.0 mm",
                "weather": "Clouds",
                "humidity": "89 %"
            }
        ]
    }
]
```
### Daily
La seguente rotta permette di ottene in output il meteo giornaliero per 7 giorni per le città specificate nel campo ``cities``.
Andiamo ora a fare un esempio di chiamata, richiediamo il meteo per la città di Chicago (per richiedere più città basta scrivere i nomi separati da virgola, come negli esempi sopra):
```
localhost:8080/weather/daily?cities=Chicago
```
Il *JSON* resituito sarà il seguente:
```
[
    {
        "City": {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        },
        "Daily weather": [
            {
                "date": "2022-01-09 11:00:00",
                "precipitation volume": "0.66 mm",
                "snow volume": "0.0 mm",
                "min temperature": "12.76 F",
                "wind speed": "19.08 mi/h",
                "weather": "Rain",
                "wind direction": "308°",
                "probability percipitation": "0.63 %",
                "humidity": "68 %",
                "max temperature": "33.73 F",
                "cloudiness": "87 %",
                "weather descritpion": "light rain"
            },
            {
                "date": "2022-01-10 11:00:00",
                "precipitation volume": "0.12 mm",
                "snow volume": "0.0 mm",
                "min temperature": "9.86 F",
                "wind speed": "13.78 mi/h",
                "weather": "Snow",
                "wind direction": "310°",
                "probability percipitation": "0.55 %",
                "humidity": "69 %",
                "max temperature": "15.89 F",
                "cloudiness": "92 %",
                "weather descritpion": "light snow"
            },
            {
                "date": "2022-01-11 11:00:00",
                "precipitation volume": "0.0 mm",
                "snow volume": "0.0 mm",
                "min temperature": "9.88 F",
                "wind speed": "21.56 mi/h",
                "weather": "Clear",
                "wind direction": "202°",
                "probability percipitation": "0.0 %",
                "humidity": "60 %",
                "max temperature": "34.7 F",
                "cloudiness": "0 %",
                "weather descritpion": "clear sky"
            },
            {
                "date": "2022-01-12 11:00:00",
                "precipitation volume": "0.0 mm",
                "snow volume": "0.0 mm",
                "min temperature": "30.07 F",
                "wind speed": "18.99 mi/h",
                "weather": "Clouds",
                "wind direction": "218°",
                "probability percipitation": "0.0 %",
                "humidity": "80 %",
                "max temperature": "35.24 F",
                "cloudiness": "93 %",
                "weather descritpion": "overcast clouds"
            },
            {
                "date": "2022-01-13 11:00:00",
                "precipitation volume": "1.24 mm",
                "snow volume": "0.0 mm",
                "min temperature": "26.26 F",
                "wind speed": "11.1 mi/h",
                "weather": "Snow",
                "wind direction": "78°",
                "probability percipitation": "0.99 %",
                "humidity": "90 %",
                "max temperature": "30.56 F",
                "cloudiness": "100 %",
                "weather descritpion": "light snow"
            },
            {
                "date": "2022-01-14 11:00:00",
                "precipitation volume": "0.11 mm",
                "snow volume": "0.0 mm",
                "min temperature": "25.47 F",
                "wind speed": "14.09 mi/h",
                "weather": "Snow",
                "wind direction": "61°",
                "probability percipitation": "0.2 %",
                "humidity": "64 %",
                "max temperature": "29.07 F",
                "cloudiness": "100 %",
                "weather descritpion": "light snow"
            },
            {
                "date": "2022-01-15 11:00:00",
                "precipitation volume": "0.24 mm",
                "snow volume": "0.0 mm",
                "min temperature": "22.75 F",
                "wind speed": "7.9 mi/h",
                "weather": "Snow",
                "wind direction": "291°",
                "probability percipitation": "0.22 %",
                "humidity": "68 %",
                "max temperature": "26.53 F",
                "cloudiness": "96 %",
                "weather descritpion": "light snow"
            }
        ]
    }
]
```
### Dayslot
La seguente rotta permette di ottene in output il meteo giornaliero raggruppato in parti della giornata ( notte, mattina, pomeriggio, sera ) per le città specificate nel campo ``cities``.
Andiamo ora a fare un esempio di chiamata, richiediamo il meteo per la città di Chicago (per richiedere più città basta scrivere i nomi separati da virgola, come negli esempi sopra):
```
localhost:8080/weather/dayslot?cities=Chicago
```
Il *JSON* resituito sarà il seguente:
```
[
    {
        "Night, morning, afternoon, evening weather": [
            {
                "Night": {
                    "feels like temperature": "32.1 F",
                    "precipitation volume": "0.0 mm",
                    "snow volume": "0.0 mm",
                    "visibility": "10000 ft",
                    "wind speed": "2.64 mi/h",
                    "weather": "Clouds",
                    "wind direction": "69°",
                    "probability percipitation": "0 %",
                    "humidity": "87 %",
                    "current temperature": "33.69 F",
                    "cloudiness": "75 %"
                }
            },
            {
                "Morning": {
                    "feels like temperature": "27.3 F",
                    "precipitation volume": "0.0 mm",
                    "snow volume": "0.0 mm",
                    "visibility": "10000 ft",
                    "wind speed": "8.66 mi/h",
                    "weather": "Clouds",
                    "wind direction": "178°",
                    "probability percipitation": "0 %",
                    "humidity": "87 %",
                    "current temperature": "34.32 F",
                    "cloudiness": "87 %"
                }
            },
            {
                "Afternoon": {
                    "feels like temperature": "29.0 F",
                    "precipitation volume": "0.0 mm",
                    "snow volume": "0.0 mm",
                    "visibility": "10000 ft",
                    "wind speed": "7.88 mi/h",
                    "weather": "Clouds",
                    "wind direction": "339°",
                    "probability percipitation": "0 %",
                    "humidity": "80 %",
                    "current temperature": "35.35 F",
                    "cloudiness": "96 %"
                }
            },
            {
                "Evening": {
                    "feels like temperature": "27.25 F",
                    "precipitation volume": "0.0 mm",
                    "snow volume": "0.0 mm",
                    "visibility": "10000 ft",
                    "wind speed": "7.68 mi/h",
                    "weather": "Clouds",
                    "wind direction": "336°",
                    "probability percipitation": "0 %",
                    "humidity": "83 %",
                    "current temperature": "33.79 F",
                    "cloudiness": "99 %"
                }
            }
        ],
        "City": {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        }
    }
]
```
## Statistiche
Le statistiche fanno riferimento ai valori di: *Pressione, Umidità, Nuvolosità e Temperatura*.</br>
Per ognuno di essi vengono calcolati i valori: *Massimo, Minimo, Medio e la Varianza*.

| Tipo  | Indirizzo  | Campi  | Descrizione |
| ------------ | ------------ | ------------ | ------------ |
| ``GET`` | ``\stats`` | ``exlcude`` ``bthour`` ``btdate`` | Consente di generare le statistiche per le città presenti in ``cityList`` |

Nessuno dei parametri è obbligatorio.</br>
Il parametro ``exlcude`` è un lista dei nomi di città che l'utente vuole escludere dalla generazione delle statistiche. In caso venga inserito un nome **non** presente in ``cityList``, esso verrà semplicemente ignorato.</br>
Il parametro ``bthour`` rappresenta l'intervallo di orario per cui si vogliono ottenere le statistiche. In esso va specificata univocamente l'ora iniziale e quella finale, qualora non venga inserito viene preso come *default* l'intervallo ``00:00 - 23:59``.</br>
Il parametro ``btdate`` rappresenta l'intervallo di giorni per cui si vogliono ottenere le statistiche. In esso va specificato univocamente il giorno iniziale e quello finale, qualora non venga inserito viene preso come *default* l'intervallo che va da 4 giorni prima del giorno della richiesta al giorno successivo al giorno della richiesta.</br>
 > *Va tenuto in cosiderazione che attraverso una Api Key gratuita di OpenWeather è possibile richiede informazioni meteo fino ad un massimo di 7 giorni passati da giorno corrente e fino ad un massimo di 2 giorni in avanti* </br>
> **Gli orari e i giorni inseriti nei rispettivi parametri vengono valutati in base al *fuso orario delle città prese in considerazione***

#### Esempio-di-chiamata-alle-statistiche
Prima di tutto andiamo ad aggiungere un città alla nostra lista personale, attraverso il comando:
```
localhost:8080/weather/add?cities=Chicago
```
Esso restituirà un JSON contente le informazioni delle città presenti attualmente in ``cityList``.
```
[
    [
        {
            "Time zone":America/Chicago,
            "Latitude and longitude": "lat=41.8755616&lon=-87.6244212",
            "Name": "Chicago"
        }
    ]
]
```
Andiamo ora ad richiedere le statistiche per gli orari *dalle 14 alle 20*, nei giorni *5/1/2022, 6/1/2022, 7/1/2022*:
> La richiesta è stata fatta il giorno 6/1/2022
```
localhost:8080/stats?btdate=[05/01/2022,07/01/2022]&bthour=[14:00:00,20:00:00]
```
In tal caso verrà restituito un errore di Bad Request perchè è necessario prima effettuare un Encode. La richiesta diventa quindi:
```
localhost:8080/stats?btdate=%5B05%2F01%2F2022%2C07%2F01%2F2022%5D&bthour=%5B14%3A00%3A00%2C20%3A00%3A00%5D
```
Il JSON restituito è il seguente:
```
[
    {
        "city": "Chicago",
        "statistics": [
            {
                "temp": {
                    "average": 13.82,
                    "min": 11.46,
                    "max": 17.01,
                    "variance": 3.21
                }
            },
            {
                "clouds": {
                    "average": 95.62,
                    "min": 66.0,
                    "max": 100.0,
                    "variance": 81.19
                }
            },
            {
                "pressure": {
                    "average": 1020.33,
                    "min": 1009.0,
                    "max": 1030.0,
                    "variance": 50.79
                }
            },
            {
                "humidity": {
                    "average": 79.48,
                    "min": 71.0,
                    "max": 90.0,
                    "variance": 47.87
                }
            }
        ]
    }
]
```

> I valori di temperatura sono riportati in Fahrenheit</br>
> I valori di umidità e nuvolosità sono descritti in %</br>
> La pressione è descritta in Pascal</br>

## Eccezioni
Durante lo sviluppo abbiamo riscontrato degli errori che abbiamo gestito mediante eccezioni personalizzate:
##### CityNotFoundException
Questa eccezione viene generata qualora venga inserito il nome di una città che non esiste.</br>
In tal caso verrà restituito un errore in formato JSON:
```
[
    {
        "city": "NotExistingCity",
        "error": "City not found in OpenWeather cities database"
    }
]
```

##### CityNotAddedException
Questa eccezione viene generata qualora non sia possibile aggiungere un città alla lista personale perchè è gia presenta una città con lo stesso nome.</br>
In tal caso verrà restituito un errore in formato JSON:
```
{
    "city": "Chicago",
    "error": "Already exists a city with that name in the list"
}
```

##### NotRemovedCity
Questa eccezione viene generata qualora non sia possibile rimuovere un città alla lista personale perchè non è presente nella lista.</br>
In tal caso verrà restituito un errore in formato JSON:
```
{
    "city": "Chicago",
    "error": "City not found in personal list of cities"
}
```

##### DateOutOfRangeException
Questa eccezione viene generata qualora non sia possibile generare delle statistiche per i giorni o gli orari specificati, in quanto essi sono fuori dal range di richiesta consentito da openWeather.</br>
In tal caso verrà restituito un errore in formato JSON:
```
[
    {
        "city": "Chicago",
        "error": "Requested time is out of allowed range"
    }
]
```

##### StatNotAvailableException
Questa eccezione viene generata qualora non sia possibile generare delle statistiche per i giorni o gli orari specificati, in quanto non sono presenti dati in quel range temporale.</br>
In tal caso verrà restituito un errore in formato JSON:
```
[
    {
        "city": "Chicago",
        "error": "Statistics not available for date or time specified"
    }
]
```

## Documentazione-e-Test
Al seguente [link](https://github.com/EdoardoSampaolesi/WeatherProject/tree/main/weather/doc "link") forniamo la documentazione JavaDoc del codice.

Abbiamo provveduto a scrivere alcuni Test per verificare il corretto funzionamento delle rotte e di altre funzionalità tra le quali la gestione delle date per le statistiche.
Tutti i Test sono documentati e disponibili alla consultazione.
