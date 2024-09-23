package work.javiermantilla.finance.cross.exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class ArgumentNotValidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773902223910591596L;
	
	private final List<FieldError> listFieldErrors;
	
	public ArgumentNotValidException(List<FieldError> listFieldErrors, String message) {
		super(message);
		this.listFieldErrors = listFieldErrors;
	}
	
	
	public ArgumentNotValidException(List<FieldError> listFieldErrors, String message, Throwable throwable) {
		super(message,throwable);
		this.listFieldErrors = listFieldErrors;
	}


	public List<FieldError> getListFieldErrors() {
		return listFieldErrors;
	}
	
	

}
