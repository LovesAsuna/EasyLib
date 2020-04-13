package org.sct.plugincore.api;

import java.io.IOException;
import java.util.Collection;

public interface SerializerAPI {
    String singleObjectToString(Object object) throws IOException;

    byte[] singleObjectToByteArray(Object object) throws IOException;

    String collectionToString(Collection<Object> objects) throws IOException;

    byte[] collectionToByteArray(Collection<Object> objects) throws IOException;

    <T> T singleObjectFromString(String serialized, Class<T> classOfT) throws IOException;

    @SuppressWarnings("unchecked")
    <T> T singleObjectFromByteArray(byte[] serialized, Class<T> classOfT) throws IOException;

    <T, C extends Collection<T>> C collectionFromString(String serialized, Class<C> classOfC, Class<T> classOfT) throws IOException;

    @SuppressWarnings("unchecked")
    <T, C extends Collection<T>> C collectionFromByteArray(byte[] serialized, Class<C> classOfC, Class<T> classOfT) throws IOException;
}
