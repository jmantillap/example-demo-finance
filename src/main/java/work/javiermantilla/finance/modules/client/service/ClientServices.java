package work.javiermantilla.finance.modules.client.service;

import java.util.List;

import work.javiermantilla.finance.modules.client.dto.ClientDTO;

public interface ClientServices {
	
	ClientDTO createClient(ClientDTO client);
	ClientDTO updateClient(ClientDTO client);
	boolean deleteClient(Integer id);	
	List<ClientDTO> getListClients();	
}
