package pl.coderslab.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.customer.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}