import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class UserTest {
    
    @BeforeAll
    static void setup(){
        User user = new User();
        user.setEmail("garagemanager@mechanic.com");
        user.setPassword("P@ssw0rd");
        user.createAccount(3);
    }

//unit test for account not found    
    @Test
    @DisplayName("No account found")
    public void noAccountFound() {
        // given
        User user = new User();
        user.setEmail("heber.testingstuff@test.com"); 
        user.setPassword("Test123!"); 
        user.login(0);

        // when
        boolean result = user.getLoginSuccessCheck();

        // then
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Successful Login")
    public void successfulLogin() {
        // given
        User user = new User();
        user.setEmail("garagemanager@mechanic.com"); 
        user.setPassword("P@ssw0rd");
        user.login(3);
    
        // when
        boolean result = user.getLoginSuccessCheck();
        
        // then
        assertEquals(true, result);
    }
}