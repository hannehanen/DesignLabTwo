package storage.Datalayer.DAO;

public class DAOSql extends AbstractDAOFactory {

    @Override
    public DAOInterface getDAO() {
        return new SQLDAO();
    }
}
