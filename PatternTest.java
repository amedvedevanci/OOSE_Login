import java.util.regex.*;

public class PatternTest {
    // https://datatracker.ietf.org/doc/html/rfc3696#section-3
    // https://www.asciitable.com/ 
    static Pattern emailPattern = Pattern.compile("^[^\\.](?!.+@+@)(?!..+\\.\\.)[(#-\\&)(\\--9)(A-Z)(\\^-~)+!\\'=\\s](.+)[^\\.]@[^\\-](?!..+\\-\\-)(?=[\\w])([\\w.-]+\\.)+[a-z]{2,6}+$", Pattern.CASE_INSENSITIVE);
    static String[] emails = {
        "someone@example.com", //true
        "someone@examp-le.co.uk", //true
        "someone@examplecom", //false
        "test", //false
        "someone@@flexinbox.vo", //false
        ".someone@example.com", //false
        "someone.@example.com",
        "someone@-example.com",
        "someone@example..com",
        "someone@example.rewgjiore",
        "someone@example.h2o",
        "\"Abc@def\"@example.com",
        "\"Fred Bloggs\"@example.com",
        "user+mailbox@example.com",
        "customer/department=shipping@example.com",
        "$A12345@example.com",
        "!def!xyz%abc@example.com",
        "_somename@example.com"
    };

    static Pattern passwordPattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!-/:-@+_])[a-zA-Z0-9!-/:-@+_]{8,64}$");
    static String password="";

    public static void main(String[] args) {
        for(int i=0;i<emails.length;i++){
            Matcher emailMatcher = emailPattern.matcher(emails[i]);
            System.out.print(emails[i]+" ");
            System.out.println(emailMatcher.matches());
        }
    }
    

}
