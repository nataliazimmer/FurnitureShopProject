package pl.coderslab.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.admin.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "phone_number"}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Size(min = 3, message = "Imię musi zawierać conajmniej 3 znaki")
    private String firstName;

    @Size(min = 3, message = "Nazwisko musi zawierać conajmniej 3 znaki")
    private String lastName;

    private String username;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String city;

    private String country;

    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

}