package com.alinesno.infra.ops.logback.demo;

import org.springframework.boot.SpringProject;
import org.springframework.boot.autoconfigure.SpringBootProject;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootProject(exclude = DataSourceAutoConfiguration.class)
public class Project {

	public static void main(String[] args) {
		SpringProject.run(Project.class, args);
	}

}