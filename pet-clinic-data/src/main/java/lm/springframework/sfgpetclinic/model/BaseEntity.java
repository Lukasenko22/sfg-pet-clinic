package lm.springframework.sfgpetclinic.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass // -> This object will not be created in DB
public class BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
