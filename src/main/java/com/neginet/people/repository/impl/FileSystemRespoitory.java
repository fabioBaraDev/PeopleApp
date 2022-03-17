package com.neginet.people.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.neginet.people.repository.Repository;


public class FileSystemRespoitory implements Repository {
	
	private String fileName;
	
	public FileSystemRespoitory(String absolutePath) {
		this.fileName = absolutePath;
	}

	public List<String> getNames() {

		List<String> response = new ArrayList<String>();
		Path path;
		try {   
		    
			path = Paths.get(fileName);
			response = Files.lines(path).collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return response;
	}

}
