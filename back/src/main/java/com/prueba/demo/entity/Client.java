package com.prueba.demo.entity;

import com.prueba.demo.enums.Occupation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {

    @Id
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 20, message = "El documento no debe superar los 20 caracteres")
    private String document;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
    private String names;

    @Column(nullable = false, length = 70)
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 70, message = "Los apellidos no deben superar los 70 caracteres")
    private String lastNames;

    @Column(nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate bornDate;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    private String city;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    @Size(max = 150, message = "El correo electrónico no debe superar los 150 caracteres")
    private String email;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no debe superar los 20 caracteres")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "La ocupación es obligatoria")
    private Occupation occupation;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean viable = false;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    /**
     * Actualiza los datos del cliente actual con los valores de otro objeto {@link Client}.
     * <p>
     * Este método copia todos los campos relevantes desde el objeto {@code request}
     * hacia el objeto actual: nombres, apellidos, fecha de nacimiento, ciudad, correo,
     * teléfono, ocupación y viabilidad.
     *
     * @param request el objeto {@link Client} que contiene los nuevos datos a establecer.
     */
    public void updateFromRequest(Client request) {
        this.setNames(request.getNames());
        this.setLastNames(request.getLastNames());
        this.setBornDate(request.getBornDate());
        this.setCity(request.getCity());
        this.setEmail(request.getEmail());
        this.setPhone(request.getPhone());
        this.setOccupation(request.getOccupation());
        this.setViable(request.getViable());
    }

}
