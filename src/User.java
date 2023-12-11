
abstract class User {
	
    private String username;
    private String password;
    private String email;
    private boolean active;

    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = false;
    }

    public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
	
    public void displayInfo() {
    	System.out.println("Name "+username+", his email "+email);
    }
}