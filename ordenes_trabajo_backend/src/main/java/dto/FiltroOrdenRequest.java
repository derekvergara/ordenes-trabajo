package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FiltroOrdenRequest {

    private String cliente;
    private String equipo;
    private String estado;
    private String tecnicoasigando;
    private LocalDate fechaingresodesde;
    private LocalDate fechaingresohasta;
    private LocalDate fechasalidadesde;
    private LocalDate fechasalidahasta;
    private boolean activo = true;
}
