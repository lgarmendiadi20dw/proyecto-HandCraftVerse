package com.proyeto.hand_craft_verse.aplicacion;

import java.util.List;

import com.proyeto.hand_craft_verse.persistencia.IPersistencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Aplicacion<T> implements IAplicacion<T> {

    IPersistencia<T> persistencia;

    @Override
    public boolean guardar(T t) {
        
        try{
            return persistencia.guardar(t);
        }
        catch(Exception e){
            return false;
        }
        
    }

    @Override
    public boolean actualizar(T t) {
        
        // if(persistencia.actualizar(t))
        // {
        //     return t;
        // }
        // return null;
        try{
            return persistencia.actualizar(t);
        }
        catch(Exception e){
            return false;
        }
        
    }

    @Override
    public T buscar(Object id) {
        
        return persistencia.obtener(id);
    }
    

    @Override
    public T buscarPorNombre(String name) {
        return null;
    }

    @Override
    public boolean eliminar(Object id) {
        
        T t = persistencia.obtener(id);

        if(t == null)
        {
            return false;
        }
        else{
            return persistencia.eliminar(t);
        }
    }

    @Override
    public java.util.List<T> obtenerTodos() {
        return persistencia.obtenerTodos();
    }



    @Override
    public List<Object[]> obtenerDatosColumnas(String nombreColumna1, String nombreColumna2) {
        return persistencia.obtenerDatosColumnas( nombreColumna1,  nombreColumna2);
    }

    @Override
    public List<T> obtenerDatosColumna(String nombreColumna1, String dato) {
        return persistencia.query( nombreColumna1,  dato);
    }


  /**
     * Método para obtener entidades T filtrando por un valor en una colección relacionada.
     *
     * @param collectionName El nombre de la colección en la entidad T.
     * @param attributeName El nombre del atributo en la entidad de la colección.
     * @param value El valor del atributo a filtrar.
     * @return Lista de entidades T que cumplen con el filtro.
     */
    public List<T> obtenerPorColeccion(String collectionName, String attributeName, Object value) {
        return persistencia.obtenerPorColeccion(collectionName, attributeName, value);
    } 
    public List<T> buscarPorCampo(String campo, String valor) {
        return persistencia.buscarPorCampo(campo, valor);
    }
}
