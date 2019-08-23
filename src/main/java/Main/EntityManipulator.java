package Main;

import Enteties.DataBaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EntityManipulator {
    private SessionFactory sessionFactory;
    private Session session;

    public EntityManipulator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    //Добавление сущности DataBaseEntity в базу данных
    public void addEntityToDB(DataBaseEntity dataBaseEntity) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(dataBaseEntity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //Удаление сущности DataBaseEntity из базы данных
    public void deleteEntityFromDB(DataBaseEntity dataBaseEntity) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(dataBaseEntity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    //Чтение сущности из базы с нужным id
    public DataBaseEntity readEntityFromDB(int id, Class<? extends DataBaseEntity> dataBaseEntityClass) {
        DataBaseEntity dataBaseEntity = null;

        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            dataBaseEntity = session.get(dataBaseEntityClass, id);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return dataBaseEntity;
    }

    public List<? extends DataBaseEntity> queryEntity(String hqlRequest) {

        List<? extends DataBaseEntity> dataBaseEntities = null;

        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            dataBaseEntities = session.createQuery(hqlRequest).list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return dataBaseEntities;
    }

    public void addEntityListToDB(List<? extends DataBaseEntity> dataBaseEntities) {
        for (DataBaseEntity dataBaseEntity : dataBaseEntities) {
            addEntityToDB(dataBaseEntity);
        }
    }

    public void deleteEntityListFromDB(List<? extends DataBaseEntity> dataBaseEntities) {
        for (DataBaseEntity dataBaseEntity : dataBaseEntities) {
            deleteEntityFromDB(dataBaseEntity);
        }
    }

}
