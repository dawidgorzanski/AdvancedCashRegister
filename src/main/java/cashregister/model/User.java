package cashregister.model;

public class User {
    private int id;
    private String name;
    private boolean isAdmin;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(boolean admin) { isAdmin = admin; }
}