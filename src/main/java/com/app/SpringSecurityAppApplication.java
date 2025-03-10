package com.app;

import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}

	// La siguiente funcion es para poblar la base con lo minimo indispensable
	// cuando la base  de datos de todo el proyecto esta vacia.
	// Tambien se puede iptar por un .sql con los inserts a la base de datos.
	/*
	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {
			// creacion de permisos
			PermissionEntity createPermission = PermissionEntity.builder().name("CREATE").build();
			PermissionEntity readPermission = PermissionEntity.builder().name("READ").build();
			PermissionEntity updatePermission = PermissionEntity.builder().name("UPDATE").build();
			PermissionEntity deletePermission = PermissionEntity.builder().name("DELETE").build();
			PermissionEntity refactorPermission = PermissionEntity.builder().name("REFACTOR").build();

			// creacion de los roles con los permisos
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission,readPermission,updatePermission,deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissions(Set.of(createPermission,readPermission,updatePermission))
					.build();

			RoleEntity roleDev = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissions(Set.of(createPermission,readPermission,updatePermission,deletePermission,refactorPermission))
					.build();

			RoleEntity roleInv = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissions(Set.of(readPermission))
					.build();

			// creaciond e los usuarios minimos indispensables del cual partira nuestra aplicacion


			UserEntity masterUser = UserEntity.builder()
					.username("master")
					.password(new BCryptPasswordEncoder().encode("secret"))
					.isEnable(true)
					.accountNoExpired(true)
					.accountNotLocked(true)
					.credentialNoExpired(true)
					// le asignamos todos rolesque se creen en la base de datos con el ALL cacade ya configurado
					.roles(Set.of(roleAdmin,roleUser,roleDev,roleInv))
					.build();


			userRepository.save(masterUser);
		};
	}
*/


}
