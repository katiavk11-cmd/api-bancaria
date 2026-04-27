import org.mindrot.jbcrypt.BCrypt;

public class BcryptGenerator {

    public static void main(String[] args) {
        String hash = BCrypt.hashpw("senha123", BCrypt.gensalt());
        System.out.println(hash);
    }
}