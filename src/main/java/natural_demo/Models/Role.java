package natural_demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name="sys_roles")

public class Role {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idrole",updatable = false, nullable = false)
    private Long idrole;
    @Column(name = "nome")
    private String nome;
}
