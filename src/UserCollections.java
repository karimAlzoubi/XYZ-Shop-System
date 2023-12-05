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
    
    static void eeee (User user) {
        if (users.contains(user)) {
            // Duplicate found, remove it from the list
            users.remove(user);
        } else {
            // Add the unique user to the set
            users.add(user);
        }
    }
    
    public boolean userNameExists(String userName) {
        for (User user : users) {
            if (user.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    void displayAllUsers() {
        for (User user : users) {
            user.displayInfo();
        }
    }
}