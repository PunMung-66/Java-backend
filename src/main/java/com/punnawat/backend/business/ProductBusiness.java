package com.punnawat.backend.business;

import com.punnawat.backend.expections.ProductException;
import org.springframework.stereotype.Service;

@Service
public class ProductBusiness {
    public String findProductById(String id) throws ProductException {
        if ( id == null){
            throw ProductException.idNull();
        }
        if (!id.equals("123")){
            throw ProductException.productNotFound();
        }
        return "We found it";
    }
}
