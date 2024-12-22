package Utilidades;

import java.util.List;
import Exceptions.ServiceException;
import Exceptions.DAOException;
import Exceptions.ObjetoDuplicadoException;

// Clase abstracta aunque no tenga métodos abstractos para evitar que se instancie, es una plantilla nada más
public abstract class GenericService<T> {
	protected final GenericDAO<T> dao;

	protected GenericService(GenericDAO<T> dao) {
		this.dao = dao;
	}

	public void create(T entity) throws ServiceException {
		try {
			dao.create(entity);
		} catch (ObjetoDuplicadoException e) {
			throw new ServiceException("Error: la entidad ya existe", e);
		} catch (DAOException e) {
			throw new ServiceException("Error al crear la entidad", e);
		}
	}

	public T read(int id) throws ServiceException {
		try {
			return dao.read(id);
		} catch (DAOException e) {
			throw new ServiceException("Error al obtener la entidad", e);
		}
	}

	public List<T> readAll() throws ServiceException {
		try {
			return dao.readAll();
		} catch (DAOException e) {
			throw new ServiceException("Error al obtener las entidades", e);
		}
	}

	public void update(T entity) throws ServiceException {
		try {
			dao.update(entity);
		} catch (DAOException e) {
			throw new ServiceException("Error al actualizar la entidad", e);
		}
	}

	public void delete(int id) throws ServiceException {
		try {
			dao.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Error al eliminar la entidad", e);
		}
	}
}
