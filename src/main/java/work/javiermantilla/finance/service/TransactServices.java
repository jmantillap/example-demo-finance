package work.javiermantilla.finance.service;

import work.javiermantilla.finance.dto.transaction.TransactDTO;
import work.javiermantilla.finance.dto.transaction.TransactFullDTO;

public interface TransactServices {
	TransactFullDTO consignmentMoney(TransactDTO transactDTO);
	
	TransactFullDTO withdrawMoney(TransactDTO transactDTO);
	
}
