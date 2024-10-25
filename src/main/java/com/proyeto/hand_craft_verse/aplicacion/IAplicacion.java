package com.proyeto.hand_craft_verse.aplicacion;

import java.util.List;

public interface IAplicacion<T> {

    public boolean guardar(T t);

    public T buscar(Object id);

    public T buscarPorNombre(String name);
    public List<Object[]> obtenerDatosColumna(String nombreColumna);

    public boolean eliminar(Object id);

    public List<T> obtenerTodos();

    public T actualizar(T t);
    

}
