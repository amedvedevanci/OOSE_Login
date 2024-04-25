import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {
    
@Test
public void noAccountFound() {
    // given
    User user = new User();
    user.setEmail("heber.testingstuff@test.com"); 
    user.setPassword("test123"); 
    var input = 0;

    // when
    var result = user.login(input);

    // then
    assertEquals("No account found", result);
}
}