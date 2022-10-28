import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TmpTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void Test01(){
        String password = encryptor.encrypt("pacuynxtstasbdhj");
        System.out.println(password);
    }
}
