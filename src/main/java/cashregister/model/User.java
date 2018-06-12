package cashregister.model;

public class User {
    private int id;
    private String name;
    private boolean isAdmin;
    private String password;

    public User() {
        super();
    }

    /**
     * Returns user's id
     * @return user's id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets user id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns user's name
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns whether user is admin or not
     * @return true if user is admin, false otherwise
     */
    public boolean getIsAdmin() { return isAdmin; }

    /**
     * Sets user' admin rights
     * @param admin
     */
    public void setIsAdmin(boolean admin) { isAdmin = admin; }

    /**
     * Sets user's password
     * @param password
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Returns user's password
     * @return user's password
     */
    public String getPassword() { return password;}
}