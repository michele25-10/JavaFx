package it.unife.lp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonController {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Reads all objects from the JSON file.
     *
     * @param file  The file to read from
     * @param clazz The class of the objects in the file
     * @param <T>   The type of objects
     * @return ObservableList of objects of type T
     */
    public static <T> ObservableList<T> loadData(File file, Class<T> clazz) {
        try {
            if (!file.exists() || file.length() == 0) {
                return FXCollections.observableArrayList();
            }
            // Usa CollectionType per leggere direttamente una lista di oggetti
            CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            List<T> data = MAPPER.readValue(file, listType);
            return FXCollections.observableArrayList(data);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dei dati dal file " + file.getName() + ": " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    /**
     * Writes a list of objects to the JSON file.
     *
     * @param file The file to write to
     * @param data The list of objects to write
     * @param <T>  The type of objects
     */
    public static <T> void writeAll(File file, List<T> data) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            System.err.println("Errore nella scrittura dei dati nel file '" + file.getName() + "': " + e.getMessage());
        }
    }

    /**
     * Adds a new object to the JSON file.
     *
     * @param file  The file to write to
     * @param obj   The object to add
     * @param clazz The class of the objects in the file
     * @param <T>   The type of objects
     */
    public static <T> void add(File file, T obj, Class<T> clazz) {
        ObservableList<T> currentData = loadData(file, clazz);
        currentData.add(obj);
        writeAll(file, currentData);
    }

    /**
     * Updates an existing object in the JSON file.
     *
     * @param file       The file to write to
     * @param id         The identifier of the object to update
     * @param updatedObj The updated object
     * @param clazz      The class of the objects in the file
     * @param idGetter   Function to extract the ID from an object
     * @param <T>        The type of objects
     */
    public static <T> void update(File file, Object id, T updatedObj, Class<T> clazz, IdGetter<T> idGetter) {
        ObservableList<T> currentData = loadData(file, clazz);
        for (int i = 0; i < currentData.size(); i++) {
            if (idGetter.getId(currentData.get(i)).equals(id)) {
                currentData.set(i, updatedObj);
                writeAll(file, currentData);
                return;
            }
        }
        System.err.println("Oggetto con ID '" + id + "' non trovato per l'aggiornamento.");
    }

    /**
     * Deletes an object from the JSON file by ID.
     *
     * @param file     The file to write to
     * @param id       The identifier of the object to delete
     * @param clazz    The class of the objects in the file
     * @param idGetter Function to extract the ID from an object
     * @param <T>      The type of objects
     */
    public static <T> void delete(File file, Object id, Class<T> clazz, IdGetter<T> idGetter) {
        ObservableList<T> currentData = loadData(file, clazz);
        boolean removed = currentData.removeIf(obj -> idGetter.getId(obj).equals(id));
        if (removed) {
            writeAll(file, currentData);
        } else {
            System.err.println("Oggetto con ID '" + id + "' non trovato per la rimozione.");
        }
    }

    /**
     * Functional interface for getting an ID from an object.
     *
     * @param <T> The type of the object
     */
    @FunctionalInterface
    public interface IdGetter<T> {
        Object getId(T obj);
    }
}
