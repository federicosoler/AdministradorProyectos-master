package Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exceptions.DAOException;
import Exceptions.ObjetoDuplicadoException;

public abstract class GenericH2Impl<T> implements GenericDAO<T> {
	protected final String tableName;
	protected final String idColumnName;

	protected GenericH2Impl(String tableName, String idColumnName) {
		this.tableName = tableName;
		this.idColumnName = idColumnName;
	}

	@Override
	public void create(T entity) throws ObjetoDuplicadoException, DAOException {
		String sql = buildInsertSQL(entity);
		try {
			DBUtils.ejecutarSQL(sql);
		} catch (SQLException e) {
			if (e.getMessage().contains("duplicate")) {
				throw new ObjetoDuplicadoException("Ya existe una entidad con el mismo ID");
			}
			throw new DAOException("Error al crear la entidad", e);
		}
	}

	@Override
	public T read(int id) throws DAOException {
		String sql = String.format("SELECT * FROM %s WHERE %s = %d", tableName, idColumnName, id);
		ResultSet rs = null;
		try {
			rs = DBUtils.ejecutarSQL(sql);
			if (rs != null && rs.next()) {
				return mapResultSetToEntity(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DAOException("Error al leer la entidad", e);
		} finally {
			DBUtils.cerrarRecursos(rs);
		}
	}

	@Override
	public List<T> readAll() throws DAOException {
		String sql = String.format("SELECT * FROM %s", tableName);
		List<T> entities = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = DBUtils.ejecutarSQL(sql);
			if (rs != null) {
				while (rs.next()) {
					entities.add(mapResultSetToEntity(rs));
				}
			}
			return entities;
		} catch (SQLException e) {
			throw new DAOException("Error al leer las entidades", e);
		} finally {
			DBUtils.cerrarRecursos(rs);
		}
	}

	@Override
	public void update(T entity) throws DAOException {
		String sql = buildUpdateSQL(entity);
		try {
			DBUtils.ejecutarSQL(sql);
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar la entidad", e);
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		String sql = String.format("DELETE FROM %s WHERE %s = %d", tableName, idColumnName, id);
		try {
			DBUtils.ejecutarSQL(sql);
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar la entidad", e);
		}
	}

	// Métodos abstractos que deben ser implementados por las clases concretas
	protected abstract String buildInsertSQL(T entity);

	protected abstract String buildUpdateSQL(T entity);

	protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException, DAOException;
}
