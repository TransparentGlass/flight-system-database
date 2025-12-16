package cc14;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cc14.Databases.PassengerDatabase;
import static cc14.Databases.PassengerDatabase.*;

public class passengerDB_test {
    
    @Test
    public void registerTest(){
        String username = "test";
        String password = "test";
        String fullname = "test";
        assert(PassengerDatabase.register(username, password, fullname) == true);
    }

    @Test
    public void nullregister(){
        String username = "rar1";
        String password = "rar1";
        String fullname = "rar1";
        assert(register(null, password, fullname) == false);
        assert(register("rar2", null, fullname) == false);
        assert(register("rar3", password, null) == false);
        assert(register("", password, fullname) == false);
    }

    @Test
    public void userExists(){
        assertTrue(isUserExist("default"));
    }

    @Test
    public void passwordCheck_test(){
        String username = "test";
        String password = "test";
        
        assert(passwordCheck(username, password) == 12);
        assert(passwordCheck("rar", "rar") != 12);
    }

    @Test
    public void test_getPassenger(){
        assert(getPassenger("rar1") == null);
        assert(getPassenger("rar").getUsername() == "rar");
    }

    
}
