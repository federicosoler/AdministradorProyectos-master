package Reporte;

import Utilidades.GenericService;

public class ReporteService extends GenericService<Reporte> {

	public ReporteService() {
		super(new ReporteH2Impl());
	}
}