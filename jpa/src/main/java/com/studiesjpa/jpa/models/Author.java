package com.studiesjpa.jpa.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TableGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Author {

	@Id
	@GeneratedValue
	/*(
			strategy = GenerationType.SEQUENCE,
			generator = "author_sequence"
			)
	/*@SequenceGenerator(
			name = "author_sequence",
			sequenceName = "author_sequence",
			allocationSize=1
			)*/
	/*@TableGenerator(
			
			name = "author_id_gen",
			table = "id_generator",
			pkColumnName = "id_name",
			valueColumnName = "id_value",
			allocationSize = 1
			
			)
	*/
	private Integer Id;
	
	@Column(
			name = "f_name",
			length = 50
			)
	private String firstName;
	
	private String lastName;
	
	@Column(
			
			unique = true,
			nullable = false
			
			)
	private String email;
	
	private int age;
	
	@Column(
			unique = true,
			nullable = false
			)
	private LocalDateTime createdAt;
	
	@Column(
			
			insertable = false
			
			)
	private LocalDateTime lastModif;
	

	
}
