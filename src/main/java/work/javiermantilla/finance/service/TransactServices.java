package work.javiermantilla.finance.service;

import java.util.List;

import work.javiermantilla.finance.dto.transaction.TransactDTO;
import work.javiermantilla.finance.dto.transaction.TransactFullDTO;
import work.javiermantilla.finance.dto.transaction.TransferDTO;
import work.javiermantilla.finance.dto.transaction.TransferFullDTO;

public interface TransactServices {
	TransactFullDTO consignmentMoney(TransactDTO transactDTO);
	
	TransactFullDTO withdrawMoney(TransactDTO transactDTO);
	
	TransferFullDTO transfer(TransferDTO transferDTO);
	
	List<TransactFullDTO> getListTransaction();
	
}
