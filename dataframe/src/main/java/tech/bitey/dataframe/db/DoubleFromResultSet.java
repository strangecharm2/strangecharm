/*
 * Copyright 2020 biteytech@protonmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.bitey.dataframe.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import tech.bitey.dataframe.ColumnType;
import tech.bitey.dataframe.DoubleColumnBuilder;

/**
 * Logic for reading a {@code double} value from a {@link ResultSet} into a
 * {@link DoubleColumnBuilder}:
 * <ul>
 * <li>{@link #DOUBLE_FROM_DOUBLE}
 * </ul>
 * 
 * @author biteytech@protonmail.com
 */
public enum DoubleFromResultSet implements IFromResultSet<Double, DoubleColumnBuilder> {
	/**
	 * Reads an {@code double} from the {@code ResultSet} using
	 * {@link ResultSet#getDouble(int)}, and adds it to the builder using
	 * {@link DoubleColumnBuilder#add(double)}
	 */
	DOUBLE_FROM_DOUBLE {
		@Override
		public void get(ResultSet rs, int columnIndex, DoubleColumnBuilder builder) throws SQLException {

			final double d = rs.getDouble(columnIndex);

			if (rs.wasNull())
				builder.addNull();
			else
				builder.add(d);
		}
	};

	@Override
	public ColumnType<Double> getColumnType() {
		return ColumnType.DOUBLE;
	}
}