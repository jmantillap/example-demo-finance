package work.javiermantilla.finance.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GeneratorNumberAccount {

	private Random random = new Random();
	
	public String getNumber(ETipoProducto tipo) {				 
		StringBuilder numero = new StringBuilder();		
		for (int i = 0; i < 10; i++) {
			int digito = random.nextInt(10); 
			numero.append(digito);
		}		
		String numeroAleatorio = numero.toString();		
		return tipo.getNumero().concat(numeroAleatorio); 
		
	}
}
