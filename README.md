# Biblioteca

Progetto finale percorso di studi linguaggi di programmazione.

## Requisiti Funzionali

1. Gestione dei Libri

   - Aggiunta di un libro: L'utente deve poter aggiungere nuovi libri al sistema.
     Ogni libro deve avere:

     - Codice ISBN (unico)
     - Titolo
     - Autore
     - Anno di pubblicazione
     - Genere

   - Modifica di un libro: L'utente deve poter modificare le informazioni di un libro
     esistente.
   - Rimozione di un libro: L'utente deve poter rimuovere un libro dal sistema.
   - Ricerca di un libro: L'utente deve poter cercare un libro in base a diversi criteri (ISBN, titolo, autore, genere).

2. Gestione dei Prestiti

   - Prestito di un libro: L'utente deve poter registrare il prestito di un libro. I dati richiesti includono:

     - Codice ISBN del libro
     - Nome dell'utente che prende in prestito il libro
     - Data di inizio prestito
     - Data di restituzione prevista

   - Restituzione di un libro: L'utente deve poter registrare la restituzione di un
     libro, aggiornando lo stato del libro come disponibile.
   - Lista dei prestiti: L'utente deve poter visualizzare tutti i prestiti attivi, con la
     possibilità di filtrare per utente o per libro.

3. Gestione degli Utenti

   - Aggiunta di un utente: L'utente deve poter aggiungere nuovi utenti al sistema.
   - Modifica di un utente: L'utente deve poter modificare i dettagli di un utente
     esistente.
   - Rimozione di un utente: L'utente deve poter rimuovere un utente dal sistema.
   - Ricerca di un utente: L'utente deve poter cercare un utente in base al nome o
     ad altri dettagli identificativi.

4. Reportistica
   - Generazione di un report dei libri in prestito: L'utente deve poter generare un
     report di tutti i libri attualmente in prestito, con la possibilità di ordinare per
     data di scadenza del prestito.
   - Generazione di un report dei libri disponibili: L'utente deve poter visualizzare
     un elenco di tutti i libri attualmente disponibili in biblioteca.
