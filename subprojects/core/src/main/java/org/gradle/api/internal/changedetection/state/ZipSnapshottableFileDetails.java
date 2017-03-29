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

import com.google.common.hash.HashCode;
import org.gradle.api.file.RelativePath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import static org.gradle.internal.nativeintegration.filesystem.FileType.RegularFile;

class ZipSnapshottableFileDetails extends DefaultFileDetails implements SnapshottableFileDetails {
    private final byte[] contents;

    ZipSnapshottableFileDetails(ZipEntry entry, byte[] contents, HashCode contentHash) {
        super(entry.getName(),
            new RelativePath(true, entry.getName()), RegularFile, false, new FileHashSnapshot(contentHash));
        this.contents = contents;
    }

    @Override
    public InputStream open() {
        return new ByteArrayInputStream(contents);
    }
}