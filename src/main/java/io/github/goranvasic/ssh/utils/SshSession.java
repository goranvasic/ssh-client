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

package io.github.goranvasic.ssh.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.github.goranvasic.ssh.credentials.SshCredentials;
import org.apache.commons.lang3.StringUtils;

public class SshSession {

    private final JSch jSch;

    public SshSession(JSch jSch) {
        this.jSch = jSch;
    }

    public Session getSession(final SshHost host, final SshCredentials credentials) throws JSchException {
        Session session = jSch.getSession(credentials.getUser(), host.getHost(), host.getPort());
        if (null != credentials.getKey()) {
            byte[] passphrase;
            if (StringUtils.isNotEmpty(credentials.getPass())) {
                passphrase = credentials.getPass()
                    .getBytes();
            } else {
                passphrase = null;
            }
            jSch.addIdentity(credentials.getUser(), credentials.getKey()
                    .getBytes(),
                null, passphrase);
        }
        if (StringUtils.isNotEmpty(credentials.getPass())) {
            session.setPassword(credentials.getPass());
        }
        session.setConfig(credentials.getConfig());
        session.connect();
        return session;
    }
}
