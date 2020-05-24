package br.com.challenge.ifoodpaymentmethods.shared;

import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.Optional;

public class FindById {

    public static <T> T execute(Long id, CrudRepository<T, Long> repository) {

        Assert.notNull(id, "id não pode ser nulo.");

        Optional<T> entityOptional = repository.findById(id);
        if (!entityOptional.isPresent()) {
            throw new IllegalArgumentException("id: " + id + "  encontrado.");
        }

        return entityOptional.get();
    }

    public static <T> T execute(Long id, EntityManager manager, Class<T> tClass) {
        T result = manager.find(tClass, id);
        if (result == null) {
            throw new IllegalArgumentException("id: " + id + " for " + tClass.getSimpleName() + " not found.");
        }
        return result;
    }

}
