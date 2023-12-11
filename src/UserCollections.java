import java.util.ArrayList;
import java.util.List;

class UserCollections {
	
     static List<User> users;

    UserCollections() {
        users = new ArrayList<>();
    }
    
    void addUser(User user) {
        users.add(user);
    }
    
    public boolean userNameExists(String userName) {
        for (User user : users) {
            if (user.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getInactiveUsers(UserCollections userCollection) {
        List<User> inactiveUsers = new ArrayList<>();

        for (User user : users) {
            if (!user.isActive()) {
                inactiveUsers.add(user);
            }
        }

        return inactiveUsers;
    }
    
    void displayAllUsers() {
        for (User user : users) {
            user.displayInfo();
        }
    }
    
    
    
    public List<User> getUserList() {
        return users;
    }

    public void setUserList(List<User> userArrayList) {
        users = userArrayList;
    }
    
    
}