package work.javiermantilla.finance.modules.product.service;


import java.util.List;

import work.javiermantilla.finance.modules.product.dto.CuentaDTO;
import work.javiermantilla.finance.modules.product.dto.ProductDTO;

public interface ProductServices {
	
	ProductDTO createProduct(ProductDTO productDTO);	
	ProductDTO inactivateProduct(CuentaDTO cuentaDTO);
	ProductDTO activateProduct(CuentaDTO cuentaDTO);
	ProductDTO calcelProduct(CuentaDTO cuentaDTO);
	List<ProductDTO> getListProduct();
}
