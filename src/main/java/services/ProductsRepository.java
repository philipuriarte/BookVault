package services;

import com.philipuriarte.BookVault.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Book, Integer>  {

}
