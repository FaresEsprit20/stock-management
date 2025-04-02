import com.fares.stock.management.core.exception.EntityNotFoundException;
import com.fares.stock.management.core.exception.ErrorCodes;
import com.fares.stock.management.core.exception.InvalidEntityException;
import com.fares.stock.management.core.exception.InvalidOperationException;
import com.fares.stock.management.core.validators.SupplierOrderDtoValidator;
import com.fares.stock.management.domain.dto.supplier_dto.SupplierOrderDto;
import com.fares.stock.management.domain.dto.supplier_order_line.SupplierOrderLineDto;
import com.fares.stock.management.domain.entities.Product;
import com.fares.stock.management.domain.entities.Supplier;
import com.fares.stock.management.domain.entities.SupplierOrder;
import com.fares.stock.management.domain.entities.SupplierOrderLine;
import com.fares.stock.management.domain.entities.enums.OrderStatus;
import com.fares.stock.management.domain.repository.jpa.ProductRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderLineRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierOrderRepository;
import com.fares.stock.management.domain.repository.jpa.SupplierRepository;
import com.fares.stock.management.domain.services.SupplierOrderService;
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
public class SupplierOrderServiceImpl implements SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final SupplierOrderLineRepository supplierOrderLineRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StockMovementService stockMovementService;

    @Autowired
    public SupplierOrderServiceImpl(SupplierOrderRepository supplierOrderRepository, SupplierOrderLineRepository supplierOrderLineRepository, SupplierRepository supplierRepository, ProductRepository productRepository, StockMovementService stockMovementService) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.supplierOrderLineRepository = supplierOrderLineRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.stockMovementService = stockMovementService;
    }


    @Override
    public SupplierOrderDto save(SupplierOrderDto dto) {

        List<String> errors = SupplierOrderDtoValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Supplier Order validation failed: {}", errors);
            throw new InvalidEntityException("The supplier order is not valid", ErrorCodes.SUPPLIER_ORDER_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible to modify the order when it's already delivered", ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }

        Optional<Supplier> supplier = supplierRepository.findById(dto.getSupplier().getId());
        if (supplier.isEmpty()) {
            log.warn("Supplier with ID {} was not found in the DB", dto.getSupplier().getId());
            throw new EntityNotFoundException("No supplier with the ID" + dto.getSupplier().getId() + " has been found in the DB",
                    ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getSupplierOrderLines() != null) {
            dto.getSupplierOrderLines().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getProduct() != null) {
                    Optional<Product> article = productRepository.findById(ligCmdFrs.getProduct().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("The product with the ID " + ligCmdFrs.getProduct().getId() + " does not exist in the DB");
                    }
                } else {
                    articleErrors.add("Impossible to save the order with a NULL product ");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Product does not exist in the DB", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        dto.setOrderDate(Instant.now());
        SupplierOrder savedCmdFrs = supplierOrderRepository.save(SupplierOrderDto.toEntity(dto));

        if (dto.getSupplierOrderLines() != null) {
            dto.getSupplierOrderLines().forEach(ligCmdFrs -> {
                SupplierOrderLine supplierOrderLine = SupplierOrderLineDto.toEntity(ligCmdFrs);
                supplierOrderLine.setSupplierOrder(savedCmdFrs);
                supplierOrderLine.setCompanyId(savedCmdFrs.getIdEnterprise());
                SupplierOrderLine saveLine = supplierOrderLineRepository.save(supplierOrderLine);

                effectuerEntree(saveLine);
            });
        }

        return SupplierOrderDto.fromEntity(savedCmdFrs);
    }

    @Override
    public SupplierOrderDto findById(Integer id) {
        if (id == null) {
            log.error("Supplier Order ID is NULL");
            return null;
        }
        return supplierOrderRepository.findById(id)
                .map(SupplierOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No supplier order has been found with the ID " + id, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public SupplierOrderDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Supplier CODE is NULL");
            return null;
        }
        return supplierOrderRepository.findSupplierOrderByCode(code)
                .map(SupplierOrderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No supplier order has been found with the  CODE " + code, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND
                ));
    }

    @Override
    public List<SupplierOrderDto> findAll() {
        return supplierOrderRepository.findAll().stream()
                .map(SupplierOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierOrderLineDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer orderId) {
        return supplierOrderLineRepository.findAllBySupplierOrderId(orderId).stream()
                .map(SupplierOrderLineDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Supplier code  ID is NULL");
            return;
        }
        List<SupplierOrderLine> supplierOrderLines = supplierOrderLineRepository.findAllBySupplierOrderId(id);
        if (!supplierOrderLines.isEmpty()) {
            throw new InvalidOperationException("Impossible to delete a supplier order that is already used",
                    ErrorCodes.SUPPLIER_ORDER_ALREADY_IN_USE);
        }
        supplierOrderLineRepository.deleteById(id);
    }

    @Override
    public SupplierOrderDto updateOrderDto(Integer orderId, OrderStatus orderStatus) {
        checkIdCommande(orderId);
        if (!StringUtils.hasLength(String.valueOf(orderStatus))) {
            log.error("The order status is NULL");
            throw new InvalidOperationException("Impossible to modify the status of the order with a null status ",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        SupplierOrderDto supplierOrderDto = checkEtatCommande(orderId);
        supplierOrderDto.setOrderStatus(orderStatus);

       SupplierOrder savedSupplierOrder = supplierOrderRepository.save(SupplierOrderDto.toEntity(supplierOrderDto));
        if (supplierOrderDto.isCommandeLivree()) {
            updateMvtStk(orderId);
        }
        return SupplierOrderDto.fromEntity(savedSupplierOrder);
    }

    @Override
    public SupplierOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
        checkIdCommande(orderId);
        checkIdLigneCommande(orderLineId);

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            log.error("The ID of the order line is NULL");
            throw new InvalidOperationException("Impossible to modify the status of the order with a null ou ZERO quantity ",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }

        SupplierOrderDto supplierOrderDto = checkEtatCommande(orderId);
        Optional<SupplierOrderLine> ligneCommandeFournisseurOptional = findSupplierOrderLine(orderLineId);

        if(ligneCommandeFournisseurOptional.isEmpty()){
            log.error("The order line  is NULL");
            throw new EntityNotFoundException("Impossible to get the order line with Null value " ,
                    ErrorCodes.SUPPLIER_ORDER_NOT_FOUND);
        }

        SupplierOrderLine supplierOrderLine = ligneCommandeFournisseurOptional.get();
        supplierOrderLine.setQuantity(quantity);
        supplierOrderLineRepository.save(supplierOrderLine);

        return supplierOrderDto;
    }

    @Override
    public SupplierOrderDto updateSupplier(Integer orderId, Integer supplierId) {
        checkIdCommande(orderId);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        SupplierOrderDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.SUPPLIER_NOT_FOUND);
        }
        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return SupplierOrderDto.fromEntity(
                commandeFournisseurRepository.save(SupplierOrderDto.toEntity(commandeFournisseur))
        );
    }

    @Override
    public SupplierOrderDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        SupplierOrderDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<SupplierOrderLine> ligneCommandeFournisseur = findSupplierOrderLine(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        SupplierOrderLine ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    @Override
    public SupplierOrderDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        SupplierOrderDto commandeFournisseur = checkEtatCommande(idCommande);
        // Just to check the SupplierOrderLine and inform the fournisseur in case it is absent
        findSupplierOrderLine(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    private SupplierOrderDto checkEtatCommande(Integer idCommande) {
        SupplierOrderDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    private Optional<SupplierOrderLine> findSupplierOrderLine(Integer idLigneCommande) {
        Optional<SupplierOrderLine> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.SUPPLIER_ORDER_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.SUPPLIER_ORDER_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<SupplierOrderLine> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
    }

    private void effectuerEntree(SupplierOrderLine lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
    }
}
