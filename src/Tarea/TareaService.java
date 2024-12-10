package Tarea;

import Utilidades.GenericService;

public class TareaService extends GenericService<Tarea> {

	public TareaService() {
		super(new TareaH2Impl());
	}
}