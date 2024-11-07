package redsocial.user;

public class UserNotFoundException extends RuntimeException {

    private long userid;

    public UserNotFoundException(long userid) {
        super("User not found with id: " + userid);
    }

    public long getUserid() {
        return userid;
    }
}
