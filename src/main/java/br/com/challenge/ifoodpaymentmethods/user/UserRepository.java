package br.com.challenge.ifoodpaymentmethods.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);

    Optional<User> findByLogin(String login);
}
