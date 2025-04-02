package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.CustomerOrderValidator;
import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.entities.Customer;
import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.entities.CustomerOrderLine;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.repository.jpa.*;
import com.fares.stock.management.domain.services.CustomerOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderLineRepository customerOrderLineRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, CustomerOrderLineRepository customerOrderLineRepository, CustomerRepository customerRepository, ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerOrderLineRepository = customerOrderLineRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }


    @Override
    public CustomerOrderDto save(CustomerOrderDto customerOrderDto) {

        List<String> errors = CustomerOrderValidator.validate(customerOrderDto);

        if (!errors.isEmpty()) {
            log.error("Customer order is not valid {}", customerOrderDto);
            throw new InvalidEntityException(" Customer Order is not valid ", ErrorCodes.CUSTOMER_ORDER_NOT_VALID, errors);
        }

        if (customerOrderDto.getId() != null && customerOrderDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible to modify the order after it's finished and delivered ", ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }

        Optional<Customer> customer = customerRepository.findById(customerOrderDto.getCustomer().getId());
        if (customer.isEmpty()) {
            log.warn("Customer with ID {} was not found in the DB", customerOrderDto.getCustomer().getId());
            throw new EntityNotFoundException("No CustomerID" + customerOrderDto.getCustomer().getId() + " has been found in the DB ",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> productErrors = new ArrayList<>();

        if (customerOrderDto.getOrderLines() != null) {
            customerOrderDto.getOrderLines().forEach(ligCmdClt -> {
                if (ligCmdClt.getProduct() != null) {
                    Optional<Product> product = productRepository.findById(ligCmdClt.getProduct().getId());
                    if (product.isEmpty()) {
                        productErrors.add("The product with the ID " + ligCmdClt.getProduct().getId() + " does not exist in the DB");
                    }
                } else {
                    productErrors.add("Impossible to save an order with a NULL Product");
                }
            });
        }

        if (!productErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Product does not exist in DB ", ErrorCodes.ARTICLE_NOT_FOUND, productErrors);
        }
        customerOrderDto.setOrderDate(Instant.now());
        CustomerOrder savedCmdClt = customerOrderRepository.save(CustomerOrderDto.toEntity(customerOrderDto));

        if (customerOrderDto.getOrderLines() != null) {
            customerOrderDto.getOrderLines().forEach(ligCmdClt -> {
                CustomerOrderLine cltOrderLineEntity = CustomerOrderLineDto.toEntity(ligCmdClt);
                cltOrderLineEntity.setCustomerOrder(savedCmdClt);
                cltOrderLineEntity.setCompanyId(ligCmdClt.getCompanyId());
                CustomerOrderLine savedOrderLine = customerOrderLineRepository.save(cltOrderLineEntity);

                effectuerSortie(savedOrderLine);
            });
        }

        return CustomerOrderDto.fromEntity(savedCmdClt);
    }

    @Override
    public CustomerOrderDto findById(Integer customerOrderId) {
        if (customerOrderId== null) {
            log.error("Customer Order ID is NULL");
            return null;
        }
        return customerOrderRepository.findById(customerOrderId)
                .map(CustomerOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No Customer Order has been found with the ID = " + customerOrderId, ErrorCodes.CUSTOMER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public CustomerOrderDto findByCode(String customerOrderCode) {
        if (!StringUtils.hasLength(customerOrderCode)) {
            log.error("Customer Order CODE is NULL");
            return null;
        }
        return customerOrderRepository.findCustomerOrderByCode(customerOrderCode)
                .map(CustomerOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No customer order has been found with  CODE = " + customerOrderCode, ErrorCodes.CUSTOMER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public List<CustomerOrderDto> findAll() {
        return customerOrderRepository.findAll().stream()
                .map(CustomerOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer customerOrderId) {
        if (customerOrderId == null) {
            log.error("Customer order ID is NULL");
            return;
        }
        List<CustomerOrderLine> customerOrderLines =
                customerOrderLineRepository.findAllByCustomerOrderId(customerOrderId);
        if (!customerOrderLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a customer order that is already in use",
                    ErrorCodes.CUSTOMER_ORDER_ALREADY_IN_USE);
        }
        customerOrderRepository.deleteById(customerOrderId);
    }

    @Override
    public List<CustomerOrderLineDto> findAllCustomerOrderLinesByCustomerOrderId(Integer orderId) {
        return customerOrderLineRepository.findAllByCustomerOrderId(orderId).stream()
                .map(CustomerOrderLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
        checkIdCommande(orderId);
        if (!StringUtils.hasLength(String.valueOf(orderStatus))) {
            log.error("The customer's order status is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the customer with a null state ",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
        CustomerOrderDto customerOrderDto = checkEtatCommande(orderId);
        customerOrderDto.setOrderStatus(orderStatus);

        CustomerOrder savedCmdClt = customerOrderRepository.save(CustomerOrderDto.toEntity(customerOrderDto));
        if (customerOrderDto.isCommandeLivree()) {
            updateMvtStk(orderId);
        }

        return CustomerOrderDto.fromEntity(savedCmdClt);
    }

    @Override
    public CustomerOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        checkIdCommande(orderId);
        checkIdLigneCommande(orderLineId);

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            log.error("The ID of order line is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order with a null quantity ou ZERO",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }

        CustomerOrderDto customerOrderDto = checkEtatCommande(orderId);
        Optional<CustomerOrderLine> customerOrderLineOptional = findLigneCommandeClient(orderLineId);

        if(customerOrderLineOptional.isEmpty()) {
            log.error("The customer Order Line is NULL");
            throw new EntityNotFoundException("The customer Order Line is null or not found ",
                    ErrorCodes.CUSTOMER_ORDER_NOT_FOUND);
        }
            CustomerOrderLine customerOrderLine = customerOrderLineOptional.get();
            customerOrderLine.setQuantity(quantity);
            customerOrderLineRepository.save(customerOrderLine);

        return customerOrderDto;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null) {
            log.error("L'ID du client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun client n'a ete trouve avec l'ID " + idClient, ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
                commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeClient and inform the client in case it is absent
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande client n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            effectuerSortie(lig);
        });
    }

    private void effectuerSortie(LigneCommandeClient lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}
