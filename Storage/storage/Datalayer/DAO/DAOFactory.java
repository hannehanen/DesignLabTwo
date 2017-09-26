package storage.Datalayer.DAO;

public class DAOFactory {
    public JsonDAO getJsonDAO(){
        return JsonDAO.getInstance();
    }
    public SQLDAO getSQLDAO(){
        return SQLDAO.getSqldao();
    }
}
