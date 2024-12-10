package Utilidades;

import java.util.List;
import Exceptions.DAOException;
import Exceptions.ObjetoDuplicadoException;

public interface GenericDAO<T> {
	void create(T entity) throws ObjetoDuplicadoException, DAOException;

	T read(int id) throws DAOException;

	List<T> readAll() throws DAOException;

	void update(T entity) throws DAOException;

	void delete(int id) throws DAOException;
}
