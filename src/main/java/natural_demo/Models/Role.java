package natural_demo.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name="sys_roles")
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idrole")
    private Long idrole;
    @Column(name = "nome")
    private String nome;
}
