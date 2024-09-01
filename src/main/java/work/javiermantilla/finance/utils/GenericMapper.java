package work.javiermantilla.finance.utils;

import java.util.List;


import org.modelmapper.ModelMapper;

public class GenericMapper {

	private static final ModelMapper modelMapper = new ModelMapper();

	private GenericMapper() {
		
	}

	public static <S, D> D map(S source, Class<D> destinationClass) {
		return modelMapper.map(source, destinationClass);
	}
	
	 public static <S, D> List<D> mapList(List<S> sourceList, Class<D> destinationClass) {
	        return sourceList.stream()
	                .map(source -> modelMapper.map(source, destinationClass))
	                .toList();
	    }

}
