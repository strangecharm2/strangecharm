/*
 * Copyright 2021 biteytech@protonmail.com
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

package tech.bitey.dataframe;

public enum ColumnTypeCode {

	B, // Boolean
	DA, // Date
	DT, // DateTime
	TI, // Time
	D, // Double
	F, // Float
	I, // Int
	L, // Long
	T, // Short
	Y, // Byte
	S, // String
	BD, // Decimal
	UU, // UUID
	NS, // Normal String
	;

	ColumnType<?> getType() {
		switch (this) {
		case B:
			return ColumnType.BOOLEAN;
		case DA:
			return ColumnType.DATE;
		case DT:
			return ColumnType.DATETIME;
		case TI:
			return ColumnType.TIME;
		case D:
			return ColumnType.DOUBLE;
		case F:
			return ColumnType.FLOAT;
		case I:
			return ColumnType.INT;
		case L:
			return ColumnType.LONG;
		case T:
			return ColumnType.SHORT;
		case Y:
			return ColumnType.BYTE;
		case S:
			return ColumnType.STRING;
		case BD:
			return ColumnType.DECIMAL;
		case UU:
			return ColumnType.UUID;
		case NS:
			return ColumnType.NSTRING;
		}
		throw new IllegalStateException();
	}
}