package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.Invoice;
import io.dborrego.domain.InvoiceRepository;

@ApplicationScoped
public class InvoiceService {

    @Inject
    InvoiceRepository invoiceRepository;

    public List<Invoice> listAll() {
        return invoiceRepository.listAll();
    }

    public Invoice findById(final Long idInvoice) {
        return invoiceRepository.findById(idInvoice);
    }

    @Transactional
    public Invoice saveOrUpdate(final Invoice inv) {
        final Invoice invoice = Optional.ofNullable(inv).map(Invoice::getId).map(id -> invoiceRepository.findById(id))
                .orElse(new Invoice());
        invoice.setPlate(inv.getPlate());
        invoice.setInvoiceDate(inv.getInvoiceDate());
        invoice.setPaymentDate(inv.getPaymentDate());
        invoice.setAmount(inv.getAmount());
        invoiceRepository.persist(invoice);
        return invoice;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id).map(idInvoice -> invoiceRepository.deleteById(idInvoice)).orElse(false);
    }

}
