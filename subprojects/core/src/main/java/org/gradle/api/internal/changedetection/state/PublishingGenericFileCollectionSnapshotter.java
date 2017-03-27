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

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import org.gradle.api.internal.cache.StringInterner;
import org.gradle.api.internal.file.collections.DirectoryFileTreeFactory;
import org.gradle.api.internal.hash.FileHasher;
import org.gradle.internal.nativeintegration.filesystem.FileSystem;

public class PublishingGenericFileCollectionSnapshotter extends PublishingFileCollectionSnapshotter implements GenericFileCollectionSnapshotter {
    public PublishingGenericFileCollectionSnapshotter(FileHasher hasher, StringInterner stringInterner, FileSystem fileSystem, DirectoryFileTreeFactory directoryFileTreeFactory, FileSystemMirror fileSystemMirror) {
        super(hasher, stringInterner, fileSystem, directoryFileTreeFactory, fileSystemMirror, new ObservableTransformer<FileDetails, FileDetails>() {

            @Override
            public ObservableSource<FileDetails> apply(Observable<FileDetails> upstream) {
                return upstream;
            }
        });
    }

    @Override
    public Class<? extends FileCollectionSnapshotter> getRegisteredType() {
        return GenericFileCollectionSnapshotter.class;
    }
}
