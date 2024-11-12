package com.proyeto.hand_craft_verse.persistencia;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Persistencia<T> implements IPersistencia<T> {

    private SessionFactory sessionFactory;
    private Class<T> classType;

    @Override
    public boolean guardar(T t) {

        Session session = null;

        try {

            session = sessionFactory.openSession();

            session.beginTransaction();
            session.persist(t);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {

            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                // Manejar la excepción de duplicado aquí
                System.out.println("Duplicate entry detected: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public T obtener(Object id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(classType, id);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean actualizar(T t) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(t);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean eliminar(T t) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(t);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<T> obtenerTodos() {
        Session session = sessionFactory.openSession();
        List<T> entities = null;
        try {
            while (session.getTransaction().isActive()) {
                wait(10);
            }
            session.beginTransaction();
            entities = session.createQuery("from " + classType.getName(), classType).list();
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
            return entities;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Object[]> obtenerDatosColumnas(String nombreColumna1, String nombreColumna2) {
        Session session = sessionFactory.openSession();
        try {
            // Asegúrate de que los nombres de columna están correctamente validados y
            // escapados
            String hql = "select " + nombreColumna1 + ", " + nombreColumna2 + " from " + classType.getName();
            return session.createQuery(hql, Object[].class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<T> obtenerPorNombre(String nombre) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from " + classType.getName() + " where nombre = :nombre", classType)
                    .setParameter("nombre", nombre).list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<T> query(String key, String value) {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("from " + classType.getName() + " where " + key + " = :value", classType)
                    .setParameter("value", value).list();
        } finally {
            session.close();
        }
    }

    /**
     * Método genérico para obtener entidades T filtrando por un valor en una
     * colección relacionada.
     *
     * @param collectionName El nombre de la colección en la entidad T.
     * @param attributeName  El nombre del atributo en la entidad de la colección.
     * @param value          El valor del atributo a filtrar.
     * @return Lista de entidades T que cumplen con el filtro.
     */
    @Override
    public List<T> obtenerPorColeccion(String collectionName, String attributeName, String value) {
        Session session = sessionFactory.openSession();
        String hql = "select t from " + classType.getSimpleName() + " t join t." + collectionName + " c where c."
                + attributeName + " = :value";

        return session.createQuery(hql, classType)
                .setParameter("value", value)
                .list();
    }

}
