package work.javiermantilla.finance.service;

import java.util.List;

import work.javiermantilla.finance.dto.client.ClientFullDTO;

public interface ClientServices {
	
	ClientFullDTO createClient(ClientFullDTO client);
	ClientFullDTO updateClient(ClientFullDTO client);
	boolean deleteClient(Integer id);	
	List<ClientFullDTO> getListClients();
}
