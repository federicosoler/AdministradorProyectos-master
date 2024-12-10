package Historial;

import Utilidades.GenericService;

public class HistorialService extends GenericService<Historial> {

	public HistorialService() {
		super(new HistorialH2Impl());
	}
}