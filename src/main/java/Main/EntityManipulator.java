package Main;

import Enteties.DataBaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.QueryException;
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
    @MeasuredFunction(name = "addEntity")
    public void addEntityToDB(DataBaseEntity dataBaseEntity) throws Exception {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(dataBaseEntity);
        session.getTransaction().commit();
        session.close();
    }


    //Удаление сущности DataBaseEntity из базы данных
    @MeasuredFunction(name = "deleteEntity")
    public void deleteEntityFromDB(DataBaseEntity dataBaseEntity) throws Exception {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(dataBaseEntity);
        session.getTransaction().commit();
        session.close();
    }


    //Чтение сущности из базы с нужным id
    @MeasuredFunction(name = "readEntity")
    public DataBaseEntity readEntityFromDB(int id, Class<? extends DataBaseEntity> dataBaseEntityClass) throws Exception {
        DataBaseEntity dataBaseEntity = null;

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        dataBaseEntity = session.get(dataBaseEntityClass, id);

        session.getTransaction().commit();
        session.close();

        return dataBaseEntity;
    }


    @MeasuredFunction(name = "queryEntityList")
    public List<? extends DataBaseEntity> queryEntity(String hqlRequest) throws Exception {

        List<? extends DataBaseEntity> dataBaseEntities = null;

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        dataBaseEntities = session.createQuery(hqlRequest).list();

        session.getTransaction().commit();
        session.close();

        return dataBaseEntities;
    }

    @MeasuredFunction(name = "queryNumOfEntities")
    public int queryNumOfEntities(String hqlRequest) throws Exception {

        List<? extends DataBaseEntity> dataBaseEntities = null;

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        int numOfNotes = session.createQuery(hqlRequest).list().size();

        session.getTransaction().commit();
        session.close();
        return numOfNotes;
    }


    @MeasuredFunction(name = "addEntityList")
    public void addEntityListToDB(List<? extends DataBaseEntity> dataBaseEntities) throws Exception {
        for (DataBaseEntity dataBaseEntity : dataBaseEntities) {
            addEntityToDB(dataBaseEntity);
        }
    }


    @MeasuredFunction(name = "deleteEntityList")
    public void deleteEntityListFromDB(List<? extends DataBaseEntity> dataBaseEntities) throws Exception {
        for (DataBaseEntity dataBaseEntity : dataBaseEntities) {
            deleteEntityFromDB(dataBaseEntity);
        }
    }

}
