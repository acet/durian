/*
 * Original Guava code is copyright (C) 2015 The Guava Authors.
 * Modifications from Guava are copyright (C) 2016 DiffPlug.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.common.collect.testing.testers;

import static com.diffplug.common.collect.testing.Helpers.assertContentsInOrder;
import static com.diffplug.common.collect.testing.features.CollectionFeature.SUPPORTS_REMOVE;
import static com.diffplug.common.collect.testing.features.CollectionSize.ONE;
import static com.diffplug.common.collect.testing.features.CollectionSize.SEVERAL;
import static com.diffplug.common.collect.testing.features.CollectionSize.ZERO;

import java.util.Arrays;

import com.diffplug.common.annotations.GwtCompatible;
import com.diffplug.common.collect.testing.MinimalCollection;
import com.diffplug.common.collect.testing.features.CollectionFeature;
import com.diffplug.common.collect.testing.features.CollectionSize;

/**
 * A generic JUnit test which tests {@code retainAll} operations on a list.
 * Can't be invoked directly; please see
 * {@link com.diffplug.common.collect.testing.ListTestSuiteBuilder}.
 *
 * @author Chris Povirk
 */
@GwtCompatible
public class ListRetainAllTester<E> extends AbstractListTester<E> {
	@CollectionFeature.Require(SUPPORTS_REMOVE)
	@CollectionSize.Require(absent = {ZERO, ONE})
	public void testRetainAll_duplicatesKept() {
		E[] array = createSamplesArray();
		array[1] = e0();
		collection = getSubjectGenerator().create(array);
		assertFalse("containsDuplicates.retainAll(superset) should return false",
				collection.retainAll(MinimalCollection.of(createSamplesArray())));
		expectContents(array);
	}

	@SuppressWarnings("unchecked")
	@CollectionFeature.Require(SUPPORTS_REMOVE)
	@CollectionSize.Require(SEVERAL)
	public void testRetainAll_duplicatesRemoved() {
		E[] array = createSamplesArray();
		array[1] = e0();
		collection = getSubjectGenerator().create(array);
		assertTrue("containsDuplicates.retainAll(subset) should return true",
				collection.retainAll(MinimalCollection.of(e2())));
		expectContents(e2());
	}

	@SuppressWarnings("unchecked")
	@CollectionFeature.Require(SUPPORTS_REMOVE)
	@CollectionSize.Require(SEVERAL)
	public void testRetainAll_countIgnored() {
		resetContainer(
				getSubjectGenerator().create(e0(), e2(), e1(), e0()));
		assertTrue(getList().retainAll(Arrays.asList(e0(), e1())));
		assertContentsInOrder(getList(), e0(), e1(), e0());
	}
}