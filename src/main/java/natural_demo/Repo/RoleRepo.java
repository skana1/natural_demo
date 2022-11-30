package natural_demo.Repo;

import natural_demo.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByNome(String nome);
}
