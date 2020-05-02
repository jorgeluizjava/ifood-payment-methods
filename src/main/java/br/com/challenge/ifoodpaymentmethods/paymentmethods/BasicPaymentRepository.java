package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BasicPaymentRepository<T, ID> extends Repository<T, ID> {

    <S extends T> Iterable<S> saveAll(Iterable<S> var1);
}
