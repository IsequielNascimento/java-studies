package com.studiesjpa.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Author {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "author_sequence"
			)
	@SequenceGenerator(
			name = "author_sequence",
			sequenceName = "author_sequence",
			allocationSize=1
			)
	private Integer Id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private int age;

	
}
