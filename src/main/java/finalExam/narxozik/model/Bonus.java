package finalExam.narxozik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name ="t_bonus")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bonus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "pic" , columnDefinition = "TEXT")
    private String pic;


}
