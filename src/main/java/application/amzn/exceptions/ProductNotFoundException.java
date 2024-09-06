package application.amzn.exceptions;

import application.amzn.exceptions.primitives.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Long id) {
        super("O produto #" + id + " não foi encontrado.");
    }
}
