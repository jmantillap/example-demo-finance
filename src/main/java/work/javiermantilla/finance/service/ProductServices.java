package work.javiermantilla.finance.service;


import work.javiermantilla.finance.dto.product.CuentaDTO;
import work.javiermantilla.finance.dto.product.ProductDTO;

public interface ProductServices {
	
	ProductDTO createProduct(ProductDTO productDTO);	
	ProductDTO inactivateProduct(CuentaDTO cuentaDTO);
	ProductDTO activateProduct(CuentaDTO cuentaDTO);
	ProductDTO calcelProduct(CuentaDTO cuentaDTO);
}
