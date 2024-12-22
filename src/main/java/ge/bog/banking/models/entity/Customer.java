package ge.bog.banking.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate values
    public  int id;

    public int customerId;

    public String firstName;

    public String lastName;

    public String email;
    @Column(name = "personal_id")
    public String personalId;

    public String address;
}
