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

import static java.nio.charset.StandardCharsets.UTF_8;

interface VarLenPacker<E> {

	final VarLenPacker<String> STRING = new VarLenPacker<String>() {
		@Override
		public byte[] pack(String value) {
			return value.getBytes(UTF_8);
		}

		@Override
		public String unpack(byte[] packed) {
			return new String(packed, UTF_8);
		}
	};
	
	byte[] pack(E value);
	E unpack(byte[] packed);
}