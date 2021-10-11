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

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

import tech.bitey.bufferstuff.BufferBitSet;

final class NullableLongColumn extends NullableLongArrayColumn<Long, LongColumn, NonNullLongColumn, NullableLongColumn>
		implements LongColumn {

	NullableLongColumn(NonNullColumn<Long, LongColumn, NonNullLongColumn> column, BufferBitSet nonNulls,
			INullCounts nullCounts, int offset, int size) {
		super((NonNullLongColumn) column, nonNulls, nullCounts, offset, size);
	}

	@Override
	public long getLong(int index) {
		checkGetPrimitive(index);
		return column.getLong(nonNullIndex(index + offset));
	}

	@Override
	public LongStream longStream() {
		return subColumn.longStream();
	}

	@Override
	public LongColumn cleanLong(LongPredicate predicate) {

		BufferBitSet cleanNonNulls = new BufferBitSet();
		LongColumn cleaned = subColumn.cleanLong(predicate, cleanNonNulls);

		if (cleaned == subColumn)
			return this;
		else if (cleaned.isEmpty())
			return construct((NonNullLongColumn) cleaned, cleanNonNulls, size);

		NullableLongColumn nullableCleaned = (NullableLongColumn) cleaned;

		BufferBitSet nonNulls = new BufferBitSet();
		for (int i = offset, j = 0; i <= lastIndex(); i++)
			if (this.nonNulls.get(i) && nullableCleaned.nonNulls.get(j++))
				nonNulls.set(i - offset);

		return construct(nullableCleaned.column, nonNulls, size);
	}
}