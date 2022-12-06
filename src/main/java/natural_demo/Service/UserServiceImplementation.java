package natural_demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import natural_demo.Models.Role;
import natural_demo.Models.User;
import natural_demo.Repo.RoleRepo;
import natural_demo.Repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
       User user = userRepo.findByNome(nome);
       if(user == null){
           log.error("User not found in DB");
           throw new UsernameNotFoundException("User nor found in DB");
       }else {
           log.info("User found in DB: {}", nome);
       }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       user.getRoles().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.getNome()));
       });
       return new org.springframework.security.core.userdetails.User(user.getNome(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getNome());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getNome());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String nome, String roleNome) {
        log.info("Adding role {} to user {}", roleNome, nome);
        User user = userRepo.findByNome(nome);
        Role role = roleRepo.findByNome(roleNome);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String nome) {
        log.info("Fetching user {}", nome);
        return userRepo.findByNome(nome);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll() ;
    }


}
