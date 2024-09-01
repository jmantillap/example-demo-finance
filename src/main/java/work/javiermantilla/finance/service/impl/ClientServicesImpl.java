package work.javiermantilla.finance.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.dto.client.ClientFullDTO;
import work.javiermantilla.finance.entity.ClientEntity;
import work.javiermantilla.finance.repository.ClientRepository;
import work.javiermantilla.finance.security.ContextSession;
import work.javiermantilla.finance.service.ClientServices;
import work.javiermantilla.finance.utils.GenericMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClientServicesImpl implements ClientServices {

	private final ClientRepository clientRepository;
	private final ContextSession session;

	@Override
	public ClientFullDTO createClient(ClientFullDTO client) {
		log.info("Cliente a guardar: {}", client);
		client.setId(null);
		boolean isExiste = this.clientRepository.findByTipoIdentificacionAndNumeroIdentificacion(
				client.getTipoIdentificacion(), client.getNumeroIdentificacion()).isEmpty();
		if (!isExiste) {
			log.error("El cliente con tipo documento: {}  y numero de documento: {} ya existe ",
					client.getTipoIdentificacion(), client.getNumeroIdentificacion());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya Existe");
		}
		ClientEntity clientEntity = GenericMapper.map(client, ClientEntity.class);
		clientEntity.setFechaCreacion(LocalDateTime.now());
		clientEntity.setFechaUltimaActualizacion(LocalDateTime.now());
		clientEntity = this.clientRepository.save(clientEntity);
		log.info("El usuario {}  creo el cliente : {}",session.getContextoDeSesionEnHilo().getName(), clientEntity);
		return GenericMapper.map(clientEntity, ClientFullDTO.class);
	}

	@Override
	public ClientFullDTO updateClient(ClientFullDTO client) {		
		ClientEntity clienteEntity= this.validationExist(client.getId());					
		List<ClientEntity> listClient = this.clientRepository.findByTipoIdentificacionAndNumeroIdentificacion(
				client.getTipoIdentificacion(), client.getNumeroIdentificacion());
		Long reg = listClient.stream().filter(c -> !c.getId().equals(client.getId())).count();
		if (reg > 0) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un cliente con numero y tipo de documento igual");
				
		clienteEntity.setNombre(client.getNombre());
		clienteEntity.setApellido(client.getApellido());
		clienteEntity.setFechaNacimiento(client.getFechaNacimiento());
		clienteEntity.setFechaUltimaActualizacion(LocalDateTime.now());
		clienteEntity.setTipoIdentificacion(client.getTipoIdentificacion());
		clienteEntity.setNumeroIdentificacion(client.getNumeroIdentificacion());
		clienteEntity.setEmail(client.getEmail());
		ClientEntity clientUpdate = this.clientRepository.save(clienteEntity);
		log.info("El usuario: {},  actualizo el cliente : {}",this.session.getContextoDeSesionEnHilo().getName() ,clientUpdate);
		return GenericMapper.map(clientUpdate, ClientFullDTO.class);
	}

	@Override
	public boolean deleteClient(Integer id) {		
		ClientEntity clientEntity= this.validationExist(id);
		// TODO Pendiente no poder eliminar un cliente que tenga cuentas.
		
		log.info("El usuario {} elimino el cliente con id: {}",session.getContextoDeSesionEnHilo().getName(), id);		
		this.clientRepository.delete(clientEntity);		
		return true;
	}
	
	@Override
	public List<ClientFullDTO> getListClients() {		
		List<ClientEntity> list= this.clientRepository.findAll();
		log.info("El usuario {}  consulto todos los clientes",session.getContextoDeSesionEnHilo().getName());
		return GenericMapper.mapList(list, ClientFullDTO.class);
	}
	
	private ClientEntity validationExist(Integer id) {
		if (id <= 0) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El identificador debe ser mayor a cero");
		
		Optional<ClientEntity> oClient = this.clientRepository.findById(id);
		if (oClient.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El Cliente No existe");
		
		return oClient.get();
	}

	

}
