/*
 * Copyright 2019 biteytech@protonmail.com
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

import static java.util.Spliterator.DISTINCT;
import static java.util.Spliterator.SORTED;
import static tech.bitey.bufferstuff.BufferUtils.EMPTY_BUFFER;
import static tech.bitey.dataframe.DfPreconditions.checkElementIndex;
import static tech.bitey.dataframe.LongArrayPacker.LONG;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

final class NonNullLongColumn extends LongArrayColumn<Long, LongColumn, NonNullLongColumn> implements LongColumn {

	static final Map<Integer, NonNullLongColumn> EMPTY = new HashMap<>();
	static {
		EMPTY.computeIfAbsent(NONNULL_CHARACTERISTICS, c -> new NonNullLongColumn(EMPTY_BUFFER, 0, 0, c, false));
		EMPTY.computeIfAbsent(NONNULL_CHARACTERISTICS | SORTED,
				c -> new NonNullLongColumn(EMPTY_BUFFER, 0, 0, c, false));
		EMPTY.computeIfAbsent(NONNULL_CHARACTERISTICS | SORTED | DISTINCT,
				c -> new NonNullLongColumn(EMPTY_BUFFER, 0, 0, c, false));
	}

	static NonNullLongColumn empty(int characteristics) {
		return EMPTY.get(characteristics | NONNULL_CHARACTERISTICS);
	}

	NonNullLongColumn(ByteBuffer buffer, int offset, int size, int characteristics, boolean view) {
		super(buffer, LONG, offset, size, characteristics, view);
	}

	@Override
	NonNullLongColumn construct(ByteBuffer buffer, int offset, int size, int characteristics, boolean view) {
		return new NonNullLongColumn(buffer, offset, size, characteristics, view);
	}

	@Override
	public double min() {
		if (size == 0)
			return Double.NaN;
		else if (isSorted())
			return at(offset);

		long min = at(offset);

		for (int i = offset + 1; i <= lastIndex(); i++) {
			long x = at(i);
			if (x < min)
				min = x;
		}

		return min;
	}

	@Override
	public double max() {
		if (size == 0)
			return Double.NaN;
		else if (isSorted())
			return at(lastIndex());

		long max = at(offset);

		for (int i = offset + 1; i <= lastIndex(); i++) {
			long x = at(i);
			if (x > max)
				max = x;
		}

		return max;
	}

	@Override
	public double mean() {
		if (size == 0)
			return Double.NaN;

		long sum = 0;
		for (int i = offset; i <= lastIndex(); i++)
			sum += at(i);
		return sum / (double) size;
	}

	@Override
	public double stddev(boolean population) {
		if (size == 0 || (!population && size == 1))
			return Double.NaN;

		double μ = mean();

		double numer = 0;
		for (int i = offset; i <= lastIndex(); i++) {
			double d = at(i) - μ;
			numer += d * d;
		}

		return Math.sqrt(numer / (population ? size : size - 1));
	}

	@Override
	NonNullLongColumn empty() {
		return EMPTY.get(characteristics);
	}

	@Override
	public Comparator<Long> comparator() {
		return Long::compareTo;
	}

	@Override
	public ColumnType getType() {
		return ColumnType.LONG;
	}

	@Override
	public long getLong(int index) {
		checkElementIndex(index, size);
		return at(index + offset);
	}

	@Override
	boolean checkType(Object o) {
		return o instanceof Long;
	}
}
