package cashregister.dao;

import cashregister.model.Person;
import cashregister.dao.interfaces.IPersonDao;

public class PersonDao extends BaseDao<Person> implements IPersonDao {
    public PersonDao() {
        super.setClass(Person.class);
    }
}
