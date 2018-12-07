package com.aisy.b2c.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("message")
public class Message {
	
	@Value("#{messageConfig['MUI01']}")
	public String MUI01;
	
	@Value("#{messageConfig['MUI02']}")
	public String MUI02;
	
	@Value("#{messageConfig['MUI03']}")
	public String MUI03;
	
	@Value("#{messageConfig['MUI04']}")
	public String MUI04;

	@Value("#{messageConfig['MPI01']}")
	public String MPI01;
	
	@Value("#{messageConfig['MPI02']}")
	public String MPI02;
	
	@Value("#{messageConfig['MPI03']}")
	public String MPI03;
	
	@Value("#{messageConfig['MPI04']}")
	public String MPI04;
	
	@Value("#{messageConfig['MPE01']}")
	public String MPE01;
	
	@Value("#{messageConfig['MPE02']}")
	public String MPE02;
	
	@Value("#{messageConfig['MSI01']}")
	public String MSI01;
	
	@Value("#{messageConfig['MSI02']}")
	public String MSI02;
	
	@Value("#{messageConfig['MU01']}")
	public String MU01;
	
	@Value("#{messageConfig['MU02']}")
	public String MU02;
	
	@Value("#{messageConfig['MU03']}")
	public String MU03;
	
	@Value("#{messageConfig['MU04']}")
	public String MU04;
	
	@Value("#{messageConfig['MU05']}")
	public String MU05;
	
	@Value("#{messageConfig['MU06']}")
	public String MU06;
	
	@Value("#{messageConfig['MU07']}")
	public String MU07;
	
	@Value("#{messageConfig['MU08']}")
	public String MU08;
	
	@Value("#{messageConfig['MU09']}")
	public String MU09;
	
	@Value("#{messageConfig['MU10']}")
	public String MU10;
	
	@Value("#{messageConfig['MU11']}")
	public String MU11;
	
	@Value("#{messageConfig['MU12']}")
	public String MU12;
	
	@Value("#{messageConfig['MP01']}")
	public String MP01;
	
	@Value("#{messageConfig['MP02']}")
	public String MP02;
	
	@Value("#{messageConfig['MP03']}")
	public String MP03;
	
	@Value("#{messageConfig['MP04']}")
	public String MP04;
	
	@Value("#{messageConfig['MP13']}")
	public String MP13;
}
