package com.proyeto.hand_craft_verse.persistencia;

import java.util.List;

import org.hibernate.Session;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Persistencia<T> implements IPersistencia<T> {

    private Session session;
    private Class<T> classType;

    @Override
    public boolean guardar(T t) {
        try {
            session.beginTransaction();
            session.persist(t);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public T obtener(Object id) {

        return session.get(classType, id);
    }

    @Override
    public boolean actualizar(T t) {
        try {
            session.beginTransaction();
            session.merge(t);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public boolean eliminar(T t) {
        try {
            session.beginTransaction();
            session.remove(t);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }
    @Override
    public List<T> obtenerTodos() {
        return session.createQuery("from " + classType.getName(), classType).list();
    }

    @Override
    public List<Object[]> obtenerDatosColumnas(String nombreColumna1, String nombreColumna2) {
        // Asegúrate de que los nombres de columna están correctamente validados y escapados
        String hql = "select " + nombreColumna1 + ", " + nombreColumna2 + " from " + classType.getName();
        return session.createQuery(hql, Object[].class).list();
    }
    

    @Override
    public List<T> obtenerPorNombre(String nombre) {
        return session.createQuery("from " + classType.getName() + " where nombre = :nombre", classType)
                .setParameter("nombre", nombre).list();

    }


    @Override
    public List<T> query(String key, String value) {
        return session.createQuery("from " + classType.getName() + " where " + key + " = :value", classType)
                .setParameter("value", value).list();
    }

    /**
     * Método genérico para obtener entidades T filtrando por un valor en una colección relacionada.
     *
     * @param collectionName El nombre de la colección en la entidad T.
     * @param attributeName El nombre del atributo en la entidad de la colección.
     * @param value El valor del atributo a filtrar.
     * @return Lista de entidades T que cumplen con el filtro.
     */
    @Override
    public List<T> obtenerPorColeccion(String collectionName, String attributeName, String value) {
        String hql = "select t from " + classType.getSimpleName() + " t join t." + collectionName + " c where c." + attributeName + " = :value";

        return session.createQuery(hql, classType)
                      .setParameter("value", value)
                      .list();
    }

}
