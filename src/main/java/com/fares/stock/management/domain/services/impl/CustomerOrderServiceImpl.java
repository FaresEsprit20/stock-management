package com.fares.stock.management.domain.services.impl;

import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.CustomerOrderValidator;
import com.fares.stock.management.core.validators.ProductDtoValidator;
import com.fares.stock.management.domain.dto.customer.CustomerDto;
import com.fares.stock.management.domain.dto.customer_order.CustomerOrderDto;
import com.fares.stock.management.domain.dto.customer_order_line.CustomerOrderLineDto;
import com.fares.stock.management.domain.dto.product.ProductDto;
import com.fares.stock.management.domain.dto.stock_movement.StockMovementDto;
import com.fares.stock.management.domain.entities.Customer;
import com.fares.stock.management.domain.entities.CustomerOrder;
import com.fares.stock.management.domain.entities.CustomerOrderLine;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.entities.enums.StockMvtSource;
import com.fares.stock.management.domain.entities.enums.StockMvtType;
import com.fares.stock.management.domain.repository.jpa.*;
import com.fares.stock.management.domain.services.CustomerOrderService;
import com.fares.stock.management.domain.services.StockMvtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerOrderLineRepository customerOrderLineRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockMvtService stockMovementService;

    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, CustomerOrderLineRepository customerOrderLineRepository, CustomerRepository customerRepository, ProductRepository productRepository, StockMvtService stockMovementService) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerOrderLineRepository = customerOrderLineRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockMovementService = stockMovementService;
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
        checkOrderId(orderId);
        if (!StringUtils.hasLength(String.valueOf(orderStatus))) {
            log.error("The customer's order status is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the customer with a null state ",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
        CustomerOrderDto customerOrderDto = checkOrderState(orderId);
        customerOrderDto.setOrderStatus(orderStatus);

        CustomerOrder savedCmdClt = customerOrderRepository.save(CustomerOrderDto.toEntity(customerOrderDto));
        if (customerOrderDto.isCommandeLivree()) {
            updateMvtStk(orderId);
        }

        return CustomerOrderDto.fromEntity(savedCmdClt);
    }

    @Override
    public CustomerOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        checkOrderId(orderId);
        checkOrderLineId(orderLineId);

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            log.error("The ID of order line is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order with a null quantity ou ZERO",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }

        CustomerOrderDto customerOrderDto = checkOrderState(orderId);
        Optional<CustomerOrderLine> customerOrderLineOptional = findCustomerOrderLine(orderLineId);

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
    public CustomerOrderDto updateCustomer(Integer orderId, Integer customerId) {
        checkOrderId(customerId);
        if (customerId == null) {
            log.error("The id of the customer is NULL");
            throw new InvalidOperationException("Impossible dto modify the state of the order with a Null customer ID ",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
        CustomerOrderDto customerOrderDto = checkOrderState(orderId);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No customer has been found with the ID " + customerId, ErrorCodes.CLIENT_NOT_FOUND);
        }
        customerOrderDto.setCustomer(CustomerDto.fromEntity(customerOptional.get()));

        return CustomerOrderDto.fromEntity(
                customerOrderRepository.save(CustomerOrderDto.toEntity(customerOrderDto))
        );
    }

    @Override
    public CustomerOrderDto updateProduct(Integer orderId, Integer orderLineId, Integer productId) {
        checkOrderId(orderId);
        checkOrderLineId(orderLineId);
        checkProductId(productId, "nouvel");

        CustomerOrderDto customerOrderDto = checkOrderState(orderId);

        Optional<CustomerOrderLine> customerOrderLine = findCustomerOrderLine(orderLineId);

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No product ID has been found with the ID " + productId, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ProductDtoValidator.validate(ProductDto.fromEntity(productOptional.get()));

        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        if (customerOrderLine.isEmpty()) {
            throw new EntityNotFoundException(
                    "Order line is null , could not complete  ID " + orderLineId, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        CustomerOrderLine customerOrderLineSaved = customerOrderLine.get();
        customerOrderLineSaved.setProduct(productOptional.get());
        customerOrderLineRepository.save(customerOrderLineSaved);

        return customerOrderDto;
    }

    @Override
    public CustomerOrderDto deleteCustomerOrderLine(Integer orderId, Integer orderLineId) {
        checkOrderId(orderId);
        checkOrderLineId(orderLineId);

        CustomerOrderDto customerOrderDto = checkOrderState(orderId);
        // Just to check the Customer order line and inform the customer in case it is absent
        findCustomerOrderLine(orderLineId);
        customerOrderLineRepository.deleteById(orderLineId);

        return customerOrderDto;
    }

    private CustomerOrderDto checkOrderState(Integer orderId) {
        CustomerOrderDto customerOrderDto = findById(orderId);
        if (customerOrderDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible to modify the Order State when it's delivered ", ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
        return customerOrderDto;
    }

    private Optional<CustomerOrderLine> findCustomerOrderLine(Integer customerOrderLineId) {
        Optional<CustomerOrderLine> ligneCommandeClientOptional = customerOrderLineRepository.findById(customerOrderLineId);
        if (ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "No Customer Order Line has been found with the ID " + customerOrderLineId, ErrorCodes.CUSTOMER_ORDER_LINE_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void checkOrderId(Integer orderId) {
        if (orderId == null) {
            log.error("Customer Order ID is NULL");
            throw new InvalidOperationException("Impossible de modify the sate of the Order Id with a null",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkOrderLineId(Integer orderLineId) {
        if (orderLineId == null) {
            log.error("the ID of the order line is NULL");
            throw new InvalidOperationException("Impossible de modify the state of the order with an order line that is null",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkProductId(Integer productId, String msg) {
        if (productId == null) {
            log.error("The ID of " + msg + " is NULL");
            throw new InvalidOperationException("Impossible to modify the state of the order with an " + msg + " null product ID",
                    ErrorCodes.CUSTOMER_ORDER_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer orderId) {
        List<CustomerOrderLine> customerOrderLines = customerOrderLineRepository.findAllByCustomerOrderId(orderId);
        customerOrderLines.forEach(lig -> {
            effectuerSortie(lig);
        });
    }

    private void effectuerSortie(CustomerOrderLine lig) {
        StockMovementDto mvtStkDto = new StockMovementDto();
        mvtStkDto.setProduct(ProductDto.fromEntity(lig.getProduct()));
        mvtStkDto.setMovementDate(Instant.now());
        mvtStkDto.setStockMvtType(StockMvtType.OUT);
        mvtStkDto.setMovementSource(StockMvtSource.CLIENT_COMMAND);
        mvtStkDto.setQuantity(lig.getQuantity());
        mvtStkDto.setCompanyId(lig.getCompanyId());

        stockMovementService.operateOutStock(mvtStkDto);
    }
}
