package work.javiermantilla.finance.modules.transaction.service;

import java.util.List;

import work.javiermantilla.finance.modules.transaction.dto.TransactDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransactFullDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferDTO;
import work.javiermantilla.finance.modules.transaction.dto.TransferFullDTO;

public interface TransactServices {
	TransactFullDTO consignmentMoney(TransactDTO transactDTO);
	
	TransactFullDTO withdrawMoney(TransactDTO transactDTO);
	
	TransferFullDTO transfer(TransferDTO transferDTO);
	
	List<TransactFullDTO> getListTransaction();
	
}
