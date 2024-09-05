package work.javiermantilla.finance.utils;

public class FinanceConstants {
	public static final String[] URLS_ADMITIDAS = { "/security/", "/status/health", "/swagger-ui/", "v3/api-docs", };

	public static final String APIKEY = "e3172f3a0208db5c9b0c955e4301f291912c70ff9658698f76d5e06c41856d00";

	// Token Structure
	public static final String TOKEN_IDUSER= "idUser";
	public static final String TOKEN_NAME = "name";
	
	
	public static final String RESPONSE_LOGIN = "Cookie necesaria para las peticiones del sistema.";
	
	public static final Long SECOND_TO_MILLIS = 1000l;
	public static final Long MINUTES_TO_SECONDS = 60l;
	public static final Long HOURS_TO_MINUTES = 60l;
	public static final Long DAYS_TO_HOURS = 24l;

	
	public static final Long TIME_JWT_HORAS = 12l;
	public static final Long TIME_LIFE_JWT = TIME_JWT_HORAS * HOURS_TO_MINUTES * MINUTES_TO_SECONDS
			* SECOND_TO_MILLIS;
	
	public static final String DOMAIN_LOCAL_COOKIE = "localhost";
	
	
	public static final String RESPONSE_CREATED = "Registro creado con éxito";
	public static final String TITTLE_CREATED = "Registro éxitoso";
	
	public static final String RESPONSE_UPDATE = "Actualización realizada con éxito";
	public static final String TITTLE_UPDATE = "Actualización éxitosa";
	
	public static final String RESPONSE_DELETE = "Eliminación realizada con éxito";
	public static final String TITTLE_DELETE = "Eliminación éxitosa";
	
	public static final String RESPONSE_FIND = "Consulta realizada con éxito";
	public static final String TITTLE_FIND = "Consulta éxitosa";
	
	public static final String MENSAJE_ERROR_VALIDACION_CAMPOS = "Error validación de campos";
	
}
