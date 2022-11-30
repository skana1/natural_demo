package natural_demo.Service;

import natural_demo.Models.Role;
import natural_demo.Models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String nome,String roleName);
    User getUser(String nome);
    List<User> getUsers();

}
