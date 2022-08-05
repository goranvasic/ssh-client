/*******************************************************************************
 * Copyright 2022 Goran Vasic
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
 ******************************************************************************/

package io.github.goranvasic.ssh.credentials;

import io.github.goranvasic.ssh.utils.Strings;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SshKey {

    @Getter
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Getter
    private final File key;

    @Getter
    private final String content;

    @Getter
    private final byte[] bytes;

    @Getter
    private final String pass;

    public SshKey(final File key) {
        this.key = Objects.requireNonNull(key);
        this.content = getContent(key);
        this.bytes = content.getBytes();
        this.pass = null;
    }

    public SshKey(final File key, final String pass) {
        this.key = Objects.requireNonNull(key);
        this.content = getContent(key);
        this.bytes = content.getBytes();
        this.pass = pass;
    }

    public SshKey(final String key) {
        this.key = null;
        this.content = getContent(key);
        this.bytes = content.getBytes();
        this.pass = null;
    }

    public SshKey(final String key, final String pass) {
        this.key = null;
        this.content = getContent(key);
        this.bytes = content.getBytes();
        this.pass = pass;
    }

    private String getContent(final File key) {
        try {
            return FileUtils.readFileToString(key, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(Strings.CANNOT_READ_SSH_KEY_CONTENT, e);
        }
        return "";
    }

    private String getContent(final String key) {
        return StringUtils.isNotEmpty(key) ? key : Strings.EMPTY;
    }
}
