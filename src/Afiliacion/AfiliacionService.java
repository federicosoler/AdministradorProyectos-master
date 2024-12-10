package Afiliacion;

import Utilidades.GenericService;

public class AfiliacionService extends GenericService<Afiliacion> {

	public AfiliacionService() {
		super(new AfiliacionH2Impl());
	}
}
