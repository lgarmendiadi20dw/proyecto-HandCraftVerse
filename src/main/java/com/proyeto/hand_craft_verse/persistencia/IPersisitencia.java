package com.proyeto.hand_craft_verse.persistencia;

import java.util.List;

public interface IPersisitencia<T> {
    
    public boolean guardar(T t);

    public T obtener(Object id);
    public boolean actualizar(T t);

    public boolean eliminar(T t);

    public List<T> obtenerTodos();

    public List<T> obtenerPorNombre(String nombre);

    public List<T> query(String key, String value);
}