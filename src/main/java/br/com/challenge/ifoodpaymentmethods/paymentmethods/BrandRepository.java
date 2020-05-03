package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.repository.Repository;

public interface BrandRepository extends Repository<Brand, Long> {

    Iterable<Brand> saveAll(Iterable<Brand> var1);

}
