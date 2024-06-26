package com.studiesjpa.jpa.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Author {

	@Id
	@GeneratedValue
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
