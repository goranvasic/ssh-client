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

import java.util.Properties;

public final class SshCredentials {

    @Getter
    private final String user;

    @Getter
    private final SshKey key;

    @Getter
    private final String pass;

    @Getter
    private final Properties config;

    public SshCredentials(final String user, final String pass) {
        this.user = user;
        this.key = null;
        this.pass = pass;
        this.config = setConfig();
    }

    public SshCredentials(final String user, final SshKey key) {
        this.user = user;
        this.key = key;
        this.pass = key.getPass();
        this.config = setConfig();
    }

    private Properties setConfig() {
        Properties properties = new Properties();
        properties.setProperty(Strings.STRICT_HOST_KEY_CHECKING, Strings.NO);
        if (null != key) {
            properties.setProperty(Strings.PREFERRED_AUTHENTICATIONS, Strings.PUBLIC_KEY);
        } else {
            properties.setProperty(Strings.PREFERRED_AUTHENTICATIONS, Strings.PASSWORD);
        }
        return properties;
    }

}
