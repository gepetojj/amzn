package application.amzn.exceptions;

import application.amzn.exceptions.primitives.NotFoundException;

public class SaleNotFoundException extends NotFoundException {
    public SaleNotFoundException(Long id) {
        super("A venda #" + id + " não foi encontrada.");
    }
}
