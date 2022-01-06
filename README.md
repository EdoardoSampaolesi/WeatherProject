# WeatherProject
L'applicativo sviluppato consente di ottenere informazioni meteorologiche sulle città statunitensi.</br>
Consente inoltre la visualizzazione di statistiche rigurdardanti particolari dati meteo, descritti in seguito, con possiblità di filtraggio per data ed ora.

### Indice
- [Installazione](#Installazione)
- [Configurazione](#Configurazione)
- [Descrizione](#Descrizione)
  - [Funzionalità](#Funzionalità)
- [Rotte](#Rotte)
- [Statistiche](#Statistiche) 

## Installazione
WeatherProject è installabile dal Prompt dei Comandi digitando:
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

**I momenti della giornata sono così definiti:**
- Notte, dalle 00:00 alle 05:59
- Mattino, dalle 06:00 alle 11:59 
- Pomeriggio dalle 12:00 alle 17:59
- Notte dalle 18:00 alle 23:59

Per gestire più città contemporaneamente basta semplicemente inserire i vari nomi separati da virgola come di seguito:
```
localhost:8080/weather/add?cities=New%20York,Chicago,Los%20Angeles
```
Come si evince da sopra, i caratteri speciali necessitano di essere codificati, esistono strumenti appositi all'interno di servizi quali Postman, che consentono di farlo in automatico. ( Le virgole **non** devono essere codificate, esse servono per definire che gli elementi separati dalla virgola stessa sono due o più elementi diversi )

Ogni altra rotta è gestita mediante ``@GetMapping("/error")``, ad esso è associato un metodo che ritorna un semplice messaggio di errore **non in formato JSON**

## Statistiche
| Tipo  | Indirizzo  | Campi  | Descrizione |
| ------------ | ------------ | ------------ | ------------ |

| ``GET`` | ``\stats`` | ``exlcude`` ``bthour`` ``btdate`` | Consente di generare le statistiche per le città presenti in ``cityList`` |

Nessuno dei campi è obbligatorio, in caso di mancati inserimenti dei parametri richiesti vengono inseriti valore di default 


