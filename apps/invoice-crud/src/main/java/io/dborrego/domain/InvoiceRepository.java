package io.dborrego.domain;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {

}
