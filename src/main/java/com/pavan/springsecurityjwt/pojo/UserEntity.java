package com.pavan.springsecurityjwt.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_name", nullable = false)
	private String username;

	@Column
	private String password;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column
	private Long mobile;

	@JsonManagedReference // helps avoid circular dependency in bidirectional mapping 
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role_details", 
				joinColumns = { @JoinColumn(name ="user_id") }, 
				inverseJoinColumns = { @JoinColumn(name ="role_id") })
	private Set<RoleEntity> roles = new HashSet<>();

}
