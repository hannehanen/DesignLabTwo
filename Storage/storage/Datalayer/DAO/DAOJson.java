package storage.Datalayer.DAO;

public class DAOJson extends AbstractDAOFactory {
    @Override
    public DAOInterface getDAO() {
        return new JsonDAO();
    }
}
