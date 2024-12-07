package com.proyeto.hand_craft_verse.aplicacion;

import java.util.List;

import com.proyeto.hand_craft_verse.dominio.productos.Producto;

public interface IAplicacion<T> {

    public boolean guardar(T t);

    public T buscar(Object id);

    public T buscarPorNombre(String name);
    public List<Object[]> obtenerDatosColumnas(String nombreColumna1, String nombreColumna2);
    public List<T> obtenerDatosColumna(String nombreColumna, String dato);

    public boolean eliminar(Object id);

    public List<T> obtenerTodos();

    public T actualizar(T t);

    public List<T> obtenerPorColeccion(String collectionName, String attributeName, Object nombre);

    public List<T> buscarPorCampo(String campo, String query);
    

}
