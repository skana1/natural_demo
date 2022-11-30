package natural_demo.Repo;

import natural_demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByNome(String nome);
}
