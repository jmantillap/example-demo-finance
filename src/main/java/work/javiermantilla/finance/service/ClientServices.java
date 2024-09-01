package work.javiermantilla.finance.service;

import java.util.List;

import work.javiermantilla.finance.dto.client.ClientDTO;

public interface ClientServices {
	
	ClientDTO createClient(ClientDTO client);
	ClientDTO updateClient(ClientDTO client);
	boolean deleteClient(Integer id);	
	List<ClientDTO> getListClients();	
}
