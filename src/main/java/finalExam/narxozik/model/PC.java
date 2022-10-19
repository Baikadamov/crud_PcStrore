package finalExam.narxozik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="t_pc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "proc")
    private String proc;

    @Column(name = "video")
    private String video;

    @Column(name = "ozu")
    private String ozu;

    @Column(name = "memory")
    private String memory;

    @Column(name = "price")
    private double price;

    @ManyToOne
    private Bonus bonus;

}
