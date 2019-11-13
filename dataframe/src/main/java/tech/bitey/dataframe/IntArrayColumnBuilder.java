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
import static tech.bitey.bufferstuff.BufferUtils.isSorted;
import static tech.bitey.bufferstuff.BufferUtils.isSortedAndDistinct;
import static tech.bitey.dataframe.DfPreconditions.checkState;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

abstract class IntArrayColumnBuilder<E, C extends Column<E>, B extends IntArrayColumnBuilder<E, C, B>>
		extends SingleBufferColumnBuilder<E, IntBuffer, C, B> {

	private final IntArrayPacker<E> packer;

	IntArrayColumnBuilder(int characteristics, IntArrayPacker<E> packer) {
		super(characteristics);
		this.packer = packer;
	}

	@Override
	void addNonNull(E element) {
		ensureAdditionalCapacity(1);
		elements.put(packer.pack(element));
		size++;
	}

	@Override
	void checkCharacteristics() {
		if ((characteristics & DISTINCT) != 0) {
			checkState(isSortedAndDistinct(elements, 0, elements.position()),
					"column elements must be sorted and distinct");
		} else if ((characteristics & SORTED) != 0) {
			checkState(isSorted(elements, 0, elements.position()), "column elements must be sorted");
		}
	}

	@Override
	IntBuffer asBuffer(ByteBuffer buffer) {
		return buffer.asIntBuffer();
	}

	@Override
	int elementSize() {
		return 4;
	}
}
