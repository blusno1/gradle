/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.changedetection.state;

import org.gradle.caching.internal.BuildCacheHasher;

import java.util.Arrays;

/**
 * An immutable snapshot of the state of some value.
 */
public class DefaultValueSnapshot implements ValueSnapshot {
    private final byte[] serializedValue;

    public DefaultValueSnapshot(byte[] serializedValue) {
        this.serializedValue = serializedValue;
    }

    public byte[] getValue() {
        return serializedValue;
    }

    @Override
    public ValueSnapshot snapshot(Object value, ValueSnapshotter snapshotter) {
        ValueSnapshot snapshot = snapshotter.snapshot(value);
        if (snapshot instanceof DefaultValueSnapshot) {
            DefaultValueSnapshot valueSnapshot = (DefaultValueSnapshot) snapshot;
            if (Arrays.equals(this.serializedValue, valueSnapshot.serializedValue)) {
                return this;
            }
        }
        return snapshot;
    }

    @Override
    public void appendToHasher(BuildCacheHasher hasher) {
        hasher.putBytes(serializedValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        DefaultValueSnapshot other = (DefaultValueSnapshot) obj;
        return Arrays.equals(serializedValue, other.serializedValue);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(serializedValue);
    }
}